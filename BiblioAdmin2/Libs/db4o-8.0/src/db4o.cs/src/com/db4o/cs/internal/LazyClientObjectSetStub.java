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
package com.db4o.cs.internal;

import com.db4o.foundation.*;
import com.db4o.internal.query.result.*;


/**
 * @exclude
 */
public class LazyClientObjectSetStub {
	
	private final AbstractQueryResult _queryResult;
	
	private IntIterator4 _idIterator;
	
	public LazyClientObjectSetStub(AbstractQueryResult queryResult, IntIterator4 idIterator) {
		_queryResult = queryResult;
		_idIterator = idIterator;
	}
	
	public IntIterator4 idIterator(){
		return _idIterator;
	}
	
	public AbstractQueryResult queryResult(){
		return _queryResult;
	}
	
	public void reset(){
		_idIterator = _queryResult.iterateIDs();
	}

}