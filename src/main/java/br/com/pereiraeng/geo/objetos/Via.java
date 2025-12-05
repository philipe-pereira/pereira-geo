package br.com.pereiraeng.geo.objetos;

/**
 * Objeto geográfico obtido pela união de vários {@link TrechoVia trechos},
 * formando assim uma via.
 * 
 * @author Philipe PEREIRA
 *
 */
public class Via extends GeoMultiString<TrechoVia> {
	private static final long serialVersionUID = 1L;

	public Via(String name) {
		super.setName(name);
	}
}
