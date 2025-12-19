package br.com.pereiraeng.geo.objetos;

import java.awt.geom.Point2D;

import br.com.pereiraeng.geo.GeoCoordinate;

public abstract class GeoLine implements Geo {

	protected GeoCoordinate c1, c2;

	public GeoLine(GeoCoordinate c1, GeoCoordinate c2) {
		if (c1 == null || c2 == null)
			throw new IllegalArgumentException("Uma das coordenadas é nula");
		this.c1 = c1;
		this.c2 = c2;
	}

	// --------------------------------- GEO ---------------------------------

	private transient int index;

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public void addCoordinate(GeoCoordinate coordinate) {
		if (index == 0)
			c1 = coordinate;
		else
			c2 = coordinate;
	}

	public GeoCoordinate getC1() {
		return this.c1;
	}

	public GeoCoordinate getC2() {
		return this.c2;
	}

	public float getDistance() {
		return this.c1.getDistance(this.c2);
	}

	public Point2D.Float getMin() {
		return new Point2D.Float((float) Math.min(c1.getX(), c2.getX()), (float) Math.min(c1.getY(), c2.getY()));
	}

	public Point2D.Float getMax() {
		return new Point2D.Float((float) Math.max(c1.getX(), c2.getX()), (float) Math.max(c1.getY(), c2.getY()));
	}

	// --------------------------------- KML ---------------------------------

	@Override
	public String getKML() {
		StringBuilder out = new StringBuilder("<coordinates>\n");
		out.append(c1.getX());
		out.append(",");
		out.append(c1.getY());
		out.append(",");
		out.append(Float.isNaN(c1.getAltitude()) ? "0" : c1.getAltitude());
		out.append(" ");
		out.append(c2.getX());
		out.append(",");
		out.append(c2.getY());
		out.append(",");
		out.append(Float.isNaN(c2.getAltitude()) ? "0" : c2.getAltitude());
		out.append(" ");
		out.append("\n</coordinates>");
		return out.toString();
	}
}
