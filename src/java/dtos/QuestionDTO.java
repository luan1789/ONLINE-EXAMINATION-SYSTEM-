/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class QuestionDTO implements Serializable {

    private int questionID;
    private String question_content;
    private List<AnswerDTO> answer_content;
    private Date createDate;
    private String subject;
    private boolean status;

    public QuestionDTO() {
    }

    public QuestionDTO(int questionID, String question_content, List<AnswerDTO> answer_content, Date createDate, String subject, boolean status) {
        this.questionID = questionID;
        this.question_content = question_content;
        this.answer_content = answer_content;
        this.createDate = createDate;
        this.subject = subject;
        this.status = status;
    }

   

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public List<AnswerDTO> getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(List<AnswerDTO> answer_content) {
        this.answer_content = answer_content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    

}
