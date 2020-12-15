package UI.Forum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class CheckDelete extends JFrame {
    private BufferedReader in=null;//输入流
    private PrintStream out=null;//输出流
    java.util.Timer showtimeTimer;
    JLabel jLtime=new JLabel();
    private String id;
    private Boolean ispost;

    public CheckDelete(String id, BufferedReader in, PrintStream out, Boolean ispost,Delete delete,int index) {
        this.in = in;
        this.out = out;
        this.id = id;
        this.ispost = ispost;
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("核查");
        JLabel tip = new JLabel();
        JButton no = new JButton("否");
        JButton yes = new JButton("是");

        if(ispost) {
            tip.setText("删除这个帖子？");
        } else {
            tip.setText("删除这个评论？");
        }
        tip.setBounds(50,50,200,25);
        tip.setFont(new Font("宋体", Font.PLAIN, 11));

        yes.setBounds(50,100,80,30);
        yes.setFont(new Font("宋体", Font.PLAIN, 11));

        no.setBounds(170,100,80,30);
        no.setFont(new Font("宋体", Font.PLAIN, 11));

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ispost) {
                    out.println("DeletePost");
                    out.flush();
                    out.println(id);
                    out.flush();
                    try {
                        String ok = in.readLine();
                        if (ok.equals("DeletePostOver")){
                            delete.model1.removeRow(index);
                            delete.postlist.setModel(delete.model1);
                            String t = new String();
                            t = "删除成功";
                            Success success = new Success(t);
                            success.setVisible(true);
                            CheckDelete.this.setVisible(false);
                        }
                        else if(ok.equals("DeleteFail")){
                            System.out.println(ok);
                            String t = new String();
                            t = "删除失败，请重试";
                            Error error = new Error(t);
                            error.setVisible(true);
                            CheckDelete.this.setVisible(false);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    out.println("DeleteComment");
                    out.flush();
                    out.println(id);
                    out.flush();
                    try {
                        String ok = in.readLine();
                        if(ok.equals("DeleteCommentOver")){
                            delete.model2.removeRow(index);
                            delete.commentlist.setModel(delete.model2);
                            String t = new String();
                            t = "删除成功";
                            Success success = new Success(t);
                            success.setVisible(true);
                            CheckDelete.this.setVisible(false);
                        } else if (ok.equals("DeleteFail")){
                            String t = new String();
                            t = "删除失败，请重试";
                            Error error = new Error(t);
                            error.setVisible(true);
                            CheckDelete.this.setVisible(false);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckDelete.this.setVisible(false);
            }
        });
        this.add(tip);
        this.add(yes);
        this.add(no);
    }
}
