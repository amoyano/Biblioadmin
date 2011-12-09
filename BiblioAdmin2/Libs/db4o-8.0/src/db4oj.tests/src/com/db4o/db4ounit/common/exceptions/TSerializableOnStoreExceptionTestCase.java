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
package com.db4o.db4ounit.common.exceptions;

import java.io.*;

import com.db4o.config.*;
import com.db4o.internal.*;

import db4ounit.*;
import db4ounit.extensions.*;


public class TSerializableOnStoreExceptionTestCase extends AbstractDb4oTestCase {

	public static void main(String[] args) {
		new TSerializableOnStoreExceptionTestCase().runAll();
	}

	/**
	 * @sharpen.ignore
	 */
	public static class SerializableItem implements Serializable {
		private void writeObject(ObjectOutputStream out)
				throws IOException {
			throw new IOException();
		}
	}

	/**
	 * @sharpen.ignore
	 */
	protected void configure(Configuration config) {
		config.objectClass(SerializableItem.class).translate(new TSerializable());
	}

	/**
	 * @sharpen.ignore
	 */
	public void testOnStoreException() {
		Assert.expect(ReflectException.class, IOException.class,
				new CodeBlock() {
					public void run() throws Throwable {
						db().store(new SerializableItem());
					}
				});
	}
}
