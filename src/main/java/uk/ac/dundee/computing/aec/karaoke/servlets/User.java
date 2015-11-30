package uk.ac.dundee.computing.aec.karaoke.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.karaoke.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.karaoke.lib.Convertors;
import uk.ac.dundee.computing.aec.karaoke.models.UserModel;
import uk.ac.dundee.computing.aec.karaoke.stores.LoggedIn;
import uk.ac.dundee.computing.aec.karaoke.stores.Person;

@WebServlet(name = "Register", urlPatterns = {"/Register", "/Login", "/User/*"})
public class User extends HttpServlet {

    private Cluster cluster = null;
    private UserModel us;
    private final HashMap URLmap = new HashMap();
    private String[] args;
    private RequestDispatcher rd;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
        us = new UserModel();
        URLmap.put("Register", 1);
        URLmap.put("Login", 2);
        URLmap.put("User", 3);
    }//end init();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        args = Convertors.SplitRequestPath(request);
        us.setCluster(cluster);
        int command;
        try {
            command = (Integer) URLmap.get(args[1]);
        }//end try
        catch (Exception e) {
            error("Bad request", response);
            return;
        }//end catch

        //sanitize inputs
        String firstname = sanitise(request.getParameter("firstname"));
        String lastname = sanitise(request.getParameter("lastname"));
        String username = sanitise(request.getParameter("username"));
        String password = sanitise(request.getParameter("password"));
        String cpassword = sanitise(request.getParameter("cpassword"));
        String email = sanitise(request.getParameter("email"));
        String age = sanitise(request.getParameter("age"));
        String street = sanitise(request.getParameter("street"));
        String city = sanitise(request.getParameter("city"));
        String zip = sanitise(request.getParameter("zip"));

        switch (command) {
            /* /Register */
            case 1:
                if (firstname == null || lastname == null || username == null || password == null
                        || cpassword == null || email == null || age == null || street == null || city == null || zip == null) {
                    rd.forward(request, response);
                }
                try {
                    Integer.parseInt(age);
                } catch (NumberFormatException e) {
                    rd = request.getRequestDispatcher("/register.jsp");
                    HttpSession session = request.getSession();
                    session.setAttribute("msg", "Invalid age");
                    rd.forward(request, response);
                }
                if (!password.equals(cpassword) || password.isEmpty()) {
                    rd = request.getRequestDispatcher("/register.jsp");
                    HttpSession session = request.getSession();
                    session.setAttribute("msg", "Check your passwords");
                    rd.forward(request, response);

                } else if (us.userExist(username)) {
                    rd = request.getRequestDispatcher("/register.jsp");
                    HttpSession session = request.getSession();
                    session.setAttribute("msg", "Username taken");
                    rd.forward(request, response);
                } else {
                    us.RegisterUser(username, password, firstname, lastname, email, age, street, city, zip);
                    if (setLoggedInUser(username, password, request)) {
                        response.sendRedirect("/Karaoke/Music");
                    } else {
                        response.sendRedirect("register.jsp");
                    }
                }
                break;
            /* /Login */
            case 2:
                String uname = org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(request.getParameter("username"));
                String pword = org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(request.getParameter("password"));
                if (uname == null || pword == null) {
                    response.sendRedirect("login.jsp");
                } else {
                    if (setLoggedInUser(uname, pword, request)) {
                        response.sendRedirect("/Karaoke/Music");
                    } else {
                        response.sendRedirect("login.jsp");
                    }
                }
                break;
            /* /User */
            case 3:
                if (firstname == null || lastname == null || email == null || username == null
                        || street == null || zip == null || city == null || age == null) {
                    response.sendRedirect("/Karaoke/Music");
                } else {
                    us.updateUser(username, firstname, lastname, email, street, city, zip);
                    response.sendRedirect("/Karaoke/User/" + username);
                }
        }//end switch
    }//end doPost()

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LoggedIn li = (LoggedIn) request.getSession().getAttribute("LoggedIn");
        if (li == null) {
            response.sendRedirect("/Karaoke/login.jsp");
        } else {

            args = Convertors.SplitRequestPath(request);
            us.setCluster(cluster);
            int command;
            try {
                command = (Integer) URLmap.get(args[1]);
            }//end try
            catch (Exception e) {
                error("Bad request", response);
                return;
            }//end catch

            switch (command) {
                case 3:
                    if (args.length != 3) {
                        break;
                    } else {
                        String username = args[2];
                        if (us.userExist(username)) {
                            Person p = us.getUser(username);
                            rd = request.getRequestDispatcher("/profile.jsp");
                            request.setAttribute("person", p);
                            rd.forward(request, response);
                        } else {
                            response.sendRedirect("/Karaoke/Music");
                        }
                        break;
                    }
            }//end switch
        }
    }//end doGet

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    //displays an error message
    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have a an error in your input</h1>");
        out.println("<h2>" + mess + "</h2>");
        out.close();
    }//end error()

    private boolean setLoggedInUser(String username, String password, HttpServletRequest request) {
        boolean validUser = us.IsValidUser(username, password);
        HttpSession session = request.getSession();
        if (validUser) {
            LoggedIn li = new LoggedIn();
            li.setLoggedIn();
            li.setUsername(username);
            session.setAttribute("LoggedIn", li);
            return true;
        } else {
            return false;
        }
    }//end setLoggedInUser

    private String sanitise(String input) {
        return org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(input);
    }
}//end class
