package moodleLogic;

import static java.lang.Boolean.*;
import java.util.HashSet;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jordi
 */
public class Assignment {
    private int id;
    private int value;
    private Set<Submission> Submissions;

    Assignment(int id) {
        this.id = id;
        this.value = 0;
        this.Submissions = new HashSet();
    }
    
    public int getValue() {
        return this.value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public void incValue() {
        this.value+=1;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    Submission getSubmission(int userId) {
        for (Submission s: Submissions) {
            if (s.getId() == userId) return s;
        }
        return null;
    }
    
    public void addSubmission(int userId){
        Submissions.add(new Submission(userId));
    }

    boolean existSubmission(int userId) {
        for (Submission s: Submissions) {
            if (s.getId() == userId) return TRUE;
        }
        return FALSE;
    }

    void viewSubmission(int userId) {
        // DO SOME VIEWING STUFF
    }

    void viewAssignment() {
        // DO SOME VIEWING STUFF
    }
    
}
