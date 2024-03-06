package GetDBConnection;
import java.sql.*;
public class GetDBConnection {
    public static Connection connectDB(String DBName,String user,String password){
        Connection con = null;
        String uri = "jdbc:mysql://localhost:3306/"+DBName;

        //加载JDBC驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC驱动加载成功！");
        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("JDBC驱动加载失败！");
        }

        //发起数据库连接请求
        try {
            con = DriverManager.getConnection(uri,user,password);
            System.out.println("数据库连接成功！");
        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("数据库连接失败！");
        }
        return con;
    }
}
