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
public class Section {
    private int id;
    private int value;
    private Set<Url> Urls;
    
    Section(int id){
        this.id = id;
        this.value = 0;
        this.Urls = new HashSet();
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

    public boolean existUrl(int urlId) {
        for (Url u: Urls) {
            if (u.getId() == urlId) return TRUE;
        }
        return FALSE;
    }
    
    public Url getUrl(int urlId) {
        for (Url u: Urls) {
            if (u.getId() == urlId) return u;
        }
        return null;
    }

    void addUrl(int urlId) {
        Urls.add(new Url(urlId));
    }

    void deleteUrl(int urlId) {
        Urls.remove(getUrl(urlId));
    }

    void viewUrl(int urlId) {
        // Do some viewing stuff
    }
    
    
}
