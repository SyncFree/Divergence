package moodleLogic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jordi
 */
public class Blog {
    private String id;
    private int value;

    Blog(String id) {
        this.id = id;
        this.value = 0;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }

    void incValue() {
        this.value+=1;
    }
    public int computeValue() {
    	return this.value;
    }
    
}
