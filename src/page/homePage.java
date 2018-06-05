package page;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class homePage extends Application {

    Stage stage = new Stage();

  @Override
  public void start(Stage primaryStage) {
    BorderPane root = new BorderPane();
    
    primaryStage.setTitle("英语阅读器");
    MenuBar menuBar = new MenuBar();
    menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
    root.setTop(menuBar);
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(browser);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    
    root.setCenter(scrollPane);


    
    

    

    
    // File menu - new, save, exit
    Menu fileMenu = new Menu("文件");
    MenuItem newMenuItem = new MenuItem("打开");
    MenuItem saveMenuItem = new MenuItem("保存");
    MenuItem exitMenuItem = new MenuItem("退出");
    //打开操作
    newMenuItem.setOnAction(actionEvent -> Platform.runLater(() -> {
        try {
        	 FileChooser fileChooser = new FileChooser();
             FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("WORD files (*.doc)", "*.doc");
             FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("WORD1 files (*.docx)", "*.docx");
             fileChooser.getExtensionFilters().addAll(extFilter,extFilter1);
             File file = fileChooser.showOpenDialog(primaryStage);
             String path=file.getAbsolutePath();             
             String content = readWord(path);            
             webEngine.loadContent(content);
       } catch (Exception e) {
    	   System.out.println("打开失败");
       }
   }));
    //保存操作
    
    
 
    //退出操作
    exitMenuItem.setOnAction(actionEvent -> Platform.exit());

    fileMenu.getItems().addAll(newMenuItem,new SeparatorMenuItem(), saveMenuItem,
        new SeparatorMenuItem(), exitMenuItem);

    

    menuBar.getMenus().add(fileMenu);
    Scene scene = new Scene(root, 600, 400, Color.WHITE);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  
  public String readWord(String path) {  
      String buffer = "";  
      
      try {  
          if (path.endsWith(".doc")) {  
        	  FileInputStream fis = new FileInputStream(path);
              HWPFDocument doc = new HWPFDocument(fis);
              buffer = doc.getDocumentText();
              doc.close();
              fis.close();
          } else if (path.endsWith("docx")) {  
              /*OPCPackage opcPackage = POIXMLDocument.openPackage(path);  
              POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);  
              buffer = extractor.getText();  
              extractor.close();  */
        	  FileInputStream fis = new FileInputStream(path);
              XWPFDocument xdoc = new XWPFDocument(fis);
              XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
              buffer = extractor.getText();
              extractor.close();
              fis.close();

          } else {  
              System.out.println("此文件不是word文件！");  
          }  

      } catch (Exception e) {  
          e.printStackTrace();  
      }  

      return buffer;  
  }  

  public static void main(String[] args) {
    launch(args);
  }

    public void showWindow() throws IOException {
        start(stage);
    }
}
