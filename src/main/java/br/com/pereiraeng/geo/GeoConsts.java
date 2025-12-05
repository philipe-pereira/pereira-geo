package br.com.pereiraeng.geo;

import br.com.pereiraeng.core.ExtendedMath;

public class GeoConsts {

	/**
	 * Raio da Terra, em metros
	 */
	public static final double EARTH_RADIUS = 6372797.560856;

	/**
	 * Quantos graus tem um quilômetro no sentido leste-oestre a latitude zero
	 */
	public static final double GRAUS_POR_KM_LAT_0 = 360000 / (ExtendedMath.TWO_PI * EARTH_RADIUS);

	// WGS-84

	/**
	 * major axis/Equatorial radius (WGS-84)
	 */
	protected static final double WGS84_A = 6_378_137;

	/**
	 * minor axis/Polar radius (WGS-84)
	 */
	protected static final double WGS84_B = 6_356_752.3142;

	/**
	 * Polar Radius of Curvature (WGS-84)
	 */
	protected static final double WGS84_C = WGS84_A * WGS84_A / WGS84_B;

	/**
	 * Second Eccentricity Squared (WGS-84)
	 */
	protected static final double WGS84_EP2 = (WGS84_A * WGS84_A - WGS84_B * WGS84_B) / (WGS84_B * WGS84_B);
}