package br.com.pereiraeng.geo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IBGE {

	private static final Pattern PAT = Pattern
			.compile("codigo=\"\\d{6}\" nome=\".+?\" link=\"/brasil/mg/[\\-\\p{Lower}]+\"");

	public static Map<Integer, String> getMunicipios(File src) {
		Map<Integer, String> out = null;
		try {
			RandomAccessFile raf = new RandomAccessFile(src, "r");

			out = new LinkedHashMap<>();

			String str = null;
			while ((str = raf.readLine()) != null) {
				str = str.trim();
				if (str.startsWith("<g _ngcontent-c21=\"\" ")) {
					Matcher m = PAT.matcher(str);
					if (m.find()) {
						String ss = new String(m.group().getBytes("ISO-8859-1"), "UTF-8");
						ss = ss.substring(8);
						int cod = Integer.parseInt(ss.substring(0, 6));
						ss = ss.substring(14);
						String nome = ss.substring(0, ss.indexOf("\""));

						out.put(cod, nome);
					}
				}
			}

			raf.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return out;
	}

	/**
	 * 
	 * @param src
	 * @param newFile
	 * @param defaultColor cor a ser usada no caso de município cuja cor não foi
	 *                     explicitamente indicada
	 * @param ibge2color   tabela de dispersão que associa para cada código do IBGE
	 *                     a cor a ser preenchida a área do município
	 */
	public static void getNewMap(File src, File newFile, String defaultColor, Map<Integer, String> ibge2color) {
		try {
			RandomAccessFile raf = new RandomAccessFile(src, "r");
			RandomAccessFile newRaf = new RandomAccessFile(newFile, "rw");

			String str = null;
			while ((str = raf.readLine()) != null) {
				str = str.trim();
				if (str.startsWith("<g _ngcontent-c21=\"\" ")) {
					Matcher m = PAT.matcher(str);
					if (m.find()) {
						String read = new String(m.group().getBytes("ISO-8859-1"), "UTF-8");

						int ibge = Integer.parseInt(read.substring(8, 14));

						String color = ibge2color.get(ibge);
						if (color == null) {
							String ss = str;
							ss = ss.substring(0, 28) + defaultColor + ss.substring(34);
							newRaf.writeBytes(ss + "\n");
						} else {
							String ss = str;
							ss = ss.substring(0, 28) + color + ss.substring(34);
							newRaf.writeBytes(ss + "\n");
						}

					} else
						newRaf.writeBytes(str + "\n");
				} else
					newRaf.writeBytes(str + "\n");
			}

			raf.close();
			newRaf.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
