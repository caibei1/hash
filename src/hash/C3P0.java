package hash;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;


import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0 {

    private ComboPooledDataSource comb;

    public void init(){
        //首先读取配置文件信息，以供配置c3p0所用
        //加载配置
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src/hash/conf.properties"));
        } catch (IOException e) {
            System.out.println("读取配置文件异常");
            System.out.println(e);
        }

        comb = new ComboPooledDataSource();//下面就是开始配置
        try {
            comb.setDriverClass(prop.getProperty("jdbcdriver"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        comb.setJdbcUrl(prop.getProperty("url"));
        comb.setUser(prop.getProperty("username"));
        comb.setPassword(prop.getProperty("password"));
    }

    public Connection getConnection() {


        Connection con = null;//从c3p0，拿一条数据库连接
        try {
            con = comb.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;


//        //执行查询语句
//        String sql = "select * from hash5";
//        PreparedStatement ps = con.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//        //打印数据库信息
//        while(rs.next())
//        {
//            System.out.println(rs.getString(1));
//            System.out.println(rs.getString(2));
//            System.out.println(rs.getString(3));
//            System.out.println(rs.getString(5));
//            System.out.println(rs.getString(6));
//            System.out.println(rs.getString(7));
//        }
//        con.close();
//        comb.close();
//        rs.close();
//        ps.close();
    }

    public DataSource getDataSource() {
        return comb;
    }
}
///root/java/hash2/src/hash