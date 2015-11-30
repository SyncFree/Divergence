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
public class Subscriber {
    private String id; // That should the User's id
                    // Nicer ways to do it??

    Subscriber(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}
