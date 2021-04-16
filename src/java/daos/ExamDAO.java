/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.AnswerDTO;
import dtos.ExamDTO;
import dtos.ExamDetailDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import utils.DBUtils;

/**
 *
 * @author PC
 */
public class ExamDAO {

    private static final Logger LOGGER = Logger.getLogger(ExamDAO.class);

    public int saveFullExam(ExamDTO exam) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        int result = -1;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                conn.setAutoCommit(false);

                String sql = "INSERT INTO tblExam(examID, email,dateTimeStart, subjectID, numberCorrect,point) VALUES (?,?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, exam.getExamID());
                stm.setString(2, exam.getEmail());
                stm.setTimestamp(3, exam.getTimeStart());
                stm.setString(4, exam.getSubjectID());
                stm.setInt(5, exam.getNumberCorrect());
                stm.setFloat(6, exam.getPoint());
                stm.executeUpdate();
                for (ExamDetailDTO examDetailDTO : exam.getList()) {
                    sql = "INSERT INTO tblExamDetail(examID ,questionID ,answerID ,status ) VALUES (?,?,?,?)";
                    stm = conn.prepareStatement(sql);
                    stm.setInt(1, exam.getExamID());
                    stm.setInt(2, examDetailDTO.getQuestionID());
                    stm.setInt(3, examDetailDTO.getAnswerIDUser());
                    stm.setBoolean(4, examDetailDTO.isStatus());
                    result = stm.executeUpdate();
                }
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            result = -1;
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

    public ExamDTO getExamDetail(int examID) throws SQLException {
        ExamDTO result = new ExamDTO();
        List<ExamDetailDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        QuestionDAO dao = new QuestionDAO();
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "SELECT C.questionID,C.question_content,"
                        + "B.answerID,B.status "
                        + "FROM tblExam A "
                        + "JOIN tblExamDetail B "
                        + "ON A.examID=? AND A.examID=B.examID "
                        + "JOIN tblQuestions C   "
                        + "ON B.questionID=C.questionID ";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, examID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int questionID = rs.getInt("questionID");
                    int answerID = rs.getInt("answerID");
                    String question_content = rs.getString("question_content");
                    boolean status = rs.getBoolean("status");
                    List<AnswerDTO> listAnswer = dao.getAnswerContent(questionID);
                    list.add(new ExamDetailDTO(questionID, question_content, listAnswer, answerID, status));
                }
                result.setList(list);
                result.setExamID(examID);
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

    public List<ExamDTO> getAllExam(String email) throws SQLException {
        List<ExamDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "SELECT examID,dateTimeStart,dateTimeFinish,subjectID,point,numberCorrect "
                        + "FROM tblExam "
                        + "WHERE email=? ORDER BY dateTimeStart DESC";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int examID = rs.getInt("examID");
                    Timestamp dateTimeStart = rs.getTimestamp("dateTimeStart");
                    Timestamp dateTimeEnd = rs.getTimestamp("dateTimeFinish");
                    String subjectID = rs.getString("subjectID");
                    int numberCorrect = rs.getInt("numberCorrect");
                    float point = rs.getFloat("point");
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new ExamDTO(examID, email, subjectID, dateTimeStart, dateTimeEnd, point, numberCorrect));
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

    public List<ExamDTO> getExamBySearch(String email, String subjectID, Date dateSearch, int pagesize, int pageNumber) throws SQLException {
        List<ExamDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "SELECT examID,subjectID,dateTimeStart,dateTimeFinish,point,numberCorrect "
                        + "FROM tblExam "
                        + "WHERE email=? AND ( (CAST(dateTimeStart AS DATE)= ?) or ? is null) "
                        + "AND(subjectID like ? )"
                        + "ORDER BY dateTimeStart DESC "
                        + "OFFSET ? * (? - 1) ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                if (dateSearch == null) {
                    stm.setDate(2, null);
                    stm.setDate(3, null);
                } else {
                    stm.setDate(2, new java.sql.Date(dateSearch.getTime()));
                    stm.setDate(3, new java.sql.Date(dateSearch.getTime()));
                }
                stm.setString(4, "%" + subjectID + "%");
                stm.setInt(5, pagesize);
                stm.setInt(6, pageNumber);
                stm.setInt(7, pagesize);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int examID = rs.getInt("examID");
                    Timestamp dateTimeStart = rs.getTimestamp("dateTimeStart");
                    Timestamp dateTimeEnd = rs.getTimestamp("dateTimeFinish");
                    int numberCorrect = rs.getInt("numberCorrect");
                    String sub = rs.getString("subjectID");
                    float point = rs.getFloat("point");
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new ExamDTO(examID, email, sub, dateTimeStart, dateTimeEnd, point, numberCorrect));
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

    public int getRecordSearchHistory(String email, String subjectID, Date dateSearch, int pagesize, int pageNumber) throws SQLException {
        int result = 0;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "SELECT count(*) as noRecord "
                        + "FROM tblExam "
                        + "WHERE email=? AND ( (CAST(dateTimeStart AS DATE)= ?) or ? is null) "
                        + "AND(subjectID like ?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                if (dateSearch == null) {
                    stm.setDate(2, null);
                    stm.setDate(3, null);
                } else {
                    stm.setDate(2, new java.sql.Date(dateSearch.getTime()));
                    stm.setDate(3, new java.sql.Date(dateSearch.getTime()));
                }
                stm.setString(4, "%" + subjectID + "%");
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("noRecord");
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
