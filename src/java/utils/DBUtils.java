package utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author User
 */
public class DBUtils {

    public static Connection getConnnection() throws SQLException, NamingException {
        Connection conn = null;
        Context context = new InitialContext();
        Context envCtx = (Context) context.lookup("java:comp/env");
        DataSource ds=(DataSource) envCtx.lookup("DBConn");
        conn = ds.getConnection();
        return conn;
    }
}
