package client.Forum;

import common.ShowTimeTask;
import common.UserBean;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Vector;

public class Delete extends JFrame{
    java.util.Timer showtimeTimer;
    private Hashtable ForumTable= new Hashtable();//将Forum的对象统一存储到HashTable中
    private BufferedReader in=null;//输入流
    private PrintStream out=null;//输出流
    UserBean userBean = null;

    public Vector data1 = new Vector();
    public Vector column1 = new Vector();
    public  Vector row1 = new Vector();
    public Vector data2 = new Vector();
    public Vector column2 = new Vector();
    public Vector row2 = new Vector();
    public DefaultTableModel model1 = new DefaultTableModel();
    public DefaultTableModel model2 = new DefaultTableModel();

    public String[] postid;
    public String[] commentid;
    public String userid;

    JLabel jLtime=new JLabel();
    JLabel Background = new JLabel(new ImageIcon("src/file/ForumBackground.jpg"));
    JLabel tip = new JLabel("提示：双击删除！");
    JLabel post = new JLabel("我发布过的帖子");
    JLabel comment =new JLabel("我发布过的评论");
    JTable postlist = new JTable();
    JTable commentlist = new JTable();
    public Delete(UserBean userBean,BufferedReader in, PrintStream out){
        this.in = in;
        this.out = out;
        this.userBean = userBean;
        this.userid = userBean.getUserNum();
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("删除");
        init();
        this.add(tip);
        this.add(jLtime);
        this.add(post);
        this.add(comment);
        this.add(Background);
        showtimeTimer =new java.util.Timer();
        java.util.TimerTask task_showtime=new ShowTimeTask(jLtime);
        showtimeTimer.schedule(task_showtime, 0,1000);
    }

    public void init(){
        try {
            out.println("Delete");
            out.flush();
            out.println(userid);
            out.flush();
            String judge = in.readLine();
            if (judge.equals("DeleteOver")) {
                String Count1 = in.readLine();
                int count1 = Integer.parseInt(Count1);
                postid = new String[count1];
                for(int i=0;i<count1;i++)
                {
                    for(int j=0;j<2;j++)
                    {
                        row1.add(in.readLine());
                    }
                    data1.add(row1.clone());
                    row1.clear();
                    postid[i] = in.readLine();
                }

                String Count2 = in.readLine();
                int count2 = Integer.parseInt(Count2);
                commentid = new String[count2];
                for(int i=0;i<count2;i++)
                {
                    for(int j=0;j<3;j++)
                    {
                        row2.add(in.readLine());
                    }
                    data2.add(row2.clone());
                    row2.clear();
                    commentid[i] = in.readLine();
                }

                column1.add("帖子标题");
                column1.add("发帖时间");
                model1 = new DefaultTableModel(data1,column1);
                postlist.setModel(model1);
                postlist.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int r = postlist.getSelectedRow();
                        CheckDelete checkDelete = new CheckDelete(postid[r],in,out,true,Delete.this,r);
                        checkDelete.setVisible(true);
                    }
                });
                JScrollPane pane1 = new JScrollPane(postlist);
                pane1.setBounds(100,75,250,450);
                pane1.setFont(new Font("宋体", Font.PLAIN, 11));
                this.add(pane1);

                column2.add("帖子标题");
                column2.add("评论内容");
                column2.add("评论时间");
                model2 = new DefaultTableModel(data2,column2);
                commentlist.setModel(model2);
                commentlist.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int r = commentlist.getSelectedRow();
                        CheckDelete checkDelete = new CheckDelete(commentid[r],in,out,false,Delete.this,r);
                        checkDelete.setVisible(true);
                    }
                });
                JScrollPane pane2 = new JScrollPane(commentlist);
                pane2.setBounds(400,75,250,450);
                pane2.setFont(new Font("宋体", Font.PLAIN, 11));
                this.add(pane2);
            }
            if (judge.equals("DeleteFail")) {
                System.out.println("删除失败！");
                Delete.this.setVisible(false);
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        tip.setBounds(100,25,150,25);
        tip.setFont(new Font("宋体", Font.PLAIN, 11));

        post.setBounds(100,50,100,25);
        post.setFont(new Font("宋体", Font.PLAIN, 11));

        comment.setBounds(400,50,100,25);
        comment.setFont(new Font("宋体", Font.PLAIN, 11));

        jLtime.setBounds(500,550,200,25);
        jLtime.setFont(new Font("宋体", Font.PLAIN, 11));

        Background.setBounds(0,0,800,700);
    }
}
