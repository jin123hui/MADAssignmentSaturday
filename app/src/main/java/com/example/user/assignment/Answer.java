package com.example.user.assignment;

/**
 * Created by Jin Hui on 21-Dec-16.
 */

import java.util.Date;

public class Answer {

    private int id;
    private int quesId;
    private String content;
    private int up;
    private int down;
    private Date date;
    private String studId;
    private String studName;

    public Answer() {
        this(0, 0, "", 0, 0, new Date(), "", "");
    }

    public Answer(int id, int quesId, String content, int up, int down, Date date, String studId, String studName) {
        this.id = id;
        this.quesId = quesId;
        this.content = content;
        this.up = up;
        this.down = down;
        this.setDate(date);
        this.studId = studId;
        this.studName = studName;
    }

    public void setId(int id) {
        this.id = id;


    }

    public void setQuesId(int quesId) {
        this.quesId = quesId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public int getId() {
        return id;
    }

    public int getQuesId() {
        return quesId;
    }

    public String getContent() {
        return content;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public String getStudId() {
        return studId;
    }


    public Date getDate() {
        return date;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }
}
