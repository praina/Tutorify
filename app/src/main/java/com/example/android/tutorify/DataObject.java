package com.example.android.tutorify;

/**
 * Created by Prateek Raina on 22-10-2016.
 */
public class DataObject {

    private String FIRST_NAME;
    private String LAST_NAME;
    private String IMAGE;
    private String LOCATION;
    private String CONTACT_ONE;
    private String CONTACT_TWO;
    private String EDUCATION;
    private String MEDIUM;
    private String DESCRIPTION;
    private String CLASS_FROM;
    private String CLASS_UPTO;

    /*public DataObject(String first_name, String last_name, String image, String location, String contact_one, String contact_two, String education, String medium, String description, String class_from, String class_upto) {
        FIRST_NAME = first_name;
        LAST_NAME = last_name;
        IMAGE = image;
        LOCATION = location;
        CONTACT_ONE = contact_one;
        CONTACT_TWO = contact_two;
        EDUCATION = education;
        MEDIUM = medium;
        DESCRIPTION = description;
        CLASS_FROM = class_from;
        CLASS_UPTO = class_upto;
    }
    */

    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public void setFIRST_NAME(String FIRST_NAME) {
        this.FIRST_NAME = FIRST_NAME;
    }

    public String getLAST_NAME() {
        return LAST_NAME;
    }

    public void setLAST_NAME(String LAST_NAME) {
        this.LAST_NAME = LAST_NAME;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getCONTACT_ONE() {
        return CONTACT_ONE;
    }

    public void setCONTACT_ONE(String CONTACT_ONE) {
        this.CONTACT_ONE = CONTACT_ONE;
    }

    public String getCONTACT_TWO() {
        return CONTACT_TWO;
    }

    public void setCONTACT_TWO(String CONTACT_TWO) {
        this.CONTACT_TWO = CONTACT_TWO;
    }

    public String getEDUCATION() {
        return EDUCATION;
    }

    public void setEDUCATION(String EDUCATION) {
        this.EDUCATION = EDUCATION;
    }

    public String getMEDIUM() {
        return MEDIUM;
    }

    public void setMEDIUM(String MEDIUM) {
        this.MEDIUM = MEDIUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getCLASS_FROM() {
        return CLASS_FROM;
    }

    public void setCLASS_FROM(String CLASS_FROM) {
        this.CLASS_FROM = CLASS_FROM;
    }

    public String getCLASS_UPTO() {
        return CLASS_UPTO;
    }

    public void setCLASS_UPTO(String CLASS_UPTO) {
        this.CLASS_UPTO = CLASS_UPTO;
    }
}
