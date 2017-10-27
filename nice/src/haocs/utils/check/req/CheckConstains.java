package haocs.utils.check.req;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author haocs
 * @date 2017年10月27日
 */
public class CheckConstains {

	/**
	 * 是否包含中文
	 * 
	 * @param str
	 * @return
	 * @author haocs
	 * @date 2017年10月27日
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 数字是否正常
	 * 
	 * @param str
	 * @return
	 * @author haocs
	 * @date 2017年10月27日
	 */
	public static boolean isNum(String str) {
		return str.matches("^[-]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
}
