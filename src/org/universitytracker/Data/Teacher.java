package org.universitytracker.Data;

public abstract class Teacher {
    private int teacherId;
    private String teacherName;
    protected double baseSalary;
    protected double teacherSalary;

    protected String contractType;


    public void setTeacherId(int id){
        teacherId=id;
    }

    public int getTeacherId(){
        return teacherId;
    }

    public void setTeacherName(String name){
        teacherName = name;
    }

    public String getTeacherName(){
        return teacherName;
    }

    public double getBaseSalary(){
        return baseSalary;
    }

    public abstract void setBaseSalary();

    public double getTeacherSalary(){
        return teacherSalary;
    }

    public abstract void setSalary();

    public abstract void setContractType();

    public String getContractType(){
        return contractType;
    }

}
