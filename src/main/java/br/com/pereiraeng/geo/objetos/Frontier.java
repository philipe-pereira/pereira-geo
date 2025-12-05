package br.com.pereiraeng.geo.objetos;

import java.awt.Color;

public class Frontier extends GeoStringMark {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isLoop() {
		return true;
	}

	@Override
	protected Color getColor() {
		return Color.DARK_GRAY;
	}
}