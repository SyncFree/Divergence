/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moodleLogic;

import static java.lang.Boolean.*;

/**
 *
 * @author jordi
 */
class Submission {
    private String id;
    private int grade;
    private int value;
    
    Submission(String student){
        this.value = 0;
        this.id = student;
        this.grade = -1;
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
        
    public int getGrade() {
        return this.grade;    
    }
    
    public void setGrade(int grade) {
        this.grade = grade;
    }   
}
