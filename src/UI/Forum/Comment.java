package UI.Forum;

import UI.ShowTimeTask;
import user.UserBean;

import javax.swing.*;
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

public class Comment extends JFrame{
    public String postid;
    public String userid;
    public String contenttext;
    public SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-mm-dd HH-mm-ss");
    Date date;

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
    LookupPost lookupPost;
    public Comment(String postid,UserBean userBean,BufferedReader in, PrintStream out,LookupPost lookupPost){
        this.in =in;
        this.out = out;
        this.userBean = userBean;
        this.postid = postid;
        this.lookupPost = lookupPost;
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("评论");
        init();
        this.add(ContentTip);
        this.add(Content);
        this.add(content);
        this.add(Post);
        this.add(jLtime);
        this.add(Background);
        showtimeTimer =new java.util.Timer();
        java.util.TimerTask task_showtime=new ShowTimeTask(jLtime);
        showtimeTimer.schedule(task_showtime, 0,1000);
    }

    public void init(){
        Content.setBounds(100, 150, 50, 25);
        Content.setFont(new Font("宋体", Font.PLAIN, 11));

        content.setBounds(150, 150, 400, 150);
        content.setFont(new Font("宋体", Font.PLAIN, 11));

        ContentTip.setBounds(150, 125, 200, 25);
        ContentTip.setFont(new Font("宋体", Font.PLAIN, 11));

        jLtime.setBounds(500,600,200,25);
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

    public void post(){
        if(content.getText().trim().length()>10000) {
            String t = new String();
            t = "内容过长，不超过10000个字符";
            Error error = new Error(t);
            error.setVisible(true);
        }
        else {
            String name = userBean.getUserName();
            userid = userBean.getUserNum();
            contenttext = content.getText().trim();
            date = new Date(System.currentTimeMillis());
            try {
                out.println("Comment");
                out.flush();
                out.println(this.postid);
                out.flush();
                out.println(userid);
                out.flush();
                out.println(contenttext);
                out.flush();
                out.println(date);
                out.flush();
                String judge = in.readLine();
                if (judge.equals("CommentOver")) {
                    lookupPost.date.add(name);
                    lookupPost.date.add(contenttext);
                    lookupPost.date.add(date);
                    lookupPost.maxpage = lookupPost.maxpage+1;
                    lookupPost.currentpage=lookupPost.maxpage;
                    String tmppage = "当前页数：" + (lookupPost.currentpage+1);
                    lookupPost.page.setText(tmppage);
                    lookupPost.page.revalidate();
                    String tmp = "评论者："+lookupPost.date.elementAt(lookupPost.currentpage*3)+'\n'+"内容："+lookupPost.date.elementAt(lookupPost.currentpage*3+1)+'\n'+"评论时间："+lookupPost.date.elementAt(lookupPost.currentpage*3+2);
                    lookupPost.jTextArea2.setText(tmp);
                    lookupPost.jTextArea2.revalidate();
                    String t = new String();
                    t = "评论成功";
                    Success success = new Success(t);
                    success.setVisible(true);
                    Comment.this.setVisible(false);
                }
                if (judge.equals("CommentFail")) {
                    String t = new String();
                    t = "评论失败";
                    Error error = new Error(t);
                    error.setVisible(true);
                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}
