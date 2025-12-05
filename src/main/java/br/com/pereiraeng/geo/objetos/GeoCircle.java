package br.com.pereiraeng.geo.objetos;

import java.awt.Graphics2D;
import java.awt.Point;

import br.com.pereiraeng.geo.GeoCoordinate;
import br.com.pereiraeng.math.geometry.Circle;

/**
 * Objeto que representa um círculo de um determinado raio a ser representada
 * sobre um mapa
 * 
 * @author Philipe PEREIRA
 *
 */
public class GeoCircle extends GeoPoint {
	private static final long serialVersionUID = 1L;

	/**
	 * Raio do círculo (em radianos TODO isso não está muito certo...)
	 */
	protected double radius;

	public GeoCircle() {
		super(0f, 0f);
	}

	public GeoCircle(GeoCoordinate coordinate, double radius) {
		super(coordinate.x, coordinate.y);
		this.radius = radius;
	}

	public GeoCircle(Circle circle) {
		this(new GeoCoordinate(circle.getC()), circle.getR());
	}

	public double getRadius() {
		return radius;
	}
}
