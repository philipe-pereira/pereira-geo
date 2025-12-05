package br.com.pereiraeng.geo.objetos;

public abstract class GeoStringMark extends GeoString {
	private static final long serialVersionUID = 1L;

	/**
	 * Designação das linhas geográficas
	 */
	protected String name;

	/**
	 * Descrição das linhas geográficas
	 */
	protected String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return name;
	}

	// ------------------------------ KML ------------------------------

	private String styleUrl;

	public void setStyleUrl(String styleUrl) {
		this.styleUrl = styleUrl;
	}

	@Override
	public String getKML() {
		StringBuilder out = new StringBuilder("<Placemark>\n");

		// --------------- data ---------------

		if (name != null) {
			out.append("<name>");
			out.append(name);
			out.append("</name>\n");
		}
		if (description != null) {
			out.append("<description>");
			out.append(description);
			out.append("</description>\n");
		}
		if (styleUrl != null) {
			out.append("<styleUrl>#");
			out.append(styleUrl);
			out.append("</styleUrl>");
		}

		// ------------ coordinates ------------

		if (isLoop())
			out.append("<Polygon>\n<outerBoundaryIs>\n<LinearRing>\n");
		else
			out.append("<LineString>\n");

		out.append(super.getKML());

		if (isLoop())
			out.append("\n</LinearRing>\n</outerBoundaryIs>\n</Polygon>\n");
		else
			out.append("\n</LineString>\n");

		out.append("</Placemark>\n");

		return out.toString();
	}
}
