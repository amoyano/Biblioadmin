package locators;

import java.util.ArrayList;
import java.util.Date;
import models.Libro;

import models.Sancion;
import models.Socio;

public class ModelLocator
{
	private static ModelLocator instance = new ModelLocator();
	private ModelLocator(){}

	public static ModelLocator getInstance()
	{
		return instance;
	}

	public ArrayList<Sancion> sanciones = null;

	public Sancion sancionFilter = null;
        public Libro libroSeleccionado;
        public Libro libroPrestamo;
        public Socio socioPrestamo;
        public Socio socioSuspendido;
	public Date fechaDesde;
	public Date fechaHasta;
}
