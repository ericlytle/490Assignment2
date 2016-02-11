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
public class Controller {
    private LinkedList<Department> departments=new LinkedList<Department>();
    
    public Course addCourse(String cID, String cTitle, int cCredits, String dept){
        Course result = new Course(cID, cTitle, cCredits);//, cDepartment);
        for (Department de : this.departments){
            if (dept == de.getID()){
                de.addCourse(result);
            }
        }
        return result;
    }
    
    public boolean deleteCourse(String cID, String dept){
        for (Department de : this.departments){
            if (dept == de.getID()){
                return de.deleteCourse(cID);
            }
        }
        return false;
    }
    
    public void editCourse(String cID, String dept, String cTitle, int cCredits){
        deleteCourse(cID, dept);
        addCourse(cID, cTitle, cCredits, dept);
    }
    
    public Department addDepartment(String dID, String dName){
        Department result = new Department(dID, dName);
        this.departments.add(new Department(dID, dName));
        return result;
    }
    
    public LinkedList<Department> getDepartments(){
        return this.departments;
    }
    
    public boolean checkCourseAvailable(String cID, String Dept){
        String ID;
        for (Department d: this.departments){
            if (d.getID() == Dept){
                for (Course c: d.getCourses()){
                    ID = c.getID();
                    if (ID.equals(cID)){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
