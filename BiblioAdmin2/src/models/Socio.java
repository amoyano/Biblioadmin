package models;

import constants.Constants;

public class Socio
{
	public String nombre;
	public String apellido;
	public String dni;
	public String telefono;
	public String email;
	public String direccion;
	public Localidad localidad;
	public Boolean dadoDeBaja;
	public TipoSocio tipoSocio;
	public Integer cantidadReservado;

       
        
	public Boolean puedeReservar()
	{
		return cantidadReservado <= Constants.CANTIDAD_RESERVAS_MAXIMA;
	}
}
