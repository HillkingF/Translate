package translate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionUtil {
    public static String Connect(String address){
        HttpURLConnection conn = null;
        URL url = null;
        InputStream in = null;
        BufferedReader reader = null;
        StringBuffer stringBuffer = null;
        try {
            url = new URL(address);//根据地址生成URL对象
            conn = (HttpURLConnection) url.openConnection();//建立一个连接
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setConnectTimeout(5000);//设置5000秒超时
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.connect();
            System.setProperty("http.keepAlive", "false");
            System.out.println(conn.getResponseCode());
			in = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			stringBuffer = new StringBuffer();
            String line = null;
            while((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
			
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("本句无法翻译！！！");
        } finally{
            conn.disconnect();
					try {
		                in.close();
		                reader.close();
		            } catch (Exception e) {
		                e.printStackTrace();
		                //System.out.println("本句无法翻译！！！");
		            }
        }

        return stringBuffer.toString();//返回一个包含该HTML的字符串
    }
}