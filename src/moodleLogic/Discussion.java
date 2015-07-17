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
    private String id;
    private int value;
    Set<Post> Posts;

    Discussion(String id) {
        this.id = id;
        this.value = 0;
        this.Posts = new HashSet<Post>();
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
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    boolean existPost(String posId) {
        for (Post p: Posts) {
            if (p.getId() == posId) return TRUE;
        }
        return FALSE;
    }

    void addPost(String posId, String userId) {
        Posts.add(new Post(posId, userId));
    }

    Post getPost(String posId) {
        for (Post p: Posts) {
            if (p.getId() == posId) return p;
        }
        return null;
    }
}
