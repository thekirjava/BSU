package com.company;

import java.util.ArrayList;
import java.util.List;

public class Student  {
    public Student() {
        this.name = "";
        this.id = "";
        exams = null;
    }

    public Student(String name, String id, ArrayList<Exam> exams) {
        this.name = name;
        this.id = id;
        this.exams = exams;
    }


    static class Exam {
        public Exam(String name, int sem, int grade) {
            this.name = name;
            this.sem = sem;
            this.grade = grade;
        }


        public Exam() {
            name = "";
            grade = 0;
            sem =0;
        }

        String name;
        int sem;
        int grade;
    }

    String name;
    String id;
    ArrayList<Exam> exams;
}
