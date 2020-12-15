package user;

import UI.PersonelView;
import user.UserBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

//修改个人资料
public class ChangePass extends JDialog implements ActionListener {
    BufferedReader in;//定义输入流
    PrintStream out;//定义输出流
    UserBean myInfo=null;
    PersonelView father=null;

    JLabel jLoldpass=new JLabel("旧密码");
    JLabel jLnewpass1=new JLabel("新密码");
    JLabel jLnewpass2=new JLabel("重复新密码");
    JPasswordField jTold=new JPasswordField();
    JPasswordField jTnew1=new JPasswordField();
    JPasswordField jTnew2=new JPasswordField();
    JLabel alertold=new JLabel("");
    JLabel alertnew2=new JLabel("");
    JButton jBupdate=new JButton("提交");

    public ChangePass(JFrame owner, String title, Boolean b, BufferedReader in, PrintStream out, UserBean myInfo, PersonelView father) {
        super(owner,title,b);
        this.in=in;
        this.out=out;
        this.myInfo=myInfo;
        this.father=father;
        init();
        this.setSize(455, 270);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(jLoldpass);
        this.add(jLnewpass1);
        this.add(jLnewpass2);
        this.add(jTold);
        this.add(jTnew1);
        this.add(jTnew2);
        this.add(alertold);
        this.add(alertnew2);
        this.add(jBupdate);
    }
    public void init() {
        int _base_layout_w_L=50;   // X基线，标签
        int _base_layout_w_T=80;   // X基线，文本框
        int _base_layout_w_A=200;
        int _base_layout_w=50,_base_duration=40;    // Y基线; Y间隔
        int _calculated_layout_h_L[] = new int[20];
        for (int i=0;i<_calculated_layout_h_L.length;++i){
            _calculated_layout_h_L[i]=_base_layout_w+i*_base_duration;
        }

        jLoldpass.setBounds(_base_layout_w_L, _calculated_layout_h_L[0], 50, 30);
        jLoldpass.setFont(new Font("宋体",Font.PLAIN,12));
        jTold.setBounds(_base_layout_w_L+_base_layout_w_T, _calculated_layout_h_L[0], 180, 30);
        jTold.setFont(new Font("宋体",Font.PLAIN,12));
        alertold.setBounds(_base_layout_w_L+_base_layout_w_T+_base_layout_w_A, _calculated_layout_h_L[0]+10, 200, 20);
        alertold.setFont(new Font("宋体",Font.PLAIN,12));

        jLnewpass1.setBounds(_base_layout_w_L, _calculated_layout_h_L[1], 50, 30);
        jLnewpass1.setFont(new Font("宋体",Font.PLAIN,12));
        jTnew1.setBounds(_base_layout_w_L+_base_layout_w_T, _calculated_layout_h_L[1], 180, 30);
        jTnew1.setFont(new Font("宋体",Font.PLAIN,12));

        jLnewpass2.setBounds(_base_layout_w_L, _calculated_layout_h_L[2], 80, 30);
        jLnewpass2.setFont(new Font("宋体",Font.PLAIN,12));
        jTnew2.setBounds(_base_layout_w_L+_base_layout_w_T, _calculated_layout_h_L[2], 180, 30);
        jTnew2.setFont(new Font("宋体",Font.PLAIN,12));
        alertnew2.setBounds(_base_layout_w_L+_base_layout_w_T+_base_layout_w_A, _calculated_layout_h_L[2]+10, 200, 20);
        alertnew2.setFont(new Font("宋体",Font.PLAIN,12));

        jBupdate.setFont(new Font("宋体",Font.BOLD,26));
        jBupdate.setBackground(Color.green);
        jBupdate.setForeground(Color.white);
        jBupdate.setBounds(_base_layout_w_L+60, _calculated_layout_h_L[3], 200, 60);
        jBupdate.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
// TODO Auto-generated method stub
        if(!( String.valueOf(jTnew1.getPassword()).equals(String.valueOf(jTnew2.getPassword())))){
            alertnew2.setText("密码不一致");
            alertnew2.setForeground(Color.RED);
        }else{
            alertnew2.setText("");
            alertnew2.setForeground(Color.BLACK);
            alertold.setText("");
            alertold.setForeground(Color.BLACK);
            out.println("updatePass");

            out.println(myInfo.getUserNum());
            out.println(jTold.getPassword());
            out.println(jTnew2.getPassword());
            String ret= "unexpected error";
            try {
                ret = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(ret.contains("OK")){
                myInfo.setPassword(String.valueOf(jTnew2.getPassword()));
                father.setUserPass(String.valueOf(jTnew2.getPassword()));
                JOptionPane.showMessageDialog(null, "Done");
            }else if(ret.contains("NOMATCH")){
                alertold.setText("旧密码错误");
                alertold.setForeground(Color.RED);
            }
        }
    }
}
