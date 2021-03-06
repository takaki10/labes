<%-- 
    Document   : home
    Created on : Jan 2, 2018, 10:15:23 PM
    Author     : sidious
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="base/header.jsp" %>
        <title>QPA | Sair</title>
    </head>
    <body>
        <%
            String userName = null;
            // Only allow autenticated users
            if (session.getAttribute("user") == null) {
                response.sendRedirect("login.jsp");
            } else {
                userName = (String) session.getAttribute("user");
            }
            String sessionID = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("user")) {
                        userName = c.getValue();
                    }
                }
            }
        %>
        <%@include file="base/navbarlogged.jsp" %>
        <div class="container text-center">
            <div class="panel panel-danger col-md10">
                <div class="panel-heading">
                    Finalizar Sessão
                </div>
                <div class="panel-body">
                    <h3>Deseja realmente sair? Qualquer trabalho não salvo será perdido.</h3>
                    <br>
                    <form action="<%= response.encodeURL("LogoutServlet")%>" method="post">
                        <button type="submit" class="btn btn-lg btn-danger">Sair</button>
                    </form>
                </div>
            </div>
        </div>
        <hr>
        <%@include file="base/footer.jsp" %>
        <%@include file="base/scripts.jsp" %>
    </body>
</html>
