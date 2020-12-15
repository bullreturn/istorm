package user;
//获取ISTORM号码
import UI.HomePage;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Check extends JFrame {
    HomePage enter;
    String userNum;
    JLabel jLtop=new JLabel(new ImageIcon("src/file/checkQQ1.jpg"));
    JLabel jLeft=new JLabel(new ImageIcon("src/file/checkQQ2.jpg"));
    JLabel jLgetqq=new JLabel(new ImageIcon("src/file/checkGet.jpg"));
    JLabel jLtext1=new JLabel("申请成功");
    JLabel jLtext2=new JLabel("您获得号码：");
    JLabel jLqq=new JLabel();
    JButton jBenter=new JButton("登陆ISTORM");
    public Check(String userNum) {
        // TODO Auto-generated constructor stub
        this.userNum=userNum;
        this.setSize(600, 389);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("ISTORM号获取");
        init();
        this.add(jLeft);
        this.add(jLtop);
        this.add(jLgetqq);
        this.add(jLtext1);
        this.add(jLtext2);
        this.add(jLqq);
        this.add(jBenter);
    }
    public void init() {
        jLtop.setBounds(0, 0, 600, 59);
        jLeft.setBounds(0, 56, 201, 330);
        jLgetqq.setBounds(220, 85, 45, 43);
        jLtext1.setForeground(Color.BLACK);
        jLtext1.setFont(new Font("宋体",Font.BOLD,24));
        jLtext1.setBounds(285, 60, 220, 50);
        jLtext2.setForeground(Color.BLACK);
        jLtext2.setFont(new Font("宋体",Font.BOLD,24));
        jLtext2.setBounds(285, 100, 150, 50);
        jLqq.setText(userNum);
        jLqq.setForeground(Color.RED);
        jLqq.setFont(new Font("宋体",Font.BOLD,24));
        jLqq.setBounds(435, 100, 165, 50);
        jBenter.setForeground(Color.WHITE);
        jBenter.setBackground(Color.GREEN);
        jBenter.setFont(new Font("宋体",Font.BOLD,24));
        jBenter.setBounds(285, 200, 120, 50);
        jBenter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Check.this.setVisible(false);
                enter=new HomePage();
                enter.setVisible(true);
            }

        });
    }

}
