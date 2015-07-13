/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moodleLogic;

import static java.lang.Boolean.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jordi
 */
public class Users {
    private Set<User> Users;
    
    Users (){
        this.Users = new HashSet();
    }
    
    public void addUser(int userId) {
        Users.add(new User(userId));
    }

    public Boolean existUser(int userId) {
        for (User u: Users) {
            if (u.getId() == userId) {
                return TRUE;
            }
        }
        return FALSE;
    }
    
    public User getUser(int userId) {
        for (User u: Users) {
            if (u.getId() == userId) {
                return u;
            }
        }
        return null;
    }

    void viewUser(int viewId) {
//  DO some viewing stuff
    }
}
