package com.example.em9310li.dad_pushdocument;

import java.util.Date;

/**
 * Created by EM9310LI on 2017-11-01.
 */

public class Post {

    public int postId;
    public String postText;
    public String datePosted;
    public int likes;

    public Post(int postId, String postText, String datePosted, int likes){
        this.postId = postId;
        this.postText = postText;
        this.datePosted = datePosted;
        this.likes = likes;
    }
    public int getPostId(){return postId;}
    public void setPostId(int postId){this.postId = postId;}
    public String getPostText(){return postText;}
    public void setPostText(String postText){this.postText = postText;}
    public String getDatePosted(){return datePosted;}
    public void setDatePosted(String datePosted){this.datePosted = datePosted;}
    public int getLikes(){return likes;}
    public void setLikes(int likes){this.likes = likes;}

}
