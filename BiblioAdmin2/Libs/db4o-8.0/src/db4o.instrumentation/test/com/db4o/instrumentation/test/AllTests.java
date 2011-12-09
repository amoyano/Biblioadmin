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
package com.db4o.instrumentation.test;

import com.db4o.instrumentation.test.classfilter.*;
import com.db4o.instrumentation.test.core.*;

import db4ounit.*;

public class AllTests extends ReflectionTestSuite {

	public static void main(String[] args) {
		new ConsoleTestRunner(AllTests.class).run();
	}

	protected Class[] testCases() {
		return new Class[] {
			com.db4o.instrumentation.test.bloat.AllTests.class,
			DefaultFilePathRootTestCase.class,
			JarFileClassFilterTestCase.class,
		};
	}
}
