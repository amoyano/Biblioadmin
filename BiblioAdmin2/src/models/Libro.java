package models;

public class Libro
{
	public String isbn;
	public String titulo;
	public String editorial;
	public String edicion;
	public String disciplina;
	public String autor;
	public Integer cantidadEjemplaresDisponibles;
	public Integer totalEjemplares;
	public Boolean dadoDeBaja;

	public Boolean existeAlMenosUnEjemplar()
	{
		return cantidadEjemplaresDisponibles > 0;
	}
}
