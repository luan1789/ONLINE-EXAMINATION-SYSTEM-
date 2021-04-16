/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.ExamDAO;
import daos.QuestionDAO;
import dtos.ExamDTO;
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
import utils.RandomExam;

/**
 *
 * @author PC
 */
@WebServlet(name = "GetResultController", urlPatterns = {"/GetResultController"})
public class GetResultController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GetResultController.class);
    private static final String ERROR = "exam.jsp";
    private static final String SUCCESS = "result.jsp";

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
            ExamDAO dao = new ExamDAO();
            QuestionDAO quesDao = new QuestionDAO();
            TestBankDTO testBank = (TestBankDTO) session.getAttribute("BANK");
            if (testBank != null) {
                int numberCorrect = 0;
                for (ExamDetailDTO ex : testBank.getExam().values()) {
                    boolean isCorrect = quesDao.checkAnswerCorrect(ex.getAnswerIDUser());
                    if (isCorrect) {
                        ex.setStatus(true);
                        numberCorrect += 1;
                    } else {
                        ex.setStatus(false);
                    }
                }

                ExamDTO exam = (ExamDTO) session.getAttribute("EXAM");
                int examID = RandomExam.randomInt(1, 9999);
                int numberQuestion = exam.getNumberQuestion();
                float point = (float) (Math.round((10 * 1.0 / numberQuestion * numberCorrect) * 100.0) / 100.0);
                exam.setExamID(examID);
                exam.setNumberCorrect(numberCorrect);
                exam.setPoint(point);
                exam.getList().addAll(testBank.getExam().values());

                int rs = dao.saveFullExam(exam);
                if (rs != -1) {
                    url = SUCCESS;
                    request.setAttribute("numberCorrect", numberCorrect);

                }
                request.setAttribute("QUIZ", null);
                session.setAttribute("BANK", null);
                session.setAttribute("TIME", null);
                session.setAttribute("EXAM", exam);
                session.setAttribute("EXAM_DETAIL", exam);

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
