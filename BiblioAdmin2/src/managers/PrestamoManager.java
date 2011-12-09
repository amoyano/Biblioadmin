package managers;

import java.util.Calendar;

import constants.Constants;
import java.util.Date;

import models.Libro;
import models.Prestamo;
import models.Socio;

public class PrestamoManager extends DBManager
{
	private static PrestamoManager instance = new PrestamoManager();
	private PrestamoManager()
	{
	}

	public static PrestamoManager getInstance()
	{
		return instance;
	}

	public Prestamo buscar (Socio socio, Libro libro)
	{
		Prestamo result = null;
		try
		{
			Prestamo p = new Prestamo();
			p.socio = socio;
			p.libro = libro;
			p.devuelto = false;
			result = (Prestamo)db.queryByExample(p).next();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public Boolean noTieneSolicitadoElLibro(Socio socio, Libro libro)
	{
		Boolean b = false;
		try
		{
			Prestamo prestamo = new Prestamo();
			prestamo.socio = socio;
			prestamo.libro = libro;
			prestamo.devuelto = false;
			prestamo.enSala = true;
			prestamo.reservado = true;
			b = db.queryByExample(prestamo).size() == 0;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			b = false;
		}
		return b;
	}

        public Boolean noExistenReservasParaEseDia(Libro libro){
            Boolean b = false;
            Prestamo prestamo = new Prestamo();
            prestamo.libro = libro;
            prestamo.fechaEntrega = new Date();
            prestamo.reservado = true;
            b = db.queryByExample(prestamo).size() == 0;
            return b;
        }

        public Boolean noTieneMasDeTresPrestamos(Libro libro, Socio socio){
            Boolean b = false;
		
			Prestamo prestamo = new Prestamo();
			prestamo.socio = socio;
			prestamo.libro = libro;
			prestamo.devuelto = false;

			b = db.queryByExample(prestamo).size() <= 3;
		
		return b;
        }
        public Boolean noEstaDadoDeBaja(Libro libro){
            Boolean b;

            if(libro.dadoDeBaja == true){
                b = false;
            }
             else{
                b=true;
             }
            return b;

        }

        public Boolean prestamo(Socio socio, Libro libro){
           Boolean b = false;
           if(libro.cantidadEjemplaresDisponibles > 0
             && noTieneSolicitadoElLibro(socio, libro) && noExistenReservasParaEseDia(libro)
               && noTieneMasDeTresPrestamos(libro, socio) && noEstaDadoDeBaja(libro)) {
               Prestamo prestamo = new Prestamo();
               libro.cantidadEjemplaresDisponibles--;
               prestamo.libro = libro;
               prestamo.socio = socio;
               prestamo.fechaEntrega = new Date();
               saveOrUpdate(libro);
               saveOrUpdate(prestamo);
               
               b = true;
           }

           return b;
        }


	public void devolverEjemplar(Socio socio, Libro libro)
	{
		try
		{
			libro.cantidadEjemplaresDisponibles += 1;
			saveOrUpdate(libro);

			Prestamo prestamo = buscar(socio, libro);
			prestamo.devuelto = true;
                        
			saveOrUpdate(prestamo);
			db.commit();

			//Calendar today = new Date();
                        //int diasTranscurridos;
                      //  diasTranscurridos = today - prestamo.fechaDevolucion;
			// TODO: Calcular dias transcurridos desde prestamo.fechaDevolucion hasta today.
			// Y en funcion, guardar una SancionSocio y Avisar al Usuario

		}
		catch (Exception e)
		{
			e.printStackTrace();
			db.rollback();
		}
	}

	public void renovarEjemplar(Socio socio, Libro libro)
	{
		try
		{
			Prestamo p = buscar(socio, libro);
			Calendar calendar = Calendar.getInstance();

			if (p.fechaDevolucion.after(calendar.getTime()))
			{
				Calendar fechaDevolucionCalendar = Calendar.getInstance();
				fechaDevolucionCalendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR));
				fechaDevolucionCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
				fechaDevolucionCalendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+ Constants.DIAS_DEVOLUCION);
				fechaDevolucionCalendar.set(Calendar.HOUR, Constants.HORA_DEVOLUCION);
				p.fechaDevolucion = fechaDevolucionCalendar.getTime();
				saveOrUpdate(p);
				db.commit();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			db.rollback();
		}
	}

}
