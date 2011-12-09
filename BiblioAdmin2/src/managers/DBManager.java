package managers;

import locators.ModelLocator;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import constants.DBConstants;

public class DBManager
{
	protected static ObjectContainer db;
	protected ModelLocator ml = ModelLocator.getInstance();

	protected DBManager()
	{
		db = Db4oEmbedded.openFile(DBConstants.FILE_NAME);
	}

	public static void closeDb()
	{
		db.close();
	}

	public Boolean saveOrUpdate(Object o)
	{
		Boolean b = false;
		try
		{
			db.store(o);
			b = true;
			db.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			db.rollback();
		}
		return b;
	}

	protected <T> ObjectSet<T> all(Class<T> clazz)
	{
		ObjectSet<T> result;
		try
		{
			result = db.queryByExample(clazz);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = null;
		}
		return result;
	}

}
