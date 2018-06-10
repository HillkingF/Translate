package sample;

public class Constant {

    // http请求返回值, 与servlet中对应
    public static final String FLAG_SUCCESS = "100";
    public static final String FLAG_FAIL = "200";
    public static final String FLAG_ACCOUNT_EXIST = "300";
    public static final String FLAG_NULL = "400";
    public static final String FLAG_FORGET_PASSWORD = "500";

    //连接至远程服务器
    public static String URL_Register = "http://127.0.0.1:8080/TranServlet/registerServlet?";
    public static String URL_Login = "http://127.0.0.1:8080/TranServlet/loginServlet?";
    public static String URL_LastReplace= "http://127.0.0.1:8080/TranServlet/SetFinalReplaceServlet?";
    public static String URL_MyTranslation= "http://127.0.0.1:8080/TranServlet/SubmitMyTranslationServlet?";
    public static String URL_GetAllInfo= "http://127.0.0.1:8080/TranServlet/GetAllServlet?";
}
