package br.com.pereiraeng.geo.objetos;

/**
 * Objeto que representa um ponto gráfico sobre o mapa apontado por uma etiqueta
 * fixa no mapa
 * 
 * @author Philipe PEREIRA
 *
 */
public class GeoTag extends GeoMark {
	private static final long serialVersionUID = 5376684068102307075L;

	protected final int xTag;

	protected final int yTag;

	public GeoTag(double longitude, double latitude, int xTag, int yTag, String tag) {
		super(longitude, latitude);
		setDescription(tag);
		this.xTag = xTag;
		this.yTag = yTag;
	}
}
