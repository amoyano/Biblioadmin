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
package com.db4o.db4ounit.optional;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.consistency.*;
import com.db4o.db4ounit.common.api.*;
import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.slots.*;

import db4ounit.*;

public class ConsistencyCheckerTestCase extends Db4oTestWithTempFile {

	public static class Item {
	}
	
	public void testFreeUsedSlot() {
		assertInconsistencyDetected(new Procedure4<Pair<LocalObjectContainer,Item>>() {
			public void apply(Pair<LocalObjectContainer, Item> state) {
				Item item = state.second;
				LocalObjectContainer db = state.first;
				int id = (int) db.getID(item);
				Slot slot = db.idSystem().committedSlot(id);
				db.freespaceManager().free(slot);
			}
		});
	}

	public void testFreeShiftedUsedSlot() {
		assertInconsistencyDetected(new Procedure4<Pair<LocalObjectContainer,Item>>() {
			public void apply(Pair<LocalObjectContainer, Item> state) {
				Item item = state.second;
				LocalObjectContainer db = state.first;
				int id = (int) db.getID(item);
				Slot slot = db.idSystem().committedSlot(id);
				db.freespaceManager().free(new Slot(slot.address() + 1, slot.length()));
			}
		});
	}

	private void assertInconsistencyDetected(Procedure4<Pair<LocalObjectContainer, Item>> proc) {
		EmbeddedConfiguration config = newConfiguration();
		LocalObjectContainer db = (LocalObjectContainer) Db4oEmbedded.openFile(config, tempFile());
		try {
			Item item = new Item();
			db.store(item);
			db.commit();
			Assert.isTrue(new ConsistencyChecker(db).checkSlotConsistency().consistent());
			proc.apply(new Pair<LocalObjectContainer, Item>(db, item));
			db.commit();
			Assert.isFalse(new ConsistencyChecker(db).checkSlotConsistency().consistent());
		}
		finally {
			db.close();			
		}
	}

}
