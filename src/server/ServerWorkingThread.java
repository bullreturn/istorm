package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JTextArea;

interface SWT_Callback{
	void unregisterSession(UserServiceThread session);
}

/*服务器连接线程*/
public class ServerWorkingThread extends Thread implements SWT_Callback{

	// store each new incoming sessions
	CopyOnWriteArrayList<UserServiceThread> clientSessions =new CopyOnWriteArrayList<UserServiceThread>();

	JTextArea jTServerLog=null;
	Boolean _shouldExit =false;
	String line_separator=System.getProperty("line.separator");
	ServerSocket server;
	Connection dbconnection;
	/*-------------------传送文件----------------------------*/
	Vector clients=new Vector();
	/*-----------------------------------------------------------*/
	public ServerWorkingThread(JTextArea jTServerLog, Connection dbc) {
		dbconnection=dbc;
		// TODO Auto-generated constructor stub
		this.jTServerLog=jTServerLog;
	}

	public void stopServerSocket(){
		try{
			server.close();
		}catch (IOException e){

		}

	}

	public void unregisterSession(UserServiceThread session){
		if(!clientSessions.remove(session)){
			System.err.println("Failed to remove session: "+session.getId());
		}
	}

	//线程中的主方法
	public void  run()
	{
		System.out.printf("ServerWorkingThread started\n");
		try {
			 server=new ServerSocket(6544);
			 server.setReuseAddress(true);
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
		while(!_shouldExit)
		{
			jTServerLog.append("looping"+line_separator);
			try {
				//监听客户端的连接请求
				Socket socket=server.accept();
				jTServerLog.append("****************************"+line_separator);
				jTServerLog.append("Connection accept : "+socket+line_separator);
				Date time=new java.util.Date();
				SimpleDateFormat format=new SimpleDateFormat("yyy-MM-dd kk:mm:ss");
				String timeInfo=format.format(time);
				jTServerLog.append("处理时间 : "+timeInfo+line_separator);
				jTServerLog.append("****************************"+line_separator);

				// 為不同傳入連接建立线程-端口复用
				UserServiceThread ut= new UserServiceThread(socket, this, dbconnection);
				ut.start();
				clientSessions.add(ut);
				
			} catch (SocketException e){	// workaround: expected to be normal exit
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jTServerLog.append("客户连接失败・ ・ ・ ・ ・ ・"+line_separator);
				jTServerLog.append(line_separator);
			}
		}

			// stop any session threads
		Iterator<UserServiceThread> it=clientSessions.iterator();
		while (it.hasNext()){
			UserServiceThread session=it.next();
			System.out.printf("Stopping session "+session.getId()+"\n");
			session._shouldExit=true;
			session.stopThreadSocket();
			//session.interrupt();	// this one should, but doesn't work. Use the above one instead
		}
			try {
				sleep(3000);	// sleep enough time to allow threads stopping, UGLY!
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		System.out.printf("ServerWorkingThread exited\n");
	}
	}
}
