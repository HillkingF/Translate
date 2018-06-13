package interact;

import enroll.Constant;
import org.apache.xmlbeans.impl.xb.xsdschema.All;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetAll {
    private String account;
    private String word;
    private String AllString;
    private String lastchoice;
    private String selftrans;
    private String othertrans;

    private Pattern plastchoice;
    private Pattern pselftrans;
    private Pattern pothertrans;

    private Matcher mlastchoice;
    private Matcher mselftrans;
    private Matcher mothertrans;

    private StringBuffer reponse;


    public GetAll(String account,String word){
        super();
        this.account = account;
        this.word = word;

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
            System.out.println(sb.toString());
//            //替换
//            plastchoice = Pattern.compile("(.*)(<last>)(.*)(</last>)(.*)");
//            mlastchoice= plastchoice.matcher(sb);
//            if (mlastchoice.find()){
//                lastchoice = mlastchoice.group(3);
//            }
//            //自译
//            pselftrans = Pattern.compile("(.*)(<selftrans>)(.*)(</selftrans>)(.*)");
//            mselftrans= pselftrans.matcher(sb);
//            if (mselftrans.find()){
//                selftrans = mselftrans.group(3);
//            }
//            //他译
//            pothertrans = Pattern.compile("(.*)(<othertrans>)(.*)(</othertrans>)(.*)");
//            mothertrans= pothertrans.matcher(sb);
//            if (mothertrans.find()){
//                othertrans = mothertrans.group(3);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getreplace(){
        return lastchoice;
    }
    public String getselftrans(){
        return selftrans;
    }

    public String getothertrans() {
        return othertrans;
    }


    //主方法用于测试能否从数据库中获得字符串，之后还要对字符串字段进行解析，以分割出翻译等
    public static void main(String[] args){

//        String a = new GetAll("111","page").getreplace();
//        String b = new GetAll("111","page").getselftrans();
//        String c = new GetAll("111","page").getothertrans();
//        System.out.println("get替换"+a);
//        System.out.println("getziyi"+b);
//        System.out.println("zheshi他译"+c);





    }

}
