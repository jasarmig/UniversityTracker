package org.universitytracker.Data;

import org.universitytracker.Persistence.IOBridge;

import java.io.IOException;
import java.util.ArrayList;

public class Course {

    private int courseId;
    private String courseName;
    private String classroom;
    private int teacherId;
    private ArrayList<Integer> studentList;

    //from file
    public Course(int id, String name, String croom, int teacher, ArrayList<Integer> slist){
        setCourseId(id);
        setCourseName(name);
        setClassroom(croom);
        setTeacherId(teacher);
        setStudentList(slist);
    }

    // write to file
    public Course(String name, String room, int id, ArrayList<Integer> sList) throws IOException {
        IOBridge bridge = new IOBridge("course.csv");
        setCourseId(bridge.countRegisters());
        setCourseName(name);
        setClassroom(room);
        setTeacherId(id);
        setStudentList(sList);
        bridge.toFile(this);
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int id) {
        courseId = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String name) {
        courseName = name;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String room) {
        classroom = room;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacher) {
        teacherId = teacher;
    }

    public ArrayList<Integer> getStudentList() {
        return studentList;
    }

    public void setStudentList(ArrayList<Integer> sList) {
        studentList = sList;
    }

    public void addToCourse(int studentId, int courseId) throws IOException {
        studentList.add(studentId);
        String update = this.toString();
        IOBridge bridge = new IOBridge("course.csv");
        bridge.updateCourse(update,courseId);
    }

    @Override
    public String toString() {
        return getCourseId() + ";" + getCourseName() + ";" + getClassroom() + ";" + getTeacherId() + ";" + getStudentList() + "\n";
    }
}
