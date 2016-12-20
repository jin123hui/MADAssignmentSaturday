package com.example.user.assignment;

/**
 * Created by Jin Hui on 21-Dec-16.
 */

public class Favourite {

    public String studId;
    public int questionId;

    public Favourite() {
    }


    public Favourite(String studId, int questionId) {
        this.studId = studId;
        this.questionId = questionId;

    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;

    }

    public String getStudId() {
        return studId;

    }

    public int getQuestionId() {
        return questionId;
    }

}
