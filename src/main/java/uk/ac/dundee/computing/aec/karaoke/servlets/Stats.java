package uk.ac.dundee.computing.aec.karaoke.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.aec.karaoke.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.karaoke.models.MusicModel;
import uk.ac.dundee.computing.aec.karaoke.stores.LoggedIn;
import uk.ac.dundee.computing.aec.karaoke.stores.Track;

@WebServlet(name = "Stats", urlPatterns = {"/Stats"})
public class Stats extends HttpServlet {

    private Cluster cluster = null;
    private MusicModel mm;
    private String[] args;
    private RequestDispatcher rd;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
        mm = new MusicModel();
    }//end init()

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LoggedIn li = (LoggedIn) request.getSession().getAttribute("LoggedIn");
        if (li == null) {
            response.sendRedirect("/Karaoke/login.jsp");
        } else {
            mm.setCluster(cluster);
            LinkedList<Track> topTracks = mm.getTopTracks();
            if (topTracks != null) {
                rd = request.getRequestDispatcher("timeseries.jsp");
                request.setAttribute("tracks", topTracks);
                rd.forward(request, response);
            } else {
                rd = request.getRequestDispatcher("/Upload");
                rd.forward(request, response);
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
