package moodleLogic;

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
public class Directory {
    
    private String id;
    private int value;
    Set<String> Path; 

    Directory(String id) {
        this.id = id;
        this.value = 0;
        this.Path = new HashSet<String>();
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
}
