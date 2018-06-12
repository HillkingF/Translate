package page;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

//import page.Home;

public class YiwenPage{
	JFrame f = new JFrame("my window");// 创建窗体对象
	JDialog d1 = new JDialog(f, "注释" , false);
	 String str;
	 String w;
	//子界面上方，显示单词
	TextField tf=new TextField();
	//子界面中间，显示译文
	Box vbox=Box.createVerticalBox();
		//(1)金山词霸译文
		Border lb = BorderFactory.createLineBorder(Color.GRAY, 5);
		TitledBorder tb1 = new TitledBorder(lb , "金山词霸翻译"
				, TitledBorder.CENTER , TitledBorder.TOP
				, new Font("StSong" , Font.BOLD , 20), Color.ORANGE);
		JTextArea tt=new JTextArea();	//金山词霸的翻译		
		//(2)自己的译文
		Box myBox=Box.createVerticalBox();
		JTextField myInput=new JTextField();//输入自定义译文
		JTextArea tmy=new JTextArea();  //显示自己的译文
		TitledBorder tb2 = new TitledBorder(lb , "自定义翻译"
								, TitledBorder.CENTER , TitledBorder.TOP
								, new Font("StSong" , Font.BOLD , 20), Color.ORANGE);
		//(3)别人的译文
		JTextArea telse=new JTextArea();  //其他用户的译文
		TitledBorder tb3 = new TitledBorder(lb , "其他用户翻译"
				, TitledBorder.CENTER , TitledBorder.TOP
				, new Font("StSong" , Font.BOLD , 20), Color.ORANGE);
		//(4)上次选择的译文
		JTextArea tlast=new JTextArea();  //上次选择的翻译
		TitledBorder tb4 = new TitledBorder(lb , "上次选择的翻译"
				, TitledBorder.CENTER , TitledBorder.TOP
				, new Font("StSong" , Font.BOLD , 20), Color.ORANGE);
		

	//子界面下方，功能按键
	Box box=Box.createHorizontalBox();
	Button b1=new Button("replace");
	Button b2=new Button("recover");
	Button b3=new Button("create");
	
	
	
	
	 public void init() {
		 //设置边框与标题
		 tt.setBorder(tb1);
		 myBox.setBorder(tb2);
		 telse.setBorder(tb3);
		 tlast.setBorder(tb4);
		 //设置自动换行
		 tt.setLineWrap(true);
		 tmy.setLineWrap(true);
		 telse.setLineWrap(true);
		 tlast.setLineWrap(true);
		
		 
		 //在布局中安排各组件顺序
	
		 myBox.add(myInput);
		 myBox.add(tmy);
		 vbox.add(tt);
		 vbox.createHorizontalStrut(5);
		 vbox.add(myBox);
		 vbox.createHorizontalStrut(5);
		 vbox.add(telse);
		 vbox.createHorizontalStrut(5);
		 vbox.add(tlast);
		 vbox.createHorizontalStrut(5);
			
		 //子界面下方功能区按钮
		 box.add(b1);
		 box.add(b2);
		 box.add(b3);
		 //子界面对话框设置布局
		 d1.setBounds(100 , 100 , 500, 700);
		 d1.getContentPane().add(tf,BorderLayout.PAGE_START);
		 d1.getContentPane().add(vbox,BorderLayout.CENTER);
		 d1.getContentPane().add(box,BorderLayout.PAGE_END);   
	     d1.setVisible(false);	     	     
	     
		}
	 
	 //设置子界面显示译文	 
	 public void display(String word,String translateJS,String translateMy,String translateElse,String translateLast ) {
		 	 
		 init();	
		 if(word!=null) {
			 w=word;
				//显示子界面
			    d1.toFront();
				d1.setVisible(true);
				//显示输出单词
				tf.setText(word);
				//显示金山词霸的翻译
				if(translateJS!=null) tt.setText(translateJS);
				else tt.setText("未查询到翻译结果");
				//显示自定义的译文
				if(translateMy!=null) tmy.setText(translateMy);
				else tmy.setText("用户未曾自定义此单词的翻译");
				
				//显示其他用户的译文
				if(translateElse!=null) telse.setText(translateElse);
				else telse.setText("未查询到其他用户的 翻译");
				//显示上一次替换的译文
				if(translateLast!=null) tlast.setText(translateLast);
				else tlast.setText("未选择过此单词的翻译");
				
				
				
			}
   
	 }
	 
	
	 
	
	 //设置按钮监听器
	public void listen(JTextPane ta)  {
		
		 b1.addMouseListener(new MouseAdapter() {
    		 public void mouseReleased(MouseEvent e) {
 				
					if(tlast.getSelectedText()!=null) {
						str=tlast.getSelectedText().toString();
						tlast.setSelectionStart(0);
						tlast.setSelectionEnd(0);
						
						}
				    else if(tt.getSelectedText()!=null) {
					str=tt.getSelectedText().toString();
					tt.setSelectionStart(0);
					tt.setSelectionEnd(0);
					}
					else if(tmy.getSelectedText()!=null) {
						str=tmy.getSelectedText().toString();
						tmy.setSelectionStart(0);
						tmy.setSelectionEnd(0);
						}
					else if(telse.getSelectedText()!=null) {
						str=telse.getSelectedText().toString();
						telse.setSelectionStart(0);
						telse.setSelectionEnd(0);
						}
					  
				     
				        int i=ta.getSelectionStart();
						int j=i+str.length();
						ta.replaceSelection(str);
						ta.select(i, j);			
			  
				}	
    	 }
    	 );
		 
		 b2.addMouseListener(new MouseAdapter() {
			
    		 public void mouseReleased(MouseEvent e) {
    			 int i=ta.getSelectionStart();
    			 int j=i+w.length();
    			 ta.replaceSelection(w);
				 ta.select(i, j);
				
    	 }
		 }
    	 );
		 
		 b3.addMouseListener(new MouseAdapter() {
				
				public void mouseReleased(MouseEvent e) {
					
					if(myInput.getText()!=null) {
						tmy.setText(myInput.getText());
						myInput.setText("");
						
					}
					
				}
        });
        d1.addWindowListener(new WindowAdapter() {
	            public void windowClosing(WindowEvent evt) {
	            
	              d1.dispose();
	            }
	        });        
	
	    
	 }
	
}