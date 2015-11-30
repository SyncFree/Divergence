/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moodleLogic;

/**
 *
 * @author jordi
 */
class Page {
    private String id;
    private int value;

    Page(String id) {
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
    
    public void incValue() {
        this.value+=1;
    }
    
    public int computeValue() {
    	return this.value;
    }
}
