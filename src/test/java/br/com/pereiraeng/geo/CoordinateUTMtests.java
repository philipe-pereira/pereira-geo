package br.com.pereiraeng.geo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class CoordinateUTMtests {

	private static final double TOL = 1e-5;

	@Test
	void testUTMconversion() {
		// https://www.engineeringtoolbox.com/docs/documents/1370/utm_vs_latitude_longitude.png
		CoordinateUTM utm = CoordinateUTM.longLat2utm(9.210989, 63.510617);

		assertEquals(utm.getZone(), 32);
		assertTrue(utm.isHn());
		assertEquals(utm.x, 510500);
		assertEquals(utm.y, 7042500);

		double[] polar = CoordinateUTM.utm2longLat((byte) 32, true, 510500, 7042500);

		assertEquals(polar[0], 9.210989, TOL);
		assertEquals(polar[1], 63.510617, TOL);

		// http://rcn.montana.edu/resources/Converter.aspx
		utm = CoordinateUTM.longLat2utm(-45.76248776717301, -21.23590542531385);

		assertEquals(utm.getZone(), 23);
		assertFalse(utm.isHn());
		assertEquals(utm.x, 420879);
		assertEquals(utm.y, 7651553);

		polar = CoordinateUTM.utm2longLat((byte) 23, false, 420879, 7651553);

		assertEquals(polar[0], -45.76248776717301, TOL);
		assertEquals(polar[1], -21.23590542531385, TOL);

		// https://coordinates-converter.com/en/decimal/31.778253,35.231587
		utm = CoordinateUTM.longLat2utm(35.231587, 31.778253);

		assertEquals(utm.getZone(), 36);
		assertTrue(utm.isHn());
		assertEquals(utm.x, 711315);
		assertEquals(utm.y, 3518025);

		polar = CoordinateUTM.utm2longLat((byte) 36, true, 711315, 3518025);

		assertEquals(polar[0], 35.231583, TOL);
		assertEquals(polar[1], 31.778257, TOL);
	}

	@Test
	void testAzimutDistance() {
		// https://keisan.casio.com/exec/system/1224587128
		GeoCoordinate coordinate1 = new GeoCoordinate(139.74477, 35.6544);
		GeoCoordinate coordinate2 = new GeoCoordinate(39.8261, 21.4225);

		assertEquals(9491280.549, coordinate1.getDistance(coordinate2), 1e4);
		assertEquals(293.052984, coordinate1.getAzimut(coordinate2), 1e-5);

		// https://opencagedata.com/tools/distance-between-two-points
		coordinate1 = new GeoCoordinate(-73.57562827261538, 45.46060837115695);
		coordinate2 = new GeoCoordinate(-43.992880490390526, -19.942264823146306);

		assertEquals(7863000., coordinate1.getDistance(coordinate2), 1e4);
		assertEquals(150.54964, coordinate1.getAzimut(coordinate2), 1e-5);
	}

}
