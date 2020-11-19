package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import common.*;

public class ChangeHead extends JDialog {
	 BufferedReader in;//定义输入流
	 PrintStream out;//定义输出流
	 UserBean myInfo;
	 JLabel jLtop=new JLabel(new ImageIcon("src/file/changeHeadtop.jpg"));//530*25
	 JButton jB1=new JButton(new ImageIcon("src/file/changeHead1.jpg"));//75*20自定义头像
	 JPanel jP=new JPanel();//显示系统头像模块
	 //JScrollPane js=new JScrollPane(jP);
	 JButton jB2=new JButton(new ImageIcon("src/file/changeHead2.jpg"));//65*20系统头像
	 JButton jB3=new JButton(new ImageIcon("src/file/changeHead3.jpg"));//90*20会员头像
	 JButton jB4=new JButton(new ImageIcon("src/file/changeHead4.jpg"));//70*20会员头像
	 JLabel jLtop2=new JLabel(new ImageIcon("src/file/changeHeadtop2.jpg"));//140*20
	 JLabel jLshow=new JLabel("推荐头像");
	 JLabel jLprepare=new JLabel("预览");
	 JButton jBsure=new JButton(new ImageIcon("src/file/changeHeadSure.jpg"));//65*20
	 JLabel por=new JLabel(new ImageIcon("src/file/personelView1.jpg"));
	 String imagePath="src/head/10-1.gif";//用户选择的图像的路径
	 PersonelView father;
	public ChangeHead(JFrame owner, String title, boolean b,
			UserBean myInfo, BufferedReader in, PrintStream out,PersonelView father) {
		// TODO Auto-generated constructor stub
		super(owner,title,b);
		this.in=in;
		this.out=out;
		this.myInfo=myInfo;
		this.father=father;
		this.setSize(530, 403);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		init();
		this.add(jLtop);
		this.add(jB1);
		this.add(jB2);
		this.add(jB3);
		this.add(jB4);
		this.add(jLtop2);
		this.add(jP);
		this.add(jLshow);
		this.add(jLprepare);
		this.add(por);
		this.add(jBsure);
		this.setVisible(true);
	}
	 public void init()
	 {
		 jLtop.setBounds(0, 0, 530, 25);
		 jB1.setBounds(0, 25, 75, 20);
		 jB2.setBounds(75, 25, 65, 20);
		 jP.setBounds(0, 70, 400, 500);
		 jP.setBackground(Color.WHITE);
		 jP.setLayout(new FlowLayout());
		 makeIcon();
		 jB3.setBounds(140, 25, 90, 20);
		 jB4.setBounds(230, 25, 70, 20);
		 jLtop2.setBounds(300, 25, 100, 20);
		 jLshow.setFont(new Font("楷体",Font.PLAIN,14));
		 jLshow.setForeground(Color.BLACK);
		 jLshow.setBounds(10,45, 400, 25);
		 jLshow.setBackground(Color.WHITE);
		 
		 jLprepare.setFont(new Font("楷体",Font.PLAIN,16));
		 jLprepare.setForeground(Color.BLACK);
		 jLprepare.setBounds(410,30, 100, 25);
		 jLprepare.setBackground(Color.WHITE);
		
		 por.setIcon(new ImageIcon(myInfo.getPortrait()));
		 por.setBounds(420, 60, 60, 60);
		 jBsure.setBounds(420, 335, 65, 20);
		 jBsure.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String image=por.getIcon().toString();
				out.println("UpdateMyportrait");
				out.flush();
				out.println(myInfo.getUserNum());
				out.flush();
				out.println(image);
				out.flush();
				try {
					String judge=in.readLine();
					if(judge.equals("updateMyportraitOver"))
					{
						ChangeHead.this.setVisible(false);
						//JOptionPane.showMessageDialog(father, "更换头像成功！");
						System.out.println("更换头像成功！");
					}
					else if(judge.equals("updateMyportraitFail"))
					{
						ChangeHead.this.setVisible(false);
						JOptionPane.showMessageDialog(father, "更换头像成功！");
						System.out.println("系统繁忙，请稍后再试！");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			 
		 });
	
	 }
	 private void makeIcon()
	 {
		 String path="src/head/";
		 try {
			RandomAccessFile file=new RandomAccessFile(path+"face.txt","r");
			long fileLongth=file.length();
			System.out.println(fileLongth);
			long filePointer=0;
			JLabel[] jLimage=new JLabel[84];
			int i=0;
			while(filePointer<fileLongth)
			{
				jLimage[i]=new JLabel(new ImageIcon(new String(path+file.readLine())));
				jLimage[i].addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent e)
					{
						String iconInfo=e.toString();
						int begin=iconInfo.indexOf("src/head");
						int last=iconInfo.lastIndexOf("-1.gif");
						imagePath=iconInfo.substring(begin, last+6);
						por.setIcon(new ImageIcon(imagePath));
						System.out.println(imagePath);
					}
				});
				jP.add(jLimage[i]);
				i++;
				filePointer=file.getFilePointer();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
