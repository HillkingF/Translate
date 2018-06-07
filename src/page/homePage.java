package page;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javafx.stage.FileChooser;
/**
 * 1、编码格式问题，utf-8，无法正常显示汉字
 * 2、窗口优化美观
 * 3、调整字体大小、格式
 * 4、保存，打开争取pdf，并且更加灵活可以供用户选择
 * @author hp
 *
 */
public class  homePage {
    private JFrame f;// 定义窗体
    private MenuBar bar;// 定义菜单栏
    private JTextArea ta;
    private Menu fileMenu;// 定义"文件"和"子菜单"菜单
    private MenuItem openItem, saveItem, closeItem;// 定义条目“退出”和“子条目”菜单项
    private JScrollPane sp;
    private FileDialog openDia, saveDia;// 定义“打开、保存”对话框
    private File file;//定义文件
    
    public homePage() {
        init();
    }

    /* 图形用户界面组件初始化 */
    public void init() {
        f = new JFrame("my window");// 创建窗体对象
        f.setBounds(300, 100, 650, 600);// 设置窗体位置和大小

        bar = new MenuBar();// 创建菜单栏
        
        sp=new JScrollPane();
        ta = new JTextArea();// 创建文本域
        ta.setLineWrap(true);
      
        sp.setViewportView(ta);
        f.add(sp);
        
        fileMenu = new Menu("file");// 创建“文件”菜单
       
        openItem = new MenuItem("open");// 创建“打开"菜单项
        saveItem = new MenuItem("save");// 创建“保存"菜单项
        closeItem = new MenuItem("exit");// 创建“退出"菜单项

        fileMenu.add(openItem);// 将“打开”菜单项添加到“文件”菜单上
        fileMenu.add(saveItem);// 将“保存”菜单项添加到“文件”菜单上
        fileMenu.add(closeItem);// 将“退出”菜单项添加到“文件”菜单上

        bar.add(fileMenu);// 将文件添加到菜单栏上

        f.setMenuBar(bar);// 将此窗体的菜单栏设置为指定的菜单栏。
        
        openDia = new FileDialog(f, "打开", FileDialog.LOAD);
        saveDia = new FileDialog(f, "保存", FileDialog.SAVE);
       
        myEvent();// 加载事件处理
      
        
        f.setVisible(true);// 设置窗体可见

    }

    private void myEvent() {
        
        // 打开菜单项监听
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            	 openDia.setVisible(true);//显示打开文件对话框
                 
                 String dirpath = openDia.getDirectory();//获取打开文件路径并保存到字符串中。
                 String fileName = openDia.getFile();//获取打开文件名称并保存到字符串中
                 String path=dirpath+fileName;
              
              
                String content = readWord(path);          
                ta.setText(content);
                
                
                
                ta.addMouseListener(new MouseAdapter() {
                	
					
                
            		public void mouseReleased(MouseEvent e) {
                		
            			String s=ta.getSelectedText();
          
            			//System.out.println(s);
            			Dialog d1 = new Dialog(f, "注释" , false);
            			
            			
            			d1.setBounds(20 , 30 , 300, 400);
            			d1.setVisible(true);
            			
            			
            			
            			TextArea tt=new TextArea();
            			d1.add(tt);
            			tt.setText(s);
            			
            			tt.append("\ngggg");
            			//d1.add(zhushi);
            			d1.addWindowListener(new WindowAdapter() {
            	            public void windowClosing(WindowEvent evt) {
            	               d1.setVisible(false);
            	            }
            	        });
                	}
            		
					
            		
            	});
                
           
            }

        });
        
        
     // 为保存文件按钮注册监听器  
        saveItem.addActionListener(new ActionListener() {  
        @Override  
        public void actionPerformed(ActionEvent e) {  
        String path;  
        String content = ta.getText();  
        JFileChooser chooser = new JFileChooser();  
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("word(*.doc)", "doc");  
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("word(*.docx)", "docx");  
        
        
        chooser.setFileFilter(filter1);  
        chooser.setFileFilter(filter2);  
        
        int res =chooser.showSaveDialog(null);
        if(res == JFileChooser.APPROVE_OPTION){  
        path = chooser.getSelectedFile().getAbsolutePath() + ".doc";// 获得保存路径  
        try {  
        // 注意编码问题  
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path), "utf-8");  
        writer.write(content);  
        writer.close();  
        } catch (IOException e1) {  
        e1.printStackTrace();  
        }  
        }  
        }  
        });  
        
        // 退出菜单项监听
        closeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });
        
        // 窗体关闭监听
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }

        });
    }

    public static void main(String[] args) {
        new homePage();
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
    
    public void showWindow() {
    	 f.toFront();
    	
    }
   

}

