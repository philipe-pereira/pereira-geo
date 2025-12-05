package br.com.pereiraeng.geo.objetos;

/**
 * Objeto geográfico obtido pela união de vários {@link Curso cursos
 * hidrográficos}, formando assim um rio.
 * 
 * @author Philipe PEREIRA
 *
 */
public class Rio extends GeoMultiString<Curso> {

	private static final long serialVersionUID = 1L;

	public Rio(String name) {
		super.setName(name);
	}
}
