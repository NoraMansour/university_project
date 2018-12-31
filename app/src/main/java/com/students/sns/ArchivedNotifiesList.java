package com.students.sns;

/**
 * Created by meem on 9/22/2017.
 */

public class ArchivedNotifiesList {
    String title,text,collage,section,level,date;

    public ArchivedNotifiesList(String title, String text, String collage, String section, String level, String date) {
        this.title = title;
        this.text = text;
        this.collage = collage;
        this.section = section;
        this.level = level;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getCollage() {
        return collage;
    }

    public String getSection() {
        return section;
    }

    public String getLevel() {
        return level;
    }

    public String getDate() {
        return date;
    }
}
