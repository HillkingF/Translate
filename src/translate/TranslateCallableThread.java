package translate;

import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranslateCallableThread implements Callable<String> {

	
	private String sentence;  
	private String sentenceTranslation;
	  
    public TranslateCallableThread(String sentence) {  
        this.sentence = sentence;  
    }  
  
    // 需要实现Callable的Call方法 
    @Override
    public String call() throws Exception {  
		if (sentence.startsWith(" ")) {
			sentence = sentence.substring(1);
		}
		if (sentence.contains("\n")) {
			sentence = sentence.replace("\n", "");
		}
		String url = "https://www.iciba.com/"+sentence;
		System.out.println(url);
		String result = ConnectionUtil.Connect(url);
		if (result == "200") {
			return "您划取的单词不合法！！！";
		}else {
			//System.out.println(result);
			sentenceTranslation = analyzeSentence(result);
	        return sentenceTranslation;  
		}
    }
    public static String analyzeSentence(String result) {
		String lastResult = " ";
		Pattern juziPattern = Pattern.compile("<div style=\"width: 580px; margin-top: 15px; font-size: 15px; line-height: 24px; color: #333333;\">(.*?)</div>");
		//Pattern juziPattern = Pattern.compile("<span class=\"\">(.*?)</span>");
		Matcher juziMatcher = juziPattern.matcher(result);
		while (juziMatcher.find()) {
			String[] juzi = juziMatcher.group().substring(97).split("<");
			//String[] juzi = juziMatcher.group().substring(15).split("<");
			System.out.println(juzi[0]);
			lastResult = lastResult+juzi[0]+"\n";
		}
		return lastResult;
	}
}
