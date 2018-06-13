package enroll;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Register extends Application {

    Stage stage = new Stage();
    @FXML
    private TextField tfAccount;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Button certain;
    @FXML
    private Label lbHint;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("enroll/Register.fxml"));
        primaryStage.setTitle("注册");
        primaryStage.setResizable(false);   //修改不可以改变窗口的大小
        primaryStage.setScene(new Scene(root, 400, 350));
        primaryStage.show();
    }

    public void showWindow() throws IOException {
        start(stage);
    }

    public void btncertain(ActionEvent actionEvent) throws IOException {

        if (tfAccount.getText().isEmpty() || pfPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "账号密码不能为空！");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (!tfAccount.getText().matches("[a-zA-Z0-9]{3,10}") || !pfPassword.getText().matches("[a-zA-Z0-9]{3,10}")) {
           // lbHint.setText("填写不符合规则！");
            Alert alert = new Alert(Alert.AlertType.ERROR, "填写不符合规则！" +
                    "请输入3到10位字母数字");
            alert.setHeaderText(null);
            alert.showAndWait();

        } else if (tfAccount.getText().matches("[a-zA-Z0-9]{3,10}") && pfPassword.getText().matches("[a-zA-Z0-9]{3,10}")) {
                lbHint.setText("");
                new Thread(() -> {
                    Platform.runLater(() -> {
                        try {
                            Register();
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "注册失败！");
                            alert.setHeaderText(null);
                            alert.showAndWait();
                        }
                    });
                }).start();
        }
    }

    public void btnback(ActionEvent actionEvent) throws IOException {
        //点击返回按钮之后返回上一级页面，同时注销此页面
        try {
            new Login().start(new Stage());
            stage = (Stage) certain.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 注册模块
     */
    public void Register() {
        try {
            // 获取账号、单词和提交的译文
            String account = tfAccount.getText();
            String password = pfPassword.getText();
            /**
             * 坑来了！！
             * 传入含有中文字符的URL，需要将其进行编码
             */
            URL url = new URL(Constant.URL_Register + "account=" + account + "&"
                    + "password=" + password);
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
                System.out.println("注册成功！");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "注册成功！");
                alert.setHeaderText("");
                alert.showAndWait();
            } else if (sb.toString().equals(Constant.FLAG_ACCOUNT_EXIST)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "该账号已被注册！");
                alert.setHeaderText("");
                alert.showAndWait();
                //System.out.println("账号已被注册！");
            } else if (sb.toString().equals(Constant.FLAG_FAIL)) {
                System.out.println("注册失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
