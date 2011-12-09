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
package com.db4o.test.nativequery;

import java.lang.reflect.*;
import java.util.*;

import com.db4o.*;
import com.db4o.activation.*;
import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.query.*;
import com.db4o.nativequery.*;
import com.db4o.nativequery.expr.*;
import com.db4o.nativequery.main.*;
import com.db4o.query.*;
import com.db4o.ta.*;

import db4ounit.*;
import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;


public class NQRegressionTestCase extends AbstractDb4oTestCase {
	private final static boolean RUN_LOADTIME = false;
	
	private static final String CSTR = "Cc";
	private static final String BSTR = "Ba";
	private static final String ASTR = "Aa";
	public final static Integer INTWRAPPER=new Integer(1);
	public final static Date DATE=new Date(0);
	private final static Integer PRIVATE_INTWRAPPER=new Integer(1);
	
	private static Data _prevData=null;
	
	private static abstract class Base {
		int id;
		Integer idWrap;
		
		public Base(int id) {
			this.id=id;
			idWrap=new Integer(id);
		}

		public int getId() {
			return id;
		}
	}
	
	private static class Other extends Base {
		public Other() {
			super(1);
		}
	}
	
	private static class Data extends Base implements Activatable {
		boolean bool;
		float value;
		String name;
		Data prev;
		int id2;
		Boolean boolWrap;
		java.util.Date curDate;
		
		public Data(int id, boolean bool,float value, String name,Data prev, int id2, java.util.Date curDate) {
			super(id);
			this.bool=bool;
			this.boolWrap=new Boolean(bool);
			this.value=value;
			this.name = name;
			this.prev=prev;
			this.id2=id2;
			this.curDate = curDate;
		}

		public float getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
		
		public boolean getBool() {
			return bool;
		}

		public Data getPrev() {
			return prev;
		}

		public void activate(ActivationPurpose purpose) {
		}

		public void bind(Activator activator) {
		}	
	}

	public static void main(String[] args) {
		Iterator4 suite=new Db4oTestSuiteBuilder(new Db4oSolo(),NQRegressionTestCase.class).iterator();
		new ConsoleTestRunner(suite).run();
	}

	public void store() {
		java.util.Date date1 = new java.util.Date(0);
		java.util.Date date2 = new java.util.Date();
		
		Data a=new Data(1,false,1.1f,ASTR,null, 0, date1);
		Data b=new Data(2,false,1.1f,BSTR,a, Integer.MIN_VALUE, date1);
		Data c=new Data(3,true,2.2f,CSTR,b, Integer.MIN_VALUE, date1);
		Data cc=new Data(3,false,3.3f,CSTR,null, Integer.MIN_VALUE, date2);
		ObjectContainer db=db();
		db.store(a);
		db.store(b);
		db.store(c);
		db.store(cc);
		db.store(new Other());
		_prevData=a;
	}
	
	private abstract static class ExpectingPredicate<E> extends Predicate<E> {
		private String _name;
		
		public ExpectingPredicate(String name) {
			_name=name;
		}

		public ExpectingPredicate(String name,Class<? extends E> extentType) {
			super(extentType);
			_name=name;
		}

		public abstract int expected();
		
		public void prepare(ObjectContainer db) {
		}
		
		public String toString() {
			return _name;
		}
	}
	
	@SuppressWarnings("serial")
	private static ExpectingPredicate[] COMMON_PREDICATES={
		// unconditional/untyped
		new ExpectingPredicate<Object>("unconditional/untyped") {
			public int expected() { return 5;}
			public boolean match(Object candidate) {
				return true;
			}
		},
		// unconditional
		new ExpectingPredicate<Base>("unconditional: Base") {
			public int expected() { return 5;}
			public boolean match(Base candidate) {
				return true;
			}
		},
		new ExpectingPredicate<Data>("unconditional: Data") {
			public int expected() { return 4;}
			public boolean match(Data candidate) {
				return true;
			}
		},
//		new ExpectingPredicate<Data>() {
//			public int expected() { return 0;}
//			public boolean match(Data candidate) {
//				return false;
//			}
//		},
		// primitive equals
		new ExpectingPredicate<Data>("bool") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.bool;
			}
		},
		new ExpectingPredicate<Data>("!bool") {
			public int expected() { return 3;}
			public boolean match(Data candidate) {
				return !candidate.bool;
			}
		},
		new ExpectingPredicate<Data>("id2==0") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.id2==0;
			}
		},
		new ExpectingPredicate<Data>("id==1") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.id==1;
			}
		},
		new ExpectingPredicate<Data>("id==3") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.id==3;
			}
		},
		new ExpectingPredicate<Data>("value==1.1") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.value==1.1f;
			}
		},
		new ExpectingPredicate<Data>("value==3.3") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.value==3.3f;
			}
		},
		// string equals
		new ExpectingPredicate<Data>("name.eq(ASTR)") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.name.equals(ASTR);
			}
		},
		new ExpectingPredicate<Data>("name.eq(CSTR)") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.name.equals(CSTR);
			}
		},
		// string specific comparisons
		new ExpectingPredicate<Data>("name.startsWith('C')") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.name.startsWith("C");
			}
		},
		new ExpectingPredicate<Data>("name.startsWith('a')") {
			public int expected() { return 0;}
			public boolean match(Data candidate) {
				return candidate.name.startsWith("a");
			}
		},
		new ExpectingPredicate<Data>("name.endsWith('A')") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.name.endsWith("a");
			}
		},
		new ExpectingPredicate<Data>("name.endsWith('A')") {
			public int expected() { return 0;}
			public boolean match(Data candidate) {
				return candidate.name.endsWith("A");
			}
		},
		new ExpectingPredicate<Data>("!(name.startsWith('C'))") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return !candidate.name.startsWith("C");
			}
		},
		// int field comparison
		new ExpectingPredicate<Data>("id<2") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.id<2;
			}
		},
		new ExpectingPredicate<Data>("id>2") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.id>2;
			}
		},
		new ExpectingPredicate<Data>("id<=2") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.id<=2;
			}
		},
		new ExpectingPredicate<Data>("id>=2") {
			public int expected() { return 3;}
			public boolean match(Data candidate) {
				return candidate.id>=2;
			}
		},
		// float field comparison
		new ExpectingPredicate<Data>("value>2.9") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.value>2.9f;
			}
		},
		new ExpectingPredicate<Data>("1.5>=value") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return 1.5f >= candidate.value;
			}
		},
		// mixed comparison (coercion)
		new ExpectingPredicate<Data>("id==1.0") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.id==1.0f;
			}
		},
		new ExpectingPredicate<Data>("id!=1.0") {
			public int expected() { return 3;}
			public boolean match(Data candidate) {
				return candidate.id!=1.0f;
			}
		},
		new ExpectingPredicate<Data>("value!=1") {
			public int expected() { return 4;}
			public boolean match(Data candidate) {
				return candidate.value!=1;
			}
		},
// won't work: SODA coercion is broken for greater/smaller comparisons
//		new ExpectingPredicate<Data>() {
//			public int expected() { return 1;}
//			public boolean match(Data candidate) {
//				return candidate.value>2.9d;
//			}
//		},
		// descend field
		new ExpectingPredicate<Data>("getPrev().getId()>=1") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.getPrev()!=null&&candidate.getPrev().getId()>=1;
			}
		},
		new ExpectingPredicate<Data>("BSTR.eq(getPrev().getName()") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return (candidate.getPrev()!=null)&&(BSTR.equals(candidate.getPrev().getName()));
			}
		},
		new ExpectingPredicate<Data>("getPrev().getName().eq('')") {
			public int expected() { return 0;}
			public boolean match(Data candidate) {
				return candidate.getPrev()!=null&&candidate.getPrev().getName().equals("");
			}
		},
		new ExpectingPredicate<Data>("getPrev()==_dataPrev") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.getPrev()==_prevData;
			}
		},
		// getter comparisons
		new ExpectingPredicate<Data>("getId()==2") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.getId()==2;
			}
		},
		new ExpectingPredicate<Data>("getId()<2") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.getId()<2;
			}
		},
		new ExpectingPredicate<Data>("getId()>2") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.getId()>2;
			}
		},
		new ExpectingPredicate<Data>("getId()<=2") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.getId()<=2;
			}
		},
		new ExpectingPredicate<Data>("getId()>=2") {
			public int expected() { return 3;}
			public boolean match(Data candidate) {
				return candidate.getId()>=2;
			}
		},
		new ExpectingPredicate<Data>("getName().eq(CSTR)") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.getName().equals(CSTR);
			}
		},
		// negation
		new ExpectingPredicate<Data>("!(id==1)") {
			public int expected() { return 3;}
			public boolean match(Data candidate) {
				return !(candidate.id==1);
			}
		},
		new ExpectingPredicate<Data>("!(getId()>2)") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return !(candidate.getId()>2);
			}
		},
		new ExpectingPredicate<Data>("!getName().eq(CSTR)") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return !(candidate.getName().equals(CSTR));
			}
		},
		// conjunction
		new ExpectingPredicate<Data>("bool&&!getBool()") {
			public int expected() { return 0;}
			public boolean match(Data candidate) {
				return candidate.bool&&!candidate.getBool();
			}
		},
		new ExpectingPredicate<Data>("id>1&&getName().eq(CSTR)") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return (candidate.id>1)&&candidate.getName().equals(CSTR);
			}
		},
		new ExpectingPredicate<Data>("id>1&&getId()<=2") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return (candidate.id>1)&&(candidate.getId()<=2);
			}
		},
		new ExpectingPredicate<Data>("id>1&&getId()<1") {
			public int expected() { return 0;}
			public boolean match(Data candidate) {
				return (candidate.id>1)&&(candidate.getId()<1);
			}
		},
		// disjunction
		new ExpectingPredicate<Data>("bool||getId()==1") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.bool||candidate.getId()==1;
			}
		},
		new ExpectingPredicate<Data>("id==1||getName().eq(CSTR)") {
			public int expected() { return 3;}
			public boolean match(Data candidate) {
				return (candidate.id==1)||candidate.getName().equals(CSTR);
			}
		},
		new ExpectingPredicate<Data>("id>1||getId()<=2") {
			public int expected() { return 4;}
			public boolean match(Data candidate) {
				return (candidate.id>1)||(candidate.getId()<=2);
			}
		},
		new ExpectingPredicate<Data>("id<=1||getId()>=3") {
			public int expected() { return 3;}
			public boolean match(Data candidate) {
				return (candidate.id<=1)||(candidate.getId()>=3);
			}
		},
		// nested boolean
		new ExpectingPredicate<Data>("id>=1||getName().eq(CSTR)&&getId()<3") {
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return ((candidate.id>=1)||candidate.getName().equals(CSTR))&&candidate.getId()<3;
			}
		},
		new ExpectingPredicate<Data>("(id==2||getId()<=1)&&!(getName().eq(BSTR))") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return ((candidate.id==2)||candidate.getId()<=1)&&!candidate.getName().equals(BSTR);
			}
		},
		// predicate member comparison
		new ExpectingPredicate<Data>("id>=P.id") {
			private int id=2;
			
			public int expected() { return 3;}
			public boolean match(Data candidate) {
				return candidate.id>=id;
			}
		},
		new ExpectingPredicate<Data>("getName().eq(P.name)") {
			private String name=BSTR;
			
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.getName().equals(name);
			}
		},
		// arithmetic
		new ExpectingPredicate<Data>("id>=P.id+1") {
			private int id=2;
			
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.id>=id+1;
			}
		},
		new ExpectingPredicate<Data>("id>=P.calc()") {
			private int factor=2;
			
			private int calc() {
				return factor+1;
			}
			
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.id>=calc();
			}
		},
		new ExpectingPredicate<Data>("getValue()==P.calc()") {
			private float predFactor=2.0f;
			
			private float calc() {
				return predFactor*1.1f;
			}
			
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.getValue()==calc();
			}
		},
		// force extent
		new ExpectingPredicate<Object>("force extent",Data.class) {
			public int expected() { return 1;}
			public boolean match(Object candidate) {
				return ((Data)candidate).getId()==1;
			}
		},
		// array access
		new ExpectingPredicate<Data>("id==P.data[3]") {
			private int[] data={0,1,2,3,4};
			
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.id==data[3];
			}
		},
		new ExpectingPredicate<Data>("prev==P.data[3]") {
			private Data[] data={null,null,null,null};
			
			public int expected() { return 2;}
			public boolean match(Data candidate) {
				return candidate.prev==data[3];
			}
		},
		// non-candidate method calls
		new ExpectingPredicate<Data>("id==P.sum(3,0)") {
			public int expected() { return 2;}
			private int sum(int a,int b) {
				return a+b;
			}
			public boolean match(Data candidate) {
				return candidate.id==sum(3,0);
			}
		},
		// primitive wrappers
		new ExpectingPredicate<Data>("boolWrapper") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.boolWrap.booleanValue();
			}
		},
		new ExpectingPredicate<Data>("INTWRAPPER.eq(idwrap)") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return NQRegressionTestCase.INTWRAPPER.equals(candidate.idWrap);
			}
		},
		new ExpectingPredicate<Data>("idwrap.value==1") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.idWrap.intValue()==1;
			}
		},
		new ExpectingPredicate<Data>("id==INTWRAPPER.intValue()") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return candidate.id==INTWRAPPER.intValue();
			}
		},
		new ExpectingPredicate<Data>("idwrap.compareTo(INTWRAPPER)<2") {
			public int expected() { return 3;}
			public boolean match(Data candidate) {
				return candidate.idWrap.compareTo(INTWRAPPER)>0;
			}
		},
		new ExpectingPredicate<Data>("curDate.equals(DATE)") {
			public int expected() { return 3;}
			public boolean match(Data candidate) {
				return candidate.curDate.equals(DATE);
			}
		},
		// Note: We never get to see a static field access here - non-static inner class
		// stuff converts this to NQRegressionTests#access$0()
		new ExpectingPredicate<Data>("PRIVATE_INTWRAPPER.eq(idWrap)") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				return NQRegressionTestCase.PRIVATE_INTWRAPPER.equals(candidate.idWrap);
			}
		},
		// activate calls
		new ExpectingPredicate<Data>("act;id2==0") {
			public int expected() { return 1;}
			public boolean match(Data candidate) {
				candidate.activate(ActivationPurpose.READ);
				return candidate.id2==0;
			}
		},
	};
	
	@decaf.ReplaceFirst("return null;")
	private static ExpectingPredicate[] jdk5Predicates() {
		return new ExpectingPredicate[] {
			new ExpectingPredicate<Data>("name.contains('a')") {
				public int expected() { return 2;}
				public boolean match(Data candidate) {
					return candidate.name.contains("a");
				}
			},
			new ExpectingPredicate<Data>("name.contains('A')") {
				public int expected() { return 1;}
				public boolean match(Data candidate) {
					return candidate.name.contains("A");
				}
			},
			new ExpectingPredicate<Data>("name.contains('C')") {
				public int expected() { return 2;}
				public boolean match(Data candidate) {
					return candidate.name.contains("C");
				}
			},
			new ExpectingPredicate<Data>("!(name.contains('A'))") {
				public int expected() { return 3;}
				public boolean match(Data candidate) {
					return !candidate.name.contains("A");
				}
			},			
		};
	}
	
	private static ExpectingPredicate[] mergePredicates(ExpectingPredicate[] common, ExpectingPredicate[] specific) {
		if(specific == null) {
			return common;
		}
		ExpectingPredicate[] all = new ExpectingPredicate[common.length +  specific.length];
		System.arraycopy(common, 0, all, 0, common.length);
		System.arraycopy(specific, 0, all, common.length, specific.length);
		return all;
	}
	
	private static ExpectingPredicate[] PREDICATES = mergePredicates(COMMON_PREDICATES, jdk5Predicates());
	
	public void testAll() throws Exception {
		_prevData = (Data) db().queryByExample(_prevData).next();
		for (int predIdx = 0; predIdx < PREDICATES.length; predIdx++) {
			ExpectingPredicate predicate = PREDICATES[predIdx];
			assertNQResult(predicate);
		}
	}
	
	private void assertNQResult(final ExpectingPredicate predicate) throws Exception {
		final String predicateId = predicateId(predicate);
		ObjectContainer db=db();
		Db4oQueryExecutionListener listener = new Db4oQueryExecutionListener() {
			private int run=0;
			
			public void notifyQueryExecuted(NQOptimizationInfo info) {
				if(run<2) {
					Assert.areEqual(info.predicate(),predicate,predicateId);
				}
				String expMsg=null;
				switch(run) {
					case 0:
						expMsg=NativeQueryHandler.UNOPTIMIZED;
						Assert.isNull(info.optimized(),predicateId);
						break;
					case 1:
						expMsg=NativeQueryHandler.DYNOPTIMIZED;
						Assert.isTrue(info.optimized() instanceof Expression,predicateId);
						break;
					case 2:
						expMsg=NativeQueryHandler.PREOPTIMIZED;
						Assert.isNull(info.optimized(),predicateId);
						break;
				}
				Assert.areEqual(expMsg,info.message(),predicateId);
				run++;
			}
		};
		((InternalObjectContainer)db).getNativeQueryHandler().addListener(listener);
		db.ext().configure().optimizeNativeQueries(false);
		ObjectSet raw=db.query(predicate);
		db.ext().configure().optimizeNativeQueries(true);
		if(NQDebug.LOG) {
			System.err.println(predicateId(predicate));
		}
		ObjectSet optimized=db.query(predicate);
		if(!raw.equals(optimized)) {
			System.out.println("RAW");
			raw.reset();
			while(raw.hasNext()) {
				System.out.println(raw.next());
			}
			raw.reset();
			System.out.println("OPT");
			optimized.reset();
			while(optimized.hasNext()) {
				System.out.println(optimized.next());
			}
			optimized.reset();
		}
		Assert.areEqual(raw,optimized,predicateId);
		Assert.areEqual(predicate.expected(),raw.size(),predicateId);

		if(RUN_LOADTIME) {
			System.out.println("LOAD TIME: " + predicateId);
			runLoadTimeTest(db, predicate, raw, optimized);
		} 
		((InternalObjectContainer)db).getNativeQueryHandler().clearListeners();
		db.ext().configure().optimizeNativeQueries(true);
	}

	private void runLoadTimeTest(ObjectContainer db,
			final ExpectingPredicate predicate, ObjectSet raw,
			ObjectSet optimized) throws ClassNotFoundException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		db.ext().configure().optimizeNativeQueries(false);
		NQEnhancingClassloader loader=new NQEnhancingClassloader(getClass().getClassLoader());
		Class filterClass=loader.loadClass(predicate.getClass().getName());
		Constructor constr=null;
		Object[] args=null;
		try {
			constr=filterClass.getDeclaredConstructor(new Class[]{String.class});
			args=new Object[]{filterClass.getName()};
		} catch(NoSuchMethodException exc) {
			constr=filterClass.getDeclaredConstructor(new Class[]{String.class,Class.class});
			args=new Object[]{filterClass.getName(),Data.class};
		}
		constr.setAccessible(true);
		Predicate clPredicate=(Predicate)constr.newInstance(args);
		ObjectSet preoptimized=db.query(clPredicate);
		Assert.areEqual(predicate.expected(),preoptimized.size(),predicateId(predicate));
		Assert.areEqual(raw,preoptimized,predicateId(predicate));
		Assert.areEqual(optimized,preoptimized,predicateId(predicate));
	}

	private String predicateId(final ExpectingPredicate predicate) {
		return "PREDICATE: "+predicate;
	}
}
