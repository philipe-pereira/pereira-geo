package br.com.pereiraeng.geo.objetos;

import br.com.pereiraeng.geo.GeoCoordinate;

/**
 * Classe dos objetos que representa um ponto notável no mapa, com
 * {@link #getName() nome} e {@link #getDescription() descrição}.
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class GeoMark extends GeoPoint {
	private static final long serialVersionUID = 1L;

	/**
	 * Designação do ponto geográfico
	 */
	protected String name;

	/**
	 * Descrição do ponto geográfico
	 */
	protected String description;

	public GeoMark(GeoCoordinate coordinate) {
		this(coordinate.x, coordinate.y);
		super.setAltitude(coordinate.getAltitude());
	}

	public GeoMark(double longitude, double latitude) {
		super(longitude, latitude);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
