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
package com.db4o.instrumentation.classfilter;

import com.db4o.instrumentation.core.*;

/**
 * A class filter composed of multiple other filters - accepts if one of the filters accepts.
 */
public class CompositeOrClassFilter implements ClassFilter {

	private final ClassFilter[] _filters;
	
	public CompositeOrClassFilter(ClassFilter[] filters) {
		_filters = filters;
	}

	public boolean accept(Class clazz) {
		for (int i = 0; i < _filters.length; i++) {
			if(_filters[i].accept(clazz)) {
				return true;
			}
		}
		return false;
	}

}
