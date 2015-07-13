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
    private int id; // That should the User's id
                    // Nicer ways to do it??

    Subscriber(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
}
