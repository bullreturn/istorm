package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*数据库连接*/
public class ConnectionDao {
    public static Connection getConnection() {
        Connection conn=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.print("");
        } catch(ClassNotFoundException ex) {
            System.out.println("加载数据库驱动失败");
        }
        try {
            // Who cares :(
            String url="jdbc:mysql://ecs.cnstl.tech:13306/test?user=root&password=123456";
            conn=DriverManager.getConnection(url);
            System.out.print("数据库连接成功\n");
            return conn;
        } catch(SQLException ex) {
            System.out.println("数据库连接失败"+ex);
            return null;
        }

    }

}
