/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.QuestionDAO;
import daos.SubjectDAO;
import dtos.ExamDTO;
import dtos.TestBankDTO;
import dtos.QuestionDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
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
@WebServlet(name = "StarExamController", urlPatterns = {"/StarExamController"})
public class StarExamController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(StarExamController.class);
    private static final String SUCCESS = "exam.jsp";
    private static final String ERROR = "home.jsp";

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
            HttpSession session = request.getSession();
            String subjectQuestion = request.getParameter("txtSub");
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            int currentQues = 1;
            if (request.getParameter("currentQues") != null) {
                currentQues = Integer.parseInt(request.getParameter("currentQues"));
            }
            List<QuestionDTO> list;
            QuestionDAO dao = new QuestionDAO();
            SubjectDAO sdao = new SubjectDAO();

            int duration = sdao.getDuration(subjectQuestion);
            int numberQuestion = sdao.getNumberQuestion(subjectQuestion);
            list = dao.getQuestionUS(numberQuestion, subjectQuestion);
            TestBankDTO bank = new TestBankDTO();
            bank.startExam(list);

            ExamDTO exam = new ExamDTO();
            exam.setEmail(user.getEmail());
            Timestamp timeStart = new Timestamp(System.currentTimeMillis());
            exam.setTimeStart(timeStart);
            exam.setSubjectID(subjectQuestion);
            exam.setDuration(duration);
            exam.setNumberQuestion(numberQuestion);
            request.setAttribute("currentQues", currentQues);
            request.setAttribute("QUIZ", bank.getExam().get(currentQues));
            session.setAttribute("BANK", bank);
            session.setAttribute("EXAM", exam);
            session.setAttribute("TIME", duration * 60);
            url = SUCCESS;

        } catch (Exception e) {
            LOGGER.equals(e);
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
