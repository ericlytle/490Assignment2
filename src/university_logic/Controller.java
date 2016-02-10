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
    
    public Department addDepartment(String dID, String dName){
        Department result = new Department(dID, dName);
        this.departments.add(new Department(dID, dName));
        return result;
    }
    
    public LinkedList<Department> getDepartments(){
        return this.departments;
    }
}
