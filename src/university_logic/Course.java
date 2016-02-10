/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university_logic;

/**
 *
 * @author Eric
 */
public class Course{
    private String ID;
    private String Title;
    private int Credits;
    
    public Course(String cID, String cTitle, int cCredits)
    {
        this.ID = cID;
        this.Title = cTitle;
        this.Credits = cCredits;
    }
    
    public String getInfo(){
        return this.ID + " - " + this.Title;
    }
    
    public String getID(){
        return this.ID;
    }
    
    public void editID(String sID){
        this.ID = sID;
    }
    
}
