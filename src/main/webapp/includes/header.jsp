<%@page import="uk.ac.dundee.computing.aec.karaoke.stores.LoggedIn"%>
<%
    LoggedIn li = (LoggedIn) session.getAttribute("LoggedIn");
    String username = "";
    if(li!=null)
         username = li.getUsername();
%>
<header class="header black-bg">
    <a href="#" class="logo" style="color :#000;" ><b>Cassandrify</b></a>
    <div class="nav notify-row" id="top_menu">
        <ul class="nav top-menu">
            <li class="dropdown">
                <a href="/Karaoke/Music">Home</a>
            </li>
            <li class="dropdown">
                <a href="/Karaoke/User/<%=username%>">Your Profile</a>
            </li>
            <li class="dropdown">
                <a href="/Karaoke/Upload">Upload Track</a>
            </li>
            <li class="dropdown">
                <a href="/Karaoke/Stats">Statistics</a>
            </li>
    </div>
    <div class="top-menu">
        <ul class="nav pull-right top-menu">
            <li id="user">Hello <%=username%></li>
            <li><a class="logout" href="/Karaoke/logout.jsp">Logout</a></li>
        </ul>
    </div>
</header>