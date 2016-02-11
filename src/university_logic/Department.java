/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university_logic;

import java.util.LinkedList;

/**
 *
 * @author Eric
 */
public class Department{
    private String ID;
    private String Name;
    private LinkedList<Course> Courses = new LinkedList<Course>();
    
    public Department(String dID, String dName){
        this.ID = dID;
        this.Name = dName;
    }
    
    public String getID(){
        return this.ID;
    }
    
    public Course addCourse(Course course){
        this.Courses.add(course);
        return course;
    }
    
    public LinkedList<Course> getCourses(){
        return this.Courses;
    }
    
    public boolean deleteCourse(String cID){
        for (Course sCourse : this.Courses){
            if (sCourse.getID().equals(cID)){
                this.Courses.remove(sCourse);
                return true;
            }
        }
        return false;
    }
}
