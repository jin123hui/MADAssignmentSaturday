package com.example.user.assignment;

import java.util.Date;

/**
 * Created by Jin Hui on 21-Dec-16.
 */

public class Question {

    private int id;
    private String subject;
    private String content;
    private String category;
    private String postedTime;
    private String studId;
    private int numAns;

    public Question() {
        this(1, "", "", "", new Date().toString(), "", 1);
    }

    public Question(int id, String subject, String content,
                    String category, String postedTime, String studId, int numAns) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.category = category;
        this.postedTime = postedTime;
        this.studId = studId;
        this.setNumAns(numAns);
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public String getStudId() {
        return studId;
    }

    public int getNumAns() {
        return numAns;
    }

    public void setNumAns(int numAns) {
        this.numAns = numAns;
    }
}

