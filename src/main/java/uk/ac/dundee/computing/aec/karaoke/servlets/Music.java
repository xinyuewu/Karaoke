package uk.ac.dundee.computing.aec.karaoke.servlets;

import com.datastax.driver.core.Cluster;
import uk.ac.dundee.computing.aec.karaoke.models.MusicModel;
import uk.ac.dundee.computing.aec.karaoke.stores.Track;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import org.apache.tika.Tika;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import uk.ac.dundee.computing.aec.karaoke.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.karaoke.lib.Convertors;

@WebServlet(name = "Music", urlPatterns = {
    "/Music",
    "/Music/*",
    "/Fetch/*"
})

public class Music extends HttpServlet {

    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();

    public Music() {
        super();
        CommandsMap.put("Music", 1);
        CommandsMap.put("Fetch", 2);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }//end init()

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String args[] = Convertors.SplitRequestPath(request);
        MusicModel mm = new MusicModel();
        mm.setCluster(cluster);
        RequestDispatcher rd;

        int command;
        try {
            command = (Integer) CommandsMap.get(args[1]);
        } catch (Exception et) {
            error("Bad request", response);
            return;
        }
        switch (command) {
            /* /Music */
            case 1:
                if (args.length == 2) {
                    rd = request.getRequestDispatcher("/tracks.jsp");
                    LinkedList<Track> songs = mm.getTrackList();
                    request.setAttribute("tracks", songs);
                    rd.forward(request, response);
                }

                /* /Music/track */
               else if (args.length == 3) {
                    Track t = mm.getTrack(UUID.fromString(args[2]));
                    if (t != null) {
                        rd = request.getRequestDispatcher("../track.jsp");
                        request.setAttribute("track", args[2]);
                        request.setAttribute("type", t.getType());
                        rd.forward(request, response);
                    } else {
                        response.sendRedirect("/GroupProject/");
                    }
                }
                break;
            case 2:
                sendTrackData(args[2], response);
                break;
        }//end switch
    }//end doGet()

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        for (Part part : request.getParts()) {
            String type = part.getContentType();
            System.out.println(type);
            String filename = part.getSubmittedFileName();
            InputStream is = request.getPart(part.getName()).getInputStream();
            Tika tika = new Tika();
            String result = tika.detect(filename);
            int i = is.available();
            byte[] b = new byte[i + 1];
            is.read(b);
            MusicModel mm = new MusicModel();
            mm.setCluster(cluster);
            mm.insertTrack(b, type, filename, "Scott", part.getName());
            is.close();
        }//end for loop
    }//end doPost()

    @Override
    public String getServletInfo() {
        return "Short description";
    }//end getServletInfo()
    
    //Displays a single image
    private void sendTrackData(String Image, HttpServletResponse response) throws ServletException, IOException {
        MusicModel mm = new MusicModel();
        mm.setCluster(cluster);
        Track t = mm.getTrack(java.util.UUID.fromString(Image));
        OutputStream out = response.getOutputStream();
        response.setContentType(t.getType());
        response.setContentLength(t.getLength());
        InputStream is = new ByteArrayInputStream(t.getBytes());
        BufferedInputStream input = new BufferedInputStream(is);
        byte[] buffer = new byte[8192];
        for (int length = 0; (length = input.read(buffer)) > 0;) {
            out.write(buffer, 0, length);
        }//end for loop
        out.close();
    }//end DisplayImage()

    //displays an error message
    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have a an error in your input</h1>");
        out.println("<h2>" + mess + "</h2>");
        out.close();
    }//end error()
}//end class
