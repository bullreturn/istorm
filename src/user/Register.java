package user;

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
/*注册模块*/
public class Register extends JFrame {
    String userName;
    String userPass;
    String mibao;
    String mibaodaan;
    String sex;
    String birth;
    String address;
    String type;
    String y;
    String m;
    String d;
    JLabel jLtop=new JLabel(new ImageIcon("src/file/regist1.jpg"));
    JLabel jLeft=new JLabel(new ImageIcon("src/file/regist2.jpg"));
    JLabel jLname=new JLabel("昵称");
    JLabel alertName=new JLabel("请输入昵称");
    JLabel alertPass=new JLabel("长度为6-16个字符，不能包含空格");
    JLabel alertRePass=new JLabel("请再次输入密码");
    JLabel alertmibao=new JLabel("请输入密保答案");
    JTextField jTname=new JTextField();
    JLabel jLpass=new JLabel("密码");
    JPasswordField jPass=new JPasswordField();
    JLabel jLRepass=new JLabel("确认密码");
    JPasswordField jRepass=new JPasswordField();
    JLabel jLmibao=new JLabel("密保问题");
    JComboBox jCmibao = new JComboBox();
    JLabel jLmibaoansewr=new JLabel("密保答案");
    JTextField jmibaoansewr=new JTextField();
    JLabel jLsex=new JLabel("性别");
    ButtonGroup radioGroup=new ButtonGroup();
    JRadioButton  jRboy=new JRadioButton("男",false);
    JRadioButton  jRgirl=new JRadioButton("女",false);
    JLabel jLbirth=new JLabel("生日");
    String timeT[]= {"公历","农历"};
    JLabel jyear=new JLabel("年");
    JLabel jmonth=new JLabel("月");
    JLabel jday=new JLabel("日");
    JComboBox timeType=new JComboBox(timeT);
    DefaultComboBoxModel yearModel = new DefaultComboBoxModel();
    DefaultComboBoxModel monthModel = new DefaultComboBoxModel();
    DefaultComboBoxModel dayModel=new DefaultComboBoxModel();
    JComboBox year = new JComboBox();
    JComboBox month = new JComboBox();
    JComboBox day=new JComboBox();
    JLabel jLplace=new JLabel("所在地");
    JTextField jTplace=new JTextField();
    JCheckBox jService =new JCheckBox("我已阅读并同意相关服务条款",true);
    JButton jBregist=new JButton("立即注册");
    Socket socket;
    private BufferedReader in=null;//输入流
    private PrintStream out=null;//输出流
    public Register() {
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("ISTORM注册");
        init();
        this.add(jLtop);
        this.add(jLeft);
        this.add(jLname);
        this.add(jTname);
        this.add(jLpass);
        this.add(jPass);
        this.add(jLRepass);
        this.add(jRepass);
        this.add(jLmibao);
        this.add(jCmibao);
        this.add(jLmibaoansewr);
        this.add(jmibaoansewr);
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
        this.add(alertmibao);
    }
    public void init() {

        int _base_layout_w_L=100;   // X基线，标签
        int _base_layout_w_T=100;   // X基线，文本框
        int _base_layout_w=50,_base_duration=50;    // Y基线; Y间隔
        int _calculated_layout_h_L[] = new int[20];
        for (int i=0;i<_calculated_layout_h_L.length;++i){
            _calculated_layout_h_L[i]=_base_layout_w+i*_base_duration;
        }


        for (int i = 1950; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
            yearModel.addElement(i);
        }
        for (int j = 1; j <= 12; j++) {
            monthModel.addElement(j);
        }
        for(int k=1; k<=31; k++) {
            dayModel.addElement(k);
        }
        year.setModel((ComboBoxModel) yearModel);
        month.setModel((ComboBoxModel) monthModel);
        day.setModel((ComboBoxModel)dayModel);
        jLtop.setBounds(0, 0, 800, 100);
        jLeft.setBounds(0, 100, 227, 600);
        jLname.setBounds(_base_layout_w_L, _calculated_layout_h_L[1], 50, 50);
        jLname.setFont(new Font("宋体",Font.PLAIN,20));
        alertName.setFont(new Font("宋体",Font.PLAIN,12));
        alertName.setForeground(Color.BLACK);
        alertName.setBounds(_base_layout_w_L+350, _calculated_layout_h_L[1]+10, 200, 20);
        alertPass.setFont(new Font("宋体",Font.PLAIN,12));
        alertPass.setForeground(Color.BLACK);
        alertPass.setBounds(_base_layout_w_L+350, _calculated_layout_h_L[2]+10, 200, 20);
        alertRePass.setFont(new Font("宋体",Font.PLAIN,12));
        alertRePass.setForeground(Color.BLACK);
        alertRePass.setBounds(_base_layout_w_L+350, _calculated_layout_h_L[3]+10, 200, 20);
        jTname.setBounds(jLname.getX()+_base_layout_w_T, jLname.getY(), 220, 45);
        jLpass.setFont(new Font("宋体",Font.PLAIN,20));
        jLpass.setBounds(_base_layout_w_L, _calculated_layout_h_L[2], 50, 50);
        jPass.setBounds(jLpass.getX()+_base_layout_w_T, jLpass.getY(), 220, 45);
        jLRepass.setFont(new Font("宋体",Font.PLAIN,20));
        jLRepass.setBounds(_base_layout_w_L, _calculated_layout_h_L[3], 90, 50);
        jRepass.setBounds(jLRepass.getX()+_base_layout_w_T, jLRepass.getY(), 220, 45);
        jLmibao.setFont(new Font("宋体",Font.PLAIN,20));
        jLmibao.setBounds(_base_layout_w_L, _calculated_layout_h_L[4], 220, 50);
        jCmibao.setFont(new Font("宋体",Font.PLAIN,20));
        jCmibao.setBounds(_base_layout_w_L+_base_layout_w_T, jLmibao.getY(), 220, 45);
        jCmibao.addItem(new String("1"));
        jCmibao.addItem(new String("2"));
        jCmibao.addItem(new String("3"));
        jCmibao.addItem(new String("4"));
        mibao=jCmibao.getSelectedItem().toString();
        jCmibao.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                mibao=jCmibao.getSelectedItem().toString();
            }
        });
        jLmibaoansewr.setFont(new Font("宋体",Font.PLAIN,20));
        jLmibaoansewr.setBounds(_base_layout_w_L, _calculated_layout_h_L[5], 220, 45);
        jmibaoansewr.setFont(new Font("宋体",Font.PLAIN,20));
        jmibaoansewr.setBounds(_base_layout_w_L+_base_layout_w_T, jLmibaoansewr.getY(), 220, 50);
        alertmibao.setFont(new Font("宋体",Font.PLAIN,12));
        alertmibao.setBounds(_base_layout_w_L+350, _calculated_layout_h_L[5]+10, 220, 45);
        jLsex.setFont(new Font("宋体",Font.PLAIN,20));
        jLsex.setBounds(_base_layout_w_L, _calculated_layout_h_L[6], 50, 50);
        jRboy.setFont(new Font("宋体",Font.PLAIN,16));
        jRboy.setForeground(Color.BLUE);
        jRboy.setBounds(jLsex.getX()+_base_layout_w_T, jLsex.getY(), 50, 50);
        jRboy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(e.getSource()==jRboy) {
                    sex="男";
                }
            }

        });
        jRgirl.setFont(new Font("宋体",Font.PLAIN,16));
        jRgirl.setForeground(Color.BLUE);
        jRgirl.setBounds(jLsex.getX()+_base_layout_w_T+60, jLsex.getY(), 50, 50);
        jRgirl.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub\
                if(e.getSource()==jRgirl) {
                    sex="女";
                }

            }

        });
        radioGroup.add(jRboy);
        radioGroup.add(jRgirl);
        jLbirth.setFont(new Font("宋体",Font.PLAIN,20));
        jLbirth.setBounds(_base_layout_w_L, _calculated_layout_h_L[7], 50, 50);
        timeType.setBackground(Color.WHITE);
        timeType.setFont(new Font("宋体",Font.PLAIN,16));
        timeType.setForeground(Color.BLUE);
        timeType.setBounds(jLbirth.getX()+_base_layout_w_T, jLbirth.getY(), 65, 35);
        type=timeType.getSelectedItem().toString();
        timeType.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                type=timeType.getSelectedItem().toString();
            }
        });
        year.setFont(new Font("宋体",Font.PLAIN,16));
        year.setForeground(Color.BLUE);
        year.setBounds(jLbirth.getX()+_base_layout_w_T+90, jLbirth.getY(), 65, 35);
        year.setBackground(Color.WHITE);
        y=year.getSelectedItem().toString();
        year.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                y=year.getSelectedItem().toString();
            }
        });
        jyear.setFont(new Font("宋体",Font.PLAIN,16));
        jyear.setBounds(jLbirth.getX()+_base_layout_w_T+90+70, jLbirth.getY(), 25, 35);
        month.setFont(new Font("宋体",Font.PLAIN,16));
        month.setForeground(Color.BLUE);
        month.setBounds(jLbirth.getX()+_base_layout_w_T+90*2, jLbirth.getY(), 55, 35);
        month.setBackground(Color.WHITE);
        m=month.getSelectedItem().toString();
        month.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                m=month.getSelectedItem().toString();
            }
        });
        jmonth.setFont(new Font("宋体",Font.PLAIN,16));
        jmonth.setBounds(jLbirth.getX()+_base_layout_w_T+90*2+70, jLbirth.getY(), 25, 35);
        day.setFont(new Font("宋体",Font.PLAIN,16));
        day.setForeground(Color.BLUE);
        day.setBounds(jLbirth.getX()+_base_layout_w_T+90*3, jLbirth.getY(), 55, 35);
        day.setBackground(Color.WHITE);
        d=day.getSelectedItem().toString();
        day.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                d=day.getSelectedItem().toString();
            }
        });
        jday.setFont(new Font("宋体",Font.PLAIN,16));
        jday.setBounds(jLbirth.getX()+_base_layout_w_T+90*3+70, jLbirth.getY(), 25, 35);
        jLplace.setFont(new Font("宋体",Font.PLAIN,20));
        jLplace.setBounds(_base_layout_w_L, _calculated_layout_h_L[8], 70, 50);
        jTplace.setBounds(jLplace.getX()+_base_layout_w_T, jLplace.getY(), 220, 45);
        jService.setFont(new Font("宋体",Font.PLAIN,14));
        jService.setBounds(_base_layout_w_L+100, _calculated_layout_h_L[9], 250, 25);
        jBregist.setFont(new Font("宋体",Font.BOLD,26));
        jBregist.setBackground(Color.green);
        jBregist.setForeground(Color.white);
        jBregist.setBounds(_base_layout_w_L+100, _calculated_layout_h_L[10]-20, 200, 60);
        jBregist.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                if(jTname.getText().trim().length()>10||jTname.getText().trim().length()==0) {
                    alertName.setForeground(Color.RED);
                } else if((String.valueOf(jPass.getPassword()).trim().length())<6) {
                    alertName.setText("");
                    alertPass.setText("密码长度不能小于6");
                    alertPass.setForeground(Color.RED);
                } else if((String.valueOf(jPass.getPassword()).trim().length())>16) {
                    alertName.setText("");
                    alertPass.setForeground(Color.RED);
                    alertPass.setText("密码长度不能超过16");
                } else if(!((String.valueOf(jPass.getPassword()).trim()).equals(String.valueOf(jRepass.getPassword()).trim()))) {
                    alertName.setText("");
                    alertPass.setText("");
                    alertRePass.setText("两次输入的密码不一致");
                    alertRePass.setForeground(Color.RED);
                    //JOptionPane.showMessageDialog(jBregist, "两次输入的密码不一致!");
                } else if(String.valueOf(jmibaoansewr.getText()).equals("")) {
                    alertmibao.setText("密保不能为空！");
                    alertmibao.setForeground(Color.RED);
                } else if(sex==null) {
                    alertName.setText("");
                    alertPass.setText("");
                    alertRePass.setText("");
                    JOptionPane.showMessageDialog(jBregist, "请选择你的性别！");
                } else if(type==null||y==null||m==null||d==null) {
                    alertName.setText("");
                    alertPass.setText("");
                    alertRePass.setText("");
                    JOptionPane.showMessageDialog(jBregist, "请注意将你的生日信息选择完整！");
                } else	if(jTplace.getText().trim().length()==0) {
                    alertName.setText("");
                    alertPass.setText("");
                    alertRePass.setText("");
                    JOptionPane.showMessageDialog(jBregist, "所在地不能为空！");
                }

                else {
                    userName=jTname.getText().trim();
                    userPass=String.valueOf(jPass.getPassword()).trim();
                    address=jTplace.getText();
                    birth=type+"-"+y+"年"+m+"月"+d+"日";
                    mibao = mibao;
                    mibaodaan=String.valueOf(jmibaoansewr.getText());
                    try {
                        InetAddress ip=InetAddress.getByName("127.0.0.1");
                        int port=(6544);
                        socket=new Socket(ip,port);
                        System.out.println("与服务器开始连接");
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        System.out.println("服务器端口打开出错");
                    }
                    if(socket!=null) {
                        System.out.println("与服务器连接成功");
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
                            out.println(mibao);
                            out.println(mibaodaan);
                            out.flush();
                            String judge=in.readLine();
                            if(judge.equals("registerOver")) {
                                String userNum=in.readLine();
                                Check checkQQ=new Check(userNum);
                                checkQQ.setVisible(true);
                                Register.this.setVisible(false);
                            }
                            if(judge.equals("registerFail")) {
                                System.out.println("注册失败！");
                                JOptionPane.showMessageDialog(jBregist,"注册失败，请重新注册");
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
    public static void main(String args[]) {
        Register r=new Register();
        r.setVisible(true);
    }


}
