/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moodleLogic;

import static java.lang.Boolean.FALSE;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jordi
 */
public class Courses {
    
    private Set<Course> Courses;
    
    Courses() {
        this.Courses = new HashSet();
    }
    
    
    public Boolean existCourse(int courseId){
        return FALSE;
    }
    
    public Course getCourse(int courseId) {
        for (Course c:Courses) {
            if (c.getId() == courseId) return c;
        }
        
        return null;
    }

    void addCourse(int id, int enrollmentLimit) {
        Courses.add(new Course(id, enrollmentLimit));
    }
    
}
