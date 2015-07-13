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
public class Discussion {
    private int id;
    private int value;
    Set<Post> Posts;

    Discussion(int id) {
        this.id = id;
        this.value = 0;
        this.Posts = new HashSet();
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

    boolean existPost(int posId) {
        for (Post p: Posts) {
            if (p.getId() == posId) return TRUE;
        }
        return FALSE;
    }

    void addPost(int posId, int userId) {
        Posts.add(new Post(posId, userId));
    }

    Post getPost(int posId) {
        for (Post p: Posts) {
            if (p.getId() == posId) return p;
        }
        return null;
    }
}
