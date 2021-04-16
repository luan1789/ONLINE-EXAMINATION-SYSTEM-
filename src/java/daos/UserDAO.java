/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import utils.DBUtils;

/**
 *
 * @author truon
 */
public class UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class);

    public UserDTO checkLogin(String email, String password) throws SQLException {
        UserDTO result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "SELECT name, roleID "
                        + "FROM tblUsers "
                        + "WHERE email = ? and password = ? AND status = 'active'";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String roleID = rs.getString("roleID");
                    result = new UserDTO(email, name, "", roleID);
                }
            }
        } catch (Exception e) {
            LOGGER.error("error: ", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public boolean checkEmail(String email) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "SELECT name "
                        + "FROM tblUsers "
                        + "WHERE email=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("error: ", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }

        }
        return result;
    }

    public int createUser(UserDTO user) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        int result = -1;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "INSERT INTO tblUsers(email,name,password,roleID) VALUES(?,?,?,'US')";
                stm = conn.prepareStatement(sql);
                stm.setString(1, user.getEmail());
                stm.setString(2, user.getName());
                stm.setString(3, user.getPassword());
                result = stm.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error("error: ", e);
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

}
