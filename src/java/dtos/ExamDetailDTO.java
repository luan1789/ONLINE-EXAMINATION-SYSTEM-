/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.List;

/**
 *
 * @author PC
 */
public class ExamDetailDTO {

    private int questionID;
    private String question_content;
    private List<AnswerDTO> answer_content;
    private int answerIDUser;
    private boolean status;

    public ExamDetailDTO() {
    }

    public ExamDetailDTO(int questionID, String question_content, List<AnswerDTO> answer_content, int answerIDUser, boolean status) {
        this.questionID = questionID;
        this.question_content = question_content;
        this.answer_content = answer_content;
        this.answerIDUser = answerIDUser;
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

    public int getAnswerIDUser() {
        return answerIDUser;
    }

    public void setAnswerIDUser(int answerIDUser) {
        this.answerIDUser = answerIDUser;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
