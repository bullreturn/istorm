package UI.Forum;

import UI.ShowTimeTask;
import user.UserBean;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Post extends JFrame{
    public String userid;
    public String username;
    public String titletext;
    public String contenttext;
    public SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-mm-dd HH-mm-ss");
    Date date;

    JLabel Title = new JLabel("标题：");
    JLabel TitleTip = new JLabel("（长度不超过20个字符）");
    JTextArea title = new JTextArea();
    JLabel Content = new JLabel("内容：");
    JLabel ContentTip = new JLabel("（长度不超过10000个字符）");
    JTextArea content = new JTextArea();
    JButton Post = new JButton("发布");
    JLabel jLtime=new JLabel();
    JLabel Background = new JLabel(new ImageIcon("src/file/ForumBackground.jpg"));
    java.util.Timer showtimeTimer;
    private Hashtable PostTable= new Hashtable();//将Post的对象统一存储到HashTable中
    public UserBean userBean = null;
    public Socket socket;
    private BufferedReader in=null;//输入流
    private PrintStream out=null;//输出流
    private Forum forum;

    public Post(UserBean userBean,BufferedReader in, PrintStream out,Forum forum){
        this.in = in;
        this.out = out;
        this.userBean = userBean;
        this.forum = forum;
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("发帖");
        init();
        this.add(Title);
        this.add(title);
        this.add(Content);
        this.add(content);
        this.add(Post);
        this.add(jLtime);
        this.add(TitleTip);
        this.add(ContentTip);
        this.add(Background);
        showtimeTimer =new java.util.Timer();
        java.util.TimerTask task_showtime=new ShowTimeTask(jLtime);
        showtimeTimer.schedule(task_showtime, 0,1000);
    }

    public void init(){
        Title.setBounds(100, 100, 50, 25);
        Title.setFont(new Font("宋体", Font.PLAIN, 11));

        title.setBounds(150, 100, 400, 25);
        title.setFont(new Font("宋体", Font.PLAIN, 11));

        TitleTip.setBounds(550, 100, 150, 25);
        TitleTip.setFont(new Font("宋体", Font.PLAIN, 11));

        Content.setBounds(100, 150, 50, 25);
        Content.setFont(new Font("宋体", Font.PLAIN, 11));

        content.setBounds(150, 150, 400, 150);
        content.setFont(new Font("宋体", Font.PLAIN, 11));

        ContentTip.setBounds(550, 150, 100, 25);
        ContentTip.setFont(new Font("宋体", Font.PLAIN, 11));

        jLtime.setBounds(600,550,150,25);
        jLtime.setFont(new Font("宋体", Font.PLAIN, 11));

        Post.setBounds(300, 400, 100, 50);
        Post.setFont(new Font("宋体", Font.PLAIN, 11));

        Post.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                post();
            }
        });

        Background.setBounds(0,0,800,700);
    }

    public void post() {
        if(title.getText().trim().length()>20) {
            String t = new String();
            t = "标题过长，不超过20个字符";
            Error error = new Error(t);
            error.setVisible(true);
        }
        else if(content.getText().trim().length()>10000) {
            String t = new String();
            t = "内容过长，不超过10000个字符";
            Error error = new Error(t);
            error.setVisible(true);
        }
        else {
            userid = userBean.getUserNum();
            username = userBean.getUserName();
            titletext = title.getText().trim();
            contenttext = content.getText().trim();
            date = new Date(System.currentTimeMillis());
            try {
                out.println("Post");
                out.flush();
                out.println(userid);
                out.flush();
                out.println(username);
                out.flush();
                out.println(titletext);
                out.flush();
                out.println(contenttext);
                out.flush();
                out.println(date);
                out.flush();
                String judge=in.readLine();
                if(judge.equals("PostOver")) {
                    forum.row2.clear();
                    forum.row2.add(titletext);
                    forum.row2.add(date);
                    forum.data2.add(forum.row2.clone());
                    forum.row2.clear();
                    forum.model2 = new DefaultTableModel(forum.data2,forum.column2);
                    forum.My.setModel(forum.model2);
                    String t = new String();
                    t = "发表成功";
                    Success success = new Success(t);
                    success.setVisible(true);
                    this.setVisible(false);
                }
                if(judge.equals("PostFail")) {
                    String t = new String();
                    t = "发表失败";
                    Error error = new Error(t);
                    error.setVisible(true);
                    this.setVisible(false);
                }
            } catch (IOException e1) {
                    // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}
