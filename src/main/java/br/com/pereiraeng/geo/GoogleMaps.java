package br.com.pereiraeng.geo;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;

public class GoogleMaps {

	/**
	 * Busca no google maps (o string a ser inserido deve ser as coordenadas em na
	 * forma [lat],[long], onde o separador decimal é o ponto)
	 */
	private static final String MAPS = "https://www.google.com/maps/@%s,%dz";

	private static final int ZOOM = 18;

	/**
	 * Função que abre no navegador web o google maps sobre uma dada localização
	 * 
	 * @param coords coordenadas da localização a ser exibida, no formato
	 *               XX.XXXX,XX.XXXX (latitude,longitude)
	 */
	public static void openMap(String coords) {
		try {
			URI u = new URL(String.format(MAPS, coords, ZOOM)).toURI();
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				desktop.browse(u);
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Função que abre no navegador web o google maps sobre uma dada localização
	 * 
	 * @param lon longitude
	 * @param lat latitude
	 */
	public static void openMap(float lon, float lat) {
		openMap(String.format(Locale.US, "%f,%f", lat, lon));
	}
}