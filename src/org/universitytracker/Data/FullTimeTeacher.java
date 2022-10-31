package org.universitytracker.Data;

public class FullTimeTeacher extends Teacher {

    private int experienceYears;

    // crea el objeto al leer desde el archivo
    public FullTimeTeacher(int id, String name, int experience, double base, double salary, String type){
        setTeacherId(id);
        setTeacherName(name);
        setExperienceYears(experience);
        baseSalary = base;
        teacherSalary = salary;
        contractType = type;
    }

    public void setExperienceYears(int years){
        experienceYears = years;
    }

    @Override
    public void setSalary() {
        teacherSalary = getBaseSalary() * 1.1 * experienceYears;
    }

    @Override
    public void setBaseSalary(){
        baseSalary = 5000;
    }

    @Override
    public void setContractType() {
        contractType = "Full Time";
    }

    @Override
    public String toString() {
        return getTeacherId() + ";" + getTeacherName() + ";" + experienceYears + ";" + getBaseSalary() + ";" + getTeacherSalary() + ";" + getContractType();
    }

}
