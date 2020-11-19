package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SaveChat extends JDialog{
	 BufferedReader bufr;
	 JPanel jp=new JPanel();
	 JLabel jl=new JLabel("聊天记录");
	 JButton jb=new JButton("关闭");
	 JTextArea jsave=new JTextArea();
	 JScrollPane js=new JScrollPane(jsave);
	 String line_separator=System.getProperty("line.separator");
public SaveChat(JFrame owner, String title, Boolean b, BufferedReader bufr) 
{
	super(owner,title,b);
	this.bufr=bufr;
	this.setLocationRelativeTo(null);
	this.setLayout(null);
	init();
	this.add(js);
	this.add(jp);
	this.setSize(380, 500);
}
public void init()
{
	jp.setLayout(null);
	jp.setBackground(Color.PINK);
	jp.setBounds(0, 0, 380, 30);
	jl.setFont(new Font("楷体",Font.BOLD,17));
	jl.setForeground(Color.WHITE);
	jl.setBounds(5, 3, 100, 25);
	jp.add(jl);
	jb.setBackground(Color.WHITE);
	jb.setForeground(Color.BLACK);
	jb.setFont(new Font("楷体",Font.PLAIN,14));
	jb.setBounds(290, 3, 70, 23);
	jb.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			SaveChat.this.setVisible(false);
		}
		
	});
	jp.add(jb);
	jsave.setEditable(false);//不可编辑
	jsave.setLineWrap(true);//自动换行
	jsave.setFont(new Font("黑体",Font.PLAIN,15));
	jsave.setBackground(Color.WHITE);
	jsave.setForeground(Color.BLACK);
	js.setBounds(0, 30, 380, 470);
	try {
		String s=bufr.readLine();
		while(s!=null)
		{
			jsave.append(s+line_separator);
			 s=bufr.readLine();
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
