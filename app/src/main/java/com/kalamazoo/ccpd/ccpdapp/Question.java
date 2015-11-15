package com.kalamazoo.ccpd.ccpdapp;

/**
 * A Question object has an ID number, a question, and an answer to the question.
 * Created by Marc Kuniansky on 11/3/2015.
 */
public class Question
{
    //Instance variables
    private int id;
    private String question;
    private String answer;

    //Constructors

    /**
     * Constructor with three variables. An ID number must be present for each question. Use this constructor
     * when the answer to a question is already known.
     * @param idnum a unique int
     * @param q a string- the question
     * @param a a string- the answer to the question
     */
    public Question(int idnum, String q, String a)
    {
        id = idnum;
        question = q;
        answer = a;
    }

    /**
     * Constructor with two variables. Use this constructor when the answer to a question is not
     * yet known.
     * @param idnum a unique int
     * @param q a string- the question
     */
    public Question(int idnum, String q)
    {
        id=idnum;
        question=q;
    }
    //Setters

    /**
     * Changes the ID number of a Question object
     * @param idnum a unique int
     */
    public void setId(int idnum)
    {
        id = idnum;
    }

    /**
     * Changes the question of a Question object
     * @param q the new question
     */
    public void setQuestion(String q)
    {
        question = q;
    }

    /**
     * Changes the answer of a Question object
     * @param a the new answer
     */
    public void setAnswer(String a)
    {
        answer = a;
    }

    //Getters

    /**
     * Gives the ID number of a Question object
     * @return the ID as an int
     */
    public int getId()
    {
        return id;
    }

    /**
     * Gives the question of a Question object
     * @return the question as a string
     */
    public String getQuestion()
    {
        return question;
    }

    /**
     * ggives the answer of a Question object
     * @return the answer as a astring
     */
    public String getAnswer()
    {
        return answer;
    }
}
