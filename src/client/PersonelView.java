package client;
/*Has
 * HashMap<String,String> map=new HashMap<String,String>;
 * map.put("1","student");
 *Iterator<String> mit=map.keySet().iterator();
 *while(mit.hasNext()){
 *String str=mit.next();
 *System.out.println(map.get(str));
 *} */
//dengluh
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.plaf.OptionPaneUI;

import common.FriendLabel;
import common.UserBean;
import common.UserInfo;

/*������*/
public class PersonelView extends JFrame implements Runnable{
	/*-------------------------------------�������---------------------------------------------------*/
	JPanel jPtop=new JPanel();//���ڶ���
	JPanel jPcentre=new JPanel();//�����м��ź����б�
	//����
	JLabel jLtitle=new JLabel("ISTORM");
	//QQͷ��ߴ�60*60
	JLabel jLportrait=new JLabel(new ImageIcon("src/file/personelView1.jpg"));
	JLabel jLmyName=new JLabel("����ëë��");
	JLabel jLmySign=new JLabel("����ǩ��");
	JTextField jTfind=new JTextField("����ISTORM�˺Ų�����ϵ��");
	ImageIcon imageFind=new ImageIcon("src/file/personelViewFind.jpg");//�ߴ�19*21
	JButton jBfind=new JButton(imageFind);//������ϵ�˰�ť
	//����
	JLabel jLcenter1=new JLabel(new ImageIcon("src/file/personelViewCenter1.jpg"));//�ߴ�305*7
	//�в�
	JTabbedPane jTPchoose=new JTabbedPane();//ѡ���
	JPanel jPcentrefriend=new JPanel();//�����м���м��ź����б�
	JLabel j1=new JLabel(new ImageIcon("src/file/���1.jpg"));
	JLabel j2=new JLabel(new ImageIcon("src/file/���2.jpg"));
	JLabel j3=new JLabel(new ImageIcon("src/file/���1.jpg"));
	JLabel j4=new JLabel(new ImageIcon("src/file/���2.jpg"));
	DefaultListModel listModel=new DefaultListModel();
	JList userList=new JList(listModel);
	JScrollPane jSuserList=new JScrollPane(userList);
	//�����û��ı�ǩ�������⡰�ĵ���ʽ�˵�
	JMenuItem jM11=new JMenuItem("�б���ʾ");
	JMenuItem jM12=new JMenuItem("ˢ�º����б�");
	JMenuItem jM13=new JMenuItem("��ʾ������ϵ��");
	JMenuItem jM14=new JMenuItem("�����ϵ��");
	JMenuItem jM15=new JMenuItem("���Һ���");
	JMenuItem jM16=new JMenuItem("���ѹ�����");
	JMenuItem jM17=new JMenuItem("����");
	JMenuItem jM18=new JMenuItem("����");
	JPopupMenu jPmenuser=new JPopupMenu();
	//�����б�ĵ���ʽ�˵�ѡ��
	JMenuItem jM1=new JMenuItem("���ͼ�ʱ��Ϣ");
	JMenuItem jM2=new JMenuItem("���͵����ʼ�");
	JMenuItem jM3=new JMenuItem("�����ļ�");
	JMenuItem jM4=new JMenuItem("ɾ������");
	JMenuItem jM5=new JMenuItem("�ٱ����û�");
	JMenuItem jM6=new JMenuItem("�޸ı�ע����");
	JMenuItem jM7=new JMenuItem("��Ϣ��¼");
	JMenuItem jM8=new JMenuItem("�鿴����");
	JPopupMenu jPmenufriend=new JPopupMenu();
	//�ײ�
	JLabel jLbase=new JLabel(new ImageIcon("src/file/personelView2.jpg"));//�ߴ�304*61
	
	/*-------------------------------------����ʵ��---------------------------------------------------*/
	private Hashtable friendInfoTable = new Hashtable();//�洢�����б�
	Socket socket;//�����׽ӿ�
	 BufferedReader in;//����������
	 PrintStream out;//���������
	 InetAddress ip=null;//������IP
	 int port=0;//�������˿ں�
	 String userNum;//��½�û��Լ���QQ��
	 String userPass;//��½�û��Լ�������
	 HomePage login;
	private int currentIndex=0;//�����ָ���б�����
	private String currentInfo="";//�����ָ���б�ֵ
	private String currentUserNum=null;//�����ָ���ѵ�QQ����
	private UserBean currentFriend=null;//�����ָ���ѵ���Ϣ��
	private UserBean myInfo=new UserBean();//�洢�Լ�����Ϣ
	UserBean findUserBean=new UserBean();//�洢���ҵ����û��Ļ�����Ϣ
	/*����UDPЭ�����ͨ��*/
	private DatagramSocket  receiveSocket=null;//����������Ϣ�����ݰ��׽���
	private DatagramPacket  receivePacket=null;//����������Ϣ�����ݰ�
	int udpPort=getUdpPort("udp.Port");//UDP�ĳ�ʼ�˿ں�
	InetAddress userIp = null ;
	int usePort=getNextPort(udpPort);//�����û�ʹ�õĶ˿ں�
	public static final int BUFFER_SIZE=5120;//��������Ĵ�С
	private byte inBuf[];//�������ݵĻ�������
	//ʵ�������¼
	 BufferedReader bufr;//��������¼�Ķ�
	 String path=null;
	/*-------------------------------------���췽��---------------------------------------------------*/
	public PersonelView(String userNum,String userPass,HomePage login,InetAddress ip,int port) {
		// TODO Auto-generated constructor stub
		this.userNum=userNum;
		this.userPass=userPass;
		this.login=login;
		this.ip=ip;
		this.port=port;
		this.setSize(313, 590);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		init();
		this.add(jPtop);
		this.add(jLcenter1);
		this.add(jLbase);
		this.add(jTPchoose);
		//*------------------------����TCPЭ�����������ʼ���ӣ���ɵ�½�Լ���������---------------------------------------*/
		try
		{
			socket=new Socket(ip,port);
		System.out.println("���������ʼ����");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("�������˿ڴ򿪳���");
		}
		if(socket!=null)
		{
			System.out.println("����������ӳɹ�");
			try {
				userIp= InetAddress.getLocalHost();
			} catch (UnknownHostException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			//udpPort=socket.getLocalPort();
			try {
				in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out=new PrintStream(socket.getOutputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
		//*--------------------------------------UDPЭ�飬����ģ��-----------------------------------------*/
		try {
			//����������Ϣ�����ݰ��׽���
		//	System.out.println("1�ҽ������ݵĶ˿ں�:"+usePort);
			receiveSocket=new DatagramSocket(usePort);
			//System.out.println("2�ҽ������ݵĶ˿ں�:"+usePort);
			inBuf=new byte[BUFFER_SIZE];
			//����������Ϣ�����ݱ�
			receivePacket=new DatagramPacket(inBuf,BUFFER_SIZE);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("�˿ڴ򿪳���");
		}	
		loadUserInfo();
		//�����߳�����ˢ�º�����Ϣ
		new Thread(this).start();
		this.setVisible(true);
	}
	//��ȡ������Ϣ
		private void loadUserInfo()
		{
			if(login())
			{
			getFriendInfo();//��ȡ������Ϣ�б�
			userList.setCellRenderer(new FriendLabel());
			jLportrait.setIcon(new ImageIcon(myInfo.getPortrait()));
			jLmyName.setText(myInfo.getUserName());
			jLmySign.setText(myInfo.getSign());
			}
			else
			{
				login.loginFail();
				System.out.println("��ȡ��Ϣʧ�ܣ�");
				return;
			}
		}
		//ÿ��10��ˢ��һ�κ�����Ϣ
		@Override
		public void run() {
			while(true)
			{
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//ˢ���û���Ϣ
				loadUserInfo();
			}
			
		}
	public void init()
	{
		//�������
		jPtop.setBounds(0, 0, 313, 110);
		jPtop.setLayout(null);
		jPtop.setBackground(Color.BLUE);
		jLtitle.setForeground(Color.WHITE);
		jLtitle.setFont(new Font("����",Font.BOLD,15));
		jLtitle.setBounds(0, 0, 313, 25);
		jLportrait.setBounds(5, 25, 60, 55);
		
		//Ϊ�Լ�ͷ�����ü������鿴�Լ���Ϣ
		jLportrait.addMouseListener(new PersonelView_jLportrait_mouseMotionAdapter());
		jLmyName.setForeground(Color.WHITE);
		jLmyName.setFont(new Font("����",Font.BOLD,17));
		jLmyName.setBounds(80, 27, 180, 25);
		//�ڸ�����������ӵ���ʽ�˵�
		JMenuItem jMa=new JMenuItem("�޸ĸ�������");
		JMenuItem jMb=new JMenuItem("����ͷ��");
		//JMenuItem jMc=new JMenuItem("�޸�����");
		jMa.setFont(new Font("����",Font.PLAIN,14));
		jMa.setForeground(Color.BLACK);
		jMa.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
							ChangeMyInfo changemyInfo=new ChangeMyInfo(PersonelView.this, "�޸�����", true, in, out, myInfo,PersonelView.this);
								changemyInfo.setVisible(true);
				PersonelView.this.refreshMyInfo();
			}
			
		});
		jMb.setFont(new Font("����",Font.PLAIN,14));
		jMb.setForeground(Color.BLACK);
		jMb.addActionListener(new ActionListener(){//����ͷ��

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				changHead();
			
			}
			
		});
		//jMc.setFont(new Font("����",Font.PLAIN,14));
		//jMc.setForeground(Color.BLACK);
		JPopupMenu jPmenuMy=new JPopupMenu();
		jPmenuMy.add(jMa);
		jPmenuMy.add(jMb);
		//jPmenuMy.add(jMc);
		jLmyName.setComponentPopupMenu(jPmenuMy);
		jLmySign.setForeground(Color.WHITE);
		jLmySign.setFont(new Font("����",Font.PLAIN,14));
		jLmySign.setBounds(70, 55, 235, 25);
		jLmySign.setComponentPopupMenu(jPmenuMy);
		jTfind.setFont(new Font("����",Font.BOLD,12));
		jTfind.setForeground(Color.GRAY);
		jTfind.setBounds(3, 85, 260, 25);
		jBfind.setBounds(267, 87, 19, 21);
		jBfind.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String num=jTfind.getText().trim();
				findUser(num);
			}
		});
		jPtop.add(jBfind);
		jPtop.add(jTfind);
		jPtop.add(jLmySign);
		jPtop.add(jLmyName);
		jPtop.add(jLportrait);
		jPtop.add(jLtitle);
		jPtop.add(jLtitle);
		//����
		jLcenter1.setBounds(0, 110, 313, 7);
		//�м�
		/*--------------------------���ӵ���ʽ�˵�---------------------*/
		jPmenufriend.add(jM1);
		jPmenufriend.add(jM2);
		jPmenufriend.add(jM3);
		jPmenufriend.add(jM4);
		jPmenufriend.add(jM5);
		jPmenufriend.add(jM6);
		jPmenufriend.add(jM7);
		jM7.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				path="src/�����¼/"+myInfo.getUserNum()+"-"+currentFriend.getUserNum()+".txt";
				 try {
					bufr=new BufferedReader(
								new InputStreamReader(new FileInputStream(
										path)));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SaveChat save=new SaveChat(PersonelView.this, "�����¼", true, bufr);
				save.setVisible(true);
			}});
		jPmenufriend.add(jM8);
		jM1.setFont(new Font("����",Font.PLAIN,14));
		jM1.setForeground(Color.BLUE);
		jM1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new Thread(new ChatView(myInfo,currentFriend,PersonelView.this,usePort,receiveSocket,receivePacket,friendInfoTable,in,out)).start();
			}
			
		});
		jM2.setFont(new Font("����",Font.PLAIN,14));
		jM2.setForeground(Color.BLUE);
		jM3.setFont(new Font("����",Font.PLAIN,14));
		jM3.setForeground(Color.BLUE);
		jM4.setFont(new Font("����",Font.PLAIN,14));
		jM4.setForeground(Color.BLUE);
		jM4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int option=JOptionPane.showConfirmDialog(PersonelView.this, "�ף���ȷ��Ҫɾ���˺���ô��");
				if(option==JOptionPane.YES_OPTION)
				{
					int index=userList.getSelectedIndex();
					String friendNum=null;
					if(index==-1)
					{
						JOptionPane.showMessageDialog(PersonelView.this, "�뵥��ѡ��һ���û���");
					}
					else 
					{
						try {
							String friendInfo=(String)listModel.getElementAt(index);
							friendNum=friendInfo.substring(friendInfo.indexOf("<")+1,friendInfo.indexOf(">") );
							UserBean deleteFriend=(UserBean)friendInfoTable.get(friendNum);
							String myUserNum=myInfo.getUserNum();
							//�����������ɾ����������
							out.println("deleteFriend");
							out.flush();
							//�����Լ�QQ
							out.println(myUserNum);
							out.flush();
							//���ͺ���QQ
							out.println(friendNum);
							out.flush();
							String judge_delete=in.readLine();
							if(judge_delete.equals("deleteFriendOver"))
							{
								JOptionPane.showMessageDialog(PersonelView.this, "���� <"+deleteFriend.getUserName()+"> �ѱ��ɹ�ɾ�� !");
								listModel.remove(index);
							}
							else if(judge_delete.equals("deleteFriendFail"))
							{
								JOptionPane.showMessageDialog(PersonelView.this, "ϵͳ��æ,���Ժ����ԣ�");
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							JOptionPane.showMessageDialog(PersonelView.this, "ϵͳ��æά���У�");
						}
					}
				}
			}
			
		});
		jM5.setFont(new Font("����",Font.PLAIN,14));
		jM5.setForeground(Color.BLUE);
		jM6.setFont(new Font("����",Font.PLAIN,14));
		jM6.setForeground(Color.BLUE);
		jM7.setFont(new Font("����",Font.PLAIN,14));
		jM7.setForeground(Color.BLUE);
		jM8.setFont(new Font("����",Font.PLAIN,14));
		jM8.setForeground(Color.BLUE);
		jM8.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				UserInfo friendInfo=new UserInfo(PersonelView.this,"��������",true,currentFriend);
				friendInfo.setVisible(true);
			}
			
		});
		userList.setComponentPopupMenu(jPmenufriend);
		jTPchoose.setForeground(Color.DARK_GRAY);
		jTPchoose.setBackground(Color.WHITE);
		jTPchoose.setFont(new Font("����",Font.PLAIN,11));
		jTPchoose.addTab("��ϵ��", jPcentre);
		jTPchoose.addTab("����", j1);
		jTPchoose.addTab("��Ϸ", j2);
		/*jTPchoose.addTab("����", j3);
		jTPchoose.addTab("΢��", j4);*/
	    jTPchoose.setBounds(0, 117, 313, 382);
	    jPcentre.setLayout(new BorderLayout());
	    JLabel test=new JLabel("�� �� �� ��");
	    test.setFont(new Font("����",Font.PLAIN,14));
	    test.setSize(313, 30);
	    test.setForeground(Color.BLACK);
		jPmenuser.add(jM11);
		jPmenuser.add(jM12);
		jPmenuser.add(jM13);
		jPmenuser.add(jM14);
		jPmenuser.add(jM15);
		jPmenuser.add(jM16);
		jPmenuser.add(jM17);
		jPmenuser.add(jM18);
		jM11.setFont(new Font("����",Font.PLAIN,14));
		jM11.setForeground(Color.DARK_GRAY);
		jM12.setFont(new Font("����",Font.PLAIN,14));
		jM12.setForeground(Color.DARK_GRAY);
		jM13.setFont(new Font("����",Font.PLAIN,14));
		jM13.setForeground(Color.DARK_GRAY);
		jM14.setFont(new Font("����",Font.PLAIN,14));
		jM14.setForeground(Color.DARK_GRAY);
		jM14.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PrintNumFindUser printNum=new PrintNumFindUser(PersonelView.this,"�����ϵ��",false,PersonelView.this);
				printNum.setVisible(true);
			}
		});
		jM15.setFont(new Font("����",Font.PLAIN,14));
		jM15.setForeground(Color.DARK_GRAY);
		jM15.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PrintNumFindUser printNum=new PrintNumFindUser(PersonelView.this,"�����û�",false,PersonelView.this);
				printNum.setVisible(true);
			}
			
		});
		jM16.setFont(new Font("����",Font.PLAIN,14));
		jM16.setForeground(Color.DARK_GRAY);
		jM17.setFont(new Font("����",Font.PLAIN,14));
		jM17.setForeground(Color.DARK_GRAY);
		jM18.setFont(new Font("����",Font.PLAIN,14));
		jM18.setForeground(Color.DARK_GRAY);
		jM18.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AboutMy a=new AboutMy(PersonelView.this, "����QQ", true);
				a.setVisible(true);
			}
			
		});
		test.setComponentPopupMenu(jPmenuser);
	    jPcentre.setBackground(Color.WHITE);
       jPcentre.setBounds(0, 117, 313, 382);
	   userList.addMouseListener(new PersonelView_userList_mouseAdapter());
	   userList.addMouseMotionListener(new PersonelView_userList_mouseMotionAdapter());
	   jPcentre.add(BorderLayout.NORTH,test);
    jPcentre.add(jSuserList);
		//�ײ�
		jLbase.setBounds(0, 499, 304, 51);
	}
	//���ڵ�¼���ȡ�Լ��ͺ�����Ϣ
	private Boolean login()
	{
		String judge = null;

		try {
			out.println("login");
			out.flush();
			out.println(userNum);
			out.flush();
			out.println(userPass);
			out.flush();
			out.println(userIp);
			out.flush();
			out.println(usePort);
			//System.out.println("���͸����ݿ��ҵĶ˿ں�"+usePort);
			out.flush();
			//��ȡ�Լ���Ϣ
			 judge=in.readLine();
			 if(judge.equals("loginFail"))
			 {
				 return false;
			 }
			 else if(judge.equals("sendUserInfo"))
			 {
				 String flag_1=in.readLine();
				 if(flag_1.equals("queryUserFail"))
				 {
					 return false;
				 }
				 else
				 {
						myInfo.setUserNum(flag_1);
						myInfo.setUserName(in.readLine());
						myInfo.setSex(in.readLine());
						myInfo.setBirth(in.readLine());
						myInfo.setAddress(in.readLine());
						myInfo.setSign(in.readLine());
						myInfo.setPortrait(in.readLine());
						myInfo.setStatus(Integer.valueOf(in.readLine()));
						myInfo.setPort(Integer.valueOf(in.readLine()));
						myInfo.setIp(in.readLine());
						//�Լ���Ϣ��ȡ��ϣ�����������loginSuccess����ʼ��ȡ������Ϣ
				 }
			 }
			//�Լ���Ϣ��ȡ��ϣ�����������loginSuccess����ʼ��ȡ������Ϣ
			 String flag_3=in.readLine();
			 if(flag_3.equals("loginSuccess"))
					{
					/*-----------------��ʼ�ӷ������˶�ȡ������Ϣ--------------------------*/
						friendInfoTable.clear();
						String flag2="";
							do
							{
								flag2=in.readLine().trim();
								System.out.println("flag2:"+flag2);
								if(flag2.equals("queryFriendOver"))
								{
									System.out.println("BREAK");
									break;
									
								}
								else
								{
									UserBean friendBean=new UserBean();
									friendBean.setUserNum(flag2);
									friendBean.setUserName(in.readLine());
									friendBean.setSex(in.readLine());
									friendBean.setBirth(in.readLine());
									friendBean.setAddress(in.readLine());
									friendBean.setSign(in.readLine());
									friendBean.setPortrait(in.readLine());
									friendBean.setStatus(Integer.valueOf(in.readLine()));
									friendBean.setPort(Integer.valueOf(in.readLine()));
									friendBean.setIp(in.readLine());
									friendInfoTable.put(flag2, friendBean);
								}
							}while(!(flag2.equals("queryFriendOver")));
					}
			 else if(flag_3.equals("queryUserFail"))
			 {
				 return false;
			 }
		}
			 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return true;
	}
	//��ú�����Ϣ�Դ����б�
	private void getFriendInfo()
	{
		listModel.removeAllElements();
		//ʵ�� Enumeration �ӿڵĶ�������һϵ��Ԫ�أ�һ������һ������������ nextElement ����������һϵ�е�����Ԫ�ء� 
		 Enumeration it = friendInfoTable.elements();
		 String name="";
		 String num="";
		 String portrait="";
		 String friendinfo="";//ÿһ�����ѵ���ʾ��Ϣ
		 int status=0;
		 while(it.hasMoreElements())
		 {
			 UserBean user=(UserBean)it.nextElement();
			 name=user.getUserName().trim();
			 num=user.getUserNum().trim();
			 portrait=user.getPortrait();
			 status=user.getStatus();
			 friendinfo= status + name + "<" + num + ">" + "*" + portrait+"^";
			 listModel.addElement(friendinfo);
		 }
		 
	}	
	//��ȡ�˿ں�
	private int getUdpPort(String key)
	{
		int myport=0;
		Properties p=new Properties();
		try {
			FileInputStream in=new FileInputStream("src/file/udp.txt");
		FileOutputStream out=new FileOutputStream("src/file/udp.txt",true);
		p.load(in);//���������ж�ȡ�����б�
		myport=Integer.parseInt(p.getProperty(key));
		myport=myport+1;
		p.setProperty("udp.Port", new Integer(myport).toString());
		p.store(out, "new udp.Port");
		in.close();
		out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return myport;	
	}
	private int getNextPort(int port)
	{
		int nextport=port;
		Boolean flag=true;
		DatagramSocket testsocket=null;
		//���˿��Ƿ�ռ��
		while(true)
		{
			flag=true;
			try {
				testsocket=new DatagramSocket(++nextport);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				flag=false;
			}
			if(flag==true)
			{
				break;
			}
			System.out.println(nextport);
			
		}
		testsocket.close();
		return nextport;
		
	}
	
	protected void processWindowEvent(WindowEvent e)
	{
		if(e.getID()==WindowEvent.WINDOW_CLOSING)
		{
			exit();
		}
	}
	public void exit()//�����û�����
	{
		int option=JOptionPane.showConfirmDialog(PersonelView.this, "�ף���ȷ��Ҫ�˳�ô��");
		if(option==JOptionPane.YES_OPTION)
		{
			try {
				out.println("logout");
				out.flush();
				out.println(myInfo.getUserNum());
				out.flush();
				String msg=in.readLine();
				if(msg.equals("logOut"))
				{
					out.println("end");
					out.flush();
					in.close();
					out.close();
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				this.dispose();
				System.exit(0);
			}
		}
	}
	//��Ӧ����ͷ���ϵ����˫���¼�
	class PersonelView_userList_mouseAdapter extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if(e.getClickCount()==2&&e.getButton()==MouseEvent.BUTTON1)
			{
				new Thread(new ChatView(myInfo,currentFriend,PersonelView.this,usePort,receiveSocket,receivePacket,friendInfoTable,in,out)).start();
			
			}
		}
	}
	//�����û�������ƶ���ĳһ��ͷ����
	class PersonelView_userList_mouseMotionAdapter extends MouseMotionAdapter
	{
		public void mouseMoved(MouseEvent e)
		{
			if(!(listModel.isEmpty()))
			{
				currentIndex=userList.locationToIndex(e.getPoint());//��õ�ǰ�����б������
				currentInfo=listModel.getElementAt(currentIndex).toString();//��õ�ǰ�б��ֵ
				currentUserNum=currentInfo.substring(currentInfo.indexOf("<")+1,currentInfo.indexOf(">"));
				//���ݺ��ѵ�QQ�Ų��Һ��ѵ���Ϣ
				currentFriend=(UserBean)friendInfoTable.get(currentUserNum);
				String friendSign=currentFriend.getSign();
				userList.setToolTipText(friendSign);//������ʾ��Ϣ����ʾ���ѵĸ���ǩ��
		}
	}
}
	class PersonelView_jLportrait_mouseMotionAdapter extends MouseAdapter
	{
		public void mouseEntered(MouseEvent e)
		{
			jLportrait.setCursor(new Cursor(Cursor.HAND_CURSOR));//�������Լ�ͷ��ʱ����Ϊ��״������͡�
		}
		public void mousePressed(MouseEvent e)
		{
				
				UserInfo userInfo=new UserInfo(PersonelView.this,"�ҵ�����",true,myInfo);
			userInfo.setVisible(true);
		}
	}
	public void findUser(String num)
	{
		//UserBean findUser=new UserBean();
		out.println("queryUser");
		out.flush();
		out.println(num);
		out.flush();
		try {
			String judge_find=in.readLine();
			if(judge_find.equals("noUser"))
			{
				JOptionPane.showMessageDialog(PersonelView.this, "�ף���������û��������أ�");
			}
			else if(judge_find.equals("queryUserFail"))
			{
				JOptionPane.showMessageDialog(PersonelView.this, "�����û�ʧ��");
			}
			else
			{
				findUserBean.setUserNum(judge_find);
				findUserBean.setUserName(in.readLine());
				findUserBean.setSex(in.readLine());
				findUserBean.setBirth(in.readLine());
				findUserBean.setAddress(in.readLine());
				findUserBean.setSign(in.readLine());
				findUserBean.setPortrait(in.readLine());
				findUserBean.setStatus(Integer.valueOf(in.readLine()));
				findUserBean.setPort(Integer.valueOf(in.readLine()));
				findUserBean.setIp(in.readLine());
				FindUser find=new FindUser(PersonelView.this,"�û�����",true,findUserBean,PersonelView.this);
				find.setVisible(true);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(PersonelView.this, "ϵͳ����ά���У�");
		}
		
	}
	public void addUser()
	{
		try {
			String name=findUserBean.getUserName().trim();
			 String num=findUserBean.getUserNum().trim();
			 String portrait=findUserBean.getPortrait();
			int  status=findUserBean.getStatus();
			String  friendinfo= status + name + "<" + num + ">" + "*" + portrait+"^";
			if(listModel.contains(friendinfo))
			{
				JOptionPane.showMessageDialog(PersonelView.this, "���û��Ѵ��ں����б���");
			}
			else
			{
			//�������������Ӻ�������
			out.println("addFriend");
			out.flush();
			out.println(myInfo.getUserNum());
			out.flush();
			out.println(findUserBean.getUserNum());
			out.flush();
			String judge_add=in.readLine();
			if(judge_add.equals("addFriendOver"))
			{
				//������ӵĺ�����Ϣ���뵽��ϣ����
				friendInfoTable.put(findUserBean.getUserNum().trim(), findUserBean);
				//���б�����ʾ����ӵĺ���
					listModel.addElement(friendinfo);
					JOptionPane.showMessageDialog(PersonelView.this, "��ӳɹ�");
			}
			else if(judge_add.equals("addFriendFail"))
			{
				JOptionPane.showMessageDialog(PersonelView.this, "��Ӻ���ʧ��");
			}
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//�����Լ���ͷ��
	public void refreshMyProtrait()
	{
		jLportrait.setIcon(new ImageIcon(myInfo.getPortrait()));
	}
	public void refreshMyInfo()
	{
		jLmyName.setText(myInfo.getUserName());
		jLmySign.setText(myInfo.getSign());
	}
	public void changHead()
	{
		ChangeHead changeHead=new ChangeHead(PersonelView.this,"����ͷ��",true,myInfo,in,out,PersonelView.this);
		changeHead.setVisible(true);
		PersonelView.this.refreshMyProtrait();
	}
}
