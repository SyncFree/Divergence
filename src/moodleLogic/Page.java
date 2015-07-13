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
class Page {
    private int id;
    private int value;

    Page(int id) {
        this.id = id;
        this.value = 0;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
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
}
