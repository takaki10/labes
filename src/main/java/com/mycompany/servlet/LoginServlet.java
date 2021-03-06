/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlet;

import com.mycompany.controller.ControllerUsuario;
import com.mycompany.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sidious
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

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

        
          
            // Cria uma sessão de usuário
            HttpSession session = request.getSession(false);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            Usuario user = login(request, response);
            //Usuario user = null;  (mock)
            if (user != null) {
            
                session.setAttribute("user", user.getNomeUsuario());
                session.setAttribute("userObj", user);
                LOGGER.log(Level.WARNING, session.getAttribute("userObj").toString());
                session.setMaxInactiveInterval(30 * 60); // define o tempo de inatividade
                
                // cria um cookie para o usuário
                Cookie userName = new Cookie("user", user.getNomeUsuario());
                response.addCookie(userName);
                String encodeURL = response.encodeRedirectURL("home.jsp");
                response.sendRedirect(encodeURL);
            } else {

                session.setAttribute("loginDanger", "E-mail ou senha incorretos!");
                String encodeURL = response.encodeRedirectURL("login.jsp");
                response.sendRedirect(encodeURL);
            	
            }
            
//
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("login.jsp");
//                out.println("<h2>Email ou senha inválidos!</h2>");
//                rd.include(request, response);
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
        return "Servlet Responsavel pelo login";
    }// </editor-fold>

    public Usuario login(HttpServletRequest request, HttpServletResponse response) {
        Usuario user = null;
        try {
            String email, senha;
            email = request.getParameter("email");
            senha = request.getParameter("senha");

            user = ControllerUsuario.login(email, senha);
            //LOGGER.log(Level.INFO, String.valueOf(isLogged));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERRO: [{0}]", e.getMessage());
            throw e;
        }
        return user;
    }

}