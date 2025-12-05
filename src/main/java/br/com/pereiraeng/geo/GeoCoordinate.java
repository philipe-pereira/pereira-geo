package br.com.pereiraeng.geo;

import java.awt.geom.Point2D;
import java.util.Locale;

import br.com.pereiraeng.math.Angle;
import br.com.pereiraeng.math.geometry.Coordinate;
import br.com.pereiraeng.core.ExtendedMath;

/**
 * Classe dos objetos que representam uma coordenada polar
 * 
 * @author Philipe PEREIRA
 * 
 */
public class GeoCoordinate extends Coordinate.Float {
	private static final long serialVersionUID = 2913559693600824593L;

	public static final char SEPARATOR = ',';

	protected float altitude = java.lang.Float.NaN;

	public GeoCoordinate(Point2D.Double c) {
		super(c.x, c.y);
	}

	public GeoCoordinate(Point2D.Float c) {
		super(c.x, c.y);
	}

	/**
	 * Construtor da coordenada a partir de dois números decimais
	 * 
	 * @param longitude longitude da coordenada, expressa na forma de um número
	 *                  decimal
	 * @param latitude  latitude da coordenada, expressa na forma de um número
	 *                  decimal
	 */
	public GeoCoordinate(double longitude, double latitude) {
		super(longitude, latitude);
	}

	/**
	 * Construtor da coordenada a partir de dois números decimais
	 * 
	 * @param longLat vetor com duas posições, sendo a primeira a longitude e a
	 *                segunda a latitude
	 */
	public GeoCoordinate(double[] longLat) {
		super(longLat);
	}

	/**
	 * Construtor da coordenada a partir dos valores em graus, minutos e secondos
	 * 
	 * @param degreesLon graus da longitude, expressos por um número inteiro
	 * @param minutesLon minutos da longitude, expressos por um número inteiro
	 * @param secondsLon segundos da longitude, expressos por um número decimal
	 * @param degreesLat graus da latitude, expressos por um número inteiro
	 * @param minutesLat minutos da latitude, expressos por um número inteiro
	 * @param secondsLat segundos da latitude, expressos por um número decimal
	 */
	public GeoCoordinate(int degreesLon, int minutesLon, float secondsLon, int degreesLat, int minutesLat,
			float secondsLat) {
		super(Angle.toFloatDegrees(degreesLon, minutesLon, secondsLon),
				Angle.toFloatDegrees(degreesLat, minutesLat, secondsLat));
	}

	/**
	 * Construtor da coordenada a partir dos valores em graus, minutos e secondos
	 * 
	 * @param lon sequência de caracteres que indica a longitude, na forma:
	 *            [deg]:[min]:[seg]
	 * @param lat sequência de caracteres que indica a latitude, na forma:
	 *            [deg]:[min]:[seg]
	 */
	public GeoCoordinate(String lon, String lat) {
		super();
		String[] lons = lon.split(":");
		String[] lats = lat.split(":");
		setLocation(
				Angle.toFloatDegrees(Integer.parseInt(lons[0]), Integer.parseInt(lons[1]),
						java.lang.Float.parseFloat(lons[2].replace(',', '.'))),
				Angle.toFloatDegrees(Integer.parseInt(lats[0]), Integer.parseInt(lats[1]),
						java.lang.Float.parseFloat(lats[2].replace(',', '.'))));
	}

	/**
	 * Construtor da coordenada a partir dos valores em graus, minutos e secondos
	 * 
	 * @param lonS formato variável
	 * @param latS formato variável
	 * @param type
	 *             <ol start="0">
	 *             <li>mesmo tipo gerado em {@link #toString()};</i>
	 *             <li>adaptável;</i>
	 *             <li>dois pontos (gerado em {@link #toString2(double)}).</i>
	 *             </ol>
	 */
	public GeoCoordinate(String lonS, String latS, int type) {
		super(GeoCoordinate.parse(lonS, type), GeoCoordinate.parse(latS, type));
	}

	public float getAltitude() {
		return altitude;
	}

	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}

	/**
	 * Função que retorna os graus da longitude dessa coordenada
	 * 
	 * @return graus da longitude, expressos na forma de um número inteiro
	 */
	public int getDegreesLon() {
		return Angle.getDegrees(getX());
	}

	/**
	 * Função que retorna os minutos da longitude dessa coordenada
	 * 
	 * @return minutos da longitude, expressos na forma de um número inteiro
	 */
	public int getMinutesLon() {
		return Angle.getMinutes(getX());
	}

	/**
	 * Função que retorna os segundos da longitude dessa coordenada
	 * 
	 * @return segundos da longitude, expressos na forma de um número inteiro
	 */
	public double getSecondsLon() {
		return Angle.getSeconds(getX());
	}

	/**
	 * Função que retorna os graus da latitude dessa coordenada
	 * 
	 * @return graus da latitude, expressos na forma de um número inteiro
	 */
	public int getDegreesLat() {
		return Angle.getDegrees(getY());
	}

	/**
	 * Função que retorna os minutos da latitude dessa coordenada
	 * 
	 * @return minutos da latitude, expressos na forma de um número inteiro
	 */
	public int getMinutesLat() {
		return Angle.getMinutes(getY());
	}

	/**
	 * Função que retorna os segundos da latitude dessa coordenada
	 * 
	 * @return segundos da latitude, expressos na forma de um número inteiro
	 */

	public double getSecondsLat() {
		return Angle.getSeconds(getY());
	}

	// ---------------- COORDENADAS -> SEQUÊNCIA DE CARACTERES ----------------

	// principal (longitude na forma graus[º]minutos[']segundos[''][E|W][,]latitude
	// na forma graus[º]minutos[']segundos[''][S|N])

	@Override
	public String toString() {
		return toString(getX(), getY());
	}

	/**
	 * Função que retorna uma sequência de caracteres representativa de uma
	 * coordenada
	 * 
	 * @param x longitude, expressa por um número decimal
	 * @param y latitude, expressa por um número decimal
	 * @return <code>String</code> representando a coordenada
	 */
	public static String toString(double x, double y) {
		return String.format("%s%c%s", toStringLon(x), SEPARATOR, toStringLat(y));
	}

	/**
	 * Função que retorna uma sequência de caracteres representativa de um ângulo de
	 * longitude
	 * 
	 * @param x longitude, expressa por um número decimal
	 * @return <code>String</code> representando a longitude
	 */
	public static String toStringLon(double x) {
		String s = Angle.toStringAngle(Angle.getDegrees(x), Angle.getMinutes(x), Angle.getSeconds(x));
		if (x > 0.0001)
			s += "E";
		else if (x < -0.0001)
			s = s.substring(1) + "W";
		return s;
	}

	/**
	 * Função que retorna uma sequência de caracteres representativa de um ângulo de
	 * latitude
	 * 
	 * @param theta latitude, expressa por um número decimal
	 * @return <code>String</code> representando a latitude
	 */
	public static String toStringLat(double y) {
		String s = Angle.toStringAngle(Angle.getDegrees(y), Angle.getMinutes(y), Angle.getSeconds(y));
		if (y > 0.0001)
			s += "N";
		else if (y < -0.0001)
			s = s.substring(1) + "S";
		return s;
	}

	// edição (longitude na forma graus[S|N]minutos[]segundos[-]latitude na forma
	// graus[E|W]minutos[]segundos)

	/**
	 * Função que retorna uma sequência de caracteres representativa de uma
	 * coordenada numa forma mais fácil de ser editada
	 * 
	 * @return <code>String</code> representando o ângulo a ser editado
	 */
	public String toStringEdition() {
		return toStringEdition(getY(), true) + "-" + toStringEdition(getX(), false);
	}

	/**
	 * Função que retorna uma sequência de caracteres representativa de um ângulo
	 * numa forma mais fácil de ser editado
	 * 
	 * @param angle    valor do ângulo expresso na forma decimal
	 * @param latitude se <code>true</code> o ângulo é uma latitude, senão é
	 *                 longitude
	 * @return sequência de caracteres representativa de um ângulo
	 */
	public static String toStringEdition(double angle, boolean latitude) {
		if (angle != java.lang.Float.NaN) {
			angle = ExtendedMath.circularDegree(angle);
			boolean s = 60 - Angle.getSeconds(angle) < 0.001;
			int m = Angle.getMinutes(angle) + (s ? 1 : 0);

			int deg = Math.abs(Angle.getDegrees(angle) + (m == 60 ? 1 : 0));
			int min = m == 60 ? 0 : m;
			int sec = (int) (10 * (s ? 0.0 : (Angle.getSeconds(angle) / 60.0)));

			String out = String.format("%0" + (latitude ? 2 : 3) + "d%02d%s%01d", deg, min,
					(angle > 1.0f / 600 - 0.0001 ? (latitude ? "N" : "E")
							: (angle < -1.0f / 600 + 0.0001 ? (latitude ? "S" : "W") : "")),
					sec);

			return out;
		} else
			return "";
	}

	// usando dois pontos

	/**
	 * Função que retorna uma sequência de caracteres representativa desta
	 * coordenada geográfica
	 * 
	 * @return sequência de caracteres representativa desta coordenada geográfica
	 */
	public String toString2() {
		return String.format("%s%c%s", toString2(getX()), SEPARATOR, toString2(getY()));
	}

	/**
	 * Função que retorna uma sequência de caracteres representativa de um ângulo de
	 * longitude ou da latitude
	 * 
	 * @param xy longitude ou latitude, expressa por um número decimal
	 * @return sequência de caracteres representando a longitude ou latitude
	 */
	public static String toString2(double xy) {
		return toStringAngle2(Angle.getDegrees(xy), Angle.getMinutes(xy), Angle.getSeconds(xy));
	}

	/**
	 * Função que retorna uma sequência de caracteres representativa de um ângulo de
	 * longitude ou da latitude
	 * 
	 * @param degrees graus, expressos por um número inteiro
	 * @param minutes minutos, expressos por um número inteiro
	 * @param seconds segundos, expressos por um número decimal
	 * @return sequência de caracteres representando a longitude ou latitude
	 */
	public static String toStringAngle2(int degrees, int minutes, double seconds) {
		boolean sec = 60 - seconds < 0.5f;
		int m = minutes + (sec ? 1 : 0);
		return String.format(Locale.US, "%d:%02d:%05.2f", degrees + (m == 60 ? 1 : 0), (m == 60 ? 0 : m),
				sec ? 0 : seconds);
	}

	// KML

	public String getKML() {
		return String.format(Locale.US, "<coordinates>%s</coordinates>\n", getKMLwithoutTag());
	}

	/**
	 * Função que retorna as coordenadas no formato de uma trinca de números, tal
	 * como utilizado em KML
	 * 
	 * @return sequência de caracteres com a
	 */
	public String getKMLwithoutTag() {
		return String.format(Locale.US, "%.14f,%.14f,%s", getX(), getY(),
				java.lang.Double.isNaN(altitude) ? "0" : String.format(Locale.US, "%.14f", altitude));
	}

	// ---------------- SEQUÊNCIA DE CARACTERES -> COORDENADAS ----------------

	/**
	 * Função que converte uma sequência de caracteres no ângulo correspondente
	 * 
	 * @param angle <code>String</code> representando o ângulo
	 * @param type
	 *              <ol start="0">
	 *              <li>mesmo tipo gerado em {@link #toString()};</i>
	 *              <li>adaptável;</i>
	 *              <li>dois pontos (gerado em {@link #toString2(double)}).</i>
	 *              </ol>
	 * @return
	 */
	private static float parse(String angle, int type) {
		switch (type) {
		case 0:
			int pos0 = angle.indexOf(Angle.DEGREE);
			int pos1 = angle.indexOf('\'');
			int pos2 = angle.indexOf('\"');

			String deg = angle.substring(0, pos0).trim();
			String min = angle.substring(pos0 + 1, pos1).trim();
			String sec = angle.substring(pos1 + 1, pos2).trim();

			char d = angle.charAt(angle.length() - 1);
			float v = Angle.toFloatDegrees(Integer.parseInt(deg), Integer.parseInt(min),
					java.lang.Float.parseFloat(sec.replace(',', '.')));
			return d == 'W' || d == 'S' ? -v : v;
		case 1:
			// posição do separador dos graus
			pos0 = angle.indexOf(Angle.DEGREE);
			if (pos0 == -1)
				pos0 = angle.indexOf(186);
			if (pos0 == -1)
				pos0 = angle.indexOf(176);

			// posição do separador dos minutos
			pos1 = angle.indexOf('\'');
			if (pos1 == -1)
				pos1 = angle.indexOf(8217);

			// posição do separador dos segundos
			pos2 = angle.indexOf('\"');
			if (pos2 == -1)
				pos2 = angle.indexOf(8221);
			if (pos2 == -1)
				pos2 = angle.indexOf("\'\'");
			if (pos2 == -1)
				pos2 = angle.indexOf(8220);
			if (pos2 == -1)
				pos2 = angle.indexOf("\u2019\u2019");
			if (pos2 == -1)
				pos2 = angle.length() - 1;

			deg = angle.substring(0, pos0).trim();
			min = angle.substring(pos0 + 1, pos1).trim();
			sec = angle.substring(pos1 + 1, pos2).replaceAll("\\s+", "");

			d = angle.charAt(angle.length() - 1);
			v = Angle.toFloatDegrees(Integer.parseInt(deg), Integer.parseInt(min),
					java.lang.Float.parseFloat(sec.replace(',', '.')));
			return d == 'W' || d == 'S' ? -v : v;
		case 2:
			String[] dms = angle.split(":");
			return Angle.toFloatDegrees(Integer.parseInt(dms[0]), Integer.parseInt(dms[1]),
					java.lang.Float.parseFloat(dms[2].replace(',', '.')));
		}
		return 0f;
	}

	// ---------------------- CÁLCULOS ENVOLVENDO COORDENADAS ----------------------

	/**
	 * Função que calcula a distância, em metros, entre duas coordenadas na Terra
	 * 
	 * @param coordinate coordenada a ser calculada a distância
	 * @return distância em metros
	 */
	public float getDistance(Coordinate.Float coordinate) {
		return getDistance(this, coordinate);
	}

	/**
	 * Função que calcula a distância, em metros, entre duas coordenadas na Terra
	 * 
	 * @param c0 uma coordenada
	 * @param c1 a outra coordenada
	 * @return distância em metros
	 */
	public static float getDistance(Point2D c0, Point2D c1) {
		double long0 = Math.toRadians(c0.getX()), long1 = Math.toRadians(c1.getX()), lat0 = Math.toRadians(c0.getY()),
				lat1 = Math.toRadians(c1.getY());

		return (float) (GeoConsts.EARTH_RADIUS * ExtendedMath.ahaversin(ExtendedMath.haversin(lat0 - lat1)
				+ Math.cos(lat0) * Math.cos(lat1) * ExtendedMath.haversin(long0 - long1)));
	}

	/**
	 * Função que retorna o ângulo com relação ao norte (azimute) da linha que liga
	 * duas coordenadas
	 * 
	 * @param coordenadas coordenada a ser calculada o azimute
	 * @return ângulo em graus do azimute
	 */
	public float getAzimut(GeoCoordinate coordenadas) {
		return getAzimut(this, coordenadas);
	}

	/**
	 * Função que retorna o ângulo com relação ao norte (azimute) da linha que liga
	 * duas coordenadas
	 * 
	 * @param c0 uma coordenada
	 * @param c1 a outra coordenada
	 * @return ângulo em graus do azimute
	 */
	public static float getAzimut(Point2D c0, Point2D c1) {
		double long0 = Math.toRadians(c0.getX()), long1 = Math.toRadians(c1.getX()), lat0 = Math.toRadians(c0.getY()),
				lat1 = Math.toRadians(c1.getY());

		double T = Math.cos(long0 - long1) * Math.sin(Math.PI / 2 - lat1) * Math.sin(Math.PI / 2 - lat0)
				+ Math.cos(Math.PI / 2 - lat1) * Math.cos(Math.PI / 2 - lat0);

		double angle = -Math.atan(T / (Math.sqrt(1 - T * T))) + Math.PI / 2;

		double R = (Math.sin(lat1) - Math.sin(lat0) * Math.cos(angle)) / (Math.sin(angle) * Math.cos(lat0));

		double arc = -Math.atan(R / (Math.sqrt(1 - R * R))) + Math.PI / 2;

		arc = Math.toDegrees(arc);

		if ((long1 - long0 < 0) || (long1 - long0 > Math.PI))
			arc = 360 - arc;

		return (float) arc;
	}
}