package moodleLogic;

import static java.lang.Boolean.*;
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
public class Forum {
    private int id;
    private int value;
    Set<Discussion> Discussions;
    Set<Subscriber> Subscribers;

    Forum(int id) {
        this.id = id;
        this.value = 0;
        this.Discussions = new HashSet();
        this.Subscribers = new HashSet();
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

    public void addDiscussion(int disId) {
        Discussions.add(new Discussion(disId));
    }

    public boolean existDiscussion(int disId) {
        for (Discussion d: Discussions) {
            if (d.getId() == disId) return TRUE;
        }
        
        return FALSE;
    }
    
    public Discussion getDiscussion (int disId) {
        for (Discussion d: Discussions) {
            if (d.getId() == disId) return d;
        }
        
        return null;
    }

    void addSubscriber(int userId) {
        Subscribers.add(new Subscriber(userId));
    }

    public boolean existSubscriber(int userId) {
        for (Subscriber s: Subscribers) {
            if (s.getId() == userId) return TRUE;
        }
        return FALSE;
    }
    
    public Subscriber getSubscriber(int userId) {
        for (Subscriber s: Subscribers) {
            if (s.getId() == userId) return s;
        }
        return null;
    }

    void deleteSubscriber(int userId) {
        Subscribers.remove(getSubscriber(userId));
    }
}
