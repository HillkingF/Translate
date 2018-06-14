package interact;

import enroll.Constant;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetAll implements Callable<String>{
    private String account;
    private String word;
    private String lastchoice;
    private String selftrans;
    private String othertrans;

    private Pattern plastchoice;
    private Pattern pselftrans;
    private Pattern pothertrans;

    private Matcher mlastchoice;
    private Matcher mselftrans;
    private Matcher mothertrans;

    private String allInfomation;
    private String allothertrans;

    public GetAll(String account,String word){
        super();
        this.account = account;
        this.word = word;
    }

    @Override
    public String call() throws Exception{
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


            if (!sb.toString().equals("")) {
                //替换
                plastchoice = Pattern.compile("(.*)(<last>)(.*)(</last>)(.*)");
                mlastchoice= plastchoice.matcher(sb);
                if (mlastchoice.find()){
                   lastchoice = mlastchoice.group(3);
                }

                //自译
                pselftrans = Pattern.compile("(.*)(<selftrans>)(.*)(</selftrans>)(.*)");
                mselftrans= pselftrans.matcher(sb);
                if (mselftrans.find()){
                selftrans = mselftrans.group(3);
                }

                //他译
                pothertrans = Pattern.compile("(.*)(<othertrans>)(.*)(</othertrans>)(.*)");
                mothertrans= pothertrans.matcher(sb);
                if (mothertrans.find()){
                othertrans = mothertrans.group(3);
                }

                if(!othertrans.equals("")&&(othertrans!=null)){
                    //System.out.println("---"+othertrans);
                    String[] allothertran = othertrans.split("<span>");
                    for(int i = 0; i<allothertran.length; i++){
                        if((allothertrans!=null)&&(!allothertrans.equals(""))){
                            allothertrans = allothertrans+allothertran[i]+";";
                        }else{
                            allothertrans=allothertran[i]+";";

                        }

                    }
                }else{

                }


                //封装所有的字符串信息
                allInfomation = lastchoice+","+selftrans+","+allothertrans;
                System.out.println(allothertrans+",");


            } else if (sb.toString().equals(Constant.FLAG_FAIL)) {
                //allInfomation="错误";
                //在此功能中服务器返回的响应是一行字符串的集合
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allInfomation;
    }


    //主方法用于测试能否从数据库中获得字符串，之后还要对字符串字段进行解析，以分割出翻译等
    public static void main(String[] args){

      //  String All = new GetAll("111","word").obtain();
      //  System.out.println(All);

    }

}
