package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/*为客户端提供功能服务模块
 * 1.注册新用户
 * 2.用户登录
 * 3.查找好友
 * 4.添加好友
 * 5.删除好友
 * 6.修改自己信息
 * 7.用户下线
 * 8.修改头像
 * 9.文件传输*/
public class Server extends Thread{
	private Socket socket=null;//套接字
	private BufferedReader in=null;//输入流
	private PrintStream out=null;//输出流
	private Connection con=null;//数据库连接 
	private Boolean flag=true;//控制服务器线程的启动与停止
	/*-------------------传送文件----------------------------*/
	//ServerThread father=null;
	/*-----------------------------------------------------------*/
	//public Server(Socket socket,ServerThread father)
	public Server(Socket socket){
		// TODO Auto-generated constructor stub
		this.socket=socket;
//		this.father=father;
		try {
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintStream(socket.getOutputStream());
			con=ConnectionDao.getConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//本线程的主方法，根据客户端发送的命令，调用对应的方法执行
	public void run()
	{
		try {
			while(flag)
			{
				String str=in.readLine();
				if(str.equals("end"))
				{
					break;
				}
				else if(str.equals("registNewUser"))//注册新用户
				{
					registNewUser();
				}
				else if(str.equals("login"))//登陆
				{
					login();
				}
				else if(str.equals("queryUser"))//查找用户信息
				{
					String userNum=in.readLine();
					queryUser(userNum);
				}
				else if(str.equals("addFriend"))//添加好友
				{
					addFriend();
				}
				else if(str.equals("deleteFriend"))//删除好友
				{
					deleteFriend();
				}
				else if(str.equals("updateOwnInformation"))//修改自己信息
				{
					updateOwnInformation();
				}
				else if(str.equals("logout"))//用户下线
				{
					logout();
				}
				else if(str.equals("UpdateMyportrait"))//修改头像
				{
					UpdateMyportrait();
				}
//				else if(str.equals("sendFile"))
//				{
//					sendFile();
//				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void registNewUser()
	{
		String sql1="INSERT INTO UserInformation (UserNum,UserName,Password,Sex,Birth,Address,Sign,Portrait,Status) values(?,?,?,?,?,?,?,?,?)";
		String sql2="select UserNum from QQNum where Mark = 1";
		Statement stmt1=null,stmt2=null;
		ResultSet rs=null;
		try {
			stmt1=con.createStatement();
			rs=stmt1.executeQuery(sql2);
			rs.next();
			String userNum=rs.getString("UserNum");//从QQNum表获取一个合法QQ号	
		
			PreparedStatement pstmt=con.prepareStatement(sql1);
			String userName=in.readLine();
			String password=in.readLine();
			String sex=in.readLine();
			String birth=in.readLine();
			String address=in.readLine();
			pstmt.setString(1, userNum);
			pstmt.setString(2, userName);
			pstmt.setString(3, password);
			pstmt.setString(4, sex);
			pstmt.setString(5, birth);
			pstmt.setString(6, address);
			pstmt.setString(7, "个性签名");
			pstmt.setString(8, "src/head/head.png");//当用户注册的时候，默认给用户指定一个头像
			pstmt.setInt(9, 0);//默认用户的状态为不在线
			pstmt.executeUpdate();//向UerInformation中插入信息结束
			pstmt.close();
			//修改QQNum中的Mark值，改为0，表示该QQ已被用户注册
			System.out.println(userNum);
			String sql3="UPDATE QQNum SET Mark = 0 where UserNum = '"+userNum+"'";
			stmt2=con.createStatement();
			stmt2.executeUpdate(sql3);
			out.println("registerOver");//注册完毕
			out.flush();
			//告诉客户端，你注册所获取的QQ号
			out.println(userNum);
			out.flush();
			
			stmt2.close();
			stmt1.close();
			rs.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("registerFail");
			out.flush();
		}
	}
	public void login()
	{
		Statement stmt1=null;
		Statement stmt2=null;
		ResultSet rs=null;
		try {
			String userNum=in.readLine();
			String password=in.readLine();
			String ip=in.readLine();
			String port=in.readLine();
			String sql1="select * from UserInformation where UserNum= '"+userNum+"' and Password = '"+password+"'";
			stmt1=con.createStatement();
			rs=stmt1.executeQuery(sql1);
			//System.out.println(userNum+password+ip+port);
			//如果登录成功
			if(rs.next())
			{
		      //String ip=String.valueOf(socket.getInetAddress().getLocalHost());
				//int port=socket.getLocalPort();
				String sql2="UPDATE UserInformation SET Status = 1,IP = '"+ip+"', Port = "+port+" where UserNum ='"+userNum+"'";
				System.out.println(sql2);
				System.out.println(port);	
				stmt2=con.createStatement();
				stmt2.executeUpdate(sql2);
				out.println("sendUserInfo");
				out.flush();
				queryUser(userNum);
				out.println("loginSuccess");
				out.flush();
				//查找ta的好友
				queryFriend(userNum);
				stmt2.close();
				}
			else
			{
				System.out.println("FAIL");
				out.println("loginFail");
				out.flush();
				stmt1.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("loginFail");
			out.flush();
		}
	}
	private void queryFriend(String userNum) {
		// TODO Auto-generated method stub
		Statement stmt1=null;
		Statement stmt2=null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		Vector friendNum=new Vector();//此向量用于存储好友的QQ号码
		try {
			String sql1="select FriendNum from UserFriend where UserNum = "+userNum;
			stmt1=con.createStatement();
			rs1=stmt1.executeQuery(sql1);
			while(rs1.next())
			{
				friendNum.addElement(rs1.getString("FriendNum"));
			}
			rs1.close();
			stmt1.close();
			for(int i=0;i<friendNum.size();i++)
			{
				String friend=(String) friendNum.elementAt(i);
				String sql2="select * from UserInformation where UserNum ='"+friend+"'";
				stmt2=con.createStatement();
				rs2=stmt2.executeQuery(sql2);
				rs2.next();
				out.println(friend);//QQ号码
				out.flush();
				out.println(rs2.getString("UserName"));
				out.flush();
				out.println(rs2.getString("Sex"));
				out.flush();
				out.println(rs2.getString("Birth"));
				out.flush();
				out.println(rs2.getString("Address"));
				out.flush();
				out.println(rs2.getString("Sign"));
				out.flush();
				out.println(rs2.getString("Portrait"));
				out.flush();
				out.println(rs2.getString("Status"));
				out.flush();
				out.println(rs2.getString("Port"));
				out.flush();
				out.println(rs2.getString("IP"));
				out.flush();
				rs2.close();
				stmt2.close();
			}
			out.println("queryFriendOver");
			out.flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void queryUser(String userNum)
	{
		Statement stmt=null;
		ResultSet rs=null;
		try {
			String sql="select * from UserInformation where UserNum = '"+userNum+"'";
			stmt=con.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				out.println(userNum);//QQ号码
				out.flush();
				out.println(rs.getString("UserName"));
				out.flush();
				out.println(rs.getString("Sex"));
				out.flush();
				out.println(rs.getString("Birth"));
				out.flush();
				out.println(rs.getString("Address"));
				out.flush();
				out.println(rs.getString("Sign"));
				out.flush();
				out.println(rs.getString("Portrait"));
				out.flush();
				out.println(rs.getInt("Status"));
				out.flush();
				out.println(rs.getInt("Port"));
				out.flush();
				out.println(rs.getString("IP"));
				out.flush();
			}
			else
				out.println("noUser");
			out.flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("queryUserFail");
			out.flush();
		}
		finally
		{
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void addFriend()
	{
		try {
			String sql="INSERT INTO UserFriend (UserNum,FriendNum) values (?,?)";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, in.readLine());
			pstmt.setString(2, in.readLine());
			pstmt.executeUpdate();
			out.println("addFriendOver");
			out.flush();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("addFriendFail");
			out.flush();
		}
	}
	public void deleteFriend()
	{
		try {
			String sql="DELETE FROM UserFriend WHERE UserNum = '"+in.readLine()+"' and FriendNum = '"+in.readLine()+"'";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.execute();
			out.println("deleteFriendOver");
			out.flush();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("deleteFriendFail");
			out.flush();
		} 
	}
	public void updateOwnInformation()
	{
		try {
			String sql="UPDATE UserInformation SET UserName = ? , Sex = ? , Birth = ?, Address = ? , Sign = ? where UserNum ='"+in.readLine()+"'";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, in.readLine());
			pstmt.setString(2, in.readLine());
			pstmt.setString(3, in.readLine());
			pstmt.setString(4, in.readLine());
			pstmt.setString(5, in.readLine());
			pstmt.executeUpdate();
			out.println("updateOver");
			out.flush();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("updateFail");
			out.flush();
		}
	}
	public void logout()
	{
		try {
			String sql="UPDATE UserInformation SET Status = 0 , IP = null , Port = 0 where UserNum = '"+in.readLine()+"'";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.executeUpdate();
			out.println("logOut");
			out.flush();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("logFail");
			out.flush();
		} 
	}
	public void UpdateMyportrait()
	{
		try {
			String num=in.readLine();
			String image=in.readLine();
			//System.out.println("修改头像了哈"+num+image);
			String sql="UPDATE UserInformation SET Portrait = '"+image+"' where UserNum = '"+num+"'";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.executeUpdate();
			out.println("updateMyportraitOver");
			out.flush();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("updateMyportraitFail");
			out.flush();
		}
	}
//	public synchronized void sendFile()
//	{
//		String sendQQ=null;
//		String receiveQQ=null;
//		String fileName=null;
//		String fileContent=null;
//		String judge=null;
//		String temp=null;
//		try {	
//			receiveQQ=in.readLine();//接收方的QQ号
//			sendQQ=in.readLine();//发送方的QQ号
//			fileName=in.readLine();
//			BufferedWriter bw=new BufferedWriter(
//					new OutputStreamWriter(
//					new FileOutputStream("src/接收文件/临时文件")));
//			do
//			{
//				judge=in.readLine();
//				if(judge.equals("Over"))
//				{
//					break;
//				}
//				else
//				{
//					
//					bw.write(judge);
//					bw.newLine();
//					fileContent+=judge+"\n";
//				}
//				
//			}while(!judge.equals("Over"));
//			bw.flush();
//			System.out.println(receiveQQ);
//			System.out.println(sendQQ);
//			System.out.println(fileName);
//			System.out.println(fileContent);
//			BufferedReader bufr=new BufferedReader(
//					new InputStreamReader(new FileInputStream(
//							"src/接收文件/临时文件.txt")));
//			Socket socket1;
//			PrintStream out1=null;//输出流
//			for(int i=0;i<father.clients.size();i++)
//			{
//				socket1=(Socket) father.clients.elementAt(i);
//				if(!socket1.equals(this.socket))
//				{
//					out1=new PrintStream(socket.getOutputStream());
//					out1.println("FILE");
//					out1.flush();
//					out1.println(receiveQQ);
//					out1.flush();
//					out1.println(sendQQ);
//					out1.flush();
//					out1.println(fileName);
//					out1.flush();
//					while((temp=bufr.readLine())!=null)
//					{
//						out1.println(temp);
//						out1.flush();
//					}
//					out1.println("All");
//					out1.flush();
//					System.out.println("i 的值最后为多少啊："+i);
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
