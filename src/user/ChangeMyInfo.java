package user;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import UI.PersonelView;

//修改个人资料
public class ChangeMyInfo extends JDialog {
    JPanel jp1=new JPanel();//上部，个人资料显示
    JPanel jp2=new JPanel();//中部
    JPanel jp3=new JPanel();
    JPanel jp4=new JPanel();
    JPanel jp5=new JPanel();
    JPanel jp6=new JPanel();
    JLabel jLmyPortrait=new JLabel(new ImageIcon("src/file/personelView1.jpg"));
    JButton jBchange=new JButton(new ImageIcon("src/file/CMchange.jpg"));//77*18
    JLabel jLmyName=new JLabel("我是毛毛虫");
    JLabel jLmyNum=new JLabel("593253716");
    JLabel jLmySign=new JLabel("嘿嘿");
    JLabel jLmyother=new JLabel("女   20岁   成都");
    JButton jBsave=new JButton(new ImageIcon("src/file/CMsave.jpg"));//63*19
    JButton jBclose=new JButton(new ImageIcon("src/file/CMclose.jpg"));//62*19
    JLabel jLsign=new JLabel("个性签名：");
    JTextArea jTsign=new JTextArea("嘿嘿");
    JLabel jLName=new JLabel("昵称：");
    JTextField jTname=new JTextField("我是毛毛虫");
    JLabel jLenglishName=new JLabel("英文名：");
    JTextField jTenglishName=new JTextField("Sweety");
    JLabel jLsex=new JLabel("性别：");
    String sex[]= {"男","女"};
    JComboBox jTsex=new JComboBox(sex);
    JLabel jLblood=new JLabel("血 型：");
    String blood[]= {"A型","B型","AB型","O型","其它型"};
    JComboBox jTblood=new JComboBox(blood);
    JLabel jLbirth=new JLabel("生日：");
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
    JLabel jLaddress=new JLabel("所在地：");
    JTextField jTaddress=new JTextField("四川 成都");
    JLabel jLplace=new JLabel("地址：");
    JTextField jTplace=new JTextField("四川 成都");
    BufferedReader in;//定义输入流
    PrintStream out;//定义输出流
    UserBean myInfo=null;
    PersonelView father=null;
    String age=null;
    String sign=null;
    String name=null;
    String mySex=null;
    String birth=null;
    String address=null;
    String type,myear,mymonth,myday;
    public ChangeMyInfo(JFrame owner,String title,Boolean b,BufferedReader in,PrintStream out,UserBean myInfo,PersonelView father) {
        super(owner,title,b);
        this.in=in;
        this.out=out;
        this.myInfo=myInfo;
        this.father=father;
        sign=myInfo.getSign();
        name=myInfo.getUserName();
        mySex=myInfo.getSex();
        birth=myInfo.getBirth();
        address=myInfo.getAddress();
        init();
        this.setSize(455, 540);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        this.add(jp5);
        this.add(jp6);
        this.add(jBsave);
        this.add(jBclose);
        this.add(jLsign);
        this.add(jTsign);
        this.add(jLName);
        this.add(jTname);
        this.add(jLenglishName);
        this.add(jTenglishName);
        this.add(jLsex);
        this.add(jTsex);
        this.add(jLblood);
        this.add(jTblood);
        this.add(jLbirth);
        this.add(timeType);
        this.add(year);
        this.add(jyear);
        this.add(month);
        this.add(jmonth);
        this.add(day);
        this.add(jday);
        this.add(jLaddress);
        this.add(jTaddress);
        this.add(jLplace);
        this.add(jTplace);
    }
    public void init() {
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
        jp1.setLayout(null);
        jp1.setBackground(new Color(30,144,255));
        jp1.setBounds(0, 0, 455, 120);
        jLmyPortrait.setBounds(10, 30, 60, 60);
        jBchange.setBounds(5, 100, 77, 18);
        jBchange.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                ChangeMyInfo.this.setVisible(false);
                father.changHead();
            }

        });
        jLmyName.setForeground(Color.WHITE);
        jLmyName.setFont(new Font("宋体",Font.BOLD,24));
        jLmyName.setBounds(80, 25, 150, 25);
        jLmyNum.setForeground(Color.WHITE);
        jLmyNum.setFont(new Font("楷体",Font.PLAIN,18));
        jLmyNum.setBounds(230, 27, 150, 23);
        jLmySign.setForeground(Color.WHITE);
        jLmySign.setFont(new Font("楷体",Font.PLAIN,14));
        jLmySign.setBounds(80, 60, 370, 25);
        jLmyother.setForeground(Color.WHITE);
        jLmyother.setFont(new Font("宋体",Font.PLAIN,14));
        jLmyother.setBounds(100, 90, 150, 25);
        jp1.add(jLmyPortrait);
        jp1.add(jBchange);
        jp1.add(jLmyName);
        jp1.add(jLmyNum);
        jp1.add(jLmySign);
        jp1.add(jLmyother);
        //中部，两个按钮
        jp2.setBounds(0,126,250, 19);
        jp2.setBackground(Color.WHITE);
        jp3.setBackground(Color.WHITE);
        jp4.setBackground(Color.WHITE);
        jp5.setBackground(Color.WHITE);
        jp6.setBackground(Color.WHITE);
        jp3.setBounds(0,120, 455, 6);
        jp4.setBounds(313, 126, 20, 19);
        jp5.setBounds(0,145,455,10);
        jp6.setBounds(395,126,60,19);
        jBsave.setSize(63, 19);
        jBsave.setLocation(250, 126);
        jBsave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                if(type!=null&&myear!=null&&mymonth!=null&&myday!=null) {
                    birth=type+"-"+myear+"年"+mymonth+"月"+myday+"日";
                }
                sign=jTsign.getText();
                name=jTname.getText();
                address=jTaddress.getText();
                out.println("updateOwnInformation");
                out.flush();
                out.println(myInfo.getUserNum());
                out.flush();
                out.println(name);
                out.flush();
                out.println(mySex);
                out.flush();
                out.println(birth);
                out.flush();
                out.println(address);
                out.flush();
                out.println(sign);
                out.flush();
                try {
                    String judge=in.readLine();
                    if(judge.equals("updateOver")) {
                        ChangeMyInfo.this.setVisible(false);
                        System.out.println("修改成功");
                    } else if(judge.equals("updateFail")) {
                        ChangeMyInfo.this.setVisible(false);
                        JOptionPane.showConfirmDialog(father, "系统繁忙,请稍后再试！");
                        System.out.println("系统繁忙");
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
        jBclose.setSize(62, 19);
        jBclose.setLocation(333, 126);
        jBclose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                ChangeMyInfo.this.setVisible(false);
            }

        });
        //修改资料部分
        jLsign.setForeground(Color.BLACK);
        jLsign.setFont(new Font("楷体",Font.PLAIN,14));
        jLsign.setBounds(10, 165, 100, 25);
        jTsign.setFont(new Font("楷体",Font.PLAIN,14));
        jTsign.setForeground(Color.BLACK);
        jTsign.setBackground(Color.WHITE);
        jTsign.setBounds(10, 200, 415, 40);
        jTsign.setLineWrap(true);//自动换行
        jLName.setForeground(Color.BLACK);
        jLName.setFont(new Font("楷体",Font.PLAIN,14));
        jLName.setBounds(10, 260, 45, 25);
        jTname.setForeground(Color.BLACK);
        jTname.setFont(new Font("楷体",Font.PLAIN,14));
        jTname.setBackground(Color.WHITE);
        jTname.setBounds(55, 261, 130, 22);
        jLenglishName.setForeground(Color.BLACK);
        jLenglishName.setFont(new Font("楷体",Font.PLAIN,14));
        jLenglishName.setBounds(215, 260, 65, 25);
        jTenglishName.setForeground(Color.BLACK);
        jTenglishName.setFont(new Font("楷体",Font.PLAIN,14));
        jTenglishName.setBackground(Color.WHITE);
        jTenglishName.setBounds(280, 261, 130, 22);
        jLsex.setForeground(Color.BLACK);
        jLsex.setFont(new Font("楷体",Font.PLAIN,14));
        jLsex.setBounds(10, 300, 45, 25);
        jTsex.setForeground(Color.BLACK);
        jTsex.setFont(new Font("楷体",Font.PLAIN,14));
        jTsex.setBackground(Color.WHITE);
        jTsex.setBounds(55, 301, 130, 22);
        jTsex.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                // TODO Auto-generated method stub
                mySex=jTsex.getSelectedItem().toString();
            }

        });
        jLblood.setForeground(Color.BLACK);
        jLblood.setFont(new Font("楷体",Font.PLAIN,14));
        jLblood.setBounds(215, 300, 65, 25);
        jTblood.setForeground(Color.BLACK);
        jTblood.setFont(new Font("楷体",Font.PLAIN,14));
        jTblood.setBackground(Color.WHITE);
        jTblood.setBounds(280, 301, 130, 22);
        jLbirth.setForeground(Color.BLACK);
        jLbirth.setFont(new Font("楷体",Font.PLAIN,14));
        jLbirth.setBounds(10, 340, 45, 25);
        timeType.setForeground(Color.BLACK);
        timeType.setFont(new Font("楷体",Font.PLAIN,14));
        timeType.setBackground(Color.WHITE);
        timeType.setBounds(55, 341, 60, 22);
        timeType.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                // TODO Auto-generated method stub
                type=timeType.getSelectedItem().toString();
            }

        });
        year.setForeground(Color.BLACK);
        year.setFont(new Font("楷体",Font.PLAIN,14));
        year.setBackground(Color.WHITE);
        year.setBounds(120, 341, 60, 22);
        year.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                // TODO Auto-generated method stub
                myear=year.getSelectedItem().toString();
            }

        });
        jyear.setForeground(Color.BLACK);
        jyear.setFont(new Font("楷体",Font.PLAIN,14));
        jyear.setBounds(182, 341, 20, 22);
        month.setForeground(Color.BLACK);
        month.setFont(new Font("楷体",Font.PLAIN,14));
        month.setBackground(Color.WHITE);
        month.setBounds(205, 341, 60, 22);
        month.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                // TODO Auto-generated method stub
                mymonth=month.getSelectedItem().toString();
            }

        });
        jmonth.setForeground(Color.BLACK);
        jmonth.setFont(new Font("楷体",Font.PLAIN,14));
        jmonth.setBounds(267, 341, 20, 22);
        day.setForeground(Color.BLACK);
        day.setFont(new Font("楷体",Font.PLAIN,14));
        day.setBackground(Color.WHITE);
        day.setBounds(290, 341, 60, 22);
        day.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                // TODO Auto-generated method stub
                myday=day.getSelectedItem().toString();
            }

        });
        jday.setForeground(Color.BLACK);
        jday.setFont(new Font("楷体",Font.PLAIN,14));
        jday.setBounds(352, 341, 20, 22);
        jLaddress.setForeground(Color.BLACK);
        jLaddress.setFont(new Font("楷体",Font.PLAIN,14));
        jLaddress.setBounds(10, 380, 65, 25);
        jTaddress.setForeground(Color.BLACK);
        jTaddress.setFont(new Font("楷体",Font.PLAIN,14));
        jTaddress.setBackground(Color.WHITE);
        jTaddress.setBounds(75, 381, 340, 22);
        jLplace.setForeground(Color.BLACK);
        jLplace.setFont(new Font("楷体",Font.PLAIN,14));
        jLplace.setBounds(10, 420, 65, 25);
        jTplace.setForeground(Color.BLACK);
        jTplace.setFont(new Font("楷体",Font.PLAIN,14));
        jTplace.setBackground(Color.WHITE);
        jTplace.setBounds(75, 421, 340, 22);
        jLmyPortrait.setIcon(new ImageIcon(myInfo.getPortrait()));
        jLmyName.setText(myInfo.getUserName());
        jLmyNum.setText(myInfo.getUserNum());
        jLmySign.setText(myInfo.getSign());
        age=String.valueOf((2020-Integer.valueOf(myInfo.getBirth().substring(myInfo.getBirth().indexOf("-")+1,myInfo.getBirth().indexOf("年")))));
        jLmyother.setText(myInfo.getSex()+" "+age+" "+myInfo.getAddress());
        jTsign.setText(myInfo.getSign());
        jTname.setText(myInfo.getUserName());
        jTaddress.setText(myInfo.getAddress());
        jTplace.setText(myInfo.getAddress());
    }

}
