package org.universitytracker.Data;

import org.universitytracker.Persistence.IOBridge;

import java.io.IOException;

public class Student {
    private int studentId;
    private String studentName;
    private int studentAge;

    //from file
    public Student(int id, String name, int age){
        setStudentId(id);
        setStudentName(name);
        setStudentAge(age);
    }

    //write to file
    public Student(String name, int age) throws IOException {
        IOBridge bridge = new IOBridge("student.csv");
        setStudentId(bridge.countRegisters());
        setStudentName(name);
        setStudentAge(age);
        bridge.toFile(this);
    }

    public void setStudentAge(int age) {
        studentAge = age;
    }

    public void setStudentId(int id){
        studentId = id;
    }

    public void setStudentName(String name){
        studentName = name;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getStudentAge() {
        return studentAge;
    }

    @Override
    public String toString() {
        return getStudentId() + ";" + getStudentName() + ";" + getStudentAge() + "\n";
    }

}
