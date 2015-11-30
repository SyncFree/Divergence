package moodleLogic;

import static java.lang.Boolean.FALSE;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jordi
 */
public class QuizStudent {
    private String id;
    private Boolean stopped;
    private Boolean open;
    private int attemps;
    private int grade;
    
    QuizStudent(String userId, int attemps) {
        this.id = userId;
        this.stopped = FALSE;
        this.open = FALSE;
        this.attemps = attemps;
        this.grade = -1;
    }
    
    public int getAttemps() {
        return this.attemps;
    }
    
    public void setAttemps(int attemps) {
        this.attemps = attemps;
    }
    
    public Boolean getStopped() {
        return this.stopped;
    }
    
    public void setStopped(Boolean stopped) {
        this.stopped = stopped;
    }
    
    public Boolean getOpen() {
        return this.open;
    }
    
    public void setOpen(Boolean open) {
        this.open = open;
    }
        
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getGrade() {
        return this.grade;
    }
    
    public void setGrade(int grade) {
        this.grade = grade;
    }

    void decAttemps() {
        this.attemps-=1;
    }
}
