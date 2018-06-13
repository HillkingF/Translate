package interact;

import enroll.Constant;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class GetAll {
    private String account;
    private String word;
    private String AllString;

    public GetAll(String account,String word){
        super();
        this.account = account;
        this.word = word;
    }

    public String obtain(){
        try {
            //1.获取账号、单词和提交的译文,account word translation 三个字符串在上面定义，是从外部获取到的

            //2.传入含有中文字符的URL，需要将其进行编码
            URL url = new URL(Constant.URL_GetAllInfo + "account=" + account + "&" +
                    "word=" + java.net.URLEncoder.encode(word, "utf-8") );

            //接收servlet返回值，是字节
            InputStream is = url.openStream();

            // 由于is是字节，所以我们要把它转换为String类型，否则遇到中文会出现乱码
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // judge result ，页面显示的返回码是否是100（成功）
            if (!sb.toString().equals("")) {

                AllString = sb.toString();

              //  System.out.println(AllString);

            } else if (sb.toString().equals(Constant.FLAG_FAIL)) {
                //在此功能中服务器返回的响应是一行字符串的集合


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return AllString;
    }


    //主方法用于测试能否从数据库中获得字符串，之后还要对字符串字段进行解析，以分割出翻译等
    public static void main(String[] args){

        String All = new GetAll("111","word").obtain();
        System.out.println(All);



    }

}
