package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//修改个人资料
public class FindPass extends JDialog implements ActionListener {
    BufferedReader in;//定义输入流
    PrintStream out;//定义输出流

    JLabel jLnumber =new JLabel("帐号");
    JLabel jLmibao =new JLabel("密保问题");
    JLabel jLmibaodaan =new JLabel("密保答案");
    JLabel jLpass =new JLabel("新密码");
    JTextField jTnumber =new JTextField();
    JComboBox jCmibao =new JComboBox();
    JTextField jTmibaodaan =new JTextField();
    JPasswordField jTpass =new JPasswordField();
    JButton jbu=new JButton("提交");

    public FindPass(String title) {
        super();
        Socket socket;
        try{
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 6544);
            this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out=out=new PrintStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        init();
        this.setSize(455, 270);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(jLnumber);
        this.add(jLmibao);
        this.add(jLmibaodaan);
        this.add(jTnumber);
        this.add(jCmibao);
        this.add(jTmibaodaan);
        this.add(jbu);
        this.add(jTpass);
        this.add(jLpass);
    }
    public void init() {
        int _base_layout_w_L=50;   // X基线，标签
        int _base_layout_w_T=80;   // X基线，文本框
        int _base_layout_w_A=200;
        int _base_layout_w=10,_base_duration=40;    // Y基线; Y间隔
        int _calculated_layout_h_L[] = new int[20];
        for (int i=0;i<_calculated_layout_h_L.length;++i){
            _calculated_layout_h_L[i]=_base_layout_w+i*_base_duration;
        }

        jLnumber.setBounds(_base_layout_w_L, _calculated_layout_h_L[0], 50, 30);
        jLnumber.setFont(new Font("宋体",Font.PLAIN,12));
        jTnumber.setBounds(_base_layout_w_L+_base_layout_w_T, _calculated_layout_h_L[0], 180, 30);
        jTnumber.setFont(new Font("宋体",Font.PLAIN,12));

        jLmibao.setBounds(_base_layout_w_L, _calculated_layout_h_L[1], 50, 30);
        jLmibao.setFont(new Font("宋体",Font.PLAIN,12));
        jCmibao.setBounds(_base_layout_w_L+_base_layout_w_T, _calculated_layout_h_L[1], 180, 30);
        jCmibao.setFont(new Font("宋体",Font.PLAIN,12));
        jCmibao.addItem(new String("1"));
        jCmibao.addItem(new String("2"));
        jCmibao.addItem(new String("3"));
        jCmibao.addItem(new String("4"));

        jLmibaodaan.setBounds(_base_layout_w_L, _calculated_layout_h_L[2], 80, 30);
        jLmibaodaan.setFont(new Font("宋体",Font.PLAIN,12));
        jTmibaodaan.setBounds(_base_layout_w_L+_base_layout_w_T, _calculated_layout_h_L[2], 180, 30);
        jTmibaodaan.setFont(new Font("宋体",Font.PLAIN,12));

        jLpass.setBounds(_base_layout_w_L, _calculated_layout_h_L[3], 80, 30);
        jLpass.setFont(new Font("宋体",Font.PLAIN,12));
        jTpass.setBounds(_base_layout_w_L+_base_layout_w_T, _calculated_layout_h_L[3], 180, 30);
        jTpass.setFont(new Font("宋体",Font.PLAIN,12));

        jbu.setFont(new Font("宋体",Font.BOLD,26));
        jbu.setBackground(Color.green);
        jbu.setForeground(Color.white);
        jbu.setBounds(_base_layout_w_L+60, _calculated_layout_h_L[4], 200, 60);
        jbu.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
// TODO Auto-generated method stub
            out.println("updatePassWithMibao");

            out.println(jTnumber.getText());
            out.println(jCmibao.getSelectedItem().toString());
            out.println(jTmibaodaan.getText());
            out.println(jTpass.getPassword());
            String ret= "unexpected error";
            try {
                ret = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, ret);

    }
}
