package br.com.pereiraeng.geo.objetos;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import br.com.pereiraeng.geo.GeoCoordinate;

/**
 * Objeto geográfico abstrato constituído pela reunião de vários
 * {@link GeoString segmentos} menores. É muito parecido com o
 * {@link GeoStrings}, com a diferença de que lá cada um dos segmentos menores
 * não pode ser considerado como um objeto geográfico identificável.
 * 
 * @author Philipe PEREIRA
 *
 * @param <K> classe do objeto que comporá cada uma das partes deste objeto
 *            geográfico
 */
public abstract class GeoMultiString<K extends GeoString> extends ArrayList<K> implements Geo {
	private static final long serialVersionUID = 1L;

	protected int id;

	/**
	 * Designação das linhas geográficas
	 */
	protected String name;

	/**
	 * Descrição das linhas geográficas
	 */
	protected String description;

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

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object anObject) {
		if (this == anObject)
			return true;
		if (anObject instanceof GeoMultiString) {
			GeoMultiString<?> go = (GeoMultiString<?>) anObject;
			return this.getId() == go.getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(this.id).hashCode();
	}

	// --------------------------------- GEO ---------------------------------

	@Override
	public void addCoordinate(GeoCoordinate coordinate) {
	}

	public int getId() {
		return id;
	}

	@Override
	public void setId(Object id) {
		this.id = Integer.parseInt(id.toString());
	}

	public Point2D.Float getMin() {
		Point2D.Float m = new Point2D.Float(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		for (GeoString gs : this) {
			Point2D.Float mi = gs.getMin();
			if (mi.x < m.x)
				m.x = mi.x;
			if (mi.y < m.y)
				m.y = mi.y;
		}
		return m;
	}

	public Point2D.Float getMax() {
		Point2D.Float M = new Point2D.Float(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
		for (GeoString gs : this) {
			Point2D.Float mi = gs.getMin();
			if (mi.x > M.x)
				M.x = mi.x;
			if (mi.y > M.y)
				M.y = mi.y;
		}
		return M;
	}

	// --------------------------------- KML ---------------------------------

	private String styleUrl;

	public void setStyleUrl(String styleUrl) {
		this.styleUrl = styleUrl;
	}

	@Override
	public String getKML() {
		StringBuilder out = new StringBuilder("<Placemark>\n");

		// --------------- data ---------------

		if (name != null) {
			out.append("<name>");
			out.append(name);
			out.append("</name>\n");
		}
		if (description != null) {
			out.append("<description>");
			out.append(description);
			out.append("</description>\n");
		}
		if (styleUrl != null) {
			out.append("<styleUrl>#");
			out.append(styleUrl);
			out.append("</styleUrl>");
		}

		// ------------ coordinates ------------

		out.append("<MultiGeometry>\n");
		for (GeoString gs : this) {
			if (gs.isLoop())
				out.append("<Polygon>\n<outerBoundaryIs>\n<LinearRing>\n");
			else
				out.append("<LineString>\n");

			out.append(gs.getKML());

			if (gs.isLoop())
				out.append("\n</LinearRing>\n</outerBoundaryIs>\n</Polygon>\n");
			else
				out.append("\n</LineString>\n");
		}
		out.append("</MultiGeometry>\n");

		return out.toString();
	}
}
