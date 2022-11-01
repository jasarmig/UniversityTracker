package org.universitytracker.Data;

import org.universitytracker.Persistence.IOBridge;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class University {

    private ArrayList<Teacher> teacherList = new ArrayList<>();
    private ArrayList<Course> courseList = new ArrayList<>();
    private ArrayList<Student> studentList = new ArrayList<>();

    public void initializeUniversity() throws IOException {
        IOBridge bridge = new IOBridge("teacher.csv");
        ArrayList<String> registers = bridge.fromFile();
        teacherList.clear();
        for (String register : registers) {
            String[] attributes = register.split(";");
            if (attributes[5].equals("Full Time")) {
                teacherList.add(new FullTimeTeacher(parseInt(attributes[0]), attributes[1], parseInt(attributes[2]), parseDouble(attributes[3]), parseDouble(attributes[4]), attributes[5]));
            } else {
                teacherList.add(new PartTimeTeacher(parseInt(attributes[0]), attributes[1], parseInt(attributes[2]), parseDouble(attributes[3]), parseDouble(attributes[4]), attributes[5]));
            }
        }

        bridge = new IOBridge("student.csv");
        registers = bridge.fromFile();
        studentList.clear();
        for (String register : registers){
            String[] attributes = register.split(";");
            studentList.add(new Student(parseInt(attributes[0]), attributes[1], parseInt(attributes[2])));
        }

        bridge = new IOBridge("course.csv");
        registers = bridge.fromFile();
        courseList.clear();
        for(String register : registers){
            String[] attributes = register.split(";");
            ArrayList<Integer> sList = new ArrayList<>();
            String students = attributes[4];
            int limit = students.length() -1;
            students = students.substring(1,limit);
            students = students.replaceAll(" ","");
            String[] studentList = students.split(",");
            for(String studentid : studentList){
                sList.add(parseInt(studentid));
            }
            courseList.add(new Course(parseInt(attributes[0]), attributes[1], attributes[2], parseInt(attributes[3]),sList));
        }
    }

    public Course getCourseById(int wanted){
        for(Course wantedCourse : courseList){
            if(wantedCourse.getCourseId() == wanted){
                return wantedCourse;
            }
        }
        return null;
    }

    public String getTeacherNameById(int wanted){
        for(Teacher wantedTeacher : teacherList){
            if(wantedTeacher.getTeacherId() == wanted){
                return wantedTeacher.getTeacherName();
            }
        }
        return null;
    }

    public String getStudentNameById(int wanted){
        for(Student wantedStudent : studentList){
            if(wantedStudent.getStudentId() == wanted){
                return wantedStudent.getStudentName();
            }
        }
        return null;
    }

    public boolean studentExists(String name){
        for(Student current : studentList){
            if(current.getStudentName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public boolean courseExists(String name){
        for(Course current : courseList){
            if(current.getCourseName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public void addToCourseList(String name, String room, int teacherId, ArrayList<Integer> students) throws IOException {
        if(!courseInList(name)){
            Course newCourse = new Course(name, room, teacherId, students);
            courseList.add(newCourse);
            initializeUniversity();
        }
    }

    private boolean courseInList(String name) {
        for(Course current : courseList){
            String courseName = current.getCourseName();
            if(courseName.equals(name)){
                return true;
            }
        }
        return false;
    }
}
