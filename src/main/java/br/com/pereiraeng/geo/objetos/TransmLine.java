package br.com.pereiraeng.geo.objetos;

import java.awt.Color;

/**
 * Classe dos objeto geográficos que representam o traçado de uma linha de
 * transmissão
 * 
 * @author Philipe PEREIRA
 *
 */
public class TransmLine extends GeoStringMark {
	private static final long serialVersionUID = 1L;

	private Tensao tensao;

	@Override
	public boolean isLoop() {
		return false;
	}

	@Override
	protected Color getColor() {
		if (this.tensao == null)
			return Color.WHITE;
		else
			return this.tensao.getColor();
	}

	public void setTensao(Tensao tensao) {
		this.tensao = tensao;
	}

	public Tensao getTensao() {
		return tensao;
	}
}
