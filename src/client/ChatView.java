package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import common.*;

public class ChatView extends JFrame implements Runnable,ActionListener,KeyListener{
	JPanel jPfriend=new JPanel();
	JLabel jLfriendPortrait=new JLabel(new ImageIcon("src/file/personelView1.jpg"));
	JLabel jLfriendName=new JLabel("一直很安静︶ ");
	JLabel jLfriendSign=new JLabel("人生最好的旅行，就是你在一个陌生的地方，发现一种久违的感动");
	JLabel image1=new JLabel(new ImageIcon("src/file/CVimage1.jpg"));//26*30
	JLabel image2=new JLabel(new ImageIcon("src/file/CVimage2.jpg"));//28*28
	JLabel image3=new JLabel(new ImageIcon("src/file/CVimage3.jpg"));//28*28
	JLabel image4=new JLabel(new ImageIcon("src/file/CVimage4.jpg"));//30*32
	JLabel image5=new JLabel(new ImageIcon("src/file/CVimage5.jpg"));//32*29
	JLabel image6=new JLabel(new ImageIcon("src/file/CVimage6.jpg"));//31*32
	/*------------显示与好友的聊天信息------------------------------*/
	JLabel point=new JLabel(new ImageIcon("src/file/CVpoint.jpg"));//17*19
	JLabel jLpoint=new JLabel(" 交谈中请勿轻信汇款、中奖信息、陌生电话，勿使用外挂软件。");
	JTextArea jTAshowChat=new JTextArea();
	JScrollPane jSshowChat=new JScrollPane(jTAshowChat);
	/*------------------------------------------------------*/
	/*-------------中间工具栏------------------------------*/
	JPanel jPinfo=new JPanel();
	JButton jBshowinfo=new JButton(new ImageIcon("src/file/CVshowInfo.jpg"));//81*22
	JLabel jLinfo=new JLabel(new ImageIcon("src/file/CVInfo.jpg"));//302*24
	JLabel jLinfo1=new JLabel(new ImageIcon("src/file/CVinfo1.jpg"));//40*25
	/*------------------------------------------------------*/
	JTextArea jTAshowsend=new JTextArea();
	JScrollPane jSshowsend=new JScrollPane(jTAshowsend);
	/*-------------侧部QQ秀------------------------------*/
	JPanel jPqq=new JPanel();
	JLabel friend=new JLabel(new ImageIcon("src/file/friend.jpg"));//140*224
	JLabel me=new JLabel(new ImageIcon("src/file/me.jpg"));//140*161
	//	JLabel friend=new JLabel(new ImageIcon("src/file/CVboy.jpg"));//140*224
//	JLabel me=new JLabel(new ImageIcon("src/file/CVgirl.jpg"));//140*161
	/*------------------------------------------------------*/
	/*------------最底部栏---------------------------------*/
	ImageIcon close=new ImageIcon("src/file/CVclose.jpg");
	ImageIcon send=new ImageIcon("src/file/CVsend.jpg");
	JButton jBclose=new JButton(close);//关闭按钮69*23
	JButton jBsend=new JButton(send);//发送信息按钮80*23
	//JLabel jLspare=new JLabel("2012 世界末日即将强势来袭");
	/*------------------------------------------------------*/
	private UserBean myInfo=null;
	private UserBean currentFriend=null;
	JFrame owner=null;
	int usePort=0;//收发数据的端口
	private String friendIp=null;//接收数据主机的IP地址
	private int friendPort=0;//接收数据的端口号
	public static final int BUFFER_SIZE=5120;//缓冲数组的大小
	private byte outBuf[]=null;//发送数据的缓冲数组
	private DatagramSocket sendSocket;//声明发送信息的数据报套接字
	private DatagramPacket sendPacket;//声明发送信息的数据包
	private DatagramSocket receiveSocket;//声明接收信息的数据报套接字
	private DatagramPacket receivePacket;//声明接收信息的数据包
	Hashtable friendInfoTable;//声明存储好友信息的哈希表
	String line_separator=System.getProperty("line.separator");//获取系统的换行符
	//*--------------------传送文件变量声明部分--------------------------------------------*/
	JFileChooser fc=new JFileChooser();
	String str;//文件里的内容
	String title=null;
	BufferedReader in;//定义输入流
	PrintStream out;//定义输出流
	//*---------------------聊天记录---------------------------------------------------------*/
	BufferedWriter bw;//完成聊天记录的写
	BufferedReader bufr;//完成聊天记录的读
	String path=null;
	//**--------------------构造方法，对成员变量进行初始化------------------------------------*/
	public ChatView(UserBean myInfo, UserBean currentFriend,
					JFrame owner, int usePort,
					DatagramSocket receiveSocket, DatagramPacket receivePacket,
					Hashtable friendInfoTable, BufferedReader in, PrintStream out) {
		// TODO Auto-generated constructor stub
		this.myInfo=myInfo;
		this.currentFriend=currentFriend;
		this.owner=owner;
		this.usePort=usePort;
		this.receiveSocket=receiveSocket;
		this.	receivePacket=receivePacket;
		this.friendInfoTable=friendInfoTable;
		this.in=in;
		this.out=out;
		friendIp=currentFriend.getIp();
		friendPort=currentFriend.getPort();
		System.out.println("好友IP："+friendIp);
		friendIp=friendIp.substring(friendIp.indexOf("/")+1);
		System.out.println("截取后的好友IP："+friendIp);
		System.out.println("好友端口："+friendPort);
		System.out.println("我的端口号："+usePort);
		init();
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.add(jPfriend);
		this.add(jPqq);
		this.add(point);
		this.add(jLpoint);
		this.add(jSshowChat);
		this.add(jPinfo);
		this.add(jBshowinfo);
		this.add(jSshowsend);
		//this.add(jLspare);
		this.add(jBclose);
		this.add(jBsend);
		this.setSize(420, 545);
		this.setVisible(true);
		try {
			//创建发送信息的数据报套接字
			sendSocket=new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("出现异常："+e.getMessage());
		}
		//--------------------以自己QQ号+好友QQ号创建文件保存聊天记录------------------------------------------//
		try {
			path="src/聊天记录/"+myInfo.getUserNum()+"-"+currentFriend.getUserNum()+".txt";
			bw=new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(path,true)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void processWindowEvent (WindowEvent e)
	{
		if(e.getID()==WindowEvent.WINDOW_CLOSING)
		{
			this.dispose();
		}
	}
	public void init()
	{
		//设置顶部面板
		jPfriend.setBounds(0, 0, 540, 82);
		jPfriend.setLayout(null);
		jPfriend.setBackground(Color.PINK);
		jLfriendPortrait.setIcon(new ImageIcon (currentFriend.getPortrait()));
		jLfriendPortrait.setBounds(5, 5, 60, 60);
		jPfriend.add(jLfriendPortrait);
		jLfriendName.setText(currentFriend.getUserName());
		jLfriendName.setFont(new Font("宋体",Font.BOLD,15));
		jLfriendName.setForeground(Color.WHITE);
		jLfriendName.setBounds(70, 4, 200, 20);
		jPfriend.add(jLfriendName);
		jLfriendSign.setText(currentFriend.getSign());
		jLfriendSign.setFont(new Font("楷体",Font.PLAIN,14));
		jLfriendSign.setForeground(Color.WHITE);
		jLfriendSign.setBounds(75, 26, 400, 20);
		jPfriend.add(jLfriendSign);
		image1.setBounds(70,48 , 26, 30);
		image1.setBackground(Color.PINK);
		jPfriend.add(image1);
		image2.setBounds(111, 48, 28, 28);
		image2.setBackground(Color.PINK);
		jPfriend.add(image2);
		image3.setBounds(154, 48, 28, 28);
		image3.setBackground(Color.PINK);
		image3.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e)
			{
				String temp=null;
				int returnVal = fc.showOpenDialog(ChatView.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
//						out.println("sendFile");
//						out.flush();
//						out.println(currentFriend.getUserNum());
//						out.flush();
//						out.println(myInfo.getUserNum());
//						out.flush();
						BufferedReader bufr=new BufferedReader(
								new InputStreamReader(new FileInputStream(
										file)));
						title=file.getName();
//						out.println(title);
//						out.flush();
						while((temp=bufr.readLine())!=null)
						{
//							out.println(temp);
//							out.flush();
							str+=temp+"\n";
						}
//						out.println("Over");
//						out.flush();
						System.out.println("文件名："+title+"文件内容"+str);
//						out.println(str);
//						out.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}

		});
		jPfriend.add(image3);
		image4.setBounds(197,48, 30, 32);
		image4.setBackground(Color.PINK);
		jPfriend.add(image4);
		image5.setBounds(242, 48, 32, 29);
		image5.setBackground(Color.PINK);
		jPfriend.add(image5);
		image6.setBounds(289, 48, 31, 32);
		image6.setBackground(Color.PINK);
		jPfriend.add(image6);
		//显示与好友的聊天信息
		point.setBounds(5, 85, 17, 19);
		point.setBackground(Color.WHITE);
		jLpoint.setBounds(25, 85, 375, 25);
		jLpoint.setFont(new Font("宋体",Font.PLAIN,12));
		jLpoint.setForeground(Color.GRAY);
		jLpoint.setBackground(Color.WHITE);
		jTAshowChat.setBackground(Color.WHITE);
		jTAshowChat.setLineWrap(true);//自动换行
		jTAshowChat.setFont(new Font("黑体",Font.PLAIN,17));
		jSshowChat.setBounds(0, 110, 400, 255);
		//设置QQ秀
		if(currentFriend.getSex().equals("女"))
		{
			friend.setIcon(new ImageIcon("src/file/friend.jpg"));
		}
		if(currentFriend.getSex().equals("男"))
		{
			friend.setIcon(new ImageIcon("src/file/CVboy.jpg"));
		}
		if(myInfo.getSex().equals("男"))
		{
			me.setIcon(new ImageIcon("src/file/me.jpg"));
		}
		if(myInfo.getSex().equals("女"))
		{
			me.setIcon(new ImageIcon("src/file/CVgirl.jpg"));
		}
		jPqq.setBackground(Color.WHITE);
		jPqq.setLayout(new BorderLayout());
		jPqq.setBounds(400, 82, 140, 428);
		friend.setSize(0, 0);
		jPqq.add(BorderLayout.NORTH,friend);
		me.setSize(0, 0);
		jPqq.add(BorderLayout.SOUTH,me);
		//中部工具栏
		jPinfo.setBounds(0, 365, 319, 22);
		jPinfo.setLayout(new BorderLayout());
		jPinfo.setBackground(Color.WHITE);
		jBshowinfo.setSize(81, 22);
		jLinfo.setSize(302, 22);
		jLinfo1.setSize(17, 22);
		jLinfo.setLocation(0, 370);
		jLinfo1.setLocation(302, 370);
		jBshowinfo.setLocation(319, 365);
		jBshowinfo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					bufr=new BufferedReader(
							new InputStreamReader(new FileInputStream(
									path)));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SaveChat save=new SaveChat(ChatView.this, "聊天记录", true, bufr);
				save.setVisible(true);
			}});
		jPinfo.add(BorderLayout.WEST,jLinfo);
		jPinfo.add(BorderLayout.EAST,jLinfo1);
		//发送信息
		jTAshowsend.setBackground(Color.WHITE);
		jTAshowsend.setFont(new Font("黑体",Font.PLAIN,15));
		jSshowsend.setBounds(0, 387, 400, 100);
		//最底部工具栏
		jBclose.setSize(69, 23);
		jBsend.setSize(80, 23);
		jBsend.setLocation(310, 487);
		jBclose.setLocation(231, 487);
		jBclose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ChatView.this.setVisible(false);
			}

		});
		jBsend.addActionListener(this);
		//添加广告标签，并为其添加渲染器
		class JLspare extends JLabel
		{
			private boolean isSupported;
			private String spare;

			public JLspare(String spare) {
				// TODO Auto-generated constructor stub
				this.spare=spare;
				try {
					this.isSupported = Desktop.isDesktopSupported()
							&& Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
				} catch (Exception e) {
					this.isSupported = false;
				}
				setText(false);
				addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent e) {
						setText(isSupported);
						if (isSupported)
							setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					public void mouseExited(MouseEvent e) {
						setText(false);
					}
					public void mouseClicked(MouseEvent e) {

						try {
							Desktop.getDesktop().browse(
									new java.net.URI("http://news.qihoo.com/zt/doomsday.html"));
						} catch (Exception ex) {
						}
					}
				});
			}
			private void setText(boolean b) {
				if (!b)
					setText( spare);
				else
					setText("<html><font color=blue><u>" + spare);
			}
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String receiveInfo="";
		while(true)
		{
//					 ReceiveFile f=new ReceiveFile(ChatView.this, in, out, myInfo, currentFriend);
//						f.receive();
			Date time = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			String timeInfo = format.format(time);
			try {
				receiveSocket.receive(receivePacket);
				receiveInfo=new String(receivePacket.getData(),0,receivePacket.getLength());
				int num_index=receiveInfo.indexOf("*");//获取*出现的位置
				int name_index=receiveInfo.indexOf("/");
				String friendNum=receiveInfo.substring(0,num_index).trim();//获取发送方的QQ号
				String friendName=receiveInfo.substring(num_index+1, name_index);//获取发送方的用户名
				String friendInfo=receiveInfo.substring(name_index+1);//获取发送方的信息
				if(!friendInfoTable.containsKey(friendNum))
				{
					ChatStrange strange=new ChatStrange(null,"收到陌生人信息",true,friendNum,friendName,friendInfo);
					strange.setVisible(true);
				}
				else
				{
					System.out.println("收到消息："+friendInfo);
					jTAshowChat.append(" "+friendName+"  "+timeInfo+line_separator);
					jTAshowChat.append("  "+friendInfo+line_separator+line_separator);
					jTAshowChat.append(line_separator);
					bw.write(" "+friendName+"  "+timeInfo);
					bw.newLine();
					bw.write("  "+friendInfo);
					bw.newLine();
					bw.flush();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("获取聊天信息失败");
			}

		}
	}
	public void jBsend_actionPerformed()
	{
		String myNum=myInfo.getUserNum();//获取我的QQ号
		String myName=myInfo.getUserName();//获取我的用户名
		String initInfo=jTAshowsend.getText().trim();//获取我要发送的信息
		String sendInfo=myNum+"*"+myName+"/"+initInfo;//装配我要发送的信息，由3部分组成，包括我的QQ号，我的用户名和要发送的信息
		outBuf=sendInfo.getBytes();//将我要发送的信息转换成字节数组
		if(initInfo.length()!=0)
		{
			try {
				Date time = new java.util.Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
				String timeInfo = format.format(time);
				sendPacket=new DatagramPacket(outBuf,outBuf.length,InetAddress.getByName(friendIp),friendPort);
				System.out.println("发送给好友信息："+friendIp+"端口"+friendPort+"  "+sendInfo);
				sendSocket.send(sendPacket);
				jTAshowChat.append(myInfo.getUserName()+"  "+timeInfo+line_separator);
				jTAshowChat.append("  "+initInfo+line_separator);
				jTAshowChat.append(line_separator+line_separator);
				jTAshowsend.setText(null);
				bw.write(myInfo.getUserName()+"  "+timeInfo);
				bw.newLine();
				bw.write("  "+initInfo);
				bw.newLine();
				bw.flush();
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(jBsend, "对方不在线，无法连接到指定地址");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(SocketException e)
			{
				JOptionPane.showMessageDialog(jBsend, "无法打开指定端口");
			}
			catch (IOException e) {
				JOptionPane.showMessageDialog(jBsend, "发送数据失败");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else
			JOptionPane.showMessageDialog(jBsend, "发送信息不能为空，请重新输入");
	}
	//用户在聊天中输入了Alt+回车符
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.isAltDown()&&(e.getKeyChar()=='\n'))
			jBsend_actionPerformed();
	}
	//响应发送按钮事件
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		jBsend_actionPerformed();
	}
	//------------------------------------下面两个方法都是为了实现接口所必须的----------------------------------*/
	@Override
	public void keyPressed(KeyEvent arg0)
	{
		//待定
	}
	@Override
	public void keyReleased(KeyEvent arg0)
	{
		//待定
	}
	//--------------------------------------------------------------------------------------------------------------*/
}