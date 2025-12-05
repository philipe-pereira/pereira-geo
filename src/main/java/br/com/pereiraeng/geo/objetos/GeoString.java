package br.com.pereiraeng.geo.objetos;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import br.com.pereiraeng.geo.GeoCoordinate;

/**
 * Objeto geográfico abstrato constituído que uma série de coordenadas
 * interligadas
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class GeoString extends ArrayList<GeoCoordinate> implements Geo {
	private static final long serialVersionUID = 1L;

	protected int id;

	public Point2D.Float m, M;

	public GeoString() {
		super(0);
		m = new Point2D.Float(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		M = new Point2D.Float(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
	}

	@Override
	public boolean equals(Object anObject) {
		if (this == anObject)
			return true;
		if (anObject instanceof GeoString) {
			GeoString gs = (GeoString) anObject;
			return this.getId() == gs.getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(this.id).hashCode();
	}

	@Override
	public String toString() {
		return id + ":" + super.toString();
	}

	// --------------------------------- GEO ---------------------------------

	public abstract boolean isLoop();

	@Override
	public void setId(Object id) {
		this.id = Integer.parseInt(id.toString());
	}

	public int getId() {
		return id;
	}

	@Override
	public void addCoordinate(GeoCoordinate coordinate) {
		if (coordinate.x < m.x)
			m.x = (float) coordinate.x;
		else if (coordinate.x > M.x)
			M.x = coordinate.x;

		if (coordinate.y < m.y)
			m.y = coordinate.y;
		else if (coordinate.y > M.y)
			M.y = coordinate.y;

		super.add(coordinate);
	}

	

	public Point2D.Float getMinX() {
		Point2D.Float out = new Point2D.Float(Float.POSITIVE_INFINITY, 0f);
		for (Point2D.Float p : this)
			if (p.x < out.x)
				out = p;
		return out;
	}

	public Point2D.Float getMaxX() {
		Point2D.Float out = new Point2D.Float(Float.NEGATIVE_INFINITY, 0f);
		for (Point2D.Float p : this)
			if (p.x > out.x)
				out = p;
		return out;
	}

	public Point2D.Float getMinY() {
		Point2D.Float out = new Point2D.Float(0f, Float.POSITIVE_INFINITY);
		for (Point2D.Float p : this)
			if (p.y < out.y)
				out = p;
		return out;
	}

	public Point2D.Float getMaxY() {
		Point2D.Float out = new Point2D.Float(0f, Float.NEGATIVE_INFINITY);
		for (Point2D.Float p : this)
			if (p.y > out.y)
				out = p;
		return out;
	}

	// --------------------------------- KML ---------------------------------

	@Override
	public String getKML() {
		return getKML(this);
	}

	public static String getKML(List<GeoCoordinate> cs) {
		StringBuilder out = new StringBuilder("<coordinates>\n");
		for (GeoCoordinate c : cs) {
			out.append(c.getKMLwithoutTag());
			out.append(" ");
		}
		out.append("\n</coordinates>");
		return out.toString();
	}	
}
