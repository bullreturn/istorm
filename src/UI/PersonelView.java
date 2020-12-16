package UI;
/*Has
 * HashMap<String,String> map=new HashMap<String,String>;
 * map.put("1","student");
 *Iterator<String> mit=map.keySet().iterator();
 *while(mit.hasNext()){
 *String str=mit.next();
 *System.out.println(map.get(str));
 *} */
//dengluh
import UI.Forum.Forum;
import user.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/*主界面*/
public class PersonelView extends JFrame implements Runnable {
    ChatView chatview;
    /*-------------------------------------界面设计---------------------------------------------------*/
    JPanel jPtop=new JPanel();//置于顶部
    JPanel jPcentre=new JPanel();//置于中间存放好友列表
    //顶部
    JLabel jLtitle=new JLabel("ISTORM");
    //QQ头像尺寸60*60
    JLabel jLportrait=new JLabel(new ImageIcon("src/file/personelView1.jpg"));
    JLabel jLmyName=new JLabel("我是毛毛虫");
    JLabel jLmySign=new JLabel("个性签名");
    JTextField jTfind=new JTextField("输入ISTORM账号查找联系人");
    ImageIcon imageFind=new ImageIcon("src/file/personelViewFind.jpg");//尺寸19*21
    JButton jBfind=new JButton(imageFind);//查找联系人按钮
    //过渡
    JLabel jLcenter1=new JLabel(new ImageIcon("src/file/personelViewCenter1.jpg"));//尺寸305*7
    //中部
    JTabbedPane jTPchoose=new JTabbedPane();//选项板
    JPanel jPcentrefriend=new JPanel();//置于中间的中间存放好友列表

    JLabel j1=new JLabel(new ImageIcon("src/file/你猜1.jpg"));
    JPanel j2=new ShowGame();
    //JLabel j2=new JLabel(new ImageIcon("src/file/你猜2.jpg"));
    JLabel j3=new JLabel(new ImageIcon("src/file/你猜1.jpg"));
    JLabel j4=new JLabel(new ImageIcon("src/file/你猜2.jpg"));
    DefaultListModel listModel=new DefaultListModel();
    JList userList=new JList(listModel);
    JScrollPane jSuserList=new JScrollPane(userList);
    //增加用户的标签”分组外“的弹出式菜单
    JMenuItem jM11=new JMenuItem("列表显示");
    JMenuItem jM12=new JMenuItem("刷新好友列表");
    JMenuItem jM13=new JMenuItem("显示在线联系人");
    JMenuItem jM14=new JMenuItem("添加联系人");
    JMenuItem jM15=new JMenuItem("查找好友");
    JMenuItem jM16=new JMenuItem("好友管理器");
    JMenuItem jM17=new JMenuItem("帮助");
    JMenuItem jM18=new JMenuItem("关于");
    JPopupMenu jPmenuser=new JPopupMenu();
    //好友列表的弹出式菜单选项
    JMenuItem jM1=new JMenuItem("发送即时消息");
    JMenuItem jM2=new JMenuItem("发送电子邮件");
    JMenuItem jM3=new JMenuItem("发送文件");
    JMenuItem jM4=new JMenuItem("删除好友");
    JMenuItem jM5=new JMenuItem("举报此用户");
    JMenuItem jM6=new JMenuItem("修改备注姓名");
    JMenuItem jM7=new JMenuItem("消息记录");
    JMenuItem jM8=new JMenuItem("查看资料");
    JPopupMenu jPmenufriend=new JPopupMenu();
    //底部
    JLabel jLbase=new JLabel(new ImageIcon("src/file/personelView2.jpg"));//尺寸304*61
    JButton forumbuttom = new JButton("ISTORM论坛");

    /*-------------------------------------功能实现---------------------------------------------------*/
    private Hashtable friendInfoTable = new Hashtable();//存储好友列表
    Socket socket;//定义套接口
    BufferedReader in;//定义输入流
    PrintStream out;//定义输出流
    InetAddress ip=null;//服务器IP
    int port=0;//服务器端口号
    String userNum;//登陆用户自己的QQ号

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    String userPass;//登陆用户自己的密码
    HomePage login;
    private int currentIndex=0;//鼠标所指的列表索引
    private String currentInfo="";//鼠标所指的列表值
    private String currentUserNum=null;//鼠标所指好友的QQ号码
    private UserBean currentFriend=null;//鼠标所指好友的信息类
    private UserBean myInfo=new UserBean();//存储自己的信息
    UserBean findUserBean=new UserBean();//存储查找到的用户的基本信息
    /*采用UDP协议进行通信*/
    private DatagramSocket  receiveSocket=null;//声明接收信息的数据包套接字
    private DatagramPacket  receivePacket=null;//声明接收信息的数据包
    int udpPort=getUdpPort("udp.Port");//UDP的初始端口号
    InetAddress userIp = null ;
    int usePort=getNextPort(udpPort);//当期用户使用的端口号
    public static final int BUFFER_SIZE=5120;//缓冲数组的大小
    private byte inBuf[];//接收数据的缓冲数组
    //实现聊天记录
    BufferedReader bufr;//完成聊天记录的读
    String path=null;
    /*-------------------------------------构造方法---------------------------------------------------*/
    public PersonelView(String userNum,String userPass,HomePage login,InetAddress ip,int port) {
        // TODO Auto-generated constructor stub
        this.userNum=userNum;
        this.userPass=userPass;
        this.login=login;
        this.ip=ip;
        this.port=port;
        this.setSize(313, 590);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        init();
        this.add(jPtop);
        this.add(jLcenter1);
        this.add(jLbase);
        this.add(jTPchoose);
        //*------------------------采用TCP协议与服务器开始连接，完成登陆以及其他功能---------------------------------------*/
        try {
            socket=new Socket(ip,port);
            System.out.println("与服务器开始连接");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.out.println("服务器端口打开出错");
        }
        if(socket!=null) {
            System.out.println("与服务器连接成功");
            try {
                userIp= InetAddress.getLocalHost();
            } catch (UnknownHostException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            //udpPort=socket.getLocalPort();
            try {
                in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out=new PrintStream(socket.getOutputStream());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        //*--------------------------------------UDP协议，聊天模块-----------------------------------------*/
        try {
            //创建接收信息的数据包套接字
            //	System.out.println("1我接收数据的端口号:"+usePort);
            receiveSocket=new DatagramSocket(usePort);
            //System.out.println("2我接收数据的端口号:"+usePort);
            inBuf=new byte[BUFFER_SIZE];
            //创建接收信息的数据报
            receivePacket=new DatagramPacket(inBuf,BUFFER_SIZE);
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("端口打开出错");
        }
        loadUserInfo();
        //启动线程用来刷新好友信息
        new Thread(this).start();
        this.setVisible(true);
    }
    //获取好友信息
    private void loadUserInfo() {
        if(login()) {
            getFriendInfo();//获取好友信息列表
            userList.setCellRenderer(new FriendLabel());
            jLportrait.setIcon(new ImageIcon(myInfo.getPortrait()));
            jLmyName.setText(myInfo.getUserName());
            jLmySign.setText(myInfo.getSign());
        } else {
            login.loginFail();
            System.out.println("获取信息失败！");
            return;
        }
    }
    //每隔10秒刷新一次好友信息
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //刷新用户信息
            loadUserInfo();
        }

    }
    public void init() {
        //顶部面板
        jPtop.setBounds(0, 0, 313, 110);
        jPtop.setLayout(null);
        jPtop.setBackground(new Color(30,144,255));
        jLtitle.setForeground(Color.WHITE);
        jLtitle.setFont(new Font("黑体",Font.BOLD,15));
        jLtitle.setBounds(0, 0, 313, 25);
        jLportrait.setBounds(5, 25, 60, 55);
        forumbuttom.setBounds(314, 117, 50, 25);
        forumbuttom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedReader br = null;
                PrintStream ps = null;
                try{
                    Socket ee=new Socket(ip,port);
                    br=new BufferedReader(new InputStreamReader(ee.getInputStream()));
                    ps=new PrintStream(ee.getOutputStream());
                }catch (Exception ee){
                    // pass
                }
                Forum forum = new Forum(myInfo,br,ps);
                forum.setVisible(true);
            }
        });
        //为自己头像设置监听，查看自己信息
        jLportrait.addMouseListener(new PersonelView_jLportrait_mouseMotionAdapter());
        jLmyName.setForeground(Color.WHITE);
        jLmyName.setFont(new Font("宋体",Font.BOLD,17));
        jLmyName.setBounds(80, 27, 180, 25);
        //在个人资料上添加弹出式菜单
        JMenuItem jMa=new JMenuItem("修改个人资料");
        JMenuItem jMb=new JMenuItem("更换头像");
        JMenuItem jMc=new JMenuItem("修改密码");
        jMa.setFont(new Font("楷体",Font.PLAIN,14));
        jMa.setForeground(Color.BLACK);
        jMa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                ChangeMyInfo changemyInfo=new ChangeMyInfo(PersonelView.this, "修改资料", true, in, out, myInfo,PersonelView.this);
                changemyInfo.setVisible(true);
                PersonelView.this.refreshMyInfo();
            }

        });
        jMb.setFont(new Font("楷体",Font.PLAIN,14));
        jMb.setForeground(Color.BLACK);
        jMb.addActionListener(new ActionListener() { //更换头像

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                changHead();

            }

        });
        jMc.setFont(new Font("楷体",Font.PLAIN,14));
        jMc.setForeground(Color.BLACK);
        jMc.addActionListener(new ActionListener() { //修改密码

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                ChangePass changemyInfo=new ChangePass(PersonelView.this, "账户安全", true, in, out, myInfo,PersonelView.this);
                changemyInfo.setVisible(true);
                PersonelView.this.refreshMyInfo();

            }

        });
        JPopupMenu jPmenuMy=new JPopupMenu();
        jPmenuMy.add(jMa);
        jPmenuMy.add(jMb);
        jPmenuMy.add(jMc);
        jLmyName.setComponentPopupMenu(jPmenuMy);
        jLmySign.setForeground(Color.WHITE);
        jLmySign.setFont(new Font("楷体",Font.PLAIN,14));
        jLmySign.setBounds(70, 55, 235, 25);
        jLmySign.setComponentPopupMenu(jPmenuMy);
        jTfind.setFont(new Font("宋体",Font.BOLD,12));
        jTfind.setForeground(Color.GRAY);
        jTfind.setBounds(3, 85, 260, 25);
        jBfind.setBounds(267, 87, 19, 21);
        jBfind.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

                String num=jTfind.getText().trim();
                findUser(num);
            }
        });
        jPtop.add(jBfind);
        jPtop.add(jTfind);
        jPtop.add(jLmySign);
        jPtop.add(jLmyName);
        jPtop.add(jLportrait);
        jPtop.add(jLtitle);
        jPtop.add(jLtitle);
        //过渡
        jLcenter1.setBounds(0, 110, 313, 7);
        //中间
        /*--------------------------增加弹出式菜单---------------------*/
        jPmenufriend.add(jM1);
        jPmenufriend.add(jM2);
        jPmenufriend.add(jM3);
        jPmenufriend.add(jM4);
        jPmenufriend.add(jM5);
        jPmenufriend.add(jM6);
        jPmenufriend.add(jM7);
        jM7.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                path="src/聊天记录/"+myInfo.getUserNum()+"-"+currentFriend.getUserNum()+".txt";
                try {
                    bufr=new BufferedReader(
                        new InputStreamReader(new FileInputStream(
                                                  path)));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                SaveChat save=new SaveChat(PersonelView.this, "聊天记录", true, bufr);
                save.setVisible(true);
            }
        });
        jPmenufriend.add(jM8);
        jM1.setFont(new Font("楷体",Font.PLAIN,14));
        jM1.setForeground(Color.BLUE);
        jM1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                if(chatview ==null){
                    chatview=new ChatView(myInfo,currentFriend,PersonelView.this,usePort,receiveSocket,receivePacket,friendInfoTable,in,out);
                    new Thread(chatview).start();
                }
                chatview.setVisible(true);
            }

        });
        jM2.setFont(new Font("楷体",Font.PLAIN,14));
        jM2.setForeground(Color.BLUE);
        jM3.setFont(new Font("楷体",Font.PLAIN,14));
        jM3.setForeground(Color.BLUE);
        jM4.setFont(new Font("楷体",Font.PLAIN,14));
        jM4.setForeground(Color.BLUE);
        jM4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int option=JOptionPane.showConfirmDialog(PersonelView.this, "亲，你确定要删除此好友么？");
                if(option==JOptionPane.YES_OPTION) {
                    int index=userList.getSelectedIndex();
                    String friendNum=null;
                    if(index==-1) {
                        JOptionPane.showMessageDialog(PersonelView.this, "请单击选择一个用户！");
                    } else {
                        try {
                            String friendInfo=(String)listModel.getElementAt(index);
                            friendNum=friendInfo.substring(friendInfo.indexOf("<")+1,friendInfo.indexOf(">") );
                            UserBean deleteFriend=(UserBean)friendInfoTable.get(friendNum);
                            String myUserNum=myInfo.getUserNum();
                            //向服务器发送删除好友请求
                            out.println("deleteFriend");
                            out.flush();
                            //发送自己QQ
                            out.println(myUserNum);
                            out.flush();
                            //发送好友QQ
                            out.println(friendNum);
                            out.flush();
                            String judge_delete=in.readLine();
                            if(judge_delete.equals("deleteFriendOver")) {
                                JOptionPane.showMessageDialog(PersonelView.this, "好友 <"+deleteFriend.getUserName()+"> 已被成功删除 !");
                                listModel.remove(index);
                            } else if(judge_delete.equals("deleteFriendFail")) {
                                JOptionPane.showMessageDialog(PersonelView.this, "系统繁忙,请稍后再试！");
                            }
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(PersonelView.this, "系统繁忙维护中！");
                        }
                    }
                }
            }

        });
        jM5.setFont(new Font("楷体",Font.PLAIN,14));
        jM5.setForeground(Color.BLUE);
        jM6.setFont(new Font("楷体",Font.PLAIN,14));
        jM6.setForeground(Color.BLUE);
        jM7.setFont(new Font("楷体",Font.PLAIN,14));
        jM7.setForeground(Color.BLUE);
        jM8.setFont(new Font("楷体",Font.PLAIN,14));
        jM8.setForeground(Color.BLUE);
        jM8.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                UserInfo friendInfo=new UserInfo(PersonelView.this,"好友资料",true,currentFriend);
                friendInfo.setVisible(true);
            }

        });
        userList.setComponentPopupMenu(jPmenufriend);
        jTPchoose.setForeground(Color.DARK_GRAY);
        jTPchoose.setBackground(Color.WHITE);
        jTPchoose.setFont(new Font("宋体",Font.PLAIN,11));
        jTPchoose.addTab("联系人", jPcentre);
        //jTPchoose.addTab("帖子", j1);
        jTPchoose.addTab("游戏", j2);
        jTPchoose.addTab("论坛",forumbuttom);
        /*jTPchoose.addTab("朋友", j3);
        jTPchoose.addTab("微博", j4);*/
        jTPchoose.setBounds(0, 117, 313, 382);
        jPcentre.setLayout(new BorderLayout());
        JLabel test=new JLabel("我 的 好 友");
        test.setFont(new Font("宋体",Font.PLAIN,14));
        test.setSize(313, 30);
        test.setForeground(Color.BLACK);
        jPmenuser.add(jM11);
        jPmenuser.add(jM12);
        jPmenuser.add(jM13);
        jPmenuser.add(jM14);
        jPmenuser.add(jM15);
        jPmenuser.add(jM16);
        jPmenuser.add(jM17);
        jPmenuser.add(jM18);
        jM11.setFont(new Font("楷体",Font.PLAIN,14));
        jM11.setForeground(Color.DARK_GRAY);
        jM12.setFont(new Font("楷体",Font.PLAIN,14));
        jM12.setForeground(Color.DARK_GRAY);
        jM13.setFont(new Font("楷体",Font.PLAIN,14));
        jM13.setForeground(Color.DARK_GRAY);
        jM14.setFont(new Font("楷体",Font.PLAIN,14));
        jM14.setForeground(Color.DARK_GRAY);
        jM14.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                PrintNumFindUser printNum=new PrintNumFindUser(PersonelView.this,"添加联系人",false,PersonelView.this);
                printNum.setVisible(true);
            }
        });
        jM15.setFont(new Font("楷体",Font.PLAIN,14));
        jM15.setForeground(Color.DARK_GRAY);
        jM15.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                PrintNumFindUser printNum=new PrintNumFindUser(PersonelView.this,"查找用户",false,PersonelView.this);
                printNum.setVisible(true);
            }

        });
        jM16.setFont(new Font("楷体",Font.PLAIN,14));
        jM16.setForeground(Color.DARK_GRAY);
        jM17.setFont(new Font("楷体",Font.PLAIN,14));
        jM17.setForeground(Color.DARK_GRAY);
        jM18.setFont(new Font("楷体",Font.PLAIN,14));
        jM18.setForeground(Color.DARK_GRAY);
        jM18.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                AboutMy a=new AboutMy(PersonelView.this, "关于QQ", true);
                a.setVisible(true);
            }

        });
        test.setComponentPopupMenu(jPmenuser);
        jPcentre.setBackground(Color.WHITE);
        jPcentre.setBounds(0, 117, 313, 382);
        userList.addMouseListener(new PersonelView_userList_mouseAdapter());
        userList.addMouseMotionListener(new PersonelView_userList_mouseMotionAdapter());
        jPcentre.add(BorderLayout.NORTH,test);
        jPcentre.add(jSuserList);
        //底部
        jLbase.setBounds(0, 499, 304, 51);
    }
    //用于登录后读取自己和好友信息
    private Boolean login() {
        String judge = null;

        try {
            out.println("login");
            out.flush();
            out.println(userNum);
            out.flush();
            out.println(userPass);
            out.flush();
            out.println(userIp);
            out.flush();
            out.println(usePort);
            //System.out.println("发送给数据库我的端口号"+usePort);
            out.flush();
            //读取自己信息
            judge=in.readLine();
            if(judge.equals("loginFail")) {
                return false;
            } else if(judge.equals("sendUserInfo")) {
                String flag_1=in.readLine();
                if(flag_1.equals("queryUserFail")) {
                    return false;
                } else {
                    myInfo.setUserNum(flag_1);
                    myInfo.setUserName(in.readLine());
                    myInfo.setSex(in.readLine());
                    myInfo.setBirth(in.readLine());
                    myInfo.setAddress(in.readLine());
                    myInfo.setSign(in.readLine());
                    myInfo.setPortrait(in.readLine());
                    myInfo.setStatus(Integer.valueOf(in.readLine()));
                    myInfo.setPort(Integer.valueOf(in.readLine()));
                    myInfo.setIp(in.readLine());
                    //自己信息读取完毕，服务器发送loginSuccess，开始读取好友信息
                }
            }
            //自己信息读取完毕，服务器发送loginSuccess，开始读取好友信息
            String flag_3=in.readLine();
            if(flag_3.equals("loginSuccess")) {
                /*-----------------开始从服务器端读取好友信息--------------------------*/
                friendInfoTable.clear();
                String flag2="";
                do {
                    flag2=in.readLine().trim();
                    System.out.println("flag2:"+flag2);
                    if(flag2.equals("queryFriendOver")) {
                        System.out.println("BREAK");
                        break;

                    } else {
                        UserBean friendBean=new UserBean();
                        friendBean.setUserNum(flag2);
                        friendBean.setUserName(in.readLine());
                        friendBean.setSex(in.readLine());
                        friendBean.setBirth(in.readLine());
                        friendBean.setAddress(in.readLine());
                        friendBean.setSign(in.readLine());
                        friendBean.setPortrait(in.readLine());
                        friendBean.setStatus(Integer.valueOf(in.readLine()));
                        friendBean.setPort(Integer.valueOf(in.readLine()));
                        friendBean.setIp(in.readLine());
                        friendInfoTable.put(flag2, friendBean);
                    }
                } while(!(flag2.equals("queryFriendOver")));
            } else if(flag_3.equals("queryUserFail")) {
                return false;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
    //获得好友信息以创建列表
    private void getFriendInfo() {
        listModel.removeAllElements();
        //实现 Enumeration 接口的对象，生成一系列元素，一次生成一个。连续调用 nextElement 方法将返回一系列的连续元素。
        Enumeration it = friendInfoTable.elements();
        String name="";
        String num="";
        String portrait="";
        String friendinfo="";//每一个好友的显示信息
        int status=0;
        while(it.hasMoreElements()) {
            UserBean user=(UserBean)it.nextElement();
            name=user.getUserName().trim();
            num=user.getUserNum().trim();
            portrait=user.getPortrait();
            status=user.getStatus();
            friendinfo= status + name + "<" + num + ">" + "*" + portrait+"^";
            listModel.addElement(friendinfo);
        }

    }
    //读取端口号
    private int getUdpPort(String key) {
        int myport=0;
        Properties p=new Properties();
        try {
            FileInputStream in=new FileInputStream("src/file/udp.txt");
            FileOutputStream out=new FileOutputStream("src/file/udp.txt",true);
            p.load(in);//从输入流中读取属性列表
            myport=Integer.parseInt(p.getProperty(key));
            myport=myport+1;
            p.setProperty("udp.Port", new Integer(myport).toString());
            p.store(out, "new udp.Port");
            in.close();
            out.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return myport;
    }
    private int getNextPort(int port) {
        int nextport=port;
        Boolean flag=true;
        DatagramSocket testsocket=null;
        //检测端口是否被占用
        while(true) {
            flag=true;
            try {
                testsocket=new DatagramSocket(++nextport);
            } catch (SocketException e) {
                // TODO Auto-generated catch block
                flag=false;
            }
            if(flag==true) {
                break;
            }
            System.out.println(nextport);

        }
        testsocket.close();
        return nextport;

    }

    protected void processWindowEvent(WindowEvent e) {
        if(e.getID()==WindowEvent.WINDOW_CLOSING) {
            exit();
        }
    }
    public void exit() { //处理用户下线
        int option=JOptionPane.showConfirmDialog(PersonelView.this, "亲，你确定要退出么？");
        if(option==JOptionPane.YES_OPTION) {
            try {
                out.println("logout");
                out.flush();
                out.println(myInfo.getUserNum());
                out.flush();
                String msg=in.readLine();
                if(msg.equals("logOut")) {
                    out.println("end");
                    out.flush();
                    in.close();
                    out.close();
                    socket.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                this.dispose();
                System.exit(0);
            }
        }
    }
    //响应好友头像上的鼠标双击事件
    class PersonelView_userList_mouseAdapter extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount()==2&&e.getButton()==MouseEvent.BUTTON1) {
                if(chatview ==null){
                    chatview=new ChatView(myInfo,currentFriend,PersonelView.this,usePort,receiveSocket,receivePacket,friendInfoTable,in,out);
                    new Thread(chatview).start();
                }
                chatview.setVisible(true);
            }
        }
    }
    //处理用户将鼠标移动到某一个头像上
    class PersonelView_userList_mouseMotionAdapter extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            if(!(listModel.isEmpty())) {
                currentIndex=userList.locationToIndex(e.getPoint());//获得当前所在列表的索引
                currentInfo=listModel.getElementAt(currentIndex).toString();//获得当前列表的值
                currentUserNum=currentInfo.substring(currentInfo.indexOf("<")+1,currentInfo.indexOf(">"));
                //根据好友的QQ号查找好友的信息
                currentFriend=(UserBean)friendInfoTable.get(currentUserNum);
                String friendSign=currentFriend.getSign();
                userList.setToolTipText(friendSign);//设置提示信息，显示好友的个性签名
            }
        }
    }
    class PersonelView_jLportrait_mouseMotionAdapter extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            jLportrait.setCursor(new Cursor(Cursor.HAND_CURSOR));//将进入自己头像时，变为手状光标类型。
        }
        public void mousePressed(MouseEvent e) {

            UserInfo userInfo=new UserInfo(PersonelView.this,"我的资料",true,myInfo);
            userInfo.setVisible(true);
        }
    }
    public void findUser(String num) {
        //UserBean findUser=new UserBean();
        out.println("queryUser");
        out.flush();
        out.println(num);
        out.flush();
        try {
            String judge_find=in.readLine();
            if(judge_find.equals("noUser")) {
                JOptionPane.showMessageDialog(PersonelView.this, "亲，你输入的用户不存在呢！");
            } else if(judge_find.equals("queryUserFail")) {
                JOptionPane.showMessageDialog(PersonelView.this, "查找用户失败");
            } else {
                findUserBean.setUserNum(judge_find);
                findUserBean.setUserName(in.readLine());
                findUserBean.setSex(in.readLine());
                findUserBean.setBirth(in.readLine());
                findUserBean.setAddress(in.readLine());
                findUserBean.setSign(in.readLine());
                findUserBean.setPortrait(in.readLine());
                findUserBean.setStatus(Integer.valueOf(in.readLine()));
                findUserBean.setPort(Integer.valueOf(in.readLine()));
                findUserBean.setIp(in.readLine());
                FindUser find=new FindUser(PersonelView.this,"用户资料",true,findUserBean,PersonelView.this);
                find.setVisible(true);
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            JOptionPane.showMessageDialog(PersonelView.this, "系统正在维护中！");
        }

    }

    
    public void addUser() {
        try {
            String name=findUserBean.getUserName().trim();
            String num=findUserBean.getUserNum().trim();
            String portrait=findUserBean.getPortrait();
            int  status=findUserBean.getStatus();
            String  friendinfo= status + name + "<" + num + ">" + "*" + portrait+"^";
            if(listModel.contains(friendinfo)) {
                JOptionPane.showMessageDialog(PersonelView.this, "该用户已存在好友列表中");
            } else {
                //向服务器发送添加好友请求
                out.println("addFriend");
                out.flush();
                out.println(myInfo.getUserNum());
                out.flush();
                out.println(findUserBean.getUserNum());
                out.flush();
                String judge_ad=in.readLine();
                out.println("addFriend");
                out.flush();
                out.println(findUserBean.getUserNum());
                out.flush();
                out.println(myInfo.getUserNum());
                out.flush();
                String judge_add=in.readLine();
                if(judge_add.equals("addFriendOver")) {
                    //将新添加的好友信息存入到哈希表中
                    friendInfoTable.put(findUserBean.getUserNum().trim(), findUserBean);
                    //在列表中显示新添加的好友
                    listModel.addElement(friendinfo);
                    JOptionPane.showMessageDialog(PersonelView.this, "添加成功");
                } else if(judge_add.equals("addFriendFail")) {
                    JOptionPane.showMessageDialog(PersonelView.this, "添加好友失败");
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    //更新自己的头像
    public void refreshMyProtrait() {
        jLportrait.setIcon(new ImageIcon(myInfo.getPortrait()));
    }
    public void refreshMyInfo() {
        jLmyName.setText(myInfo.getUserName());
        jLmySign.setText(myInfo.getSign());
    }
    public void changHead() {
        ChangeHead changeHead=new ChangeHead(PersonelView.this,"更换头像",true,myInfo,in,out,PersonelView.this);
        changeHead.setVisible(true);
        PersonelView.this.refreshMyProtrait();
    }
}
