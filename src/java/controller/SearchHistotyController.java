/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.ExamDAO;
import dtos.ExamDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "SearchHistotyController", urlPatterns = {"/SearchHistotyController"})
public class SearchHistotyController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchHistotyController.class);
    private static final String SUCCESS = "history.jsp";
    private static final String ERROR = "history.jsp";

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
            String txtDate = request.getParameter("txtDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateSearch = null;
            if (!"".equals(txtDate) && txtDate != null) {
                dateSearch = sdf.parse(txtDate);
            }

            String subjectID = request.getParameter("txtSub");

            subjectID = subjectID == null ? "" : subjectID;
            int pageNum = 1;
            int pageSize = 5;
            if (request.getParameter("page") != null) {
                pageNum = Integer.parseInt(request.getParameter("page"));
            }
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            ExamDAO odao = new ExamDAO();
            int noOfPages = (int) Math.ceil(odao.getRecordSearchHistory(user.getEmail(),
                    subjectID, dateSearch, pageSize, pageNum) * 1.0 / pageSize);

            List<ExamDTO> list = odao.getExamBySearch(user.getEmail(),
                    subjectID, dateSearch, pageSize, pageNum);
            session.setAttribute("HISTORY", list);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", pageNum);
            url = SUCCESS;

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
