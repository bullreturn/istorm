package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

import common.FriendLabel;
import common.UserBean;

/*管理登陆用户模块，将目前登陆的用户情况显示到ServerFrame中*/
public class LoginUser extends TimerTask {
    private DefaultListModel listModel=null;
    private JList userList=null;
    private JLabel jCount=null;
    private Hashtable userTable=new Hashtable();//存放每一个用户的基本信息
    private int count=0;//上线人数
    private Connection con=null;//数据库连接
    public LoginUser(DefaultListModel listModel,JList userList,JLabel jCount,Hashtable userTable,Connection con) {
        // TODO Auto-generated constructor stub
        this.listModel=listModel;
        this.userList=userList;
        this.jCount=jCount;
        this.userTable=userTable;
        this.con=con;
    }
//定时器的方法
    @Override
    public void run() {
        // TODO Auto-generated method stub
        count=0;
        userTable.clear();
        listModel.clear();
        getUser();//查询数据库，已获得上线用户
        getUserInfo();//创建列表的方法
        userList.setCellRenderer(new FriendLabel());//调用FriendLabel显示头像
        jCount.setText(new Integer(count).toString());
    }
    public void getUser() {
        String sql="select * from UserInformation where Status = 1";
        String userNum=null;
        try {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()) {
                ++count;//人数加1
                //创建存储用户信息的类
                UserBean user=new UserBean();
                userNum=rs.getString("UserNum");
                //将用户信息存储到该类中
                user.setUserNum(userNum);
                user.setUserName(rs.getString("UserName"));
                user.setPassword(rs.getString("Password"));
                user.setSex(rs.getString("Sex"));
                user.setBirth(rs.getString("Birth"));
                user.setAddress(rs.getString("Address"));
                user.setSign(rs.getString("Sign"));
                user.setPortrait(rs.getString("Portrait"));
                user.setStatus(rs.getInt("Status"));
                user.setPort(rs.getInt("Port"));
                user.setIp(rs.getString("IP"));
                userTable.put(userNum, user);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //获得用户信息，以创建列表
    public void getUserInfo() {
        //实现Enumeration 接口的对象，它生成一系列元素，一次生成一个。连续调用 nextElement 方法将返回一系列的连续元素。
        Enumeration it=userTable.elements();
        String userName="";
        String userNum="";
        String portrait="";
        String userInfo = "";
        int status = 0;
        while (it.hasMoreElements()) {
            UserBean user = (UserBean) it.nextElement();
            userName = user.getUserName().trim();
            userNum = user.getUserNum().trim();
            portrait = user.getPortrait();
            status = user.getStatus();
            userInfo = status + userName + "<" + userNum + ">" + "*" + portrait+"^";
            listModel.addElement(userInfo);
        }
    }

}
