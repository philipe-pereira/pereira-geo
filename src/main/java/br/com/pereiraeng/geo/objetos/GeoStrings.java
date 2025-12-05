package br.com.pereiraeng.geo.objetos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.com.pereiraeng.geo.GeoCoordinate;
import br.com.pereiraeng.math.geometry.Polygon;
import swingutils.interfaces.WL;

/**
 * Objeto geográfico abstrato constituído pela reunião de vários segmentos
 * menores. É muito parecido com o {@link GeoMultiString}, com a diferença de
 * que lá cada um dos segmentos menores é considerado como um objeto geográfico
 * identificável.
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class GeoStrings extends ArrayList<List<GeoCoordinate>> implements Geo {
	private static final long serialVersionUID = 1L;

	protected int id;

	private ArrayList<Boolean> loops = new ArrayList<>();

	/**
	 * Designação das linhas geográficas
	 */
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object anObject) {
		if (this == anObject)
			return true;
		if (anObject instanceof GeoStrings) {
			GeoStrings go = (GeoStrings) anObject;
			return this.getId() == go.getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(this.id).hashCode();
	}

	public void merge() {
		Polygon.merge(this);
	}

	// --------------------------------- GEO ---------------------------------

	public int getId() {
		return id;
	}

	@Override
	public void setId(Object id) {
		this.id = Integer.parseInt(id.toString());
	}

	private transient int pos = 0;

	public void setPos(int pos) {
		this.pos = pos;
		while (pos >= this.size()) { // expandir, se necessário
			this.add(new LinkedList<GeoCoordinate>());
			this.loops.add(false);
		}
	}

	@Override
	public void addCoordinate(GeoCoordinate coordinate) {
		this.get(pos).add(coordinate);
	}

	@Override
	public Point2D.Float getMin() {
		Point2D.Float m = new Point2D.Float(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		for (List<GeoCoordinate> cs : this) {
			for (GeoCoordinate mi : cs) {
				if (mi.x < m.x)
					m.x = mi.x;
				if (mi.y < m.y)
					m.y = mi.y;
			}
		}
		return m;
	}

	@Override
	public Point2D.Float getMax() {
		Point2D.Float M = new Point2D.Float(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
		for (List<GeoCoordinate> cs : this) {
			for (GeoCoordinate mi : cs) {
				if (mi.x > M.x)
					M.x = mi.x;
				if (mi.y > M.y)
					M.y = mi.y;
			}
		}
		return M;
	}

	// --------------------------------- KML ---------------------------------

	@Override
	public String getKML() {
		StringBuilder out = new StringBuilder("<Placemark>\n");

		// --------------- data ---------------

		if (name != null) {
			out.append("<name>");
			out.append(name);
			out.append("</name>\n");
		}

		// ------------ coordinates ------------

		out.append("<MultiGeometry>\n");
		int j = 0;
		for (List<GeoCoordinate> cs : this) {
			if (loops.get(j))
				out.append("<Polygon>\n<outerBoundaryIs>\n<LinearRing>\n");
			else
				out.append("<LineString>\n");
			out.append(GeoString.getKML(cs));
			if (loops.get(j))
				out.append("\n</LinearRing>\n</outerBoundaryIs>\n</Polygon>\n");
			else
				out.append("\n</LineString>\n");
			j++;
		}
		out.append("</MultiGeometry>\n");

		out.append("</Placemark>\n");
		return out.toString();
	}

	// ------------------------------- DRAWER -------------------------------

	protected transient WL wl;

	@Override
	public void setWL(WL wl) {
		this.wl = wl;
	}

	@Override
	public void drawObject(Graphics2D g) {
		int j = 0;
		for (List<GeoCoordinate> cs : this)
			GeoString.drawObject(g, cs, wl, getColor(), loops.get(j++), toString());
	}

	protected abstract Color getColor();

	@Override
	public boolean isDrawable() {
		return true;
	}

	@Override
	public boolean wasDrawn() {
		return true;
	}

	@Override
	public void setDrawable(boolean drawable) {
	}

	public boolean isLoop() {
		return loops.get(pos);
	}

	public void setLoop(boolean loop) {
		loops.set(pos, loop);
	}
}
