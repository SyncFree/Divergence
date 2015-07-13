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
public class Module {
    private int id;
    private int value;
    Set<Section> Sections;

    Module(int id) {
        this.id = id;
        this.value = 0;
        this.Sections = new HashSet();
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    void incValue() {
        this.value+=1;
    }

    boolean existSection(int secId) {
        for (Section s: Sections) {
            if (s.getId() == secId) {
                return TRUE;
            }           
        }
        return FALSE;
    }
    
    void addSection (int secId) {
        Sections.add(new Section(secId));
    }

    public Section getSection(int secId) {
        for (Section s: Sections) {
            if (s.getId() == secId) {
                return s;
            }          
        }
        return null;
    }

    public int getValue() {
        return this.value;
    }

    protected void viewSection(int secId) {
        // do viewy stuff
    }

    protected void deleteSection(int secId) {
        Sections.remove(getSection(secId));
    }
    
}
