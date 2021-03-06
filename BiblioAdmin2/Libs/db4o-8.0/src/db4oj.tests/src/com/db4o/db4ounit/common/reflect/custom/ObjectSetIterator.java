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
package com.db4o.db4ounit.common.reflect.custom;

import com.db4o.*;
import com.db4o.foundation.*;

public class ObjectSetIterator implements Iterator4 {

	private final ObjectSet _set;
	private Object _current;

	public ObjectSetIterator(ObjectSet set) {
		_set = set;
	}

	public Object current() {
		return _current;
	}

	public boolean moveNext() {
		if (_set.hasNext()) {
			_current = _set.next();
			return true;
		}
		return false;
	}

	public void reset() {
		throw new NotImplementedException();
	}

}
