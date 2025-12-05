package br.com.pereiraeng.geo;

import java.awt.Point;

import br.com.pereiraeng.core.ExtendedMath;

/**
 * Classe do objeto que representa um par de coordenadas UTM
 * 
 * @author Philipe PEREIRA
 *
 */
public class CoordinateUTM extends Point {
	private static final long serialVersionUID = 1L;

	/**
	 * UTM zone number
	 */
	private byte zone;

	/**
	 * <code>true</code> for northern hemisphere, <code>false</code> for southern
	 * hemisphere
	 */
	private boolean hn;

	/**
	 * Construtor do objeto que representa as coordenadas no sistema UTM
	 * 
	 * @param zone     UTM zone number
	 * @param hn       <code>true</code> for northern hemisphere, <code>false</code>
	 *                 for southern hemisphere
	 * @param easting  easting planar coordinate
	 * @param northing northing planar coordinate
	 */
	public CoordinateUTM(byte zone, boolean hn, int easting, int northing) {
		super(easting, northing);
		this.zone = zone;
		this.hn = hn;
	}

	/**
	 * Construtor do objeto que representa as coordenadas no sistema UTM
	 * 
	 * @param zone     UTM zone number
	 * @param band     latitude band
	 * @param easting  easting planar coordinate
	 * @param northing northing planar coordinate
	 */
	public CoordinateUTM(byte zone, char band, int easting, int northing) {
		this(zone, Character.toUpperCase(band) > 'M', easting, northing);
	}

	/**
	 * Função que retorna a zona UTM
	 * 
	 * @return UTM zone number
	 */
	public byte getZone() {
		return zone;
	}

	/**
	 * Função que indica em qual hemisfério está a coordenada
	 * 
	 * @return <code>true</code> for northern hemisphere, <code>false</code> for
	 *         southern hemisphere
	 */
	public boolean isHn() {
		return hn;
	}

	@Override
	public String toString() {
		return String.format("%d%c %d %d", this.zone, this.hn ? 'N' : 'S', super.x, super.y);
	}

	/**
	 * Função que calcula a distância, em metros, entre duas coordenadas na Terra
	 * 
	 * @param coordinate coordenada a ser calculada a distância
	 * @return distância em metros
	 */
	public float getDistance(CoordinateUTM coordinate) {
		return CoordinateUTM.getDistance(this, coordinate);
	}

	/**
	 * Função que calcula a distância, em metros, entre duas coordenadas na Terra
	 * 
	 * @param c0 uma coordenada
	 * @param c1 a outra coordenada
	 * @return distância em metros
	 */
	public static float getDistance(CoordinateUTM c0, CoordinateUTM c1) {
		// TODO ineficiente, ver se há algo mais simples
		GeoCoordinate gc0 = new GeoCoordinate(c0.getLongLat());
		GeoCoordinate gc1 = new GeoCoordinate(c1.getLongLat());
		return gc0.getDistance(gc1);
	}

	/**
	 * Função que converte estas coordenadas UTM em coordenadas polares
	 * 
	 * @return vetor com duas posições, sendo a primeira a longitude e a segunda a
	 *         latitude
	 */
	public double[] getLongLat() {
		return utm2longLat(this.getZone(), this.isHn(), this.x, this.y);
	}

	// ------------------------------ AUXILIARES ------------------------------

	// equações de Coticchia-Surace

	/**
	 * scale factor
	 */
	private static final double UTM_K0 = 0.9996;

	/**
	 * false easting
	 */
	private static final int UTM_FE = 500_000;

	/**
	 * false northing, southern hemisphere
	 */
	private static final int UTM_FN_S = 10000000;

	private static final double C_K0 = UTM_K0 * GeoConsts.WGS84_C;

	/**
	 * Função que converte coordenadas polares em UTM
	 * 
	 * @param lon longitude da coordenada em graus, expressa na forma de um número
	 *            decimal
	 * @param lat latitude da coordenada em graus, expressa na forma de um número
	 *            decimal
	 * @return coordenadas UTM
	 */
	public static CoordinateUTM longLat2utm(double lon, double lat) {
		boolean hn = lat > 0.;
		lat = Math.toRadians(lat);

		byte zone = (byte) Math.floor(lon / 6 + 31);
		lon = Math.toRadians(lon);
		lon -= Math.toRadians(6 * zone - 183);

		double cosLatSinLon = Math.cos(lat) * Math.sin(lon);
		double cos2 = Math.pow(Math.cos(lat), 2);
		double A1 = Math.sin(2 * lat);
		double v = C_K0 / Math.sqrt(1 + GeoConsts.WGS84_EP2 * cos2);
		double J2 = lat + A1 / 2;
		double atanh = ExtendedMath.atanh(cosLatSinLon);
		double atanh2 = Math.pow(atanh, 2);

		double easting = atanh * v * (1 + GeoConsts.WGS84_EP2 * cos2 * atanh2 / 6) + UTM_FE;

		double northing = (Math.atan(Math.tan(lat) / Math.cos(lon)) - lat)
				* (1 + GeoConsts.WGS84_EP2 / 2 * atanh2 * cos2) * v
				+ C_K0 * (lat - 5.054622556e-3 * J2 + 4.258201531e-5 * (3 * J2 + A1 * cos2) / 4
						- 1.674057895e-7 * (A1 * cos2 * cos2 + (A1 * cos2 + 3 * J2) * 5 / 4) / 3);
		if (!hn)
			northing += UTM_FN_S;

		return new CoordinateUTM(zone, hn, (int) Math.round(easting), (int) Math.round(northing));
	}

	/**
	 * Função que converte coordenadas UTM em polares
	 * 
	 * @param zone     UTM zone number
	 * @param hn       <code>true</code> for northern hemisphere, <code>false</code>
	 *                 for southern hemisphere
	 * @param easting  easting planar coordinate
	 * @param northing northing planar coordinate
	 * @return vetor com duas posições, sendo a primeira a longitude e a segunda a
	 *         latitude
	 */
	public static double[] utm2longLat(byte zone, boolean hn, int easting, int northing) {
		if (!hn)
			northing -= UTM_FN_S;

		double phi = northing / (GeoConsts.WGS84_A * UTM_K0);
		double cos2 = Math.pow(Math.cos(phi), 2);
		double A1 = Math.sin(2 * phi);
		double v = C_K0 / Math.sqrt(1 + GeoConsts.WGS84_EP2 * cos2);
		double a = (easting - UTM_FE) / v;
		double sigma = GeoConsts.WGS84_EP2 * Math.pow(a, 2) * cos2 / 2;
		double alpha = GeoConsts.WGS84_EP2 * 3 / 4;
		double J2 = phi + A1 / 2;
		double k = (3 * J2 + A1 * cos2) * 5 / 4;
		double eta = (northing - C_K0 * (phi - alpha * J2 + Math.pow(alpha, 2) / 3 * k
				- Math.pow(alpha, 3) * 35 / 27 * (k + A1 * cos2 * cos2) / 3)) / v * (1 - sigma) + phi;
		double deltaLambda = Math.atan(Math.sinh(a * (1 - sigma / 3)) / Math.cos(eta));
		double tau = Math.atan(Math.cos(deltaLambda) * Math.tan(eta)) - phi;

		double latitude = Math
				.toDegrees(phi + (1 + GeoConsts.WGS84_EP2 * cos2 - GeoConsts.WGS84_EP2 * A1 * tau * 3 / 4) * tau);
		double longitude = Math.toDegrees(deltaLambda) + zone * 6 - 183;

		return new double[] { longitude, latitude };
	}
}
