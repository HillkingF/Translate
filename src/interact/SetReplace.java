package interact;

import enroll.Constant;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SetReplace {
    private String account;
    private String word;
    private String lastchoice;

    public SetReplace(String account,String word,String lastchoice){
        super();
        this.account = account;
        this.word = word;
        this.lastchoice = lastchoice;
    }

    public void submit(){
        try {
            //1.获取账号、单词和提交的译文,account word translation 三个字符串在上面定义，是从外部获取到的

            //2.传入含有中文字符的URL，需要将其进行编码
            URL url = new URL(Constant.URL_LastReplace + "account=" + account + "&" + "word=" + word + "&" + "lastchoice=" + lastchoice );
            System.out.println(lastchoice);

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

                System.out.println("更新成功");

            } else if (sb.toString().equals(Constant.FLAG_FAIL)) {
                //页面显示的返回码是200（失败）
                System.out.println("更新失败");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //主方法用于测试能否从数据库中获得字符串，之后还要对字符串字段进行解析，以分割出翻译等

    public static void main(String[] args){

        new SetReplace("111","word","zimu").submit();
        //System.out.println(All);



    }


}
