package translate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranslateWord {

	public static String connect(String word) {
		
		String url = "http://www.iciba.com/"+word;
		System.out.println(url);
		String result = ConnectionUtil.Connect(url);
		if (word.length()<20) {
			return analyze(result);
		}else {
			return analyzeSentence(result);
		}
		
	}
	public static String analyze(String result) {
		
		//analyzeCixing(result);
		
		//analyzeCiyi(result);
		String lastResult = " ";
		//词义的筛选
		Pattern liPattern = Pattern.compile("<li class=\"clearfix\">(.*?)</li>");
		Matcher liMatcher = liPattern.matcher(result);
		
		while (liMatcher.find()) {
			String li = liMatcher.group();
			//词性的筛选
			Pattern cixingPattern = Pattern.compile("<span class=\"prop\">(.*?)</span>");
			Matcher cixingMatcher = cixingPattern.matcher(li);
			
			while (cixingMatcher.find()) {
				String[] cixing = cixingMatcher.group().substring(19).split("<");
				//System.out.println(cixing[0]);
				lastResult = lastResult+cixing[0]+"\n";
			}
			Pattern ciyiPattern = Pattern.compile("<span>(.*?)</span>");
			Matcher ciyiMatcher = ciyiPattern.matcher(li);
			while (ciyiMatcher.find()) {
				String[] ciyi = ciyiMatcher.group().substring(6).split("<");
				lastResult = lastResult+ciyi[0]+"\n";
				//System.out.println(ciyi[0]);
			}
		}
		return lastResult;
	}
	public static String analyzeSentence(String result) {
		String lastResult = " ";
		Pattern juziPattern = Pattern.compile("<div style=\"width: 580px; margin-top: 15px; font-size: 15px; line-height: 24px; color: #333333;\">(.*?)</div>");
		Matcher juziMatcher = juziPattern.matcher(result);
		while (juziMatcher.find()) {
			String[] juzi = juziMatcher.group().substring(97).split("<");
			System.out.println(juzi[0]);
			lastResult = lastResult+juzi[0]+"\n";
		}
		return lastResult;
	}
}
