package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import common.UserBean;

public class ReceiveFile{
	ChatView father; 
	BufferedReader in;
	PrintStream out;
	UserBean myInfo;
	UserBean currentFriend;
	String line_separator=System.getProperty("line.separator");//��ȡϵͳ�Ļ��з�
	public ReceiveFile(ChatView father, BufferedReader in, PrintStream out,UserBean myInfo, UserBean currentFriend)
	{
		this.father=father;
		this.in=in;
		this.out=out;
		this.myInfo=myInfo;
		this.currentFriend=currentFriend;
	}
	public void receive()
	{
		String receiveQQ=null;//�������ڽ��շ�
		String sendQQ=null;
		String fileName=null;
		String judge=null;
		//String fileContent=null;
		//-------------------------------------�����ļ�----------------------------------//
		Date time = new java.util.Date();
	     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	      String timeInfo = format.format(time);
		try {
			judge=in.readLine();
			System.out.println("�ͻ��ˣ������ļ�"+judge);
			if(judge.equals("FILE"))
			{
			System.out.println("�ͻ��ˣ������ļ��Ѿ�ִ�е�������");
			receiveQQ=in.readLine();
			System.out.println("������ISTORM�ţ�"+receiveQQ);
			System.out.println("�ҵ�ISTORM�ţ�"+myInfo.getUserNum());
			System.out.println("���ѵ�ISTORM�ţ�"+currentFriend.getUserNum());
			if(receiveQQ.equals(myInfo.getUserNum()))
			{
				sendQQ=in.readLine();
				System.out.println("���ͷ�ISTORM��"+sendQQ);
				fileName=in.readLine();
				System.out.println("�ļ�����"+fileName);
				BufferedWriter bw=new BufferedWriter(
						new OutputStreamWriter(
						new FileOutputStream("src/�����ļ�/"+fileName)));
				String j;
				do
				{
					j=in.readLine();
					if(j.equals("All"))
					{
						break;
					}
					else
					{
						
						bw.write(j);
						bw.newLine();
					//	fileContent+=j+"\n";
					}
					
				}while(!j.equals("All"));
				bw.flush();
				bw.close();
				father.jTAshowChat.append(" "+sendQQ+"  "+timeInfo+line_separator);
				father.jTAshowChat.append("�ļ��ѳɹ�����"+line_separator+line_separator);
				father.jTAshowChat.append(line_separator);
			}
		} 
		}catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//---------------------------------------------------------------------------------//	
	}

}
