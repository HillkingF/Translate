package page;

import translate.*;
import page.YiwenPage;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class Home {
    private String w;
    private String account;
    private JFrame jf;
    //private BorderLayout bl;
    //最上方的菜单栏
    private JMenu fileMenu;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;
    private JMenuBar bar;
    private Choice setF;
    private JButton setB;
    private FileDialog openDia, saveDia;
   // private File file;
    //中间文本显示
    private JScrollPane sp;
    private JTextPane txt;
    private StyledDocument doc;
    private  SimpleAttributeSet attributeSet;


    public void init() {
	//窗体处理
    jf=new JFrame("英文翻译器");
	jf.setBounds(300, 100, 650, 600);
	sp=new JScrollPane();
	//菜单处理	
	fileMenu=new JMenu("文件");
	openItem=new JMenuItem("打开");
	saveItem=new JMenuItem("保存");
	exitItem=new JMenuItem("退出");
	fileMenu.add(openItem);
	fileMenu.add(saveItem);
	fileMenu.add(exitItem);
	setF=new Choice();
	setB=new JButton("设置字体");
	bar =new JMenuBar();

	//setB.setFont(new Font("宋体",0,10));
	for(int i=10;i<=60;)
	{
		setF.add(i+"");
		i=i+2;
	}
	
	bar.add(fileMenu);
	bar.add(setB);
	bar.add(setF);
	jf.setJMenuBar(bar);
	jf.add(sp);
	//中间文本处理
	txt=new JTextPane();
	doc = txt.getStyledDocument();
	attributeSet= new SimpleAttributeSet();
	StyleConstants.setForeground(attributeSet, Color.BLACK);
	StyleConstants.setFontSize(attributeSet, 15);
	StyleConstants.setFontFamily(attributeSet, "Dialog");
	sp.setViewportView(txt);
	//StyleConstants.setUnderline(attributeSet, false);
	
	
	//对话框处理
	openDia = new FileDialog(jf, "打开", FileDialog.LOAD);
    saveDia = new FileDialog(jf, "保存", FileDialog.SAVE);
     
    myEvent();// 加载事件处理
    
      
    jf.setVisible(true);// 设置窗体可见
	
}
public static void main(String[] args) {
	new Home().init();
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
        
           
        	txt.setText(content);
    		doc.setCharacterAttributes(0 , content.length() , attributeSet, true);
            
            txt.addMouseListener(new MouseAdapter() {  
            	String lastResult = null;
            YiwenPage yiwen=new YiwenPage();  
            public void mousePressed(MouseEvent e) {
            	yiwen.f.dispose();
            }
            public void mouseReleased(MouseEvent e) {
            	//w是单词
                w=txt.getSelectedText();
            	if (w != null) {
					lastResult = TranslateWord.connect(w);
            			
        			}
            	//s1金山词霸的翻译
            	String s1=lastResult;
            	String s2="";		
            	String s3="其他用户的翻译";
            	String s4="上一次选中的翻译";
                
    			
        		if(w!=null) {
        			yiwen=new YiwenPage();
        			//System.out.println("zheg "+account );
        			yiwen.display(account,w,s1,s2,s3,s4);
        			yiwen.listen(txt);
        		      
        			
        			
        			
        			
        		}//if单词不为空
        		
        		
            	}//主界面鼠标释放
        		
				
        		
        	});//为主界面添加鼠标监听事件
            
       
        }//打开文件按钮的动作事件函数

    });//为打开文件按钮注册监听器
    
    
 // 为保存文件按钮注册监听器  
    saveItem.addActionListener(new ActionListener() {  
    @Override  
    public void actionPerformed(ActionEvent e) {  
    String path;  
    String content = txt.getText();  
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
    exitItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    });
    
    // 窗体关闭监听
    jf.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            System.exit(0);

        }

    });
    setB.addMouseListener(new MouseAdapter() {
		
		 public void mouseClicked(MouseEvent e) {
			
			 StyleConstants.setFontSize(attributeSet, Integer.parseInt(setF.getSelectedItem()));
			 doc.setCharacterAttributes(0 , txt.getText().length() , attributeSet, true);
	 }
	 }
	 );
	 
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

//用于登录界面的登录调用
public void showWindow( String getaccount) {
     init();
	 jf.toFront();
	 account = getaccount;
}


}
