package page;

import interact.GetAll;
import translate.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.Expression;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class Home {
    private String word;
    private String account;
    private JFrame jf;
    //private BorderLayout bl;
    //最上方的菜单栏
    private JMenu fileMenu;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;
    private JMenuBar bar;
    //选择框设置字体与字号
    private JComboBox<String> setF;
    private JComboBox<String> setS;
    private JButton setB;
    private JButton setI;
    private JButton setU;
    private FileDialog openDia, saveDia;
   // private File file;
    //中间文本显示
    private JScrollPane sp;
    private JTextPane txt;
    private StyledDocument doc;
    private  SimpleAttributeSet attributeSet;
    //控制按钮交替进行
    private boolean b;
    private boolean i;
    private boolean u;
    //获取用户全部信息
    private String getresponse;
    private String lastchoice;
    private String selftrans;
    private String othertrans;
    private String allothertrans;

    private Pattern plastchoice;
    private Pattern pselftrans;
    private Pattern pothertrans;

    private Matcher mlastchoice;
    private Matcher mselftrans;
    private Matcher mothertrans;

    private Future fcall;
    ExecutorService pool = Executors.newFixedThreadPool(1);
    Callable callable;



    public void init() {
        //字体
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final String fontName[] = ge.getAvailableFontFamilyNames(); // 获取系统的本地字体
        String fontSize[]=new String[51];
        int j=0;
        for(int i=10;i<=60;i++)
        {
            fontSize[j]=i+"";
            j++;

        }
	//窗体处理
    jf=new JFrame("英文翻译器");
	jf.setBounds(300, 100, 650, 600);
	sp=new JScrollPane();
        //设置图标
        String path="/img/logal.png";
        Toolkit tool=jf.getToolkit(); //得到一个Toolkit对象
        Image myimage=tool.getImage(this.getClass().getResource(path)); //由tool获取图像
        jf.setIconImage(myimage);
	//菜单处理	
	fileMenu=new JMenu("文件");
	openItem=new JMenuItem("打开");
	saveItem=new JMenuItem("保存");
	exitItem=new JMenuItem("退出");
	fileMenu.add(openItem);
	fileMenu.add(saveItem);
	fileMenu.add(exitItem);

        //设置选择框
        setS= new JComboBox<>(fontSize);//设置字体大小
        setF= new JComboBox<>(fontName);//设置字体
        setB=new JButton("B");
        setI=new JButton("I");
        setU=new JButton("U");
        bar =new JMenuBar();
        //初始化
        b=false;
        i=false;
        u=false;

        bar.add(fileMenu);
        bar.add(setB);
        bar.add(setI);
        bar.add(setU);
        bar.add(setF);
        bar.add(setS);
	jf.setJMenuBar(bar);
	jf.add(sp);
	//中间文本处理
	txt=new JTextPane();
	doc = txt.getStyledDocument();
	attributeSet= new SimpleAttributeSet();
	StyleConstants.setForeground(attributeSet, Color.BLACK);
	StyleConstants.setFontSize(attributeSet, 18);
	StyleConstants.setFontFamily(attributeSet, "宋体");
	sp.setViewportView(txt);
        //字体设置
        setB.setBorder(BorderFactory.createRaisedBevelBorder());
        setI.setBorder(BorderFactory.createRaisedBevelBorder());
        setU.setBorder(BorderFactory.createRaisedBevelBorder());

        setB.setPreferredSize(new Dimension(25,30));
        setI.setPreferredSize(new Dimension(25,30));
        setU.setPreferredSize(new Dimension(25,30));
        setS.setSelectedItem("18");
        setF.setSelectedItem("宋体");
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
                word=txt.getSelectedText();



            	//s1金山词霸的翻译

            	String s2="";		
            	String s3="其他用户的翻译";
            	String s4="上一次选中的翻译";

            	//获取用户的全部信息
        		if(word!=null) {
                    lastResult = TranslateWord.connect(word);
                    callable = new GetAll(account,word);
                    fcall = pool.submit(callable);

                    try{
                       getresponse = fcall.get().toString();
                      String [] allTranslation=getresponse.split(",");
                        lastchoice=allTranslation[0];
                        selftrans=allTranslation[1];
                        othertrans = allTranslation[2];
                        System.out.println("jian"+othertrans);
                    }catch(InterruptedException|ExecutionException e1){
                        e1.printStackTrace();
                    }




        			yiwen=new YiwenPage();
        			yiwen.display(account,word,lastResult,selftrans,othertrans,lastchoice);
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
            try {  // 注意编码问题
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
                                  if(b==false)			{
                                      StyleConstants.setBold(attributeSet, true);
                                      // setB.setBackground(Color);
                                      setB.setBorder(BorderFactory.createLoweredBevelBorder());

                                      b=true;
                                  }
                                  else {
                                      StyleConstants.setBold(attributeSet, false);
                                      // setB.setBackground(Color.WHITE);
                                      setB.setBorder(BorderFactory.createRaisedBevelBorder());

                                      b=false;
                                  }

                                  doc.setCharacterAttributes(0 , txt.getText().length() , attributeSet, true);
                              }
                          }
    );
    setI.addMouseListener(new MouseAdapter() {

                              public void mouseClicked(MouseEvent e) {
                                  if(i==false)			{
                                      StyleConstants.setItalic(attributeSet, true);
                                      setI.setBorder(BorderFactory.createLoweredBevelBorder());
                                      i=true;
                                  }
                                  else {
                                      StyleConstants.setItalic(attributeSet, false);
                                      setI.setBorder(BorderFactory.createRaisedBevelBorder());
                                      i=false;
                                  }

                                  doc.setCharacterAttributes(0 , txt.getText().length() , attributeSet, true);
                              }
                          }
    );
    setU.addMouseListener(new MouseAdapter() {

                              public void mouseClicked(MouseEvent e) {
                                  if(u==false)			{
                                      StyleConstants.setUnderline(attributeSet, true);
                                      setU.setBorder(BorderFactory.createLoweredBevelBorder());
                                      u=true;
                                  }
                                  else {
                                      StyleConstants.setUnderline(attributeSet, false);
                                      setU.setBorder(BorderFactory.createRaisedBevelBorder());
                                      u=false;
                                  }

                                  doc.setCharacterAttributes(0 , txt.getText().length() , attributeSet, true);


                              }
                          }
    );
    setF.addItemListener(e -> {  // ②
        StyleConstants.setFontFamily(attributeSet, (String)setF.getSelectedItem());
        doc.setCharacterAttributes(0 , txt.getText().length() , attributeSet, true);

    });
    setS.addItemListener(e -> {  // ②
        StyleConstants.setFontSize(attributeSet, setS.getSelectedIndex());
        doc.setCharacterAttributes(0 , txt.getText().length() , attributeSet, true);

    });
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