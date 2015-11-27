package uk.ac.dundee.computing.aec.karaoke.stores;

public class LoggedIn {
    boolean loggedIn=false;
    String Username=null;
    
    //constructor
    public void LoggedIn(){
    }//end constructor
    
    public void setUsername(String name){
        this.Username=name;
    }//end setUsername()
    
    public String getUsername(){
        return Username;
    }//end getUsername()
    
    public void setLoggedIn(){
        loggedIn=true;
    }//end setLoggedIn()
    
    public void setLoggedOut(){
        loggedIn=false;
    }//end setLoggedOut()
    
    public void setLoginState(boolean mLoggedIn){
        this.loggedIn=mLoggedIn;
    }//end setLoginState()
    
    public boolean getLoggedIn(){
        return loggedIn;
    }//end getLoggedIn()
}//end class
