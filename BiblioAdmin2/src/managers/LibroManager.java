package managers;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import java.util.Date;
import java.util.Iterator;
import models.Libro;
import models.Prestamo;

public class LibroManager extends DBManager
{
	private static LibroManager instance = new LibroManager();
	private LibroManager()
	{
	}

	public static LibroManager getInstance()
	{
		return instance;
	}

	public ObjectSet<Libro> all()
	{
		return all(Libro.class);
	}


        public ObjectSet<Libro> buscar(String isbn, String titulo, String disciplina)
	{
		ObjectSet<Libro> result = null;
		try
		{
			Libro l = new Libro();

			if (isbn != null)
				l.isbn = isbn;

			if (titulo != null)
				l.titulo = titulo;

			if (disciplina != null)
				l.disciplina = disciplina;

			l.dadoDeBaja = false;

			result = db.queryByExample(l);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

 

        public Boolean existe(String isbn) throws Exception
	{
		Boolean b = false;
		try
		{
			Libro s = new Libro();
			s.isbn = isbn;
			b = db.queryByExample(s).size() > 0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			b = false;
		}
		return b;
	}

	public Boolean darDeBaja(Libro libro)
	{
		Boolean b = false;
		try
		{
			libro.dadoDeBaja = true;
			db.store(libro);
			db.commit();
			b = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			b = false;
			db.rollback();
		}
		return b;
	}



	public ObjectSet<Libro> buscarNoDevueltos(Date fechaDesde, Date fechaHasta)
	{
            ObjectSet<Libro> result = null;
            
            
            ml.fechaDesde = fechaDesde;
               ml.fechaHasta = fechaHasta;
		ObjectSet<Prestamo> prestamos = db.query(new Predicate() {
                 
                  public boolean match(Prestamo prestamo) {
                      

                        return !prestamo.devuelto && prestamo.fechaEntrega.after(ml.fechaDesde);
                 }

            @Override
            public boolean match(Object candidate) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

           
		});

                for (int i = 0; i < prestamos.size(); i++) {
                    System.out.println(prestamos.get(i).libro.titulo);
                result.add(prestamos.get(i).libro);

            }

                return result;
        }

}

