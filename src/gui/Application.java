package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import university_logic.Controller;
import university_logic.Course;
import university_logic.Department;

public class Application extends JFrame {
    
    private static final String EMPTY_STRING = "";
    private static final String ERROR_MSG = "Error";
    private static final String SUCCESS_MSG = "Success!";
    private static final String APPLICATION_TITLE = "University System";
    private static final String COURSE_CODE_LABEL = "Course Code:";
    private static final String COURSE_NAME_LABEL = "Course Name:";
    private static final String COURSE_CREDITS_LABEL = "No. Of Credits:";
    private static final String DEPARTMENT_LABEL = "Department:";
    private static final String ADD_COURSE_BUTTON_LABEL = "Add Course";
    private static final String DELETE_COURSE_BUTTON_LABEL = "Delete Course";
    private static final String DISPLAY_ALL_BUTTON_LABEL = "Display All";
    private static final String DISPLAY_DEPT_BUTTON_LABEL = "Display(dept)";
    
    private final Controller controller;
    
    public Application(Controller cont){
        super(APPLICATION_TITLE); //set title
        this.controller = cont;
        initComponents();
        @SuppressWarnings("OverridableMethodCallInConstructor")
        Container pane=this.getContentPane();
        pane.add(top(), BorderLayout.NORTH);
        pane.add(bottom(), BorderLayout.CENTER);
    }
    
    private JPanel top(){
        //Configure the top panel of the frame
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(1,2));
        top.add(upperLeft());
        top.add(new JScrollPane(courseList));
        return top;
    }
    
    private JPanel bottom(){
        //Initialize final display panel
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1,2));
        
        //Configure the bottom right side display
        JPanel rightSide = new JPanel();
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        rightSide.add(dispAll);
        rightSide.add(dispDept);
        rightPanel.add(rightSide);
        
        //Configure the bottom left side display
        JPanel leftSide = new JPanel();
        JPanel leftPanel = new JPanel();
        rightSide.setLayout(new GridLayout(2,1));
        leftSide.setLayout(new GridLayout(2,1));
        //leftSide.add(Box.createHorizontalStrut(15));
        leftSide.add(deleteCourse);
        leftSide.add(addCourse);
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(leftSide);
        
        //Configure the final panel
        bottom.add(leftPanel);
        bottom.add(rightPanel);
        return bottom;
    }
    

    private JPanel upperLeft(){
    //Configure upper left display
    JPanel displayPanel = new JPanel();
    displayPanel.setLayout(new GridLayout(4, 1, 20, 20)); //4 rows, 1 column
    displayPanel.add(new JLabel(COURSE_CODE_LABEL));
    displayPanel.add(courseCodeTextField);
    displayPanel.add(new JLabel(COURSE_NAME_LABEL));
    displayPanel.add(courseNameTextField);
    displayPanel.add(new JLabel(COURSE_CREDITS_LABEL));
    displayPanel.add(creditNumberTextField);
    displayPanel.add(new JLabel(DEPARTMENT_LABEL));
    displayPanel.add(departments);
    return displayPanel;
    }
    
    //Frame components
    private LinkedList<String> comboBoxArray;
    private JList courseList;
    private JButton addCourse;
    private JButton deleteCourse;
    private JButton dispAll;
    private JButton dispDept;
    private JComboBox departments;
    private JTextField courseNameTextField;
    private JTextField courseCodeTextField;
    private JTextField creditNumberTextField;
    private DefaultListModel courseModel;
    
    //Initialize the components
    private void initComponents(){
        
        comboBoxArray = new LinkedList<>();
        courseModel = new DefaultListModel();
        courseList = new JList(courseModel);
        addCourse = new JButton(ADD_COURSE_BUTTON_LABEL);
        dispAll = new JButton(DISPLAY_ALL_BUTTON_LABEL);
        dispDept = new JButton(DISPLAY_DEPT_BUTTON_LABEL);
        deleteCourse = new JButton(DELETE_COURSE_BUTTON_LABEL);
        deleteCourse.setEnabled(false);
        addCourse.setEnabled(false);
        courseNameTextField = new JTextField();
        courseCodeTextField = new JTextField();
        creditNumberTextField = new JTextField();
        
        for (Department d : controller.getDepartments()){
            comboBoxArray.add(d.getID());
        }
       
        departments = new JComboBox(comboBoxArray.toArray());
        
        deleteCourse.addActionListener((java.awt.event.ActionEvent evt) -> {
            deleteCourseActionPerformed(evt);
        });
        
        addCourse.addActionListener((java.awt.event.ActionEvent evt) -> {
            addCourseActionPerformed(evt);
        });
        
        dispAll.addActionListener((java.awt.event.ActionEvent evt) -> {
            dispAllActionPerformed(evt);
        });
        
        dispDept.addActionListener((java.awt.event.ActionEvent evt) -> {
            dispDeptActionPerformed(evt);
        });
        
        courseCodeTextField.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e){
               
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                checkFields();
            }
        });
        
        creditNumberTextField.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e){
               
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                checkFields();
            }
        });
    }
    


    
    private void addCourseActionPerformed(java.awt.event.ActionEvent evt) {
        String errorMsg = "The following problems occured:";
        int numCredits = 0;
        Boolean inError = false;
        
        if (courseNameTextField.getText().isEmpty()){
            errorMsg+= "\n- Course Name Field is empty";
            inError = true;
        }
        if (courseCodeTextField.getText().isEmpty()){
            errorMsg+= "\n- Couse Code Field is empty";
            inError = true;
        }
        if (creditNumberTextField.getText().isEmpty()){
            errorMsg+= "\n- Number of Credit hours is empty";
            inError = true;
        }
        else{
            try{
                String credits = creditNumberTextField.getText();
                numCredits = Integer.valueOf(credits);
                if (numCredits < 1 || numCredits > 5){
                    errorMsg+= "\n- Invalid Number of Credit Hours";
                    inError = true;
                }
            }
            catch (Exception e)
            {
                errorMsg+= "\n- Credit Hours must be an integer";
                inError = true;
            }
        }
        
        if (inError){
            infoBox(ERROR_MSG, errorMsg);
            //JOptionPane.showMessageDialog(null, errorMsg, "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            
            String departmentCode = departments.getSelectedItem().toString();
            if (!checkCourseAvail(courseCodeTextField.getText(), departmentCode)){
                int dialogButton = JOptionPane.YES_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Course already Exists\nUpdate existing course?", "Error", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION){
                    //(String cID, String dept, String cTitle, int cCredits)
                    controller.editCourse(courseCodeTextField.getText(), departmentCode, courseNameTextField.getText(), numCredits);
                }
            }
            else{
                controller.addCourse(courseCodeTextField.getText(), courseNameTextField.getText(), numCredits, departmentCode);//courseCodeTextField.getText(), courseNameTextField.getText(), 3, "CS");//departments.getSelectedItem().toString());
                //JOptionPane.showMessageDialog(null, "Course Added Successfully", "Success!", JOptionPane.INFORMATION_MESSAGE);
                infoBox(SUCCESS_MSG, "Course Added Successfully");
                clearTextFields();
            }
        }
    }
    
    private void checkFields(){
        if (!courseCodeTextField.getText().equals(EMPTY_STRING)){
            if (!courseNameTextField.getText().equals(EMPTY_STRING) && !creditNumberTextField.getText().equals(EMPTY_STRING)){
                addCourse.setEnabled(true);
            }
            else{
                addCourse.setEnabled(false);
            }
            deleteCourse.setEnabled(true);
        }
        else{
            deleteCourse.setEnabled(false);
        }
                
            
    }
    
    //move to controller
    private boolean checkCourseAvail(String cID, String Dept){
//        String ID;
//        for (Department d: controller.getDepartments()){
//            if (d.getID() == Dept){
//                for (Course c: d.getCourses()){
//                    ID = c.getID();
//                    if (ID.equals(cID)){
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
        return controller.checkCourseAvailable(cID, Dept);
    }

    
    private void clearTextFields(){
        courseNameTextField.setText(EMPTY_STRING);
        courseCodeTextField.setText(EMPTY_STRING);
        creditNumberTextField.setText(EMPTY_STRING);
        departments.setSelectedIndex(0);
        addCourse.setEnabled(false);
        deleteCourse.setEnabled(false);
    }
    
    private void deleteCourseActionPerformed(java.awt.event.ActionEvent evt){
        if (controller.deleteCourse(courseCodeTextField.getText(), departments.getSelectedItem().toString())){
            infoBox(SUCCESS_MSG, "Course delete successfully");
            clearTextFields();
        }
        else{
            infoBox(ERROR_MSG, "Course not present");
        }
    }
    
    private void infoBox(String title, String message){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void dispAllActionPerformed(java.awt.event.ActionEvent evt){
        this.courseModel.clear();
        LinkedList<Course> result = new LinkedList<>();
        for(Department dept:controller.getDepartments()){
            result.addAll(dept.getCourses());
        }
        for (Course c: result){
            this.courseModel.addElement(c.getInfo());
        }
    }
    
    private void dispDeptActionPerformed(java.awt.event.ActionEvent evt){
        this.courseModel.clear();
        String Dept = departments.getSelectedItem().toString();
        LinkedList<Course> result = new LinkedList<>();
        for(Department dept:controller.getDepartments()){
            if (dept.getID() == null ? Dept == null : dept.getID().equals(Dept)){
                result.addAll(dept.getCourses());
            }
        }
        for (Course c: result){
            this.courseModel.addElement(c.getInfo());
        }
    }
}

