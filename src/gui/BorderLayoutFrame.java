package gui;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import university_logic.Controller;
import university_logic.Course;
import university_logic.Department;

public class BorderLayoutFrame extends JFrame {
    
    private final Controller controller;
    
    public BorderLayoutFrame(Controller cont){
        super("University System"); //set title
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
        leftSide.add(Box.createHorizontalStrut(15));
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
    displayPanel.add(new JLabel("Course Code:"));
    displayPanel.add(courseCodeTextField);
    displayPanel.add(new JLabel("Course Name:"));
    displayPanel.add(courseNameTextField);
    displayPanel.add(new JLabel("No. Of Credits:"));
    displayPanel.add(creditNumberTextField);
    displayPanel.add(new JLabel("Department:"));
    displayPanel.add(departments);
    return displayPanel;
    }
    
    //Frame components
    private LinkedList<String> comboBoxArray;
    private JList courseList;
    private JButton addCourse;
    private JButton dispAll;
    private JButton dispDept;
    private JComboBox departments;
    private JTextField courseNameTextField;
    private JTextField courseCodeTextField;
    private JTextField creditNumberTextField;
    private DefaultListModel courseModel;
    
    //Initialize the components
    private void initComponents(){
        
        comboBoxArray = new LinkedList<String>();
        courseModel = new DefaultListModel();
        courseList = new JList(courseModel);
        addCourse = new JButton("Add Course");
        dispAll = new JButton("Display All");
        dispDept = new JButton("Display(Dept)");
        courseNameTextField = new JTextField();
        courseCodeTextField = new JTextField();
        creditNumberTextField = new JTextField();
        
        for (Department d : controller.getDepartments()){
            comboBoxArray.add(d.getID());
        }
       
        departments = new JComboBox(comboBoxArray.toArray());
        
        addCourse.addActionListener((java.awt.event.ActionEvent evt) -> {
            addCourseActionPerformed(evt);
        });
        
        dispAll.addActionListener((java.awt.event.ActionEvent evt) -> {
            dispAllActionPerformed(evt);
        });
        
        dispDept.addActionListener((java.awt.event.ActionEvent evt) -> {
            dispDeptActionPerformed(evt);
        });
    }
    
    private void addCourseActionPerformed(java.awt.event.ActionEvent evt) {
        String errorMsg = "The following problems occured:";
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
                int numCredits = Integer.valueOf(credits);
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
            JOptionPane.showMessageDialog(null, errorMsg, "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            String departmentCode = departments.getSelectedItem().toString();
            Course result = controller.addCourse(courseCodeTextField.getText(), courseNameTextField.getText(), 3, departmentCode);//courseCodeTextField.getText(), courseNameTextField.getText(), 3, "CS");//departments.getSelectedItem().toString());
            JOptionPane.showMessageDialog(null, "Course Added Successfully", "Success!", JOptionPane.INFORMATION_MESSAGE);
            courseNameTextField.setText("");
            courseCodeTextField.setText("");
            creditNumberTextField.setText("");
            departments.setSelectedIndex(0);
        }
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

