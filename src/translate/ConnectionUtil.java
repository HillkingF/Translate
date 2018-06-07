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
            conn.setConnectTimeout(5000);//设置5000秒超时
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.connect();
            in = conn.getInputStream();
            reader = new BufferedReader(new      InputStreamReader(in));
            stringBuffer = new StringBuffer();
            String line = null;
            while((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            conn.disconnect();
            try {
                in.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stringBuffer.toString();//返回一个包含该HTML的字符串
    }
}