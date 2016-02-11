package gui;

import university_logic.Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MohammadA
 */
public class Main {
    
    public static void main(String[] args){
        Controller controller = new Controller();
        controller.addDepartment("CS", "Computer Science");
        controller.addDepartment("ZO", "Zoolology");
        controller.addDepartment("CH", "Chemistry");
        
        
        
        Application frame_2=new Application(controller);
        frame_2.setSize(400,400);
        frame_2.setVisible(true);
        
//       GridLayoutFrame frame_3=new GridLayoutFrame(20,20);
//        frame_3.setSize(400,400);
//        frame_3.setVisible(true);
    }
    
}
