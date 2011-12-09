package managers;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

import constants.Constants;

import locators.ModelLocator;
import models.SancionSocio;
import models.Socio;

public class SocioManager extends DBManager
{
	private static SocioManager instance = new SocioManager();
	private SocioManager()
	{
		super();
	}

	public static SocioManager getInstance()
	{
		return instance;
	}

	public ObjectSet<Socio> all()
	{
		return all(Socio.class);
	}

	public Boolean darDeBaja(Socio socio)
	{
		Boolean b = false;
		try
		{
			socio.dadoDeBaja = true;
			saveOrUpdate(socio);
			b = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return b;
	}

	public Boolean existe(String dni) throws Exception
	{
		Boolean b = false;
		try
		{
			Socio s = new Socio();
			s.dni = dni;
			b = db.queryByExample(s).size() > 0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			b = false;
		}
		return b;
	}

	public Socio buscar(String dni)
	{
		Socio result = null;
		try
		{
			Socio s = new Socio();
			s.dni = dni;

			if (!existe(dni))
				throw new Exception("No Existe el Socio");

			result = (Socio)db.queryByExample(s).next();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public ObjectSet<SancionSocio> buscarSancionados()
	{
		ObjectSet<SancionSocio> result = null;

		try
		{
			ml.sancionFilter = ml.sanciones.get(Constants.PRIMERA_SANCION);

			result = db.query(new Predicate<SancionSocio>() {
				private static final long serialVersionUID = 1L;

				public boolean match(SancionSocio sanscionSocio)
				{
					return !sanscionSocio.socio.dadoDeBaja &&
							sanscionSocio.sancion != ModelLocator.getInstance().sancionFilter;
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = null;
		}
		return result;
	}

}
