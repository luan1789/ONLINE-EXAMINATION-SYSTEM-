/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class ExamDTO {

    private int examID;
    private String email;
    private String subjectID;
    private Timestamp timeStart;
    private Timestamp timeEnd;
    private float point;
    private int numberCorrect;
    private int numberQuestion;
    private int duration;
    private List<ExamDetailDTO> list;

    public ExamDTO() {
        list = new ArrayList<>();
    }

    public ExamDTO(int examID, String email, String subjectID, Timestamp timeStart, Timestamp timeEnd, float point, int numberCorrect) {
        this.examID = examID;
        this.email = email;
        this.subjectID = subjectID;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.point = point;
        this.numberCorrect = numberCorrect;
        list = new ArrayList<>();
    }

    public int getNumberQuestion() {
        return numberQuestion;
    }

    public void setNumberQuestion(int numberQuestion) {
        this.numberQuestion = numberQuestion;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

   
    public int getNumberCorrect() {
        return numberCorrect;
    }

    public void setNumberCorrect(int numberCorrect) {
        this.numberCorrect = numberCorrect;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public Timestamp getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Timestamp timeStart) {
        this.timeStart = timeStart;
    }

    public Timestamp getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Timestamp timeEnd) {
        this.timeEnd = timeEnd;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public List<ExamDetailDTO> getList() {
        return list;
    }

    public void setList(List<ExamDetailDTO> list) {
        this.list = list;
    }

}
