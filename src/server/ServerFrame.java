package server;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import UI.ShowTimeTask;
import user.UserBean;
import user.UserInfo;

/*服务器主界面*/
public class ServerFrame extends JFrame {
    JLabel jshowList=new JLabel("在线用户列表<10秒刷新一次>");
    JLabel jshowServerLog=new JLabel("服务器日志");
    JLabel jUserCount=new JLabel("在线人数 :");
    JLabel jCount=new JLabel("0");
    JLabel jLtime=new JLabel();
    JButton jBgetInfo=new JButton("查看信息");
    JButton jBkickOut=new JButton("踢出");
    JButton jBexit=new JButton("退出");
    DefaultListModel listModel=new DefaultListModel();
    JList userList=new JList(listModel);
    JScrollPane jSuserList=new JScrollPane(userList);
    JTextArea jTServerLog=new JTextArea();
    JScrollPane jServerLog=new JScrollPane(jTServerLog);
    private Connection con=null;
    ServerWorkingThread serverWorkingThread =null;
    java.util.Timer showtimeTimer;
    private Hashtable userTable= new Hashtable();//将UserBean的对象统一存储到HashTable中
    public ServerFrame() {
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("服务器端控制界面");
        //创建数据库连接
        con=ConnectionDao.getConnection();
        init();
        this.add(jshowList);
        this.add(jSuserList);
        this.add(jBgetInfo);
        this.add(jBkickOut);
        this.add(jUserCount);
        this.add(jCount);
        this.add(jshowServerLog);
        this.add(jServerLog);
        this.add(jBexit);
        this.add(jLtime);
        serverWorkingThread =new ServerWorkingThread(jTServerLog,con);//启动ServerSocket套接字，并做好等待客户端连接的准备
        serverWorkingThread.start();
        //使jLtime动态显示时间
        showtimeTimer =new java.util.Timer();
        java.util.TimerTask task_showtime=new ShowTimeTask(jLtime);
        showtimeTimer.schedule(task_showtime, 0,1000);
        /*java.util.Timer time=new java.util.Timer();
        java.util.TimerTask task_time=new LoginUser(listModel,userList,jCount,userTable,con);
        time.schedule(task_time, 0,10000);//每10秒刷新一次
        try {
        	System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }*/
    }
    //布置服务器主界面
    public void init() {
        jshowList.setBounds(20, 10, 150, 25);
        jshowList.setFont(new Font("宋体",Font.PLAIN,11));
        jSuserList.setBounds(10, 40, 190, 500);
        jBgetInfo.setBounds(20, 550, 75, 25);
        jBgetInfo.setFont(new Font("宋体",Font.PLAIN,10));
        //查看信息按钮监听
        jBgetInfo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String selectedUser=null;
                String userNum=null;
                selectedUser=(String)userList.getSelectedValue();
                if(selectedUser==null) {
                    JOptionPane.showMessageDialog(jBgetInfo, "请单击选择一个用户");
                } else {
                    System.out.println(selectedUser);
                    userNum=selectedUser.substring(selectedUser.indexOf("<")+1,selectedUser.indexOf(">"));
                    UserBean user=(UserBean)userTable.get(userNum);
                    UserInfo userInfo=new UserInfo(ServerFrame.this,"信息查看",true,user);
                    userInfo.setVisible(true);
                }
            }

        });
        jBkickOut.setBounds(110, 550, 60, 25);
        jBkickOut.setFont(new Font("宋体",Font.PLAIN,10));
        //踢出按钮监听
        jBkickOut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int index=userList.getSelectedIndex();
                String userNum=null;
                if(index==-1) {
                    JOptionPane.showMessageDialog(jBkickOut, "请单击选择一个用户");
                } else {
                    String userInfo=(String)listModel.getElementAt(index);
                    userNum=userInfo.substring(userInfo.indexOf("<")+1,userInfo.indexOf(">"));
                    System.out.println(userNum);
                    removeUser(userNum);
                    listModel.remove(index);
                    int num=Integer.parseInt(jCount.getText())-1;
                    jCount.setText(new Integer(num).toString());
                }
            }

        });
        jUserCount.setBounds(25, 595, 100, 30);
        jUserCount.setFont(new Font("宋体",Font.PLAIN,12));
        jCount.setBounds(125, 595, 20, 30);
        jCount.setFont(new Font("宋体",Font.PLAIN,12));
        jshowServerLog.setBounds(220, 10, 150, 25);
        jshowServerLog.setFont(new Font("宋体",Font.PLAIN,11));
        jServerLog.setBounds(210, 40, 575, 550);
        jBexit.setBounds(470, 595, 60, 25);
        jBexit.setFont(new Font("宋体",Font.PLAIN,11));
        //退出按钮监听
        jBexit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int option=JOptionPane.showConfirmDialog(jBexit, "亲，你确定要退出吗？");
                if(option==JOptionPane.YES_OPTION) {
                    dispose();	// close window
                }
            }

        });
        jLtime.setBounds(600, 615, 250, 50);
        jLtime.setFont(new Font("宋体",Font.PLAIN,12));

    }

    public void deinit() {
        showtimeTimer.cancel();
        serverWorkingThread._shouldExit=true;	// flag at first
        serverWorkingThread.stopServerSocket();	// this will cause thread exit (UGLY)
        //serverWorkingThread.interrupt();	// this one should, but doesn't work. Use the above one instead
        try {
            // TODO: UI展示正在停止服务 可以先关闭当前窗口
            serverWorkingThread.join();	// Wait thread stopping
            con.close();//关闭数据库连接
            System.out.printf("数据库连接关闭\n");

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    // ONLY deinit() HERE!
    protected void processWindowEvent(WindowEvent e) {
        if(e.getID()==WindowEvent.WINDOW_CLOSED) {
            deinit();
        } else if(e.getID()==WindowEvent.WINDOW_CLOSING) {
            dispose();
        }
    }
    //踢出用户，在数据库中把Status及用户状态改为0
    public void removeUser(String userNum) {
        String sql="UPDATE UserInformation SET Status = 0 where UserNum= '"+userNum+"'";
        try {
            Statement stmt=con.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ServerFrame f=new ServerFrame();
        f.setVisible(true);
    }
}
