package br.com.pereiraeng.geo.objetos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class GeoArea extends GeoStrings {
	private static final long serialVersionUID = 1L;

	private Color color;

	public GeoArea() {
		this(Color.WHITE);
	}

	public GeoArea(Color color) {
		this.setColor(color);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	protected Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public void drawObject(Graphics2D g) {
		BasicStroke bs = (BasicStroke) g.getStroke();
		// g.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
		// 10f, new float[] { 5f }, 0f));
		super.drawObject(g);
		g.setStroke(bs);
	}
}
