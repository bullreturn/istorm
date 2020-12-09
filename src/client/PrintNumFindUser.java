package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
//输入QQ号界面
public class PrintNumFindUser extends JDialog {
    PersonelView father=null;
    String title;
    JLabel jLpoint=new JLabel();
    JTextField jTnum=new JTextField();
    ImageIcon image=new ImageIcon("src/file/PNFUfind.jpg");
    JButton jBfind=new JButton(image);//85*26
    public PrintNumFindUser(JFrame owner,String title,Boolean modal,PersonelView father) {
        super(owner,title,modal);
        this.title=title;
        this.father=father;
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setSize(250, 150);
        this.add(jLpoint);
        this.add(jTnum);
        this.add(jBfind);
        init();
        this.setVisible(true);
    }
    public void init() {
        jLpoint.setText("亲，请输入你要"+title+"的账号呢~~");
        jLpoint.setBounds(5, 5, 250, 25);
        jTnum.setFont(new Font("宋体",Font.BOLD,14));
        jTnum.setForeground(Color.GRAY);
        jTnum.setBounds(30, 45, 180, 25);
        jBfind.setFont(new Font("楷体",Font.PLAIN,10));
        jBfind.setBounds(135, 80, 85, 26);
        jBfind.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                father.findUser(jTnum.getText().trim());
                PrintNumFindUser.this.setVisible(false);
            }

        });
    }

}
