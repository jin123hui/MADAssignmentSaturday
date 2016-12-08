package com.example.user.assignment;

import java.util.Date;

/**
 * Created by Jin Hui on 04-Dec-16.
 */

public class ListViewItemPost {
    private String category;
    private String questionSubject;
    private int noOfAns;
    private Date datePosted;

    public ListViewItemPost(String category, String questionSubject, int noOfAns, Date datePosted) {
        this.category = category;
        this.questionSubject = questionSubject;
        this.noOfAns = noOfAns;
        this.datePosted = datePosted;
    }

    public ListViewItemPost(String category, String questionSubject) {
        this.category = category;
        this.questionSubject = questionSubject;

    }

    public String getCategory() {
        return category;
    }

    public String getQuestionSubject() {
        return questionSubject;
    }

    public int getNoOfAns() {
        return noOfAns;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuestionSubject(String questionSubject) {
        this.questionSubject = questionSubject;
    }

    public void setNoOfAns(int noOfAns) {
        this.noOfAns = noOfAns;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }
}
