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
package com.db4o.db4ounit.jre11.assorted;

import com.db4o.config.*;
import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.*;


public class ObjectNotStorableExceptionTestCase extends AbstractDb4oTestCase{

    public static void main(String[] args) {
        new ObjectNotStorableExceptionTestCase().runSolo();
    }
    
    protected void configure(Configuration config) throws Exception {
        config.callConstructors(true);
        config.exceptionsOnNotStorable(true);
    }
    
    public static class Item {
        
        public Item(Object obj){
            if(obj == null){
                throw new RuntimeException();
            }
        }
        
        public static Item newItem(){
            return new Item(new Object());
        }
    }
    
    public void testObjectContainerAliveAfterObjectNotStorableException(){
        assertNotStorableException(Item.newItem());
    }
    
    public void testPrimitiveCantBeStored() {
    	assertNotStorableException(42);
    }
    
    public void testStringCantBeStored() {
    	assertNotStorableException("42");
    }
    
    private void assertNotStorableException(final Object object) {
	    Assert.expect(ObjectNotStorableException.class,new CodeBlock() {
            public void run() throws Throwable {
                store(object);
            }
        });
        
        Assert.expect(ObjectNotStorableException.class,new CodeBlock() {
            public void run() throws Throwable {
                store(object);
            }
        });
    }
    

}
