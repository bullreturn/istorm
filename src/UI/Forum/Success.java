package UI.Forum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Success extends JFrame{
    public Success(String success) {
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("成功");
        JLabel ErrorImformation = new JLabel(success);
        JButton Check = new JButton("确认");
        ErrorImformation.setBounds(50,50,200,50);
        ErrorImformation.setFont(new Font("宋体", Font.PLAIN, 11));

        Check.setBounds(125,100,50,25);
        Check.setFont(new Font("宋体", Font.PLAIN, 11));
        this.add(ErrorImformation);
        this.add(Check);
        Check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Success.this.setVisible(false);
            }
        });
    }
}
