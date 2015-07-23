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
    private String id;
    private int value;
    Set<Section> Sections;

    Module(String id) {
        this.id = id;
        this.value = 0;
        this.Sections = new HashSet<Section>();
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    void incValue() {
        this.value+=1;
    }

    boolean existSection(String secId) {
        for (Section s: Sections) {
            if (s.getId() == secId) {
                return TRUE;
            }           
        }
        return FALSE;
    }
    
    void addSection (String secId) {
        Sections.add(new Section(secId));
    }

    public Section getSection(String secId) {
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

    protected void viewSection(String secId) {
        // do viewy stuff
    }

    protected void deleteSection(String secId) {
        Sections.remove(getSection(secId));
    }
    
}
