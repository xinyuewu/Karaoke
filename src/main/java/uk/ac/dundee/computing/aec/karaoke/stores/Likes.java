package uk.ac.dundee.computing.aec.karaoke.stores;

import java.util.Set;

public class Likes {

    private int totalLikes;
    private Set<String> likedUsers;
    private String name;
    public Likes() {

    }

    public void setTotalLikes(int likes) {
        this.totalLikes = likes;
    }
    
    public void setName(String name){
        this.name=name;
    }

    public void setLikedUsers(Set<String> users) {
        this.likedUsers = users;
    }

    public int getTotalLikes() {
        return this.totalLikes;
    }

    public Set<String> getLikedUsers() {
        return this.likedUsers;
    }
    
    public String getName(){
        return this.name;
    }
}
