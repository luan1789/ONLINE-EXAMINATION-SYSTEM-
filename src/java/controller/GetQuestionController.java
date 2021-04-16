/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dtos.TestBankDTO;
import dtos.ExamDetailDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author PC
 */
@WebServlet(name = "GetQuestionController", urlPatterns = {"/GetQuestionController"})
public class GetQuestionController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GetQuestionController.class);
    private static final String ERROR = "exam.jsp";
    private static final String SUCCESS = "exam.jsp";
    private static final String SUBMIT = "GetResultController";

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
        String url = SUCCESS;
        try {
            HttpSession session = request.getSession();
            TestBankDTO testBank = (TestBankDTO) session.getAttribute("BANK");
            if (testBank != null) {
                int currentQues = 1;
                if (request.getParameter("currentQues") != null) {
                    currentQues = Integer.parseInt(request.getParameter("currentQues"));
                }
                int quesFix = Integer.parseInt(request.getParameter("quesFix"));
                int time = 0;
                if (request.getParameter("time") != null) {
                    time = Integer.parseInt(request.getParameter("time"));
                }
                String quizChoose = request.getParameter("quizChoose");

                if (!"".equals(quizChoose) && quizChoose != null) {
                    int answerIDUser = Integer.parseInt(quizChoose);
                    ExamDetailDTO examDetailDTO = testBank.getExam().get(quesFix);
                    examDetailDTO.setAnswerIDUser(answerIDUser);
                    testBank.updateQuestion(examDetailDTO, quesFix);
                }

                session.setAttribute("BANK", testBank);
                session.setAttribute("TIME", time);
                request.setAttribute("QUIZ", testBank.getExam().get(currentQues));
                request.setAttribute("currentQues", currentQues);
                if ("Submit".equals(request.getParameter("btnAction"))) {
                    url = SUBMIT;
                }
            }
        } catch (Exception e) {
            LOGGER.error("error: ", e);
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
