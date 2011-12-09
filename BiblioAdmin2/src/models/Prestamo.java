package models;

import java.util.Date;

public class Prestamo
{
	public Socio socio;
	public Libro libro;
	public Boolean devuelto;
	public Boolean reservado;
	public Boolean enSala;
	// La fecha en que se le entrega el ejemplar al socio.
	public Date fechaEntrega;
	// La fecha en que el socio debe devolver el ejemplar.
	public Date fechaDevolucion;
}
