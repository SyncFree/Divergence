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
    private int id;  // This Id should be the same than the User.id or the Post.owner id
    private String role;
    private String oldRole;

    Member(int id, String role) {
        this.id = id;
        this.role = role;
        this.oldRole = role;
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
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
}
