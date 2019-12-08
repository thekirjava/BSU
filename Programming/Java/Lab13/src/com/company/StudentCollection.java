package com.company;

import java.util.*;

public class StudentCollection<T extends Student> extends ArrayList<T> {
    public StudentCollection(int initialCapacity) {
        super(initialCapacity);
    }

    public StudentCollection() {
    }

    public StudentCollection(Collection<? extends T> c) {
        super(c);
    }

    class NonPass {
        public NonPass(String student, String exam) {
            this.student = student;
            this.exam = exam;
        }

        @Override
        public String toString() {
            return
                    "student='" + student + '\'' +
                            ", exam='" + exam + '\'';
        }

        String student;
        String exam;
    }

    public ArrayList notPassed(String s) {
        ArrayList<NonPass> ans = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(s);
        int sem = Integer.parseInt(stringTokenizer.nextToken());
        ArrayList<String> exams = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            exams.add(stringTokenizer.nextToken());
        }
        Iterator<String> stringIterator = exams.iterator();
        while (stringIterator.hasNext()) {
            Iterator<T> tIterator = this.iterator();
            String exam = stringIterator.next();
            while (tIterator.hasNext()) {
                Student student = tIterator.next();
                Collections.sort(student.exams, new Comparator<Student.Exam>() {
                    @Override
                    public int compare(Student.Exam o1, Student.Exam o2) {
                        if (o1.sem != o2.sem) {
                            return o1.sem - o2.sem;
                        }
                        if (!o1.name.equals(o2.name)) {
                            return o1.name.compareTo(o2.name);
                        }
                        return o1.grade - o2.grade;
                    }
                });
                if (Collections.binarySearch(student.exams, new Student.Exam(exam, sem, 1), new Comparator<Student.Exam>() {
                    @Override
                    public int compare(Student.Exam o1, Student.Exam o2) {
                        if (o1.sem == o2.sem) {
                            return o1.name.compareTo(o2.name);
                        }
                        return o1.sem - o2.sem;
                    }
                }) < 0) {
                    ans.add(new NonPass(student.name, exam));
                }
            }
        }
        Collections.sort(ans, new Comparator<NonPass>() {
            @Override
            public int compare(NonPass o1, NonPass o2) {
                if (!o1.exam.equals(o2.exam)) {
                    return o1.exam.compareTo(o2.exam);
                }
                return o1.student.compareTo(o2.student);
            }
        });
       return ans;
    }
}
