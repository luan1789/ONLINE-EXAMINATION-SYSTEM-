/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.AnswerDTO;
import dtos.QuestionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import utils.DBUtils;

/**
 *
 * @author truon
 */
public class QuestionDAO {

    private static final Logger LOGGER = Logger.getLogger(QuestionDAO.class);

    public List<QuestionDTO> getQuestionUS(int numberQuestion, String subjectID) throws SQLException {
        List<QuestionDTO> listQuestion = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "SELECT TOP " + numberQuestion + " questionID,question_content,createDate,S.subjectID  "
                        + "FROM tblQuestions Q  JOIN tblSubjects S on Q.subjectID = S.subjectID "
                        + "WHERE status  = 1 and Q.subjectID = ? "
                        + "ORDER BY NEWID()";
                stm = conn.prepareStatement(sql);
                stm.setString(1, subjectID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int questionID = rs.getInt("questionID");
                    String question_content = rs.getString("question_content");
                    Date createDate = rs.getDate("createDate");
                    List<AnswerDTO> listAnswer = getAnswerContent(questionID);
                    if (listQuestion == null) {
                        listQuestion = new ArrayList<>();
                    }
                    listQuestion.add(new QuestionDTO(questionID, question_content, listAnswer, createDate, subjectID, true));
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
        return listQuestion;
    }

    public List<QuestionDTO> getQuestionBySearchAD(String questionName, Boolean statusQuestion, String subjectQuestion, int pagesize, int pageNumber) throws SQLException {
        List<QuestionDTO> listQuestion = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "SELECT questionID,question_content,createDate,S.subjectID,status "
                        + "FROM tblQuestions Q  join tblSubjects S ON Q.subjectID = S.subjectID  AND (Q.subjectID = ? OR ? is Null) "
                        + "AND (status = ? OR ? is Null) AND question_content like ? "
                        + "ORDER BY createDate DESC "
                        + "OFFSET ? * (? - 1) ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setString(1, subjectQuestion);
                stm.setString(2, subjectQuestion);
                if (statusQuestion != null) {
                    stm.setBoolean(3, statusQuestion);
                    stm.setBoolean(4, statusQuestion);
                } else {
                    stm.setNull(3, java.sql.Types.BOOLEAN);
                    stm.setNull(4, java.sql.Types.BOOLEAN);
                }
                stm.setString(5, "%" + questionName + "%");
                stm.setInt(6, pagesize);
                stm.setInt(7, pageNumber);
                stm.setInt(8, pagesize);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int questionID = rs.getInt("questionID");
                    String question_content = rs.getString("question_content");
                    Date createDate = rs.getDate("createDate");
                    String subjectID = rs.getString("subjectID");
                    boolean status = rs.getBoolean("status");
                    List<AnswerDTO> listAnswer = getAnswerContent(questionID);
                    if (listQuestion == null) {
                        listQuestion = new ArrayList<>();
                    }
                    listQuestion.add(new QuestionDTO(questionID, question_content, listAnswer, createDate, subjectID, status));
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
        return listQuestion;
    }

    public int getNoOfRecordsSearchAdmin(String questionName, Boolean statusQuestion, String subjectQuestion) throws SQLException {
        int result = 0;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "SELECT count(*) as noRecord "
                        + "FROM tblQuestions Q  join tblSubjects S ON Q.subjectID = S.subjectID  AND (Q.subjectID = ? OR ? is Null) "
                        + "AND (status = ? OR ? is Null) AND question_content like ? ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, subjectQuestion);
                stm.setString(2, subjectQuestion);
                if (statusQuestion != null) {
                    stm.setBoolean(3, statusQuestion);
                    stm.setBoolean(4, statusQuestion);
                } else {
                    stm.setNull(3, java.sql.Types.BOOLEAN);
                    stm.setNull(4, java.sql.Types.BOOLEAN);
                }
                stm.setString(5, "%" + questionName + "%");
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

    public QuestionDTO getQuestionByID(int questionID) throws SQLException {
        QuestionDTO question = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {

                String sql = "SELECT questionID,question_content,createDate,S.subjectID ,status "
                        + " FROM tblQuestions Q  join tblSubjects S on Q.subjectID = S.subjectID "
                        + " WHERE questionID=? ";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, questionID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String question_content = rs.getString("question_content");
                    Date createDate = rs.getDate("createDate");
                    String subjectID = rs.getString("subjectID");
                    boolean status = rs.getBoolean("status");
                    List<AnswerDTO> listAnswer = getAnswerContent(questionID);
                    question = new QuestionDTO(questionID, question_content, listAnswer, createDate, subjectID, status);
                }
            }
        } catch (Exception e) {
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

        return question;
    }

    public int createQuestion(QuestionDTO question) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        int result = -1;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                String sql = "INSERT INTO tblQuestions (question_content,subjectID) VALUES(?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, question.getQuestion_content());
                stm.setString(2, question.getSubject());
                result = stm.executeUpdate();
                for (AnswerDTO a : question.getAnswer_content()) {
                    sql = "INSERT INTO tblAnswers(Content,QuestionID,isCorrect) VALUES (?,?,?)";
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, a.getContent());
                    stm.setInt(2, question.getQuestionID());
                    stm.setBoolean(3, a.isIsCorrect());
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

    public int deleteQuestion(int questionID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        int result = -1;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "UPDATE tblQuestions SET status= 0 WHERE questionID=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, questionID);
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

    public int update(QuestionDTO question) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        int result = -1;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                String sql = "UPDATE tblQuestions SET question_content=?,subjectID=?,status=? "
                        + "WHERE questionID=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, question.getQuestion_content());
                stm.setString(2, question.getSubject());
                stm.setBoolean(3, question.isStatus());
                stm.setInt(4, question.getQuestionID());
                result = stm.executeUpdate();
                for (AnswerDTO a : question.getAnswer_content()) {
                    sql = "UPDATE tblAnswers SET Content=?,isCorrect=? WHERE AnswerID=?";
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, a.getContent());
                    stm.setBoolean(2, a.isIsCorrect());
                    stm.setInt(3, a.getAnswerID());
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

    public int getLastQuestionId() throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int id = 0;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "SELECT QuestionID From tblQuestions";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    id = rs.getInt("QuestionID");
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
        return id;
    }

    public List<AnswerDTO> getAnswerContent(int questionID) throws SQLException {
        List<AnswerDTO> listAnswer = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "SELECT AnswerID,Content,isCorrect  "
                        + "FROM tblAnswers  "
                        + "WHERE QuestionID = ? ";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, questionID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int answerID = rs.getInt("AnswerID");
                    String content = rs.getString("content");
                    boolean isCorrect = rs.getBoolean("IsCorrect");
                    if (listAnswer == null) {
                        listAnswer = new ArrayList<>();
                    }
                    listAnswer.add(new AnswerDTO(answerID, content, isCorrect));
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
        return listAnswer;
    }

    public boolean checkAnswerCorrect(int answerID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean isCorrect = false;
        try {
            conn = DBUtils.getConnnection();
            if (conn != null) {
                String sql = "SELECT isCorrect  "
                        + "FROM tblAnswers  "
                        + "WHERE AnswerID = ? ";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, answerID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    isCorrect = rs.getBoolean("IsCorrect");
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
        return isCorrect;
    }

}
