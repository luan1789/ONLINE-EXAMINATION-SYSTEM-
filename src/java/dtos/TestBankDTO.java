/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
public class TestBankDTO {

    private String userName;
    private Map<Integer, ExamDetailDTO> exam;

    public TestBankDTO() {
    }

    public TestBankDTO(String userName, Map<Integer, ExamDetailDTO> exam) {
        this.userName = userName;
        this.exam = exam;
    }

    public void addQuestion(ExamDetailDTO examDetail, int index) {
        if (exam == null) {
            exam = new HashMap<Integer, ExamDetailDTO>();
        }
        if (!exam.containsKey(index)) {
            exam.put(index, examDetail);
        }
    }

    public void updateQuestion(ExamDetailDTO dto, int index) {
        if (exam != null) {
            if (exam.containsKey(index)) {
                exam.replace(index, dto);
            }
        }
    }

    public void startExam(List<QuestionDTO> list) {
        int i = 1;
        for (QuestionDTO q : list) {
            ExamDetailDTO examDetailDTO = new ExamDetailDTO(q.getQuestionID(), q.getQuestion_content(), q.getAnswer_content(), 0,false);
            addQuestion(examDetailDTO, i);
            i++;
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<Integer, ExamDetailDTO> getExam() {
        return exam;
    }

    public void setExam(Map<Integer, ExamDetailDTO> exam) {
        this.exam = exam;
    }

}
