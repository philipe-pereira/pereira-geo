package br.com.pereiraeng.geo.objetos;

import br.com.pereiraeng.geo.GeoCoordinate;

/**
 * Classe do objeto que representa um medidor localizado em algum ponto
 * geográfico
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class GeoMed extends GeoMark {
	private static final long serialVersionUID = 3588074304134482179L;

	protected String tag;

	public GeoMed(GeoCoordinate gc) {
		super(gc);
	}
	
	public GeoMed(double longitude, double latitude) {
		super(longitude, latitude);
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

}
