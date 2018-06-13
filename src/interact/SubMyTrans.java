package interact;   //交互包--这个包中放的是前端与后端单词交互的所有类

import enroll.Constant;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SubMyTrans extends Thread {

    private String account;
    private String word;
    private String translation;

    public SubMyTrans(String account, String word, String translation){
        super();
        this.account = account;
        this.word = word;
        this.translation = translation;
        System.out.println("在我的类中验证"+account+","+word+","+translation);
    }

    @Override
    public void run(){
        try {
            //1.获取账号、单词和提交的译文,account word translation 三个字符串在上面定义，是从外部获取到的

            //2.传入含有中文字符的URL，需要将其进行编码
            URL url = new URL(Constant.URL_MyTranslation + "account=" + account + "&" +
                    "word=" + java.net.URLEncoder.encode(word, "utf-8") + "&" +
                    "translation=" + java.net.URLEncoder.encode(translation, "utf-8") );

            System.out.println(account+"jihao"+word+translation);
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
            if (sb.toString().equals(Constant.FLAG_SUCCESS)) {

                System.out.println("提交翻译成功！");

            } else if (sb.toString().equals(Constant.FLAG_FAIL)) {
                //页面显示的返回码是200（失败）
                System.out.println("提交翻译失败！");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    public void submit(){
        try {
            //1.获取账号、单词和提交的译文,account word translation 三个字符串在上面定义，是从外部获取到的

            //2.传入含有中文字符的URL，需要将其进行编码
            URL url = new URL(Constant.URL_MyTranslation + "account=" + account + "&" +
                    "word=" + java.net.URLEncoder.encode(word, "utf-8") + "&" +
                    "translation=" + java.net.URLEncoder.encode(translation, "utf-8") );

            System.out.println(account+"jihao"+word+translation);
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
            if (sb.toString().equals(Constant.FLAG_SUCCESS)) {

                System.out.println("提交翻译成功！");

            } else if (sb.toString().equals(Constant.FLAG_FAIL)) {
                //页面显示的返回码是200（失败）
                System.out.println("提交翻译失败！");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    //主方法用于测试

    public static void main(String[] args){

        new SubMyTrans("111","page","页面").run();
        //System.out.println(All);

    }


}
