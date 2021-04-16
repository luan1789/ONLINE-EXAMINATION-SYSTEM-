/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.SubjectDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import utils.DBUtils;

/**
 *
 * @author truon
 */
public class SubjectDAO {

    private static final Logger LOGGER = Logger.getLogger(SubjectDAO.class);

    public List<SubjectDTO> getSubjects() throws SQLException {
        List<SubjectDTO> listSubject = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "select subjectID, name "
                        + "from tblSubjects";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String subjectID = rs.getString("subjectID");
                    String subjectName = rs.getString("name");

                    if (listSubject == null) {
                        listSubject = new ArrayList<>();
                    }
                    listSubject.add(new SubjectDTO(subjectID, subjectName));
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
        return listSubject;
    }
    public int getNumberQuestion( String subjectID) throws SQLException {
        int result=0;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "select numberQuestion "
                        + "from tblSubjects WHERE subjectID=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, subjectID);
                rs = stm.executeQuery();
                if (rs.next()) {
                     result=rs.getInt("numberQuestion");
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
    public int getDuration( String subjectID) throws SQLException {
        int result=0;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "select duration "
                        + "from tblSubjects WHERE subjectID=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, subjectID);
                rs = stm.executeQuery();
                if (rs.next()) {
                     result=rs.getInt("duration");
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

}
