package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
/*ע��ģ��*/
public class Register extends JFrame {
	String userName;
	String userPass;
	String sex;
	String birth;
	String address;
	String type;
	String y;
	String m;
	String d;
	JLabel jLtop=new JLabel(new ImageIcon("src/file/regist1.jpg"));
	JLabel jLeft=new JLabel(new ImageIcon("src/file/regist2.jpg"));
	JLabel jLname=new JLabel("�ǳ�");
	JLabel alertName=new JLabel("�������ǳ�");
	JLabel alertPass=new JLabel("����Ϊ6-16���ַ������ܰ����ո�");
	JLabel alertRePass=new JLabel("���ٴ���������");
	JTextField jTname=new JTextField();
	JLabel jLpass=new JLabel("����");
	JPasswordField jPass=new JPasswordField();
	JLabel jLRepass=new JLabel("ȷ������");
	JPasswordField jRepass=new JPasswordField();
	JLabel jLsex=new JLabel("�Ա�");
	ButtonGroup radioGroup=new ButtonGroup();
	JRadioButton  jRboy=new JRadioButton("��",false);
	JRadioButton  jRgirl=new JRadioButton("Ů",false);
	JLabel jLbirth=new JLabel("����");
	String timeT[]={"����","ũ��"};
	JLabel jyear=new JLabel("��");
	JLabel jmonth=new JLabel("��");
	JLabel jday=new JLabel("��");
	JComboBox timeType=new JComboBox(timeT);
    DefaultComboBoxModel yearModel = new DefaultComboBoxModel();
    DefaultComboBoxModel monthModel = new DefaultComboBoxModel();
    DefaultComboBoxModel dayModel=new DefaultComboBoxModel();
    JComboBox year = new JComboBox();
    JComboBox month = new JComboBox();
    JComboBox day=new JComboBox();
    JLabel jLplace=new JLabel("���ڵ�");
    JTextField jTplace=new JTextField();
    JCheckBox jService =new JCheckBox("�����Ķ���ͬ����ط�������",true);
	JButton jBregist=new JButton("����ע��");
	Socket socket;
	private BufferedReader in=null;//������
	private PrintStream out=null;//�����
	public Register()
	{
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setTitle("ISTORMע��");
		init();
		this.add(jLtop);
		this.add(jLeft);
		this.add(jLname);
		this.add(jTname);
		this.add(jLpass);
		this.add(jPass);
		this.add(jLRepass);
		this.add(jRepass);
		this.add(jLsex);
		this.add(jRboy);
		this.add(jRgirl);
		this.add(jLbirth);
		this.add(timeType);
		this.add(jyear);
		this.add(jmonth);
		this.add(jday);
		this.add(year);
		this.add(month);
		this.add(day);
		this.add(jLplace);
		this.add(jTplace);
		this.add(jService);
		this.add(jBregist);
		this.add(alertName);
		this.add(alertPass);
		this.add(alertRePass);
	}
	public void init()
	{
		
        for (int i = 1950; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
            yearModel.addElement(i);
        }
        for (int j = 1; j <= 12; j++) {
            monthModel.addElement(j);
        }
        for(int k=1;k<=31;k++)
        {
        	dayModel.addElement(k);
        }
        year.setModel((ComboBoxModel) yearModel);
        month.setModel((ComboBoxModel) monthModel);
        day.setModel((ComboBoxModel)dayModel);
        jLtop.setBounds(0, 0, 800, 100);
        jLeft.setBounds(0, 100, 227, 600);
        jLname.setBounds(300, 151, 50, 50);
        jLname.setFont(new Font("����",Font.PLAIN,20));
        alertName.setFont(new Font("����",Font.PLAIN,12));
        alertName.setForeground(Color.BLACK);
        alertName.setBounds(580, 155, 200, 20);
        alertPass.setFont(new Font("����",Font.PLAIN,12));
        alertPass.setForeground(Color.BLACK);
        alertPass.setBounds(580, 225, 200, 20);
        alertRePass.setFont(new Font("����",Font.PLAIN,12));
        alertRePass.setForeground(Color.BLACK);
        alertRePass.setBounds(580, 295, 200, 20);
        jTname.setBounds(355, 150, 220, 45);
        jLpass.setFont(new Font("����",Font.PLAIN,20));
        jLpass.setBounds(300, 221, 50, 50);
        jPass.setBounds(355, 220, 220, 45);
        jLRepass.setFont(new Font("����",Font.PLAIN,20));
        jLRepass.setBounds(260, 291, 90, 50);
        jRepass.setBounds(355, 290, 220, 45);
        jLsex.setFont(new Font("����",Font.PLAIN,20));
        jLsex.setBounds(300, 361, 50, 50);
        jRboy.setFont(new Font("����",Font.PLAIN,16));
        jRboy.setForeground(Color.BLUE);
        jRboy.setBounds(355, 360, 50, 50);
        jRboy.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==jRboy)
				{
					sex="��";
				}
			}
        	
        });
        jRgirl.setFont(new Font("����",Font.PLAIN,16));
        jRgirl.setForeground(Color.BLUE);
        jRgirl.setBounds(430, 360, 50, 50);
        jRgirl.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub\
				if(e.getSource()==jRgirl)
				{
					sex="Ů";
				}
				
			}
        	
        });
        radioGroup.add(jRboy);
        radioGroup.add(jRgirl);
        jLbirth.setFont(new Font("����",Font.PLAIN,20));
        jLbirth.setBounds(300, 431, 50, 50);
		timeType.setBackground(Color.WHITE);
        timeType.setFont(new Font("����",Font.PLAIN,16));
        timeType.setForeground(Color.BLUE);
        timeType.setBounds(355, 440, 65, 35);
        timeType.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				type=timeType.getSelectedItem().toString();
			}
		});
        year.setFont(new Font("����",Font.PLAIN,16));
        year.setForeground(Color.BLUE);
        year.setBounds(430, 440, 65, 35);
        year.setBackground(Color.WHITE);
        year.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				y=year.getSelectedItem().toString();
			}
		});
        jyear.setFont(new Font("����",Font.PLAIN,16));
        jyear.setBounds(500, 440, 25, 35);
        month.setFont(new Font("����",Font.PLAIN,16));
        month.setForeground(Color.BLUE);
        month.setBounds(523, 440, 55, 35);
        month.setBackground(Color.WHITE);
        month.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				m=month.getSelectedItem().toString();
			}
		});
        jmonth.setFont(new Font("����",Font.PLAIN,16));
        jmonth.setBounds(580, 440, 25, 35);
        day.setFont(new Font("����",Font.PLAIN,16));
        day.setForeground(Color.BLUE);
        day.setBounds(605, 440, 55, 35);
        day.setBackground(Color.WHITE);
        day.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				d=day.getSelectedItem().toString();
			}
		});
        jday.setFont(new Font("����",Font.PLAIN,16));
        jday.setBounds(665, 440, 25, 35);
        jLplace.setFont(new Font("����",Font.PLAIN,20));
        jLplace.setBounds(280, 501, 70, 50);
        jTplace.setBounds(355, 500, 220, 45);
        jService.setFont(new Font("����",Font.PLAIN,14));
        jService.setBounds(355, 570, 250, 25);
        jBregist.setFont(new Font("����",Font.BOLD,26));
        jBregist.setBackground(Color.green);
        jBregist.setForeground(Color.white);
        jBregist.setBounds(355,600 , 200, 60);
        jBregist.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(jTname.getText().trim().length()>10||jTname.getText().trim().length()==0)
				{
					alertName.setForeground(Color.RED);
				}
				else if((String.valueOf(jPass.getPassword()).trim().length())<6)
				{
					alertName.setText("");
					alertPass.setText("���볤�Ȳ���С��6");
					alertPass.setForeground(Color.RED);
				}
				else if((String.valueOf(jPass.getPassword()).trim().length())>16)
				{
					alertName.setText("");
					alertPass.setForeground(Color.RED);
					alertPass.setText("���볤�Ȳ��ܳ���16");
				}
				else if(!((String.valueOf(jPass.getPassword()).trim()).equals(String.valueOf(jRepass.getPassword()).trim())))
				{
					alertName.setText("");
					alertPass.setText("");
					alertRePass.setText("������������벻һ��");
					alertRePass.setForeground(Color.RED);
					//JOptionPane.showMessageDialog(jBregist, "������������벻һ��!");
				}
				else if(sex==null)
				{
					alertName.setText("");
					alertPass.setText("");
					alertRePass.setText("");
					JOptionPane.showMessageDialog(jBregist, "��ѡ������Ա�");
				}
				else if(type==null||y==null||m==null||d==null)
				{
					alertName.setText("");
					alertPass.setText("");
					alertRePass.setText("");
					JOptionPane.showMessageDialog(jBregist, "��ע�⽫���������Ϣѡ��������");
				}
				else	if(jTplace.getText().trim().length()==0)
				{
					alertName.setText("");
					alertPass.setText("");
					alertRePass.setText("");
					JOptionPane.showMessageDialog(jBregist, "���ڵز���Ϊ�գ�");
				}
		
				else
				{
					 userName=jTname.getText().trim();
					 userPass=String.valueOf(jPass.getPassword()).trim();
					 address=jTplace.getText();
					 birth=type+"-"+y+"��"+m+"��"+d+"��";
						try {
							InetAddress ip=InetAddress.getByName("192.168.20.1");
							int port=(6544);
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
							InetAddress userIp = null ;
							int userPort;
							try {
								userIp= InetAddress.getLocalHost();
							} catch (UnknownHostException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							 userPort=socket.getLocalPort();
							try {
								in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
								out=new PrintStream(socket.getOutputStream());
								System.out.println(birth+sex+address+userName+userPass);
								out.println("registNewUser");
								out.flush();
								out.println(userName);
								out.flush();
								out.println(userPass);
								out.flush();
								out.println(sex);
								out.flush();
								out.println(birth);
								out.flush();
								out.println(address);
								out.flush();
								String judge=in.readLine();
								if(judge.equals("registerOver"))
								{
									String userNum=in.readLine();
									Check checkQQ=new Check(userNum);
									checkQQ.setVisible(true);
									Register.this.setVisible(false);
								}
								if(judge.equals("registerFail"))
								{
									System.out.println("ע��ʧ�ܣ�");
									JOptionPane.showMessageDialog(jBregist,"ע��ʧ�ܣ�������ע��");
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
			}
			}
        	
        });
	}
	public static void main(String args[])
	{
		Register r=new Register();
		r.setVisible(true);
	}
	

}
