package server;

import client.Forum.Forum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/*为客户端提供功能服务模块
 * 1.注册新用户
 * 2.用户登录
 * 3.查找好友
 * 4.添加好友
 * 5.删除好友
 * 6.修改自己信息
 * 7.用户下线
 * 8.修改头像
 * 9.文件传输*/
public class UserServiceThread extends Thread {
    private Socket socket;
    private BufferedReader in=null;//输入流
    private PrintStream out=null;//输出流
    private Connection con=null;//数据库连接

    private final SWT_Callback sys;

    // Manager thread check this flag and decide to delete it.
    public Boolean _shouldExit =false;//控制服务器线程的启动与停止
    /*-------------------传送文件----------------------------*/
    //ServerThread father=null;
    /*-----------------------------------------------------------*/
    //public Server(Socket socket,ServerThread father)
    public UserServiceThread(Socket socket, SWT_Callback sys, Connection dbc) {
        this.socket=socket;
        this.con=dbc;
        this.sys=sys;
        // TODO Auto-generated constructor stub
        //套接字
        try {
            socket.setSoTimeout(1000);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stopThreadSocket() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //本线程的主方法，根据客户端发送的命令，调用对应的方法执行
    public void run() {
        System.out.printf("UserServiceThread started: "+this.getId()+"\n");
        while(!_shouldExit) {
            try {
                String str=in.readLine();
                if(str==null) {	// client socket closed
                    break;
                }
                if(str.equals("end")) {
                    break;
                } else if(str.equals("registNewUser")) { //注册新用户
                    registNewUser();
                } else if(str.equals("login")) { //登陆
                    login();
                } else if(str.equals("queryUser")) { //查找用户信息
                    String userNum=in.readLine();
                    queryUser(userNum);
                } else if(str.equals("addFriend")) { //添加好友
                    addFriend();
                } else if(str.equals("deleteFriend")) { //删除好友
                    deleteFriend();
                } else if(str.equals("updateOwnInformation")) { //修改自己信息
                    updateOwnInformation();
                } else if(str.equals("logout")) { //用户下线
                    logout();
                } else if(str.equals("UpdateMyportrait")) { //修改头像
                    UpdateMyportrait();
                } else if(str.equals("searchgame")) {//搜寻游戏
                	int num= Integer.parseInt(in.readLine());
                	searchgame(num);
                } else if(str.equals("ForumInit")){
                    String usernum = in.readLine();
                    ForumInit(usernum);
                } else if(str.equals("Post")) { //发帖
                    PostPost();
                } else if(str.equals("Delete")) { //删帖
                    String tmp = in.readLine();
                    Delete(tmp);
                } else if(str.equals("Search")) { //搜索
                    String text = in.readLine();
                    SearchPost(text);
                } else if(str.equals("LookupPost")) {
                    LookupPost();
                } else if(str.equals("Comment")) {
                    Comment();
                } else if(str.equals("DeletePost")) {
                    String postid = in.readLine();
                    DeletePost(postid);
                } else if(str.equals("DeleteComment")) {
                    String commentid = in.readLine();
                    DeleteComment(commentid);
                } else if(str.equals("SaveChange")) {
                    String postid = in.readLine();
                    String content = in.readLine();
                    SaveChange(postid,content);
                } else if(str.equals("updatePass")) {
                    String usernum=in.readLine();
                    String old = in.readLine();
                    String new_ = in.readLine();
                    updatePass(usernum,old,new_);
                } else if(str.equals("updatePassWithMibao")) {
                    String usernum=in.readLine();
                    String mibao = in.readLine();
                    String mibaodaan = in.readLine();
                    String pass=in.readLine();
                    findPass(usernum,mibao,mibaodaan,pass);
                }
//				else if(str.equals("sendFile"))
//				{
//					sendFile();
//				}
                
            } catch (SocketTimeoutException e) {	// normal timeout, for stopping thread
                // do nothing
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        sys.unregisterSession(this);
        stopThreadSocket();
        System.out.printf("UserServiceThread exited: "+this.getId()+"\n");
    }
    public void registNewUser() {
        String sql1="INSERT INTO userinformation (usernum,username,password,sex,birth,address,sign,portrait,status,mibao,mibaodaan) values(?,?,?,?,?,?,?,?,?,?,?)";
        String sql2="select usernum from qqnum where mark = 1";
        Statement stmt1=null,stmt2=null;
        ResultSet rs=null;
        try {
            stmt1=con.createStatement();
            rs=stmt1.executeQuery(sql2);
            rs.next();
            String userNum=rs.getString("usernum");//从QQNum表获取一个合法QQ号

            PreparedStatement pstmt=con.prepareStatement(sql1);
            String userName=in.readLine();
            String password=in.readLine();
            String sex=in.readLine();
            String birth=in.readLine();
            String address=in.readLine();
            String mibao=in.readLine();
            String mibaodaan=in.readLine();
            pstmt.setString(1, userNum);
            pstmt.setString(2, userName);
            pstmt.setString(3, password);
            pstmt.setString(4, sex);
            pstmt.setString(5, birth);
            pstmt.setString(6, address);
            pstmt.setString(7, "个性签名");
            pstmt.setString(8, "src/head/head.png");//当用户注册的时候，默认给用户指定一个头像
            pstmt.setInt(9, 0);//默认用户的状态为不在线
            pstmt.setString(10, mibao);
            pstmt.setString(11, mibaodaan);
            pstmt.executeUpdate();//向UerInformation中插入信息结束
            pstmt.close();
            //修改QQNum中的Mark值，改为0，表示该QQ已被用户注册
            System.out.println(userNum);
            String sql3="UPDATE qqnum SET mark = 0 where usernum = '"+userNum+"'";
            stmt2=con.createStatement();
            stmt2.executeUpdate(sql3);
            out.println("registerOver");//注册完毕
            out.flush();
            //告诉客户端，你注册所获取的QQ号
            out.println(userNum);
            out.flush();

            stmt2.close();
            stmt1.close();
            rs.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("registerFail");
            out.flush();
        }
    }
    public void login() {
        Statement stmt1=null;
        Statement stmt2=null;
        ResultSet rs=null;
        try {
            String userNum=in.readLine();
            String password=in.readLine();
            String ip=in.readLine();
            String port=in.readLine();
            String sql1="select * from userinformation where usernum= '"+userNum+"' and password = '"+password+"'";
            stmt1=con.createStatement();
            rs=stmt1.executeQuery(sql1);
            //System.out.println(userNum+password+ip+port);
            //如果登录成功
            if(rs.next()) {
                //String ip=String.valueOf(socket.getInetAddress().getLocalHost());
                //int port=socket.getLocalPort();
                String sql2="UPDATE userinformation SET status = 1,ip = '"+ip+"', port = "+port+" where usernum ='"+userNum+"'";
                System.out.println(sql2);
                System.out.println(port);
                stmt2=con.createStatement();
                stmt2.executeUpdate(sql2);
                out.println("sendUserInfo");
                out.flush();
                queryUser(userNum);
                out.println("loginSuccess");
                out.flush();
                //查找ta的好友
                queryFriend(userNum);
                stmt2.close();
            } else {
                System.out.println("FAIL");
                out.println("loginFail");
                out.flush();
                stmt1.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("loginFail");
            out.flush();
        }
    }
    private void queryFriend(String userNum) {
        // TODO Auto-generated method stub
        Statement stmt1=null;
        Statement stmt2=null;
        ResultSet rs1=null;
        ResultSet rs2=null;
        Vector friendNum=new Vector();//此向量用于存储好友的QQ号码
        try {
            String sql1="select friendnum from userfriend where usernum = "+userNum;
            stmt1=con.createStatement();
            rs1=stmt1.executeQuery(sql1);
            while(rs1.next()) {
                friendNum.addElement(rs1.getString("friendnum"));
            }
            rs1.close();
            stmt1.close();
            for(int i=0; i<friendNum.size(); i++) {
                String friend=(String) friendNum.elementAt(i);
                String sql2="select * from userinformation where usernum ='"+friend+"'";
                stmt2=con.createStatement();
                rs2=stmt2.executeQuery(sql2);
                rs2.next();
                out.println(friend);//QQ号码
                out.flush();
                out.println(rs2.getString("username"));
                out.flush();
                out.println(rs2.getString("sex"));
                out.flush();
                out.println(rs2.getString("birth"));
                out.flush();
                out.println(rs2.getString("address"));
                out.flush();
                out.println(rs2.getString("sign"));
                out.flush();
                out.println(rs2.getString("portrait"));
                out.flush();
                out.println(rs2.getString("status"));
                out.flush();
                out.println(rs2.getString("port"));
                out.flush();
                out.println(rs2.getString("ip"));
                out.flush();
                rs2.close();
                stmt2.close();
            }
            out.println("queryFriendOver");
            out.flush();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void queryUser(String userNum) {
        Statement stmt=null;
        ResultSet rs=null;
        try {
            String sql="select * from userinformation where usernum = '"+userNum+"'";
            stmt=con.createStatement();
            rs=stmt.executeQuery(sql);
            if(rs.next()) {
                out.println(userNum);//QQ号码
                out.flush();
                out.println(rs.getString("username"));
                out.flush();
                out.println(rs.getString("sex"));
                out.flush();
                out.println(rs.getString("birth"));
                out.flush();
                out.println(rs.getString("address"));
                out.flush();
                out.println(rs.getString("sign"));
                out.flush();
                out.println(rs.getString("portrait"));
                out.flush();
                out.println(rs.getInt("status"));
                out.flush();
                out.println(rs.getInt("port"));
                out.flush();
                out.println(rs.getString("ip"));
                out.flush();
            } else
                out.println("noUser");
            out.flush();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("queryUserFail");
            out.flush();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public void addFriend() {
        try {
            String sql="INSERT INTO userfriend (usernum,friendnum) values (?,?)";
            PreparedStatement pstmt=con.prepareStatement(sql);
            pstmt.setString(1, in.readLine());
            pstmt.setString(2, in.readLine());
            pstmt.executeUpdate();
            out.println("addFriendOver");
            out.flush();
            pstmt.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("addFriendFail");
            out.flush();
        }
    }
    public void deleteFriend() {
        try {
            String sql="DELETE FROM userfriend WHERE usernum = '"+in.readLine()+"' and friendnum = '"+in.readLine()+"'";
            PreparedStatement pstmt=con.prepareStatement(sql);
            pstmt.execute();
            out.println("deleteFriendOver");
            out.flush();
            pstmt.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("deleteFriendFail");
            out.flush();
        }
    }
    public void updateOwnInformation() {
        try {
            String sql="UPDATE userinformation SET username = ? , sex = ? , birth = ?, address = ? , sign = ? where usernum ='"+in.readLine()+"'";
            PreparedStatement pstmt=con.prepareStatement(sql);
            pstmt.setString(1, in.readLine());
            pstmt.setString(2, in.readLine());
            pstmt.setString(3, in.readLine());
            pstmt.setString(4, in.readLine());
            pstmt.setString(5, in.readLine());
            pstmt.executeUpdate();
            out.println("updateOver");
            out.flush();
            pstmt.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("updateFail");
            out.flush();
        }
    }
    public void logout() {
        try {
            String sql="UPDATE userinformation SET status = 0 , ip = null , port = 0 where usernum = '"+in.readLine()+"'";
            PreparedStatement pstmt=con.prepareStatement(sql);
            pstmt.executeUpdate();
            out.println("logOut");
            out.flush();
            pstmt.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("logFail");
            out.flush();
        }
    }
    public void UpdateMyportrait() {
        try {
            String num=in.readLine();
            String image=in.readLine();
            //System.out.println("修改头像了哈"+num+image);
            String sql="UPDATE userinformation SET portrait = '"+image+"' where usernum = '"+num+"'";
            PreparedStatement pstmt=con.prepareStatement(sql);
            pstmt.executeUpdate();
            out.println("updateMyportraitOver");
            out.flush();
            pstmt.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("updateMyportraitFail");
            out.flush();
        }
    }
    public void searchgame(int num) {
    	Statement stmt =null;
        ResultSet rs =null;
    	try {
    		String sql="select gamename,gamelink from games where gamenum = '"+num+"'";
            stmt=con.createStatement();
            rs=stmt.executeQuery(sql);
            if(rs.next()) {
            	out.println(rs.getString("gamename"));
                out.flush();
                out.println(rs.getString("gamelink"));
            }else
                out.println("noGame");
            out.flush();
    	}
    	catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("searchgameFail");
            out.flush();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public void PostPost(){
        Statement stmt=null;
        try {
            stmt=con.createStatement();
            String userid=in.readLine();
            String username=in.readLine();
            String title=in.readLine();
            String content=in.readLine();
            String time=in.readLine();
            String sql = "insert into post(usernum,username,title,content,posttime) values(?,?,?,?,?)";
            PreparedStatement pstmt=con.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.setString(2, username);
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.setString(5, time);
            pstmt.executeUpdate();
            pstmt.close();
            stmt.close();
            out.println("PostOver");
            out.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("PostFail");
            out.flush();
        }
    }

    public void Delete(String userid){
        Statement stmt1=null,stmt2=null,stmt3=null,stmt4=null;
        ResultSet rs1 =null,rs2=null,rs3=null,rs4=null;
        try {
            out.println("DeleteOver");
            out.flush();
            String sql1 = "select count(*) as num from post where usernum='"+userid+"'";
            String sql2 = "select title,posttime,postid from post where usernum='"+userid+"'";
            String sql3 = "select count(*) as num from comment where usernum='"+userid+"'";
            String sql4 = "select p.title,c.content,c.commenttime,c.commentid from post p,comment c where c.usernum='"+userid+"' and c.postid=p.postid";
            stmt1=con.createStatement();
            stmt2=con.createStatement();
            stmt3=con.createStatement();
            stmt4=con.createStatement();
            rs1 = stmt1.executeQuery(sql1);
            rs2 = stmt2.executeQuery(sql2);
            rs3 = stmt3.executeQuery(sql3);
            rs4 = stmt4.executeQuery(sql4);
            rs1.next();
            rs3.next();
            int count1 = rs1.getInt("num");
            int count2 = rs3.getInt("num");
            out.println(count1);
            out.flush();
            rs2.next();
            for(int i=0;i<count1;i++){
                out.println(rs2.getString("title"));
                out.flush();
                out.println(rs2.getString("posttime"));
                out.flush();
                out.println(rs2.getString("postid"));
                out.flush();
                rs2.next();
            }

            out.println(count2);
            out.flush();
            rs4.next();
            for(int i=0;i<count2;i++){
                out.println(rs4.getString("p.title"));
                out.flush();
                out.println(rs4.getString("c.content"));
                out.flush();
                out.println(rs4.getString("c.commenttime"));
                out.flush();
                out.println(rs4.getString("c.commentid"));
                out.flush();
                rs4.next();
            }
            stmt1.close();
            stmt2.close();
            stmt3.close();
            stmt4.close();
            rs1.close();
            rs2.close();
            rs3.close();
            rs4.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("DeleteFail");
            out.flush();
        }
    }

    public void DeletePost(String postid){
        try{
            String sql5 = "delete from post where postid="+postid;
            String sql6 = "delete from comment where postid="+postid;
            PreparedStatement pstmt1 = con.prepareStatement(sql5);
            PreparedStatement pstmt2 = con.prepareStatement(sql6);
            pstmt1.execute();
            pstmt2.execute();
            out.println("DeletePostOver");
            out.flush();
            pstmt1.close();
            pstmt2.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("DeleteFail");
            out.flush();
        }
    }

    public void DeleteComment(String commentid){
        try {
            String sql7 = "delete from comment where commentid = "+commentid;
            PreparedStatement pstmt=con.prepareStatement(sql7);
            pstmt.execute();
            out.println("DeleteCommentOver");
            out.flush();
            pstmt.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("DeleteFail");
            out.flush();
        }
    }

    public void SearchPost(String text){
        Statement stmt1=null,stmt2=null;
        ResultSet rs1=null,rs2=null;
        try {
            String sql1 ="select count(*) as num from post where (username like '%"+text+"%') or (title like '%"+text+"%')";
            String sql2 ="select title,username,posttime,postid,usernum from post where (username like '%"+text+"%') or (title like '%"+text+"%')";
            stmt1=con.createStatement();
            rs1=stmt1.executeQuery(sql1);
            rs1.next();
            String Num = rs1.getString("num");
            int num = Integer.parseInt(Num);
            rs1.close();
            stmt2=con.createStatement();
            rs2=stmt2.executeQuery(sql2);
            rs2.next();
            out.println("SearchOver");
            out.flush();
            out.println(Num);
            out.flush();
            for(int i=0;i<num;i++){
                out.println(rs2.getString("title"));
                out.flush();
                out.println(rs2.getString("username"));
                out.flush();
                out.println(rs2.getString("posttime"));
                out.flush();
                out.println(rs2.getString("postid"));
                out.flush();
                out.println(rs2.getString("usernum"));
                out.flush();
                rs2.next();
            }
            rs2.close();
            stmt2.close();
            stmt1.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("SearchFail");
            out.flush();
        }
    }

    public void LookupPost(){
        Statement stmt1=null,stmt2=null,stmt3=null;
        ResultSet rs1,rs2,rs3;
        try {
            String postid = in.readLine();
            String sql1 = "select username,title,content,posttime from post where postid='"+postid+"'";
            String sql2 = "select count(*) as num from comment where postid='"+postid+"'";
            String sql3 = "Select p.username,c.content,c.commenttime from post p,comment c where c.postid='"+postid+"' and p.usernum=c.usernum";
            stmt1 = con.createStatement();
            stmt2 = con.createStatement();
            stmt3 = con.createStatement();
            rs1 = stmt1.executeQuery(sql1);
            rs2 = stmt2.executeQuery(sql2);
            rs3 = stmt3.executeQuery(sql3);
            rs1.next();
            rs2.next();
            rs3.next();
            out.println("LookupPostOver");
            out.flush();
            out.println(rs1.getString("username"));
            out.flush();
            out.println(rs1.getString("title"));
            out.flush();
            out.println(rs1.getString("content"));
            out.flush();
            out.println(rs1.getString("posttime"));
            out.flush();
            int num = rs2.getInt("num");
            out.println(num);
            out.flush();

            for(int i=0;i<num;i++){
                out.println(rs3.getString("p.username"));
                out.flush();
                out.println(rs3.getString("c.content"));
                out.flush();
                out.println(rs3.getString("c.commenttime"));
                out.flush();
                rs3.next();
            }
            rs1.close();
            rs2.close();
            rs3.close();
            stmt1.close();
            stmt2.close();
            stmt3.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("SearchFail");
            out.flush();
        }
    }

    public void Comment(){
        Statement stmt=null;
        try {
            stmt=con.createStatement();
            String postid=in.readLine();
            String userid=in.readLine();
            String content=in.readLine();
            String time=in.readLine();
            String sql = "insert into comment(postid,usernum,content,commenttime) values(?,?,?,?)";
            String sql1 = "update post set comments = comments + 1 where postid="+postid;
            PreparedStatement pstmt1 = con.prepareStatement(sql1);
            pstmt1.execute();
            PreparedStatement pstmt=con.prepareStatement(sql);
            pstmt.setString(1, postid);
            pstmt.setString(2, userid);
            pstmt.setString(3, content);
            pstmt.setString(4, time);
            pstmt.executeUpdate();
            pstmt.close();
            stmt.close();
            out.println("CommentOver");
            out.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("CommentFail");
            out.flush();
        }
    }

    public void ForumInit(String userid) {
        Statement stmt1=null,stmt2=null,stmt3=null,stmt4=null;
        ResultSet rs1 =null,rs2=null,rs3=null,rs4=null;
        try{
            String sql1 = "select count(*) as num from post";
            String sql2 = "select title,username,posttime,postid,usernum from post order by comments desc limit 20";
            String sql3 = "select count(*) as num from post where usernum='"+userid+"'";
            String sql4 = "select title,posttime,postid from post where usernum='"+userid+"'";

            stmt1=con.createStatement();
            stmt2=con.createStatement();
            stmt3=con.createStatement();
            stmt4=con.createStatement();
            rs1 = stmt1.executeQuery(sql1);
            rs2 = stmt2.executeQuery(sql2);
            rs3 = stmt3.executeQuery(sql3);
            rs4 = stmt4.executeQuery(sql4);
            out.println("ForumInitOver");
            out.flush();
            rs1.next();
            rs3.next();
            int count1 = rs1.getInt("num");
            int count2 = rs3.getInt("num");
            if(count1 < 20 && count1!=0) {
                out.println(count1);
                out.flush();
                rs2.next();
                for(int i=0;i<count1;i++){
                    out.println(rs2.getString("title"));
                    out.flush();
                    out.println(rs2.getString("username"));
                    out.flush();
                    out.println(rs2.getString("posttime"));
                    out.flush();
                    out.println(rs2.getString("postid"));
                    out.flush();
                    out.println(rs2.getString("usernum"));
                    out.flush();
                    rs2.next();
                }
            } else if(count1!=0){
                count1 = 20;
                out.println(count1);
                out.flush();
                rs2.next();
                for(int i=0;i<count1;i++){
                    out.println(rs2.getString("title"));
                    out.flush();
                    out.println(rs2.getString("username"));
                    out.flush();
                    out.println(rs2.getString("posttime"));
                    out.flush();
                    out.println(rs2.getString("postid"));
                    out.flush();
                    out.println(rs2.getString("usernum"));
                    out.flush();
                    rs2.next();
                }
            } else {
                out.println(count1);
                out.flush();
            }

            if(count2!=0) {
                out.println(count2);
                out.flush();
                rs4.next();
                for(int i=0;i<count2;i++){
                    out.println(rs4.getString("title"));
                    out.flush();
                    out.println(rs4.getString("posttime"));
                    out.flush();
                    out.println(rs4.getString("postid"));
                    out.flush();
                    rs4.next();
                }
            } else {
                out.println(count2);
                out.flush();
            }

            stmt1.close();
            stmt2.close();
            stmt3.close();
            stmt4.close();
            rs1.close();
            rs2.close();
            rs3.close();
            rs4.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("ForumInitFail");
            out.flush();
        }
    }

    public void SaveChange(String postid,String content) {
        try{
            String sql = "update post set content='"+content+"' where postid="+postid;
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.execute();
            out.println("SaveChangeOver");
            out.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("SaveChangeFail");
            out.flush();
        }
    }

    public void updatePass(String usernum,String old,String new_) {
        try{
            String sql="UPDATE userinformation SET password = '"+new_+"' where password = '"+old+"' and usernum = '"+usernum+"'";
            PreparedStatement pstmt=con.prepareStatement(sql);
            int ret=pstmt.executeUpdate();
            if(ret==0){
                out.println("NOMATCH");
            }else{
                out.println("OK");
            }
            out.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("unexpected error");
            out.flush();
        }
    }

    public void findPass(String usernum,String mibao,String mibaodaan, String pass) {
        try{
            String sql="UPDATE userinformation SET password = '"+pass+"' where mibao = '"+mibao+"' and mibaodaan = '"+mibaodaan+"' and usernum = '"+usernum+"'";
            PreparedStatement pstmt=con.prepareStatement(sql);
            int ret=pstmt.executeUpdate();
            if(ret==0){
                out.println("密保无效");
            }else{
                out.println("OK");
            }
            out.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println("unexpected error");
            out.flush();
        }
    }
}
//	public synchronized void sendFile()
//	{
//		String sendQQ=null;
//		String receiveQQ=null;
//		String fileName=null;
//		String fileContent=null;
//		String judge=null;
//		String temp=null;
//		try {
//			receiveQQ=in.readLine();//接收方的QQ号
//			sendQQ=in.readLine();//发送方的QQ号
//			fileName=in.readLine();
//			BufferedWriter bw=new BufferedWriter(
//					new OutputStreamWriter(
//					new FileOutputStream("src/接收文件/临时文件")));
//			do
//			{
//				judge=in.readLine();
//				if(judge.equals("Over"))
//				{
//					break;
//				}
//				else
//				{
//
//					bw.write(judge);
//					bw.newLine();
//					fileContent+=judge+"\n";
//				}
//
//			}while(!judge.equals("Over"));
//			bw.flush();
//			System.out.println(receiveQQ);
//			System.out.println(sendQQ);
//			System.out.println(fileName);
//			System.out.println(fileContent);
//			BufferedReader bufr=new BufferedReader(
//					new InputStreamReader(new FileInputStream(
//							"src/接收文件/临时文件.txt")));
//			Socket socket1;
//			PrintStream out1=null;//输出流
//			for(int i=0;i<father.clients.size();i++)
//			{
//				socket1=(Socket) father.clients.elementAt(i);
//				if(!socket1.equals(this.socket))
//				{
//					out1=new PrintStream(socket.getOutputStream());
//					out1.println("FILE");
//					out1.flush();
//					out1.println(receiveQQ);
//					out1.flush();
//					out1.println(sendQQ);
//					out1.flush();
//					out1.println(fileName);
//					out1.flush();
//					while((temp=bufr.readLine())!=null)
//					{
//						out1.println(temp);
//						out1.flush();
//					}
//					out1.println("All");
//					out1.flush();
//					System.out.println("i 的值最后为多少啊："+i);
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

