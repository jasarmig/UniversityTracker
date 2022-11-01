package org.universitytracker.Presentation;

import org.universitytracker.Data.Course;
import org.universitytracker.Data.Student;
import org.universitytracker.Data.Teacher;
import org.universitytracker.Data.University;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        University university = new University();
        university.initializeUniversity();
        int option;
        int option2;
        do{
            option = printMainMenu();
            switch (option){
                case 0:
                    break;
                case 1:
                    printTeacherList(university.getTeacherList());
                    option = -1;
                    break;
                case 2:
                    do {
                        option2 = printCourseList(university.getCourseList(),false);
                        if (option2 !=-1 && option2 != 0) {
                            printCourseDetails(option2, university);
                            option2 = -1;
                        }
                    }while(option2==-1);
                    option = -1;
                    break;
                case 3:
                    addStudent(university);
                    option = -1;
                    break;
                case 4:
                    addCourse(university);
                    option = -1;
                    break;
                default:
                    getStudentCourses(university);
                    option = -1;
                    break;
            }
        }while(option == -1);

    }

    private static void addCourse(University university) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter course name: ");
        String name = scan.nextLine();
        System.out.println("Enter classroom: ");
        String room = scan.nextLine();
        System.out.println("Assign a teacher to this course:");
        int teacherId = printTeacherMenu(university.getTeacherList());
        ArrayList<Integer> students = assignStudents(university.getStudentList());
        university.addToCourseList(name,room,teacherId,students);
        university.initializeUniversity();
    }

    private static ArrayList<Integer> assignStudents(ArrayList<Student> studentList) {
        ArrayList<Student> cloned = (ArrayList<Student>) studentList.clone();
        int option;
        int student;
        Student toRemove = null;
        ArrayList<Integer> validationOptions = new ArrayList<>();
        ArrayList<Integer> enrolledStudents = new ArrayList<>();
        validationOptions.add(1);
        validationOptions.add(2);
        do{
            student = printStudentMenu(cloned);
            Scanner scan = new Scanner(System.in);
            enrolledStudents.add(student);
            System.out.println("\n[1] Add another student");
            System.out.println("[2] Exit");
            option = optionValidation(scan,validationOptions);
            for(Student current : cloned){
                if(current.getStudentId()==student){
                    toRemove = current;
                }
            }
            cloned.remove(toRemove);
        }while (option!=2);
        return enrolledStudents;
    }

    private static void getStudentCourses(University university) {
        int studentId = printStudentMenu(university.getStudentList());
        ArrayList<Course> courses = university.getCourseList();
        System.out.println(university.getStudentNameById(studentId) + " is taking the following courses:\n");
        String format = "%1$-3s %2$s\n";
        System.out.printf(format,"ID","COURSE NAME");
        for(Course current : courses){
            ArrayList<Integer> courseStudents = current.getStudentList();
            if(courseStudents.contains(studentId)){
                System.out.println(current.getCourseId()  + "   " + current.getCourseName() );
            }
        }
    }

    private static int printStudentMenu(ArrayList<Student> studentList) {
        int option;
        ArrayList<Integer> studentOptions = new ArrayList<>();
        String format = "%1$-3s %2$s\n";
        System.out.printf(format,"ID","STUDENT NAME");
        format = "[%1$s] %2$s\n";
        for(Student current : studentList){
            System.out.printf(format,current.getStudentId(),current.getStudentName());
            studentOptions.add(current.getStudentId());
        }
        do{
            System.out.println("Select a student to continue");
            Scanner scan = new Scanner(System.in);
            option = optionValidation(scan,studentOptions);
        }while(option == -1);
        return option;
    }

    private static void printCourseDetails(int option2, University university) {
        Course detailed = university.getCourseById(option2);
        String details = detailed.toString();
        String[] attributes = details.split(";");
        int end = attributes[4].length()-2;
        String students = attributes[4].substring(1,end);
        students = students.replaceAll(" ","");
        String[] studentList = students.split(",");
        System.out.println("\nCourse: " + attributes[1]);
        System.out.println("Classroom: " + attributes[2]);
        System.out.println("Teacher: " + university.getTeacherNameById(parseInt(attributes[3])));
        System.out.println("Students taking this course:");
        System.out.println("----------------------------");
        String format = "%1$-2s\t%2$-30s\n";
        System.out.printf(format,"ID","STUDENT NAME");
        for(String studentId : studentList){
            String name = university.getStudentNameById(parseInt(studentId));
            System.out.printf(format,studentId,name);
        }
    }

    private static void addStudent(University university) throws IOException {
        String name;
        int age;
        Scanner scan = new Scanner(System.in);
        do{
            System.out.println("Please enter student name: ");
            name = scan.nextLine();
            scan = new Scanner(System.in);
            if(university.studentExists(name)){
                System.out.println("The student already exists");
            }

        }while(university.studentExists(name));

        do{
            System.out.println("Please enter student's age: ");
            if(scan.hasNextInt()){
                age = scan.nextInt();
                scan = new Scanner(System.in);
                if(age < 1){
                    System.out.println("Please enter a valid age");
                    age = -1;
                }
            }
            else{
                scan = new Scanner(System.in);
                age = -1;
                System.out.println("Please enter a valid age");
            }
        }while(age == -1);
        Student newStudent = new Student(name,age);
        int course = printCourseList(university.getCourseList(),true);
        Course destination = university.getCourseById(course);
        destination.addToCourse(newStudent.getStudentId(),destination.getCourseId());
        university.initializeUniversity();
    }

    public static int printCourseList(ArrayList<Course> courses,boolean enrollingNewStudent){
        ArrayList<Integer> optionList = new ArrayList<>();
        optionList.add(0);
        int option;
        String format = "%1$3s %2$-25s\n";
        System.out.printf(format,"\nID","COURSE");
        format = "[%1$1s] %2$-25s\n";
        for(Course current : courses){
            String[] line = current.toString().split(";");
            System.out.printf(format,line[0],line[1]);
            optionList.add(parseInt(line[0]));
        }
        if(!enrollingNewStudent){
            System.out.println("[0] Previous Menu\n");
            System.out.println("Select a course to view course details");
        }
        else{
            System.out.println("\nSelect a course to enroll the student");
        }
        Scanner scan = new Scanner(System.in);
        option = optionValidation(scan, optionList);
        return option;
    }

    public static void printTeacherList(ArrayList<Teacher> teachers){
        String format = "%1$-5s\t%2$-25s\t%3$-10s\t%4$-15s\t%5$-10s\t%6$-10s\n";
        System.out.printf(format,"ID","TEACHER NAME","EXP/HOURS","BASE SALARY","SALARY","CONTRACT");
        for(Teacher current : teachers){
            String[] line = current.toString().split(";");
            System.out.printf(format,line[0],line[1],line[2],line[3],line[4],line[5]);
        }
    }

    public static int printMainMenu(){

        int option;
        do {
            System.out.println("\nWELCOME TO TAE UNIVERSITY\n");
            System.out.println("CHOOSE AN OPTION TO BEGIN");
            System.out.println("[1] View teachers detailed information");
            System.out.println("[2] View courses list and details");
            System.out.println("[3] Create student");
            System.out.println("[4] Create course");
            System.out.println("[5] List courses for a student");
            System.out.println("[0] Exit");

            Scanner scan = new Scanner(System.in);
            option = optionValidation(scan, 6);
        }while(option == -1);
        return option;
    }

    public static int optionValidation(Scanner scan, int options){
        int option = -1;
        if(scan.hasNextInt()){
            option = scan.nextInt();
            if(option > -1 && option <=options){
                return option;
            }
            else{
                option = -1;
                System.out.println("\nInvalid option please try again\n");
            }
        }
        else{
            System.out.println("\nInvalid option please try again\n");
        }
        return option;
    }

    public static int optionValidation(Scanner scan, ArrayList<Integer> options){
        int option = -1;
        if(scan.hasNextInt()){
            option = scan.nextInt();
            scan = new Scanner(System.in);
            if(options.contains(option)){
                return option;
            }
            else{
                option = -1;
                System.out.println("\nInvalid option please try again\n");
            }
        }
        else{
            System.out.println("\nInvalid option please try again\n");
        }
        return option;
    }

    public static int printTeacherMenu(ArrayList<Teacher> tList){
        int option;
        do{
            String format = "%1$-3s %2$s\n";
            System.out.printf(format,"ID","TEACHER");
            format = "[%1s] %2$s\n";
            ArrayList<Integer> options = new ArrayList<>();
            for(Teacher current : tList){
                String[] line = current.toString().split(";");
                System.out.printf(format,line[0],line[1]);
                options.add(parseInt(line[0]));
            }
            Scanner scan = new Scanner(System.in);
            option = optionValidation(scan,options);
        }while(option == -1);
        return option;
    }
}