package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTextArea;

/*服务器连接线程*/
public class ServerThread extends Thread{
	JTextArea jTServerLog=null;
	Boolean flag=true;
	String line_separator=System.getProperty("line.separator");
	ServerSocket server;
	/*-------------------传送文件----------------------------*/
	Vector clients=new Vector();
	/*-----------------------------------------------------------*/
	public ServerThread(JTextArea jTServerLog) {
		// TODO Auto-generated constructor stub
		this.jTServerLog=jTServerLog;
	}
//恢复服务
	public void reStartThread() {
		// TODO Auto-generated method stub
		this.flag=true;	
	}
//暂停服务
	public void pauseThread() {
		// TODO Auto-generated method stub
		this.flag=false;
	}
	//线程中的主方法
	public void  run()
	{
		try {
			 server=new ServerSocket(6544);
			jTServerLog.append("聊天服务器系统开始启动・ ・ ・ ・ ・ "+line_separator);
			jTServerLog.append(line_separator);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jTServerLog.append("服务器端口打开出错・ ・ ・ ・ ・ ・"+line_separator);
			jTServerLog.append(line_separator);
		}
		//交互系统服务器端连接线程程序
		if(server!=null)
		{
		while(flag)
		{
			try {
				System.out.println("服务器："+flag);
				//监听客户端的连接请求
				Socket socket=server.accept();
				jTServerLog.append("****************************"+line_separator);
				jTServerLog.append("Connection accept : "+socket+line_separator);
				Date time=new java.util.Date();
				SimpleDateFormat format=new SimpleDateFormat("yyy-MM-dd kk:mm:ss");
				String timeInfo=format.format(time);
				jTServerLog.append("处理时间 : "+timeInfo+line_separator);
				jTServerLog.append("****************************"+line_separator);
				//创建套接字与客户端通讯
//				Server c=new Server(socket,clients);
//				Server c=new Server(socket,ServerThread.this);
//				clients.addElement(c);
				//new Thread(c).start();
			//	clients.addElement(socket);
				new Thread(new Server(socket)).start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jTServerLog.append("客户连接失败・ ・ ・ ・ ・ ・"+line_separator);
				jTServerLog.append(line_separator);
			}
		}
	}
	}
}
