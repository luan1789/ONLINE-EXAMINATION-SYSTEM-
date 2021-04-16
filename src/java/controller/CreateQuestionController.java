/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.QuestionDAO;
import dtos.AnswerDTO;
import dtos.QuestionDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author PC
 */
@WebServlet(name = "CreateQuestionController", urlPatterns = {"/CreateQuestionController"})
public class CreateQuestionController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateController.class);
    private static final String ERROR = "create_question_page.jsp";
    private static final String SUCCESS = "SearchADController";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            QuestionDAO dao = new QuestionDAO();
            int questionID = dao.getLastQuestionId() + 1;
            String question_content = request.getParameter("txtQuestion_content");

            List<AnswerDTO> listAnswer = new ArrayList<>();
            String answer_correct = request.getParameter("txtAnswer_correct");
            for (int i = 1; i <= 4; i++) {
                String position = "txtAnswer_content" + i;
                String answer_content = request.getParameter(position);
                boolean isCorrect = position.equals(answer_correct);
                listAnswer.add(new AnswerDTO(0, answer_content, isCorrect));
            }
            String sub = request.getParameter("Sub");

            QuestionDTO ques = new QuestionDTO(questionID, question_content, listAnswer, new java.util.Date(), sub, true);
            if (dao.createQuestion(ques) != -1) {
                url = SUCCESS;
            }

        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
