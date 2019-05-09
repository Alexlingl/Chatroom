package nettyserverv1;

import java.io.Serializable;

/**
 * Created by linguolong on 2019/05/08.
 * Chatroom User Infomation
 */

public class UserInfo implements Serializable{
    private String userID;
    private String userName;
    private String password;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Override
    public String toString() {
        return "SubscribeReq: [messageID]:"+ userID + " [userName]:" +userName
                + " [password]:" +password;
    }

}