package moodleLogic;

import static java.lang.Boolean.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jordi
 */
public class Post {
    private String id;
    private String owner;
    // Shouldnt we say who reported who??
    private int value;
    private Boolean reported;

    Post(String id, String userId) {
        this.id = id;
        this.owner = userId;
        this.value = 0;
        this.reported = FALSE;
    }
    
    public Boolean getReported() {
        return this.reported;
    }
    
    public void setReported(Boolean reported) {
        this.reported = reported;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getOwner() {
        return this.owner;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
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
