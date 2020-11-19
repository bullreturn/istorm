package client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/*��½ģ��*/
public class HomePage extends JFrame{
	JLabel jLtitle=new JLabel(new ImageIcon("src/file/Title.png"));
	JLabel jLhead=new JLabel(new ImageIcon("src/file/1-1.png"));
	//JLabel jLRegist=new JLabel("ע���˺�");
	//JLabel jLFindPass= new JLabel("�һ�����");
	JTextField jTusernumber=new JTextField();//�˺������
	JPasswordField jPassword=new JPasswordField();//���������
	JCheckBox rememberPass=new JCheckBox("��ס����");
	JCheckBox Autologon =new JCheckBox("�Զ���¼",true);
	JButton jBmore=new JButton(">���˺�");
	JButton jBset=new JButton("����");
	JButton jBenter=new JButton("��½");
	Register register;
	PersonelView personelView;
	HomePage h;
	Boolean pass=true;//���ڱ�ʶ��½�Ƿ�ɹ�
	HomePage enter;
	public HomePage()
	{
		this.setSize(380, 290);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setTitle("ISTORM");
		this.add(jLtitle);
		this.add(jLhead);
		this.add(jTusernumber);
		this.add(jPassword);
		this.add(Autologon);
		this.add(rememberPass);
		this.add(jBmore);
		this.add(jBset);
		this.add(jBenter);
		init();
	}
	public void loginSuccess()
	{
		pass=true;
	}
	public void loginFail()
	{
		pass=false;
	}
	public void init()
	{
		jLtitle.setBounds(0 ,0, 380, 105);
		jTusernumber.setBounds(120, 110, 160, 28);
		jPassword.setBounds(120, 145, 160, 28);
		rememberPass.setFont(new Font("����",Font.PLAIN,10));
		rememberPass.setForeground(Color.BLACK);
		rememberPass.setBounds(148, 175, 70, 25);
		Autologon.setFont(new Font("����",Font.PLAIN,10));
		Autologon.setForeground(Color.BLACK);
		Autologon.setBounds(220, 175, 70, 25);
		jLhead.setBounds(20, 105, 87, 90);
		jBmore.setFont(new Font("����",Font.PLAIN,10));
		jBmore.setForeground(Color.BLACK);
		jBmore.setBounds(10, 225, 70, 23);
		jBset.setFont(new Font("����",Font.PLAIN,10));
		jBset.setBounds(95, 225, 55, 23);
		jBenter.setFont(new Font("����",Font.PLAIN,10));
		jBenter.setBounds(300, 225, 55, 23);
		jBenter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String userName=jTusernumber.getText().trim();
				String userPass=String.valueOf(jPassword.getPassword()).trim();
				InetAddress ip = null;
				int port = 0;
				if(userName.equals(""))
				{
					JOptionPane.showMessageDialog(jBenter, "���������˺ź��ٵ�½");
					jTusernumber.requestFocus();
				}
				else if(userPass.equals(""))
				{
					JOptionPane.showMessageDialog(jBenter, "��������������ٵ�½");
					jPassword.requestFocus();
				}
				else
				{
					HomePage.this.setVisible(false);
						try {
						ip=InetAddress.getByName("192.168.20.1");
						port=(6544);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						personelView= new PersonelView(userName,userPass,HomePage.this,ip,port);
					} 
				if(!pass)
				{
					personelView.setVisible(false);
					JOptionPane.showMessageDialog(null, "��������˺������벻ƥ�䣬����");
					if(enter==null)
					{
					enter=new HomePage();
					}
				//enter.pack();
				enter.setVisible(true);
				}
			}
			
			
		});
		//����ע���˺ű�ǩ����Ϊ��������Ⱦ��
		class JlRegist extends JLabel
		{
			private boolean isSupported;
			private String regist;
			
			public JlRegist(String regist) {
				// TODO Auto-generated constructor stub
				this.regist=regist;
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
			    	 
			    	  if(register==null)
			    	  {
			    		  register=new Register();
			    		  register.setVisible(true);
							HomePage.this.setVisible(false);
			    	  }
			      }
			     });
			}
			private void setText(boolean b) {
			     if (!b)
			      setText( regist);
			     else
			      setText("<html><font color=blue><u>" + regist);
			  }
			}
	     JlRegist jLRegist=new JlRegist("ע���˺�");
			jLRegist.setFont(new Font("����",Font.PLAIN,13));
			jLRegist.setForeground(Color.BLUE);
			jLRegist.setBounds(283, 110, 60, 28);
			this.add(jLRegist);
			//�����һ������ǩ����Ϊ��������Ⱦ��
			class JlFindPass extends JLabel
			{
				private boolean isSupported;
				private String findPass;
				
				public JlFindPass(String findPass) {
					// TODO Auto-generated constructor stub
					this.findPass=findPass;
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
						          new java.net.URI("https://aq.qq.com/cn2/findpsw/pc/pc_find_pwd_input_account?source_id=1003&ptlang=2052&aquin=593253716"));	
						       } catch (Exception ex) {
						       }
				      }
				     });
				}
				private void setText(boolean b) {
				     if (!b)
				      setText( findPass);
				     else
				      setText("<html><font color=blue><u>" + findPass);
				  }
			}
			JlFindPass jLfindPass=new JlFindPass("�һ�����");
			jLfindPass.setFont(new Font("����",Font.PLAIN,13));
			jLfindPass.setForeground(Color.BLUE);
			jLfindPass.setBounds(283,145, 60, 28);
			this.add(jLfindPass);
		}
	
	public static void main(String args[])
	{
		HomePage enter=new HomePage();
			enter.setVisible(true);
	}

}
