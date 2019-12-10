package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

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

    public Student(String s) throws NoSuchElementException {
        StringTokenizer stringTokenizer = new StringTokenizer(s);
        this.name = stringTokenizer.nextToken();
        this.id = stringTokenizer.nextToken();
        this.exams = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            this.exams.add(new Exam(stringTokenizer.nextToken(), Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken())));
        }
    }
    public String getName() {
        return name;
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



        @Override
        public String toString() {
            return "name='" + name + '\'' +
                    ", sem=" + sem +
                    ", grade=" + grade;
        }

        String name;
        int sem;
        int grade;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", exams=" + exams;
    }

    public void addExam (Exam exam) {
        exams.add(exam);
    }
    String name;
    String id;
    ArrayList<Exam> exams;
}
