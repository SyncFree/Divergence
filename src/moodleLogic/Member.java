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
public class Member {
    private String id;  // This Id should be the same than the User.id or the Post.owner id
    private String role;
    private String oldRole;
    private int value;

    Member(String id, String role) {
        this.id = id;
        this.role = role;
        this.oldRole = role;
        this.value = 0;
    }
    
    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getOldRole() {
        return this.oldRole;
    }
    
    public void setOldRole(String oldRole) {
        this.oldRole = oldRole;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getValue () {
    	return this.value;
    }
    
    public void setValue (int value){
    	this.value = value;
    }
    
    public void incValue (){
    	this.value+=1;
    }
    
    public int computeValue () {
    	return this.value;
    }
}
