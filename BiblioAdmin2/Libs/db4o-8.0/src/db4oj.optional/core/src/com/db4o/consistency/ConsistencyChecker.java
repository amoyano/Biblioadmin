/* This file is part of the db4o object database http://www.db4o.com

Copyright (C) 2004 - 2010  Versant Corporation http://www.versant.com

db4o is free software; you can redistribute it and/or modify it under
the terms of version 3 of the GNU General Public License as published
by the Free Software Foundation.

db4o is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License along
with this program.  If not, see http://www.gnu.org/licenses/. */
package com.db4o.consistency;

import java.util.*;

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.btree.*;
import com.db4o.internal.classindex.*;
import com.db4o.internal.ids.*;
import com.db4o.internal.slots.*;


public class ConsistencyChecker {

	private final LocalObjectContainer _db;
	private final List<SlotWithSource> bogusSlots = new ArrayList<SlotWithSource>();
	private TreeIntObject mappings;

	public static class SlotSource {
		public final static SlotSource ID_SYSTEM = new SlotSource("IdSystem");
		public final static SlotSource FREESPACE = new SlotSource("Freespace");

		private final String _name;
		
		private SlotSource(String name) {
			_name = name;
		}
		
		@Override
		public String toString() {
			return _name;
		}
	}

	public static class SlotWithSource {
		public final Slot slot;
		public final SlotSource source;

		public SlotWithSource(Slot slot, SlotSource source) {
			this.slot = slot;
			this.source = source;
		}
		
		@Override
		public String toString() {
			return slot + "(" + source + ")";
		}
	}
	
	public static class ConsistencyReport {
		
		private static final int MAX_REPORTED_ITEMS = 50;
		final List<SlotWithSource> bogusSlots;
		final List<Pair<SlotWithSource,SlotWithSource>> overlaps;
		final List<Pair<String,Integer>> invalidObjectIds;
		final List<Pair<String,Integer>> invalidFieldIndexEntries;
		
		public ConsistencyReport(
				List<SlotWithSource> bogusSlots, 
				List<Pair<SlotWithSource,SlotWithSource>> overlaps, 
				List<Pair<String,Integer>> invalidClassIds, 
				List<Pair<String,Integer>> invalidFieldIndexEntries) {
			this.bogusSlots = bogusSlots;
			this.overlaps = overlaps;
			this.invalidObjectIds = invalidClassIds;
			this.invalidFieldIndexEntries = invalidFieldIndexEntries;
		}
		
		public boolean consistent() {
			return bogusSlots.size() == 0 && overlaps.size() == 0 && invalidObjectIds.size() == 0 && invalidFieldIndexEntries.size() == 0;
		}
		
		@Override
		public String toString() {
			if(consistent()) {
				return "no inconsistencies detected";
			}
			StringBuffer message = new StringBuffer("INCONSISTENCIES DETECTED\n")
				.append(overlaps.size() + " overlaps\n")
				.append(bogusSlots.size() + " bogus slots\n")
				.append(invalidObjectIds.size() + " invalid class ids\n")
				.append(invalidFieldIndexEntries.size() + " invalid field index entries\n");
			message.append("(slot lengths are non-blocked)\n");
			appendInconsistencyReport(message, "OVERLAPS", overlaps);
			appendInconsistencyReport(message, "BOGUS SLOTS", bogusSlots);
			appendInconsistencyReport(message, "INVALID OBJECT IDS", invalidObjectIds);
			appendInconsistencyReport(message, "INVALID FIELD INDEX ENTRIES", invalidFieldIndexEntries);
			return message.toString();
		}
		
		private <T> void appendInconsistencyReport(StringBuffer str, String title, List<T> entries) {
			if(entries.size() != 0) {
				str.append(title + "\n");
				int count = 0;
				for (T entry : entries) {
					str.append(entry).append("\n");
					count++;
					if(count > MAX_REPORTED_ITEMS) {
						str.append("and more...\n");
						break;
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		EmbeddedObjectContainer db = Db4oEmbedded.openFile(args[0]);
		try {
			System.out.println(new ConsistencyChecker(db).checkSlotConsistency());
		}
		finally {
			db.close();
		}
	}
	
	public ConsistencyChecker(ObjectContainer db) {
		_db = (LocalObjectContainer) db;
		
	}
	
	public ConsistencyReport checkSlotConsistency() {
		mapIdSystem();
		mapFreespace();
		return new ConsistencyReport(bogusSlots, collectOverlaps(), checkClassIndices(), checkFieldIndices());
	}

	private List<Pair<String,Integer>> checkClassIndices() {
		final List<Pair<String,Integer>> invalidIds = new ArrayList<Pair<String,Integer>>();
		final IdSystem idSystem= _db.idSystem();
		if(!(idSystem instanceof BTreeIdSystem)) {
			return invalidIds;
		}
		ClassMetadataIterator clazzIter = _db.classCollection().iterator();
		while(clazzIter.moveNext()) {
			final ClassMetadata clazz = clazzIter.currentClass();
			if(!clazz.hasClassIndex()) {
				continue;
			}
			BTreeClassIndexStrategy index = (BTreeClassIndexStrategy) clazz.index();
			index.traverseAll(_db.systemTransaction(), new Visitor4<Integer>() {
				public void visit(Integer id) {
					if(!idIsValid(id)) {
						invalidIds.add(new Pair(clazz.getName(), id));
					}
				}
			});
		}
		return invalidIds;
	}
	
	private List<Pair<String, Integer>> checkFieldIndices() {
		final List<Pair<String,Integer>> invalidIds = new ArrayList<Pair<String,Integer>>();
		ClassMetadataIterator clazzIter = _db.classCollection().iterator();
		while(clazzIter.moveNext()) {
			final ClassMetadata clazz = clazzIter.currentClass();
			clazz.traverseDeclaredFields(new Procedure4<FieldMetadata>() {
				public void apply(final FieldMetadata field) {
					if(!field.hasIndex()) {
						return;
					}
					BTree fieldIndex = field.getIndex(_db.systemTransaction());
					fieldIndex.traverseKeys(_db.systemTransaction(), new Visitor4<FieldIndexKey>() {
						public void visit(FieldIndexKey fieldIndexKey) {
							int parentID = fieldIndexKey.parentID();
							if(!idIsValid(parentID)) {
								invalidIds.add(new Pair<String, Integer>(clazz.getName() + "#" + field.getName(), parentID));
							}
						}
					});
				}
			});
		}
		return invalidIds;
	}

	private boolean idIsValid(int id) {
		try {
			return !Slot.isNull(_db.idSystem().committedSlot(id));
		}
		catch(InvalidIDException exc) {
			return false;
		}
	}

	private List<Pair<SlotWithSource, SlotWithSource>> collectOverlaps() {
		final BlockConverter blockConverter = _db.blockConverter();
		final List<Pair<SlotWithSource,SlotWithSource>> overlaps = new ArrayList<Pair<SlotWithSource,SlotWithSource>>();
		final ByRef<SlotWithSource> prevSlot = ByRef.newInstance();
		mappings.traverse(new Visitor4<TreeIntObject>() {
			public void visit(TreeIntObject obj) {
				SlotWithSource curSlot = (SlotWithSource) obj._object;
				if(prevSlot.value != null) {
					if(prevSlot.value.slot.address() + blockConverter.toBlockedLength(prevSlot.value.slot).length() > curSlot.slot.address()) {
						overlaps.add(new Pair<SlotWithSource, SlotWithSource>(prevSlot.value, curSlot));
					}
				}
				prevSlot.value = curSlot;
			}
		});
		return overlaps;
	}

	private void mapFreespace() {
		_db.freespaceManager().traverse(new Visitor4<Slot>() {
			public void visit(Slot slot) {
				if(slot.address() < 0) {
					bogusSlots.add(new SlotWithSource(slot, SlotSource.FREESPACE));
				}
				addMapping(slot, SlotSource.FREESPACE);
			}
		});
	}

	private void mapIdSystem() {
		IdSystem idSystem= _db.idSystem();
		if(idSystem instanceof BTreeIdSystem) {
			((BTreeIdSystem)idSystem).traverseIds(new Visitor4<IdSlotMapping>() {
				public void visit(IdSlotMapping mapping) {
					if(mapping._address < 0) {
						bogusSlots.add(new SlotWithSource(mapping.slot(), SlotSource.ID_SYSTEM));
					}
					if(mapping._address > 0) {
						addMapping(mapping.slot(), SlotSource.ID_SYSTEM);
					}
				}
			});
		}
	}

	private void addMapping(Slot slot, SlotSource source) {
		mappings = Tree.add(mappings, new MappingTree(slot, source));
	}
	
	private static class MappingTree extends TreeIntObject<SlotWithSource> {

		public MappingTree(Slot slot, SlotSource source) {
			super(slot.address(), new SlotWithSource(slot, source));
		}
		
		@Override
		public boolean duplicates() {
			return true;
		}
		
	}
}
