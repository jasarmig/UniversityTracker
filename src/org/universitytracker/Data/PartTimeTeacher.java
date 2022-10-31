package org.universitytracker.Data;

public class PartTimeTeacher extends Teacher{

    private int activeHours;

    //Object from file
    public PartTimeTeacher(int id, String name, int hours, double base, double salary, String type){
        setTeacherId(id);
        setTeacherName(name);
        setActiveHours(hours);
        baseSalary = base;
        teacherSalary = salary;
        contractType = type;
    }

    public void setActiveHours(int hours){
        activeHours = hours;
    }

    @Override
    public void setSalary() {
        teacherSalary = getBaseSalary() * activeHours;
    }

    @Override
    public void setBaseSalary(){
        baseSalary = 3000;
    }

    @Override
    public void setContractType() {
        contractType = "Part Time";
    }

    @Override
    public String toString() {
        return getTeacherId() + ";" + getTeacherName() + ";" + activeHours + ";" + getBaseSalary() + ";" + getTeacherSalary() + ";" + getContractType();
    }
}
