package classes;

/**
 * Created by EM9310LI on 2017-09-25.
 */

public class User {

    public int userID;
    public String userName;
    public String email;
    public String password;

    public int getUserID(){return userID;}
    public void setUserID(int userID){this.userID = userID;}
    public String getUserName(){return userName;}
    public void setUserName(String userName){this.userName = userName;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}
}
