package client.Forum;

import common.UserBean;

import javax.swing.*;
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

public class Search extends JFrame{
    private BufferedReader in=null;//输入流
    private PrintStream out=null;//输出流
    public String[] attribute = {"帖子","作者","发布时间"};
    public String[][] result;
    public String[] postid;
    public String[] postuserid;
    JLabel Background = new JLabel(new ImageIcon("src/file/ForumBackground.jpg"));
    JTable table;
    JScrollPane jScrollPane;
    String text;
    UserBean userBean;
    public Search(String text, UserBean userBean, BufferedReader in, PrintStream out){
        this.in = in;
        this.out=out;
        this.text = text;
        this.userBean = userBean;
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("搜索结果");
        if(text.isEmpty()){
            String t = new String("搜索内容不能为空！");
            Error error = new Error(t);
            error.setVisible(true);
            Search.this.setVisible(false);
        } else {
            try {
                out.println("Search");
                out.flush();
                out.println(this.text);
                out.flush();
                String judge = in.readLine();
                if (judge.equals("SearchOver")) {
                    String Num = in.readLine();
                    int num = Integer.parseInt(Num);
                    if(num>0) {
                        result = new String[num][3];
                        postid = new String[num];
                        postuserid = new String[num];
                        for(int i=0;i<num;i++)
                        {
                            for(int j=0;j<3;j++)
                            {
                                result[i][j] = in.readLine();
                            }
                            postid[i] = in.readLine();
                            postuserid[i] = in.readLine();
                        }

                        table = new JTable(result,attribute);
                        table.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                int r = table.getSelectedRow();
                                LookupPost lookupPost = new LookupPost(postid[r],userBean,in,out,postuserid[r]);
                                lookupPost.setVisible(true);
                            }
                        });
                        jScrollPane = new JScrollPane(table);
                        jScrollPane.setBounds(100,100,510,500);
                        jScrollPane.setFont(new Font("宋体", Font.PLAIN, 11));
                        this.add(jScrollPane);
                        this.setVisible(true);
                    } else {
                        Search.this.setVisible(false);
                        String t = new String("没有类似的帖子");
                        Error error = new Error(t);
                        error.setVisible(true);
                    }
                }
                if (judge.equals("SearchFail")) {
                    System.out.println("搜索失败！");
                    Search.this.setVisible(false);
                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}
