package translate;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranslateWord {
	
	public static String connect(String word) {
		ArrayList<Future<String>> res = new ArrayList<Future<String>>();
		if (word.length()<20) {
			String url = "http://www.iciba.com/"+word;
			System.out.println(url);
			String result = ConnectionUtil.Connect(url);
			return analyze(result);
		}else {
			System.out.println(word);
			//word.replaceAll("/s", "%20");
			String[] sentence = word.split("\\.");
			
			String sentenceTranslation = "";
			String sentenceTranslationResult = "";
			// 线程池  
	        ExecutorService pool = Executors.newFixedThreadPool(10); 
			for (int i = 0; i < sentence.length; i++) {
		        Callable<String> c1  = new TranslateCallableThread(sentence[i]);
		        // 表示异步计算的结果  
		        res.add(pool.submit(c1));
			}
			for (Future<String> r : res) {
				try {
					sentenceTranslation = r.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sentenceTranslationResult = sentenceTranslationResult+sentenceTranslation;
			}
			pool.shutdown();
			return sentenceTranslationResult;
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
	public static final boolean isChineseCharacter(String chineseStr) {  
        char[] charArray = chineseStr.toCharArray();  
        for (int i = 0; i < charArray.length; i++) {  
            if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {  
                return true;  
            }  
        }  
        return false;  
    } 
}
