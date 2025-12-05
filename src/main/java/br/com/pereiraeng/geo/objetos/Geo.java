package br.com.pereiraeng.geo.objetos;

import br.com.pereiraeng.geo.GeoCoordinate;

/**
 * Interface que designa um objeto geográfico a ser desenhado sobre um mapa.
 * 
 * @author Philipe Pereira
 * 
 */
public interface Geo {

	public void addCoordinate(GeoCoordinate coordinate);

	public void setId(Object id);

	public String getKML();
}
