/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moodleLogic;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 *
 * @author jordi
 */
class User {
    private int id;
    private int password;
    private int value;
    
    User(int id) {
        this.id = id;
        this.password = 0;
        this.value = 0;
    }
    
    User(int id, int password) {
        this.id = id;
        this.password = password;
        this.value = 0;
    }
    
    public int getPassword() {
        return this.password;
    }
    
    public void setPassword(int password) {
        this.password = password;
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
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int UserChangePasswordOperation(int oldPwd, int newPwd) {
            if (getPassword() == oldPwd) {
                setPassword(newPwd);
            } else {
                return 0;
            }
        return 1;
    }
    public int UserUpdateOperation() {
        incValue();
        return 1;
    }
    public int UserViewOperation(Users us, int viewId) {
        if (us.existUser(viewId)){
            viewUser(viewId);
        } else {
            return 0;
        }
        return 1;
    }

    protected void viewUser(int viewId) {
//  Do viewy stuff.
    }

    
}
