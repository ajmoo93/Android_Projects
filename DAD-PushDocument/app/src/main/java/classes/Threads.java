package classes;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by EM9310LI on 2017-11-01.
 */

public class Threads {

    public int threadId;
    public String title;
    public String dateCreated;

    public Threads(){
        this.threadId = threadId;
        this.title = title;

        this.dateCreated = dateCreated;
    }
    public int getThreadId(){return threadId;}
    public void setThreadId(int threadId){this.threadId = threadId;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}
    public String getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(String dateCreated){this.dateCreated = dateCreated;}
}
