package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import page.homePage;

import java.io.*;
import java.net.URL;

public class Login extends Application {

    Stage stage = new Stage();

    @FXML
    private TextField tfAccount;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Button btnSignIn;
    @FXML
    private Label lbHint;
    @FXML
    private Button btnSignUp;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/Login.fxml"));
        primaryStage.setTitle("文档翻译器");
        primaryStage.setResizable(false);   //修改不可以改变窗口的大小
        primaryStage.setScene(new Scene(root, 400, 350));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void SignIn(ActionEvent actionEvent) {


        if (tfAccount.getText().equals("") || pfPassword.getText().equals("")) {
          Alert alert = new Alert(Alert.AlertType.ERROR, "账号密码不能为空！");
          alert.setHeaderText(null);
          alert.showAndWait();
        } else{
            new Thread(() -> {
                Platform.runLater(() -> {
                    try {

                        //下面这三句暂时用于成员调试续写
                        new homePage().showWindow();
                        stage = (Stage) btnSignIn.getScene().getWindow();
                        stage.close();
                        // Login();  这个调用暂时屏蔽，以便成员登录查看
                    } catch (Exception e) {
                      Alert alert = new Alert(Alert.AlertType.ERROR, "登陆失败！");
                      alert.setHeaderText(null);
                      alert.showAndWait();
                    }
                });
            }).start();

        }

    }


    /**
     * 注册按钮 点击事件
     *
     * @param actionEvent
     * @throws IOException
     */
    public void SignUp(ActionEvent actionEvent) throws IOException {
        //跳转到注册界面
        new Register().showWindow();

        //销毁当前窗口
        stage = (Stage) btnSignUp.getScene().getWindow();
        stage.close();
    }

    /**
     * 登录模块
     */
    public void Login() {
        try {
            // 获取账号、单词和提交的译文
            String account = tfAccount.getText();
            String password = pfPassword.getText();
            /**
             * 坑来了！！
             * 传入含有中文字符的URL，需要将其进行编码
             */
            URL url = new URL(Constant.URL_Login + "account=" + account + "&"
                    + "password=" + password );
            /* 接收servlet返回值，是字节 */
            InputStream is = url.openStream();

            // 由于is是字节，所以我们要把它转换为String类型，否则遇到中文会出现乱码
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // judge result
            if (sb.toString().equals(Constant.FLAG_SUCCESS)) {

                System.out.println("登陆成功！");
                /*
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "登陆成功！");
                alert.setHeaderText("");
                alert.showAndWait();
                */
                /*
                在这里扩展登陆成功后的页面跳转
                 */
                //跳转到注册界面
                new homePage().showWindow();

                //销毁当前窗口
                stage = (Stage) btnSignIn.getScene().getWindow();
                stage.close();


            } else if (sb.toString().equals(Constant.FLAG_FAIL)) {
              //  Alert alert = new Alert(Alert.AlertType.ERROR, "输入错误！");
             //   alert.setHeaderText("");
              //  alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
