package server;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import common.ShowTimeTask;
import common.UserBean;
import common.UserInfo;

/*������������*/
public class ServerFrame extends JFrame {
	JLabel jshowList=new JLabel("�����û��б�<10��ˢ��һ��>");
	JLabel jshowServerLog=new JLabel("��������־");
	JLabel jUserCount=new JLabel("�������� :");
	JLabel jCount=new JLabel("0");
	JLabel jLtime=new JLabel();
	JButton jBgetInfo=new JButton("�鿴��Ϣ");
	JButton jBkickOut=new JButton("�߳�");
	JButton jBpauseServer=new JButton("��ͣ����");
	JButton jBexit=new JButton("�˳�");
	DefaultListModel listModel=new DefaultListModel();
	JList userList=new JList(listModel);
	JScrollPane jSuserList=new JScrollPane(userList);
	JTextArea jTServerLog=new JTextArea();
	JScrollPane jServerLog=new JScrollPane(jTServerLog);
	private Connection con=null;
	ServerThread serverThread=null;
	private Hashtable userTable= new Hashtable();//��UserBean�Ķ���ͳһ�洢��HashTable��
	public ServerFrame()
	{
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setTitle("�������˿��ƽ���");
		//�������ݿ�����
		con=ConnectionDao.getConnection();
		init();
		this.add(jshowList);
		this.add(jSuserList);
		this.add(jBgetInfo);
		this.add(jBkickOut);
		this.add(jUserCount);
		this.add(jCount);
		this.add(jshowServerLog);
		this.add(jServerLog);
		this.add(jBpauseServer);
		this.add(jBexit);
		this.add(jLtime);
	   serverThread=new ServerThread(jTServerLog);//����ServerSocket�׽��֣������õȴ��ͻ������ӵ�׼��
		serverThread.start();
		//ʹjLtime��̬��ʾʱ��
		java.util.Timer myTime=new java.util.Timer();
		java.util.TimerTask task_showtime=new ShowTimeTask(jLtime);
		myTime.schedule(task_showtime, 0,1000);
		java.util.Timer time=new java.util.Timer();
		java.util.TimerTask task_time=new LoginUser(listModel,userList,jCount,userTable,con);
		time.schedule(task_time, 0,10000);//ÿ10��ˢ��һ��
		try {
			System.out.println(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//���÷�����������
	public void init()
	{
		jshowList.setBounds(20, 10, 150, 25);
		jshowList.setFont(new Font("����",Font.PLAIN,11));
		jSuserList.setBounds(10, 40, 190, 500);
		jBgetInfo.setBounds(20, 550, 75, 25);
		jBgetInfo.setFont(new Font("����",Font.PLAIN,10));
		//�鿴��Ϣ��ť����
		jBgetInfo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String selectedUser=null;
				String userNum=null;
				selectedUser=(String)userList.getSelectedValue();
				if(selectedUser==null)
				{
					JOptionPane.showMessageDialog(jBgetInfo, "�뵥��ѡ��һ���û�");
				}
				else
				{
					System.out.println(selectedUser);
					userNum=selectedUser.substring(selectedUser.indexOf("<")+1,selectedUser.indexOf(">"));
					UserBean user=(UserBean)userTable.get(userNum);
					UserInfo userInfo=new UserInfo(ServerFrame.this,"��Ϣ�鿴",true,user);
					userInfo.setVisible(true);
				}
			}
			
		});
		jBkickOut.setBounds(110, 550, 60, 25);
		jBkickOut.setFont(new Font("����",Font.PLAIN,10));
		//�߳���ť����
		jBkickOut.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index=userList.getSelectedIndex();
				String userNum=null;
				if(index==-1)
				{
					JOptionPane.showMessageDialog(jBkickOut, "�뵥��ѡ��һ���û�");
				}
				else{
					String userInfo=(String)listModel.getElementAt(index);
					userNum=userInfo.substring(userInfo.indexOf("<")+1,userInfo.indexOf(">"));
					System.out.println(userNum);
					removeUser(userNum);
					listModel.remove(index);
					int num=Integer.parseInt(jCount.getText())-1;
					jCount.setText(new Integer(num).toString());
				}
			}
			
		});
		jUserCount.setBounds(25, 595, 100, 30);
		jUserCount.setFont(new Font("����",Font.PLAIN,12));
		jCount.setBounds(125, 595, 20, 30);
		jCount.setFont(new Font("����",Font.PLAIN,12));
		jshowServerLog.setBounds(220, 10, 150, 25);
		jshowServerLog.setFont(new Font("����",Font.PLAIN,11));
		jServerLog.setBounds(210, 40, 575, 550);
		jBpauseServer.setBounds(340, 595, 90, 25);
		jBpauseServer.setFont(new Font("����",Font.PLAIN,11));
		//��ͣ����ť����
		jBpauseServer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String command=e.getActionCommand();
				if(command.equals("��ͣ����"))
				{
					serverThread.pauseThread();
					jBpauseServer.setText("�ָ�����");
				}
				else if(command.equals("�ָ�����"))
				{
					serverThread.reStartThread();
					jBpauseServer.setText("��ͣ����");
				}
			}
			
		});
		jBexit.setBounds(470, 595, 60, 25);
		jBexit.setFont(new Font("����",Font.PLAIN,11));
		//�˳���ť����
		jBexit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int option=JOptionPane.showConfirmDialog(jBexit, "�ף���ȷ��Ҫ�˳���");
				if(option==JOptionPane.YES_OPTION)
				{
					try {
						con.close();//�ر����ݿ�����
						//�رշ������̵߳����ݿ�����
						System.exit(0);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		jLtime.setBounds(600, 615, 250, 50);
		jLtime.setFont(new Font("����",Font.PLAIN,12));
	}
	//���ڹر�ǰ����ȷ���û��������ٹر����ݿ�����
//	protected void processWindowEvent(WindowEvent e)
//	{
//		if(e.getID()==WindowEvent.WINDOW_CLOSED)
//		{
//			int option=JOptionPane.showConfirmDialog(new ServerFrame(), "�ף���ȷ��Ҫ�˳���");
//			if(option==JOptionPane.YES_OPTION)
//			{
//				try {
//					con.close();//�ر����ݿ�����
//					//�رշ������̵߳����ݿ�����
//					System.exit(0);
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		}
//	}
	//�߳��û��������ݿ��а�Status���û�״̬��Ϊ0
	public void removeUser(String userNum)
	{
		String sql="UPDATE UserInformation SET Status = 0 where UserNum= '"+userNum+"'";
		try {
			Statement stmt=con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		ServerFrame f=new ServerFrame();
		f.setVisible(true);
	}
}
