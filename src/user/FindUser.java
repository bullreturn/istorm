package user;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import UI.PersonelView;

/*---------------客户端查找好友后信息显示--------------------*/
public class FindUser extends JDialog {
    JLabel jLtitlepic=new JLabel(new ImageIcon("src/file/showInfo1.jpg"));
    JLabel jLtitlename=new JLabel("我的资料");
    JLabel jLhead=new JLabel(new ImageIcon("src/file/1-1.jpg"));//显示头像
    JLabel jLinfo5=new JLabel(new ImageIcon("src/file/info5.jpg"));
    JLabel jLchageHead=new JLabel(new ImageIcon("src/file/head.jpg"));
    JLabel info1=new JLabel(new ImageIcon("src/file/info1.jpg"));
    JLabel info2=new JLabel(new ImageIcon("src/file/info1.jpg"));
    JLabel info3=new JLabel(new ImageIcon("src/file/info3.jpg"));
    JLabel info4=new JLabel(new ImageIcon("src/file/info4.jpg"));
    JLabel jLname_num=new JLabel();
    JLabel jLqqage=new JLabel("ISTORM  龄: ");
    JLabel jLrank=new JLabel("等  级: ");
    JLabel jLrankPic=new JLabel(new ImageIcon("src/file/rank.jpg"));
    JLabel jLqq_age=new JLabel("---");
    JLabel jLrealName=new JLabel("姓  名: -");
    JLabel jLengName=new JLabel("英文名: -");
    JLabel jLcomment=new JLabel("备  注: -");
    JLabel jLsign=new JLabel("个性签名: ");
    JLabel jLshowInfo5=new JLabel(new ImageIcon("src/file/1.jpg"));
    JLabel jLsex=new JLabel("性  别: ");
    JLabel jLage=new JLabel("年  龄: ");
    JLabel jLbirth=new JLabel("生  日: ");
    JLabel jLblood=new JLabel("血  型: O型");
    JLabel jLanimal=new JLabel("生  肖: 猴");
    JLabel jLconstellation=new JLabel("星  座: 狮子座");
    JLabel jLlocation=new JLabel("地  址: ");
    JLabel jLaddress=new JLabel("所在地: ");
    JLabel jLmail=new JLabel("邮 箱: -");
    ImageIcon add=new ImageIcon("src/file/FUaddUser.jpg");//64*23
    ImageIcon close=new ImageIcon("src/file/FUclose.jpg");//65*21
    JButton jBclose=new JButton(close);
    JButton jBaddUser=new JButton(add);
    UserBean user=null;
    PersonelView father=null;
    public FindUser(Frame info, String title, boolean b,
                    UserBean user,PersonelView father) {
        super(info,title,b);
        this.setSize(555, 447);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.user=user;
        this.father=father;
        init();
        this.add(jLtitlename);
        this.add(jLtitlepic);
        this.add(jLhead);
        this.add(jLhead);
        this.add(jLchageHead);
        this.add(info1);
        this.add(info2);
        this.add(info3);
        this.add(info4);
        this.add(jLname_num);
        this.add(jLqqage);
        this.add(jLqq_age);
        this.add(jLrank);
        this.add(jLrankPic);
        this.add(jLrealName);
        this.add(jLengName);
        this.add(jLcomment);
        this.add(jLsign);
        this.add(jLshowInfo5);
        this.add(jLsex);
        this.add(jLage);
        this.add(jLbirth);
        this.add(jLblood);
        this.add(jLanimal);
        this.add(jLconstellation);
        this.add(jLlocation);
        this.add(jLaddress);
        this.add(jLmail);
        this.add(jLinfo5);
        this.add(jBaddUser);
        this.add(jBclose);
        getInfo();

    }
    public void init() {
        jLtitlepic.setBounds(5, 5, 20, 18);
        jLtitlename.setFont(new Font("宋体",Font.BOLD,11));
        jLtitlename.setBounds(30, 2, 200, 25);
        jLhead.setBounds(15, 40, 96, 93);
        jLinfo5.setBounds(15, 40, 96, 93);
        jLchageHead.setBounds(0, 133, 134, 284);
        info1.setBounds(111, 0, 23, 133);
        info2.setBounds(0, 40, 15, 93);
        info3.setBounds(0, 0, 111, 40);
        info4.setBounds(133, 0, 432, 40);
        jLname_num.setFont(new Font("黑体",Font.PLAIN,13));
        jLname_num.setForeground(Color.BLACK);
        jLname_num.setBounds(150, 50, 200, 25);
        jLqqage.setFont(new Font("黑体",Font.PLAIN,13));
        jLqqage.setForeground(Color.BLACK);
        jLqqage.setBounds(150, 80, 60, 25);
        jLqq_age.setFont(new Font("黑体",Font.PLAIN,13));
        jLqq_age.setForeground(Color.BLACK);
        jLqq_age.setBounds(210, 80, 30, 25);
        jLrank.setFont(new Font("黑体",Font.PLAIN,13));
        jLrank.setForeground(Color.BLACK);
        jLrank.setBounds(280, 80,65,25);
        jLrankPic.setBounds(350, 84, 15, 17);
        jLrealName.setFont(new Font("黑体",Font.PLAIN,13));
        jLrealName.setForeground(Color.BLACK);
        jLrealName.setBounds(150, 110, 130, 25);
        jLengName.setFont(new Font("黑体",Font.PLAIN,13));
        jLengName.setForeground(Color.BLACK);
        jLengName.setBounds(280, 110, 130, 25);
        jLcomment.setFont(new Font("黑体",Font.PLAIN,13));
        jLcomment.setForeground(Color.BLACK);
        jLcomment.setBounds(150, 140, 130, 25);
        jLsign.setFont(new Font("黑体",Font.PLAIN,13));
        jLsign.setForeground(Color.BLACK);
        jLsign.setBounds(150, 170, 405, 25);
        jLshowInfo5.setBounds(111, 200, 454, 20);
        jLsex.setFont(new Font("黑体",Font.PLAIN,13));
        jLsex.setForeground(Color.BLACK);
        jLsex.setBounds(150, 230, 80, 25);
        jLage.setFont(new Font("黑体",Font.PLAIN,13));
        jLage.setForeground(Color.BLACK);
        jLage.setBounds(250, 230, 70, 25);
        jLbirth.setFont(new Font("黑体",Font.PLAIN,13));
        jLbirth.setForeground(Color.BLACK);
        jLbirth.setBounds(340, 230, 215, 25);
        jLblood.setFont(new Font("黑体",Font.PLAIN,13));
        jLblood.setForeground(Color.BLACK);
        jLblood.setBounds(150, 260, 80, 25);
        jLanimal.setFont(new Font("黑体",Font.PLAIN,13));
        jLanimal.setForeground(Color.BLACK);
        jLanimal.setBounds(250, 260,70, 25);
        jLconstellation.setFont(new Font("黑体",Font.PLAIN,13));
        jLconstellation.setForeground(Color.BLACK);
        jLconstellation.setBounds(340,260, 215, 25);
        jLlocation.setFont(new Font("黑体",Font.PLAIN,13));
        jLlocation.setForeground(Color.BLACK);
        jLlocation.setBounds(150, 290, 405, 25);
        jLaddress.setFont(new Font("黑体",Font.PLAIN,13));
        jLaddress.setForeground(Color.BLACK);
        jLaddress.setBounds(150,320,405, 25);
        jLmail.setFont(new Font("黑体",Font.PLAIN,13));
        jLmail.setForeground(Color.BLACK);
        jLmail.setBounds(150, 350, 405, 25);
        jBaddUser.setBounds(392, 388, 64, 23);
        jBaddUser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                father.addUser();
                FindUser.this.setVisible(false);
            }

        });
        jBclose.setBounds(470, 388, 65, 21);
        jBclose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FindUser.this.setVisible(false);
            }

        });
    }
    public void getInfo() {
        String age;
        int month;
        String constellation = null;
        jLtitlename.setText(user.getUserName().trim()+"的资料");
        jLname_num.setText(user.getUserName()+" "+user.getUserNum());
        jLsign.setText("个性签名: "+user.getSign());
        jLsex.setText("性  别: "+user.getSex());
        jLbirth.setText("生  日: "+user.getBirth());
        jLaddress.setText("所在地: "+user.getAddress());
        jLlocation.setText("地  址: "+user.getAddress());
        jLhead.setIcon(new ImageIcon(user.getPortrait()));
        age=String.valueOf((2020-Integer.valueOf(user.getBirth().substring(user.getBirth().indexOf("-")+1,user.getBirth().indexOf("年")))));
        System.out.println(age);
        jLage.setText("年  龄: "+age);
        month=Integer.valueOf(user.getBirth().substring(user.getBirth().indexOf("年")+1,user.getBirth().indexOf("月")));
        switch(month) {
        case 1:
            constellation="魔羯座";
            break;
        case 2:
            constellation="水瓶座";
            break;
        case 3:
            constellation="双鱼座";
            break;
        case 4:
            constellation="牡羊座";
            break;
        case 5:
            constellation="金牛座";
            break;
        case 6:
            constellation="双子座";
            break;
        case 7:
            constellation="巨蟹座";
            break;
        case 8:
            constellation="狮子座";
            break;
        case 9:
            constellation="处女座";
            break;
        case 10:
            constellation="天秤座";
            break;
        case 11:
            constellation="天蝎座";
            break;
        case 12:
            constellation="射手座";
            break;
        }
        jLconstellation.setText("星  座: "+constellation);
    }
}
