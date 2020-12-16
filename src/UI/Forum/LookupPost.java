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
import java.util.Vector;

public class LookupPost extends JFrame{
    JLabel Background = new JLabel(new ImageIcon("src/file/ForumBackground.jpg"));
    JLabel timer = new JLabel();

    java.util.Timer showtimeTimer;
    public int maxpage=-1;
    public int currentpage=0;

    public Vector date = new Vector();

    public String checkid;
    private BufferedReader in=null;//输入流
    private PrintStream out=null;//输出流
    public UserBean userBean=null;
    JTextArea jTextArea1 = new JTextArea();
    JTextArea jTextArea2 = new JTextArea();
    JLabel page = new JLabel();
    private Boolean ischange = false;

    public LookupPost(String postid,UserBean userBean,BufferedReader in, PrintStream out,String checkid){
        this.checkid = checkid;
        this.in = in;
        this.out = out;
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("帖子");
        this.userBean = userBean;
        Background.setBounds(0,0,800,700);
        showtimeTimer =new java.util.Timer();
        java.util.TimerTask task_showtime=new ShowTimeTask(timer);
        showtimeTimer.schedule(task_showtime, 0,1000);
        timer.setBounds(500,600,200,25);
        timer.setFont(new Font("宋体", Font.PLAIN, 11));
        this.add(timer);
        try {
            out.println("LookupPost");
            out.flush();
            out.println(postid);
            out.flush();
            String judge=in.readLine();
            if(judge.equals("LookupPostOver")) {
                String name = in.readLine();
                String title = in.readLine();
                String content = in.readLine();
                String posttime = in.readLine();

                JLabel UserName = new JLabel("作者：");
                JLabel username = new JLabel(name);
                UserName.setBounds(50,25,50,25);
                UserName.setFont(new Font("宋体", Font.PLAIN, 11));
                username.setBounds(100,25,150,25);
                username.setFont(new Font("宋体", Font.PLAIN, 11));
                this.add(UserName);
                this.add(username);

                JLabel Posttitle = new JLabel("标题：");
                JLabel posttitle = new JLabel(title);
                Posttitle.setBounds(50,50,50,25);
                Posttitle.setFont(new Font("宋体", Font.PLAIN, 11));
                posttitle.setBounds(100,50,500,25);
                posttitle.setFont(new Font("宋体", Font.PLAIN, 11));
                this.add(Posttitle);
                this.add(posttitle);

                JLabel PostTime = new JLabel("时间：");
                JLabel postTime = new JLabel(posttime);
                PostTime.setBounds(50,75,50,25);
                PostTime.setFont(new Font("宋体", Font.PLAIN, 11));
                postTime.setBounds(100,75,300,25);
                postTime.setFont(new Font("宋体", Font.PLAIN, 11));
                this.add(PostTime);
                this.add(postTime);

                JLabel PostContent = new JLabel("内容：");
                jTextArea1 = new JTextArea(content);
                jTextArea1.setLineWrap(true);
                jTextArea1.setEditable(false);
                JScrollPane p1 = new JScrollPane(jTextArea1);
                PostContent.setBounds(50,100,50,25);
                PostContent.setFont(new Font("宋体", Font.PLAIN, 11));
                p1.setBounds(100,100,500,200);
                p1.setFont(new Font("宋体", Font.PLAIN, 11));
                this.add(PostContent);
                this.add(p1);

                JButton Comment = new JButton("评论");
                Comment.setBounds(650,225,50,25);
                Comment.setFont(new Font("宋体", Font.PLAIN, 11));
                Comment.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Comment c = new Comment(postid,userBean,in,out,LookupPost.this);
                        c.setVisible(true);
                    }
                });
                this.add(Comment);

                JButton change = new JButton("修改");
                change.setBounds(650,125,50,25);
                change.setFont(new Font("宋体", Font.PLAIN, 11));
                this.add(change);
                change.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String tmp = userBean.getUserNum();
                        if(tmp.equals(checkid)) {
                            ischange=!ischange;
                            jTextArea1.setEditable(ischange);
                        } else {
                            String t = new String();
                            t = "没有权限！";
                            Error error = new Error(t);
                            error.setVisible(true);
                        }
                    }
                });

                JButton save = new JButton("保存");
                save.setBounds(650,175,50,25);
                save.setFont(new Font("宋体", Font.PLAIN, 11));
                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(ischange) {
                            String tmp = jTextArea1.getText().trim();
                            try {
                                out.println("SaveChange");
                                out.flush();
                                out.println(postid);
                                out.flush();
                                out.println(tmp);
                                out.flush();
                                String ok = in.readLine();
                                if(ok.equals("SaveChangeOver")) {
                                    ischange = false;
                                    jTextArea1.setEditable(ischange);
                                    String t = new String();
                                    t = "修改成功！";
                                    Success success = new Success(t);
                                    success.setVisible(true);
                                } else if(ok.equals("SaveChangeFail")) {
                                    String t = new String();
                                    t = "修改失败！";
                                    Error error = new Error(t);
                                    error.setVisible(true);
                                }
                            } catch (IOException e2) {
                                // TODO Auto-generated catch block
                                e2.printStackTrace();
                            }
                        }
                    }
                });
                this.add(save);

                JLabel CommentList = new JLabel("评论列表");
                CommentList.setBounds(50,250,50,25);
                CommentList.setFont(new Font("宋体", Font.PLAIN, 11));
                this.add(CommentList);

                String Count = in.readLine();
                int count = Integer.parseInt(Count);
                this.maxpage = count-1;
                if(count!=0) {
                    for(int i=0;i<count;i++){
                        for(int j=0;j<3;j++)
                        {
                            date.add(in.readLine());
                        }
                    }

                    String j = "评论者："+date.elementAt(currentpage*3)+'\n'+"内容："+date.elementAt(currentpage*3+1)+'\n'+"评论时间："+date.elementAt(currentpage*3+2);
                    jTextArea2.setText(j);
                    jTextArea2.setWrapStyleWord(true);
                    jTextArea2.setEditable(false);
                    JScrollPane p = new JScrollPane(jTextArea2);
                    p.setBounds(100,310,500,200);
                    p.setFont(new Font("宋体", Font.PLAIN, 11));
                    this.add(p);
                } else {
                    String j = " ";
                    jTextArea2.setText(j);
                    jTextArea2.setWrapStyleWord(true);
                    jTextArea2.setEditable(false);
                    JScrollPane p = new JScrollPane(jTextArea2);
                    p.setBounds(100,310,500,200);
                    p.setFont(new Font("宋体", Font.PLAIN, 11));
                    this.add(p);
                    JLabel nocomment = new JLabel("空空如也");
                    nocomment.setBounds(250,500,100,50);
                    nocomment.setFont(new Font("宋体", Font.PLAIN, 11));
                    this.add(nocomment);
                }
                JButton PageUP = new JButton("上一页");
                JButton PageDOWN = new JButton("下一页");
                PageUP.setBounds(200,550,100,25);
                PageUP.setFont(new Font("宋体", Font.PLAIN, 11));
                this.add(PageUP);
                PageDOWN.setBounds(300,550,100,25);
                PageDOWN.setFont(new Font("宋体", Font.PLAIN, 11));
                this.add(PageDOWN);
                PageUP.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(currentpage>0){
                            currentpage--;
                            String tmppage = "当前页数：" + (currentpage+1);
                            page.setText(tmppage);
                            page.revalidate();
                            String tmp = "评论者："+date.elementAt(currentpage*3)+'\n'+"内容："+date.elementAt(currentpage*3+1)+'\n'+"评论时间："+date.elementAt(currentpage*3+2);
                            jTextArea2.setText(tmp);
                            jTextArea2.revalidate();
                        }
                        else{
                            String t = new String();
                            t = "到头了！";
                            Error error = new Error(t);
                            error.setVisible(true);
                        }
                    }
                });
                PageDOWN.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(currentpage<maxpage){
                            currentpage++;
                            String tmppage = "当前页数：" + (currentpage+1);
                            page.setText(tmppage);
                            page.revalidate();
                            String tmp = "评论者："+date.elementAt(currentpage*3)+'\n'+"内容："+date.elementAt(currentpage*3+1)+'\n'+"评论时间："+date.elementAt(currentpage*3+2);
                            jTextArea2.setText(tmp);
                            jTextArea2.revalidate();
                        } else{
                            String t = new String();
                            t = "到底了！";
                            Error error = new Error(t);
                            error.setVisible(true);
                        }
                    }
                });
                String tmppage = "当前页数：" + (currentpage+1);
                page.setText(tmppage);
                page.setBounds(250,600,150,25);
                page.setFont(new Font("宋体", Font.PLAIN, 13));
                this.add(page);
            }
            if(judge.equals("LookupPostFail")) {
                String t = new String();
                t = "加载失败";
                Error error = new Error(t);
                error.setVisible(true);
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
