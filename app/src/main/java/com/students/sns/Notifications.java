package com.students.sns;

/**
 * Created by meem on 9/13/2017.
 */

public class Notifications {
    public String n_title,n_text,n_sender,n_date;

    public Notifications(String n_title, String n_text, String n_sender, String n_date) {
        this.n_title = n_title;
        this.n_text = n_text;
        this.n_sender = n_sender;
        this.n_date = n_date;
    }

    public String getN_title() {
        return n_title;
    }

    public String getN_text() {
        return n_text;
    }

    public String getN_sender() {
        return n_sender;
    }

    public String getN_date() {
        return n_date;
    }
}
