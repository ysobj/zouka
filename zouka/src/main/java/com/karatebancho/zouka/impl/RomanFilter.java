package com.karatebancho.zouka.impl;

import org.apache.commons.lang3.StringUtils;

import com.karatebancho.zouka.Filter;

public class RomanFilter implements Filter {
	private static final String[] romanArray = new String[86];

	public RomanFilter() {
		// 0 - 85
		romanArray[0] = "xa";
		romanArray[1] = "a";
		romanArray[2] = "xi";
		romanArray[3] = "i";
		romanArray[4] = "xu";
		romanArray[5] = "u";
		romanArray[6] = "xe";
		romanArray[7] = "e";
		romanArray[8] = "xo";
		romanArray[9] = "o";
		romanArray[10] = "ka";
		romanArray[11] = "ga";
		romanArray[12] = "ki";
		romanArray[13] = "gi";
		romanArray[14] = "ku";
		romanArray[15] = "gu";
		romanArray[16] = "ke";
		romanArray[17] = "ge";
		romanArray[18] = "ko";
		romanArray[19] = "go";
		romanArray[20] = "sa";
		romanArray[21] = "za";
		romanArray[22] = "si";
		romanArray[23] = "zi";
		romanArray[24] = "su";
		romanArray[25] = "zu";
		romanArray[26] = "se";
		romanArray[27] = "ze";
		romanArray[28] = "so";
		romanArray[29] = "zo";
		romanArray[30] = "ta";
		romanArray[31] = "da";
		romanArray[32] = "ti";
		romanArray[33] = "di";
		romanArray[34] = "xtu";
		romanArray[35] = "tu";
		romanArray[36] = "du";
		romanArray[37] = "te";
		romanArray[38] = "de";
		romanArray[39] = "to";
		romanArray[40] = "do";
		romanArray[41] = "na";
		romanArray[42] = "ni";
		romanArray[43] = "nu";
		romanArray[44] = "ne";
		romanArray[45] = "no";
		romanArray[46] = "ha";
		romanArray[47] = "ba";
		romanArray[48] = "pa";
		romanArray[49] = "hi";
		romanArray[50] = "bi";
		romanArray[51] = "pi";
		romanArray[52] = "hu";
		romanArray[53] = "bu";
		romanArray[54] = "pu";
		romanArray[55] = "he";
		romanArray[56] = "be";
		romanArray[57] = "pe";
		romanArray[58] = "ho";
		romanArray[59] = "bo";
		romanArray[60] = "po";
		romanArray[61] = "ma";
		romanArray[62] = "mi";
		romanArray[63] = "mu";
		romanArray[64] = "me";
		romanArray[65] = "mo";
		romanArray[66] = "xya";
		romanArray[67] = "ya";
		romanArray[68] = "xyu";
		romanArray[69] = "yu";
		romanArray[70] = "xyo";
		romanArray[71] = "yo";
		romanArray[72] = "ra";
		romanArray[73] = "ri";
		romanArray[74] = "ru";
		romanArray[75] = "re";
		romanArray[76] = "ro";
		romanArray[77] = "xwa";
		romanArray[78] = "wa";
		romanArray[79] = "wi";
		romanArray[80] = "we";
		romanArray[81] = "wo";
		romanArray[82] = "nn";
		romanArray[83] = "vu";
		romanArray[84] = "xka";
		romanArray[85] = "xke";
	}

	@Override
	public String filter(String original) {
		String normlized = normalizePartOfRomanization(original);
		StringBuilder sb = new StringBuilder(normlized.length() * 2);
		for (int i = 0; i < normlized.length(); i++) {
			char c = normlized.charAt(i);
			if (12449 <= c && c <= 12534) {
				sb.append(romanArray[((int) c - 12449)]);
			} else if (c == 12540) {
				// 長音をスキップ
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	protected String normalizePartOfRomanization(String part) {
		if (StringUtils.isEmpty(part)) {
			return part;
		}
		if (StringUtils.endsWithAny(part, "zy", "my", "ny", "sy", "sh", "ry",
				"ty", "py", "dy", "gy", "hy", "jy", "ky", "vy", "by")) {
			return part.substring(0, part.length() - 1);
		}
		if (StringUtils.endsWithAny(part, "ch", "cy")) {
			return part.substring(0, part.length() - 2) + "t";
		}
		if (part.endsWith("j")) {
			return part.substring(0, part.length() - 1) + "z";
		}
		if (part.endsWith("c")) {
			return part.substring(0, part.length() - 1) + "t";
		}
		if (part.endsWith("fy")) {
			return part.substring(0, part.length() - 2) + "h";
		}
		if (part.endsWith("f")) {
			return part.substring(0, part.length() - 1) + "h";
		}
		return part;
	}
}
