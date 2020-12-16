package UI.Forum;

import UI.ShowTimeTask;
import user.UserBean;
import server.ConnectionDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Vector;

public class Forum extends JFrame {
    JLabel Background = new JLabel(new ImageIcon("src/file/ForumBackground.jpg"));
    JButton PostSign = new JButton("发帖");
    JButton DeleteSign = new JButton("删帖/评论");
    JButton SearchSign = new JButton("搜索");
    JTextField SearchText = new JTextField();
    JButton Jquit = new JButton("退出");
    JLabel PopularPost = new JLabel("热点");
    JLabel MyPosts = new JLabel("我的发布");
    JLabel jLtime = new JLabel();
    JTable HotTopic = new JTable();
    JScrollPane HotTopicList;
    JTable My = new JTable();
    JScrollPane MyList;
    private Connection con = null; //数据库连接
    //ForumServiceThread forumServiceThread = null;
    java.util.Timer showtimeTimer;
    private Hashtable ForumTable = new Hashtable();//将Forum的对象统一存储到HashTable中
    private BufferedReader in = null;//输入流
    private PrintStream out = null;//输出流
    UserBean userBean = null;

    private String[] postid1;
    private String[] postid2;
    private String[] postuserid;
    public Vector data1 = new Vector();
    public Vector column1 = new Vector();
    public  Vector row1 = new Vector();
    public Vector data2 = new Vector();
    public Vector column2 = new Vector();
    public Vector row2 = new Vector();
    public DefaultTableModel model1 = new DefaultTableModel();
    public DefaultTableModel model2 = new DefaultTableModel();

    public Forum(UserBean myInfo, BufferedReader in, PrintStream out) {
        this.in = in;
        this.out = out;
        this.userBean = myInfo;
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("ISTORM论坛");
        con = ConnectionDao.getConnection(); //创建数据库连接
        getInfo();
        init();
        this.add(PostSign);
        this.add(DeleteSign);
        this.add(SearchSign);
        this.add(SearchText);
        this.add(Jquit);
        this.add(PopularPost);
        this.add(MyPosts);
        this.add(jLtime);
        this.add(Background);
        //ForumServiceThread forumServiceThread = new ForumServiceThread(jTForumLog,con);
        //forumServiceThread.start();
        showtimeTimer = new java.util.Timer();
        java.util.TimerTask task_showtime = new ShowTimeTask(jLtime);
        showtimeTimer.schedule(task_showtime, 0, 1000);
    }

    public void init() {
        PostSign.setBounds(20, 10, 100, 25);
        PostSign.setFont(new Font("宋体", Font.PLAIN, 11));
        //查看信息按钮监听
        PostSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Post post = new Post(userBean, in, out,Forum.this);
                post.setVisible(true);
            }
        });

        DeleteSign.setBounds(180, 10, 100, 25);
        DeleteSign.setFont(new Font("宋体", Font.PLAIN, 11));
        //查看信息按钮监听
        DeleteSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Delete delete = new Delete(userBean, in, out);
                delete.setVisible(true);
            }
        });

        SearchSign.setBounds(340, 10, 100, 25);
        SearchSign.setFont(new Font("宋体", Font.PLAIN, 11));
        //查看信息按钮监听
        SearchSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search search = new Search(SearchText.getText(), userBean, in, out);
            }
        });

        Jquit.setBounds(650, 10, 100, 25);
        Jquit.setFont(new Font("宋体", Font.PLAIN, 11));
        //查看信息按钮监听
        Jquit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Forum.this.setVisible(false);
            }
        });

        SearchText.setBounds(440, 10, 150, 25);
        SearchText.setFont(new Font("宋体", Font.PLAIN, 11));

        PopularPost.setBounds(180, 45, 150, 25);
        PopularPost.setFont(new Font("宋体", Font.PLAIN, 11));

        MyPosts.setBounds(450, 45, 150, 25);
        MyPosts.setFont(new Font("宋体", Font.PLAIN, 11));

        jLtime.setBounds(600, 600, 200, 50);
        jLtime.setFont(new Font("宋体", Font.PLAIN, 11));

        Background.setBounds(0, 0, 800, 700);
    }

    public void getInfo() {
        try{
            out.println("ForumInit");
            out.flush();
            out.println(userBean.getUserNum());
            out.flush();
            String judge = in.readLine();
            if(judge.equals("ForumInitOver")) {
                int count1,count2;
                String Count1 = in.readLine();
                count1 = Integer.parseInt(Count1);
                if(count1>0) {
                    postid1 = new String[count1];
                    postuserid = new String[count1];
                    for(int i=0;i<count1;i++)
                    {
                        for(int j=0;j<3;j++)
                        {
                            row1.add(in.readLine());
                        }
                        data1.add(row1.clone());
                        row1.clear();
                        postid1[i] = in.readLine();
                        postuserid[i] = in.readLine();
                    }
                    column1.add("帖子");
                    column1.add("作者");
                    column1.add("发布时间");
                    model1 = new DefaultTableModel(data1,column1);
                    HotTopic.setModel(model1);
                    HotTopic.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int r = HotTopic.getSelectedRow();
                            LookupPost lookupPost = new LookupPost(postid1[r],userBean,in,out,postuserid[r]);
                            lookupPost.setVisible(true);
                        }
                    });
                    HotTopicList = new JScrollPane(HotTopic);
                    HotTopicList.setBounds(100, 80, 300, 500);
                    HotTopicList.setFont(new Font("宋体", Font.PLAIN, 11));
                    this.add(HotTopicList);
                }

                String Count2 = in.readLine();
                count2 = Integer.parseInt(Count2);
                if (count2>0) {
                    postid2 = new String[count2];
                    for(int i=0;i<count2;i++)
                    {
                        for(int j=0;j<2;j++)
                        {
                            row2.add(in.readLine());
                        }
                        data2.add(row2.clone());
                        row2.clear();
                        postid2[i] = in.readLine();
                    }
                    column2.add("帖子");
                    column2.add("发布时间");
                    model2 = new DefaultTableModel(data2,column2);
                    My.setModel(model2);
                    My.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int r = My.getSelectedRow();
                            LookupPost lookupPost = new LookupPost(postid2[r],userBean,in,out,userBean.getUserNum());
                            lookupPost.setVisible(true);
                        }
                    });
                    MyList = new JScrollPane(My);
                    MyList.setBounds(450, 80, 250, 500);
                    MyList.setFont(new Font("宋体", Font.PLAIN, 11));
                    this.add(MyList);
                }
                else{
                    column2.add("帖子");
                    column2.add("发布时间");
                    model2 = new DefaultTableModel(data2,column2);
                    My.setModel(model2);
                    MyList = new JScrollPane(My);
                    MyList.setBounds(450, 80, 250, 500);
                    MyList.setFont(new Font("宋体", Font.PLAIN, 11));
                    this.add(MyList);
                }
            } else {
                String t = "论坛初始化失败！";
                Error error = new Error(t);
                error.setVisible(true);
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}

