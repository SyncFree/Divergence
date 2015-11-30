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
public class Quiz {
    private String id;
    private int value;
    private int maxAttemps;
    private Set<QuizStudent> Students;
    
    Quiz(String id, int maxAttemps, Set<Member> Members) {
        this.id = id;
        this.value = 0;
        this.maxAttemps = maxAttemps;
        this.Students = new HashSet<QuizStudent>();
        for (Member m: Members){
            if (m.getRole().equals("student")) {
                Students.add(new QuizStudent(m.getId(), maxAttemps));
            }
        }
        
        
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
    
    public String getId() {
        return this.id;    
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public QuizStudent getQuizStudent(String userId) {
        for (QuizStudent qs: Students) {
            if (qs.getId() == userId) return qs;
        }
        
        return null;
    }
    
    public Boolean existQuizStudent(String userId) {
        for (QuizStudent qs: Students) {
            if (qs.getId() == userId) return TRUE;
        }
        return FALSE;
    }
    public int computeValue() {
    	return this.value;
    }

}
