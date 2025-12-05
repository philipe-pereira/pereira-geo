package br.com.pereiraeng.geo;

/**
 * Enumeração de diferentes países
 * 
 * @author Philipe Pereira
 * 
 */
public enum Country {
	ARGELIA(213, "DZ", "DZA", "012", "argelina"), ALEMANHA(49, "DE", "DEU", "276", "alemã"),
	CAZAQUISTAO(7, "KZ", "KAZ", "398", "cazaque"), COREIA_DO_SUL(82, "KR", "KOR", "410", "sul coreana"),
	ESTADOS_UNIDOS(1, "US", "USA", "840", "estadunidense"), ARGENTINA(54, "AR", "ARG", "032", "argentina"),
	AUSTRIA(43, "AT", "AUT", "040", "austríaca"), BIELORUSSIA(375, "BY", "BLR", "112", "bielorussa"),
	BRASIL(55, "BR", "BRA", "076", "brasileira"), BULGURIA(359, "BG", "BGR", "100", "búlgara"),
	CANADA(1, "CA", "CAN", "124", "canadense"), CHILE(56, "CL", "CHL", "152", "chilena"),
	CHINA(86, "CN", "CHN", "156", "chinesa"), ESPANHA(34, "ES", "ESP", "724", "espanhola"),
	FRANCA(33, "FR", "FRA", "250", "francesa"), INDIA(91, "IN", "IND", "356", "indiana"),
	ITALIA(39, "IT", "ITA", "380", "italiana"), LIBANO(961, "LB", "LBN", "422", "libanesa"),
	MARROCOS(212, "MA", "MAR", "504", "marroquina"), MEXICO(52, "MX", "MEX", "484", "mexicana"),
	PERU(51, "PE", "PER", "604", "peruana"), POLONIA(48, "PL", "POL", "616", "polonesa"),
	PORTUGAL(351, "PT", "PRT", "620", "portuguesa"), REINO_UNIDO(44, "GB", "GBR", "826", "britânica"),
	ROMENIA(40, "RO", "ROU", "642", "romena"), RUSSIA(7, "RU", "RUS", "643", "russa"),
	SIGAPURA(65, "SG", "SGP", "702", "singapuriana"), SUICA(41, "CH", "CHE", "756", "suíça"),
	TUNISIA(216, "TN", "TUN", "788", "tunisiana"), UCRANIA(380, "UA", "UKR", "804", "ucraniana"),
	VIETNA(84, "VN", "VNM", "704", "vietnamita"), CAMAROES(237, "CM", "CMR", "120", "camaronesa"),
	CAMBOJA(855, "KH", "KHM", "116", "cambojana"), TAIWAN(886, "TW", "TWN", "158", "taiwandesa");

	public final int telephoneCode;
	public final String alpha2code, alpha3code, numericCode, nacionalidade;

	private Country(int telephoneCode, String alpha2code, String alpha3code, String numericCode, String nacionalidade) {
		this.telephoneCode = telephoneCode;
		this.alpha2code = alpha2code;
		this.alpha3code = alpha3code;
		this.numericCode = numericCode;
		this.nacionalidade = nacionalidade;
	}

	public static Country getCountry(String alpha2code) {
		for (Country d : values())
			if (d.alpha2code.equals(alpha2code))
				return d;
		return null;
	}
}
