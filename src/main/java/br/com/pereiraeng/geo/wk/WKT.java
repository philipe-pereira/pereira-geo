package br.com.pereiraeng.geo.wk;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.pereiraeng.geo.GeoCoordinate;
import br.com.pereiraeng.geo.objetos.Geo;
import br.com.pereiraeng.geo.objetos.GeoLine;
import br.com.pereiraeng.geo.objetos.GeoMultiString;
import br.com.pereiraeng.geo.objetos.GeoPoint;
import br.com.pereiraeng.geo.objetos.GeoString;
import br.com.pereiraeng.geo.objetos.GeoStrings;
import br.com.pereiraeng.math.geometry.Polygon;

/**
 * Classe das funções que manipulam a linguagem de marcação Well-known text
 * representation of geometry (WKT)
 * 
 * 
 * @author Philipe PEREIRA
 *
 */
public class WKT {

	public static String getWKT(Geo g) {
		StringBuilder sb = null;

		boolean gsc = g instanceof GeoStrings;
		if (gsc || g instanceof GeoMultiString<?>) {

			boolean f = false, a = false;
			if (gsc) {
				GeoStrings gss = (GeoStrings) g;
				for (int i = 0; i < gss.size(); i++) {
					gss.setPos(i);
					f |= gss.isLoop();
					a |= !gss.isLoop();
				}
			} else {
				GeoMultiString<?> gms = (GeoMultiString<?>) g;
				for (GeoString gs : gms) {
					f |= gs.isLoop();
					a |= !gs.isLoop();
				}
			}

			if (f && a) { // misto: GEOMETRYCOLLECTION
				sb = new StringBuilder("GEOMETRYCOLLECTION (");

				if (gsc) {
					GeoStrings gss = (GeoStrings) g;
					for (int i = 0; i < gss.size(); i++) {
						gss.setPos(i);
						sb.append(gss.isLoop() ? "POLYGON " : "LINESTRING ");
						sb.append(toWKT(gss.get(i), gss.isLoop()));
						sb.append(",");
					}
				} else {
					GeoMultiString<?> gms = (GeoMultiString<?>) g;
					for (GeoString gs : gms) {
						sb.append(gs.isLoop() ? "POLYGON " : "LINESTRING ");
						sb.append(toWKT(gs, gs.isLoop()));
						sb.append(",");
					}
				}
				sb.setLength(sb.length() - 1);
				sb.append(")");
			} else if (!f && !a)
				sb = new StringBuilder("MULTIPOLYGON EMPTY");
			else { // só um dos dois
				if (f)
					sb = new StringBuilder("MULTIPOLYGON (");
				else
					sb = new StringBuilder("MULTILINESTRING (");
				for (List<GeoCoordinate> gs : (gsc ? (GeoStrings) g : (GeoMultiString<?>) g)) {
					sb.append(toWKT(gs, f));
					sb.append(",");
				}
				sb.setLength(sb.length() - 1);
				sb.append(")");
			}
		} else if (g instanceof GeoLine) {
			GeoLine gl = (GeoLine) g;
			GeoCoordinate c1 = gl.getC1(), c2 = gl.getC2();
			sb = new StringBuilder(String.format(Locale.US, "LINESTRING (%f %f,%f %f)", c1.x, c1.y, c2.x, c2.y));
		} else if (g instanceof GeoString) {
			GeoString gs = (GeoString) g;
			boolean l = gs.isLoop();
			if (l)
				sb = new StringBuilder("POLYGON ");
			else
				sb = new StringBuilder("LINESTRING ");
			sb.append(toWKT(gs, l));
		} else if (g instanceof GeoPoint) {
			GeoPoint gp = (GeoPoint) g;
			sb = new StringBuilder(String.format(Locale.US, "POINT (%f %f)", gp.x, gp.y));
		}
		return sb.toString();
	}

	public static String getWKT(Collection<List<Point2D.Float>> vertexes) {
		StringBuilder sb = null;
		if (vertexes.size() == 1) {
			List<? extends Point2D> vs = vertexes.iterator().next();
			boolean l = vs.get(0) == vs.get(vs.size() - 1);
			if (l)
				sb = new StringBuilder("POLYGON ");
			else
				sb = new StringBuilder("LINESTRING ");
			sb.append(toWKT(vs, l));
		} else {
			boolean f = false, a = false;

			for (List<Point2D.Float> vs : vertexes) {
				boolean l = vs.get(0) == vs.get(vs.size() - 1);
				f |= l;
				a |= !l;
			}

			if (f && a) { // misto: GEOMETRYCOLLECTION
				sb = new StringBuilder("GEOMETRYCOLLECTION (");

				for (List<Point2D.Float> vs : vertexes) {
					boolean l = vs.get(0) == vs.get(vs.size() - 1);
					sb.append(l ? "POLYGON " : "LINESTRING ");
					sb.append(toWKT(vs, l));
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);
				sb.append(")");
			} else if (!f && !a)
				sb = new StringBuilder("MULTIPOLYGON EMPTY");
			else { // só um dos dois
				if (f)
					sb = new StringBuilder("MULTIPOLYGON (");
				else
					sb = new StringBuilder("MULTILINESTRING (");
				for (List<Point2D.Float> vs : vertexes) {
					sb.append(toWKT(vs, f));
					sb.append(",");
				}
				sb.setLength(sb.length() - 1);
				sb.append(")");
			}
		}
		return sb.toString();
	}

	private static String toWKT(List<? extends Point2D> cs, boolean loop) {
		StringBuilder sb = new StringBuilder("(");
		if (loop)
			sb.append("(");
		for (Point2D c : cs)
			sb.append(String.format(Locale.US, "%f %f,", c.getX(), c.getY()));
		if (loop) {
			Point2D c = cs.get(0);
			sb.append(String.format(Locale.US, "%f %f", c.getX(), c.getY()));
		} else
			sb.setLength(sb.length() - 1);
		if (loop)
			sb.append(")");
		sb.append(")");
		return sb.toString();
	}

	private static final String PATTERN_LINESTRING = "\\([-\\d\\.\\s,]+?\\)";

	private static final String PATTERN_POLYGON = "\\(" + PATTERN_LINESTRING + "\\)";

	public static void set(Geo g, String wkt) {
		if (g instanceof GeoStrings) {
			GeoStrings gss = (GeoStrings) g;
			boolean mp = wkt.startsWith("MULTIPOLYGON");
			if (mp || wkt.startsWith("MULTILINESTRING")) {
				wkt = wkt.substring(mp ? 13 : 15, wkt.length() - (mp ? 1 : 0));

				Matcher m = Pattern.compile(mp ? PATTERN_POLYGON : PATTERN_LINESTRING).matcher(wkt);
				int j = gss.size();
				while (m.find()) {
					gss.setPos(j);
					gss.setLoop(mp);
					String q = m.group();
					String[] p = q.substring(mp ? 2 : 1, q.length() - (mp ? 2 : 1)).split(",");
					for (int i = 0; i < p.length; i++) {
						String[] c = p[i].split(" ");
						gss.addCoordinate(new GeoCoordinate(Double.parseDouble(c[0]), Double.parseDouble(c[1])));
					}
					j++;
				}
			} else if (wkt.startsWith("LINESTRING")) {
				wkt = wkt.substring(11, wkt.length() - 1);

				gss.setPos(gss.size());
				gss.setLoop(false);

				String[] p = wkt.split(",");
				for (int i = 0; i < p.length; i++) {
					String[] c = p[i].split(" ");
					gss.addCoordinate(new GeoCoordinate(Double.parseDouble(c[0]), Double.parseDouble(c[1])));
				}
			} else {
				// TODO
			}
		} else {
			// TODO
		}
	}

	public static Collection<? extends Object> read(String wkt) {
		boolean mp = wkt.startsWith("MULTIPOLYGON");
		if (mp || wkt.startsWith("MULTILINESTRING")) {
			wkt = wkt.substring(mp ? 13 : 15, wkt.length() - (mp ? 1 : 0));

			Matcher m = Pattern.compile(mp ? PATTERN_POLYGON : PATTERN_LINESTRING).matcher(wkt);
			List<Polygon> ps = new LinkedList<>();
			while (m.find()) {
				String q = m.group();
				String[] p = q.substring(mp ? 2 : 1, q.length() - (mp ? 2 : 1)).split(",");
				List<Point2D.Double> edgeList = new LinkedList<>();
				for (int i = 0; i < p.length; i++) {
					String[] c = p[i].split(" ");
					edgeList.add(new Point2D.Double(Double.parseDouble(c[0]), Double.parseDouble(c[1])));
				}
				if (mp)
					edgeList.add(edgeList.get(0));
				ps.add(new Polygon.Double(edgeList));
			}
			return ps;
		} else if (wkt.startsWith("LINESTRING")) {
			wkt = wkt.substring(11, wkt.length() - 1);

			List<Point2D.Double> edgeList = new LinkedList<>();
			String[] p = wkt.split(",");
			for (int i = 0; i < p.length; i++) {
				String[] c = p[i].split(" ");
				edgeList.add(new Point2D.Double(Double.parseDouble(c[0]), Double.parseDouble(c[1])));
			}
			return Arrays.asList(new Polygon.Double(edgeList));
		}
		return null;
	}
}
