package br.com.pereiraeng.geo.objetos;

import br.com.pereiraeng.geo.GeoCoordinate;

/**
 * Classe do objeto que representa um ponto no mapa, que pode ser identificado a
 * partir de seu {@link #getId() número}
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class GeoPoint extends GeoCoordinate implements Geo {
	private static final long serialVersionUID = -965647355942431808L;

	protected int id = -1;

	public GeoPoint(double longitude, double latitude) {
		super(longitude, latitude);
	}

	@Override
	public boolean equals(Object anObject) {
		if (this == anObject)
			return true;
		if (anObject instanceof GeoPoint) {
			GeoPoint go = (GeoPoint) anObject;
			return this.getId() == go.getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(this.id).hashCode();
	}

	@Override
	public String toString() {
		return getId() + ":" + super.toString();
	}

	// --------------------------------- GEO ---------------------------------

	@Override
	public void setId(Object id) {
		if (id instanceof Number)
			this.id = ((Number) id).intValue();
		else
			this.id = Integer.parseInt(id.toString());
	}

	public int getId() {
		return id;
	}

	@Override
	public void addCoordinate(GeoCoordinate coordenadas) {
		super.setLocation(coordenadas.getX(), coordenadas.getY());
		super.setAltitude(coordenadas.getAltitude());
	}
}
