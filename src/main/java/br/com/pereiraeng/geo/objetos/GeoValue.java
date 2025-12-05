package br.com.pereiraeng.geo.objetos;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import br.com.pereiraeng.geo.GeoCoordinate;

/**
 * Objeto que representa um ponto gráfico sobre o mapa portando um dado valor
 * 
 * @author Philipe PEREIRA
 *
 */
public class GeoValue extends GeoMark {
	private static final long serialVersionUID = 1L;

	private static final String TABLE = "TABLE";

	/**
	 * Nome ou descrição do objeto {@link value}
	 */
	private String label;

	/**
	 * Valor do objeto que se encontra nessa dada coordenada
	 */
	private Object value;

	public GeoValue() {
		this(0f, 0f);
	}

	public GeoValue(GeoCoordinate coordinate) {
		this(coordinate.x, coordinate.y);
	}

	public GeoValue(double lng, double lat) {
		super(lng, lat);
	}

	/**
	 * Função que estabelece o objeto associado a esta coordenada geográfica
	 * 
	 * @param value objeto associado
	 */
	public void setValue(Object value) {
		if (value instanceof Map)
			setLabel(TABLE);
		this.value = value;
	}

	/**
	 * Função que retorna o objeto associado a esta coordenada geográfica
	 * 
	 * @return objeto associado
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Função que retorna a sequência de caracteres que representa o objeto
	 * associado a esta coordenada geográfica
	 * 
	 * @return sequência de caracteres que representa o objeto associado
	 */
	public String getValueString() {
		return value != null
				? (value.getClass().isArray() ? Arrays.deepToString((Object[]) value) : this.value.toString())
				: null;
	}

	/**
	 * Função que estabelece a etiqueta do valor associado a este objeto
	 * 
	 * @param label nome do dado ou {@link #TABLE} para mais de um dado
	 *              ({@link #getValue()} retorna uma {@link Map})
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Função que retorna a etiqueta do valor associado a este objeto
	 * 
	 * @return nome do dado ou {@link #TABLE} para mais de um dado
	 *         ({@link #getValue()} retorna uma {@link Map})
	 */
	public String getLabel() {
		return label;
	}

	// ------------------------------ KML ------------------------------

	@Override
	public String getKML() {
		int id = getId();

		String name = getName();
		String description = getDescription();

		String data = null;
		if (label != null && value != null) {
			data = "<ExtendedData>\n";

			if (TABLE.equals(label)) {
				Map<?, ?> values = (Map<?, ?>) value;
				for (Entry<?, ?> e : values.entrySet())
					data += "<Data name=\"" + e.getKey() + "\"><value>" + e.getValue() + "</value></Data>\n";
			} else
				data = "<Data name=\"" + label + "\"><value>" + getValueString() + "</value></Data>\n";

			data += "</ExtendedData>\n";
		} else
			data = "";

		return "<Placemark" + (id >= 0 ? " id=\"" + id + "\"" : "") + ">\n"
				+ (name != null ? "<name>" + name + "</name>\n" : "")
				+ (description != null ? "<description>" + description + "</description>\n" : "") + data + "<Point>\n"
				+ super.getKML() + "</Point>\n" + "</Placemark>\n";
	}
}
