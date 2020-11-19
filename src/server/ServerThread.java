package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTextArea;

/*�����������߳�*/
public class ServerThread extends Thread{
	JTextArea jTServerLog=null;
	Boolean flag=true;
	String line_separator=System.getProperty("line.separator");
	ServerSocket server;
	/*-------------------�����ļ�----------------------------*/
	Vector clients=new Vector();
	/*-----------------------------------------------------------*/
	public ServerThread(JTextArea jTServerLog) {
		// TODO Auto-generated constructor stub
		this.jTServerLog=jTServerLog;
	}
//�ָ�����
	public void reStartThread() {
		// TODO Auto-generated method stub
		this.flag=true;	
	}
//��ͣ����
	public void pauseThread() {
		// TODO Auto-generated method stub
		this.flag=false;
	}
	//�߳��е�������
	public void  run()
	{
		try {
			 server=new ServerSocket(6544);
			jTServerLog.append("���������ϵͳ��ʼ������ �� �� �� �� "+line_separator);
			jTServerLog.append(line_separator);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jTServerLog.append("�������˿ڴ򿪳��� �� �� �� �� ��"+line_separator);
			jTServerLog.append(line_separator);
		}
		//����ϵͳ�������������̳߳���
		if(server!=null)
		{
		while(flag)
		{
			try {
				System.out.println("��������"+flag);
				//�����ͻ��˵���������
				Socket socket=server.accept();
				jTServerLog.append("****************************"+line_separator);
				jTServerLog.append("Connection accept : "+socket+line_separator);
				Date time=new java.util.Date();
				SimpleDateFormat format=new SimpleDateFormat("yyy-MM-dd kk:mm:ss");
				String timeInfo=format.format(time);
				jTServerLog.append("����ʱ�� : "+timeInfo+line_separator);
				jTServerLog.append("****************************"+line_separator);
				//�����׽�����ͻ���ͨѶ
//				Server c=new Server(socket,clients);
//				Server c=new Server(socket,ServerThread.this);
//				clients.addElement(c);
				//new Thread(c).start();
			//	clients.addElement(socket);
				new Thread(new Server(socket)).start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jTServerLog.append("�ͻ�����ʧ�ܡ� �� �� �� �� ��"+line_separator);
				jTServerLog.append(line_separator);
			}
		}
	}
	}
}
