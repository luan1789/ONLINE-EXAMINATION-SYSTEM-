package controller;

import daos.SubjectDAO;
import daos.UserDAO;
import dtos.SubjectDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import utils.EncryptSHA256;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);
    private static final String USER = "home.jsp";
    private static final String ADMIN = "admin.jsp";
    private static final String ERROR = "login.jsp";

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
        String err = "";

        try {

            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");

            if (email.isEmpty() || password.isEmpty()) {
                err = "Email and Password must not empty";
                request.setAttribute("ERROR_LOGIN", err);
            } else {
                UserDAO dao = new UserDAO();
                UserDTO user = dao.checkLogin(email, EncryptSHA256.toHexString(EncryptSHA256.getSHA(password)));
                if (user != null) {
                    HttpSession session = request.getSession();

                    if ("AD".equals(user.getRoleID())) {
                        url = ADMIN;
                    } else {
                        url = USER;
                        SubjectDAO subDao = new SubjectDAO();
                        List<SubjectDTO> listSub = subDao.getSubjects();
                        session.setAttribute("LIST_SUB", listSub);
                    }
                    session.setAttribute("LOGIN_USER", user);
                } else {

                    err = "Email or Password that you have entered is incorrect";

                    request.setAttribute("ERROR_LOGIN", err);
                }
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
