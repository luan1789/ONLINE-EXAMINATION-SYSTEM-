/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.QuestionDAO;
import daos.SubjectDAO;
import dtos.QuestionDTO;
import dtos.SubjectDTO;
import java.io.IOException;
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
@WebServlet(name = "SearchADController", urlPatterns = {"/SearchADController"})
public class SearchADController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchADController.class);
    private static final String SUCCESS = "ad_all_question.jsp";

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

            String questionName = request.getParameter("txtSearch");
            String subjectQuestion = request.getParameter("txtSub");
            String statusString = request.getParameter("txtStatus");
            Boolean statusQuestion = null;

            int pageNum = 1;
            int pageSize = 4;
            if (request.getParameter("page") != null) {
                pageNum = Integer.parseInt(request.getParameter("page"));
            }
            int noOfPages;
            List<QuestionDTO> list;
            QuestionDAO dao = new QuestionDAO();
            SubjectDAO subDao = new SubjectDAO();

            List<SubjectDTO> listSub = subDao.getSubjects();

            if ("All".equals(subjectQuestion) || "".equals(subjectQuestion)) {
                subjectQuestion = null;
            }

            if (!"All".equals(statusString) && statusString != null && !("".equals(statusString))) {
                statusQuestion = "Active".equals(statusString);
            }
            questionName = questionName == null ? "" : questionName;

            noOfPages = (int) Math.ceil(dao.getNoOfRecordsSearchAdmin(questionName, statusQuestion, subjectQuestion) * 1.0 / pageSize);
            list = dao.getQuestionBySearchAD(questionName, statusQuestion, subjectQuestion, pageSize, pageNum);

            HttpSession session = request.getSession();
            session.setAttribute("LIST_SUB", listSub);
            session.setAttribute("LIST_QUESTIONS", list);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", pageNum);

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
