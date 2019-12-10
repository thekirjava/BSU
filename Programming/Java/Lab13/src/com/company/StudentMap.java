package com.company;

import java.util.*;

public class StudentMap<K extends String, T extends Student> extends HashMap<K, T> {
    public StudentMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public StudentMap(int initialCapacity) {
        super(initialCapacity);
    }

    public StudentMap() {
    }

    public StudentMap(Map<? extends K, ? extends T> m) {
        super(m);
    }

    class NonPass {
        public NonPass(String student, String exam) {
            this.student = student;
            this.exam = exam;
        }

        @Override
        public String toString() {
            return "exam='" + exam + '\'' +
                            ", student='" + student + '\'';
        }

        String exam;
        String student;

    }

    public ArrayList notPassed(String s) {
        StringTokenizer stringTokenizer = new StringTokenizer(s);
        int semester = Integer.parseInt(stringTokenizer.nextToken());
        ArrayList<NonPass> ans = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            String exam = stringTokenizer.nextToken();
            Iterator<Entry<K, T>> studentIterator = this.entrySet().iterator();
            while (studentIterator.hasNext()) {
                Entry<K, T> entry = studentIterator.next();
                Student student = entry.getValue();
                Collections.sort(student.exams, new Comparator<Student.Exam>() {
                    @Override
                    public int compare(Student.Exam o1, Student.Exam o2) {
                        if (o1.sem != o2.sem) {
                            return o1.sem - o2.sem;
                        }
                        return o1.name.compareTo(o2.name);
                    }
                });
                if (Collections.binarySearch(student.exams, new Student.Exam(exam, semester, 0), new Comparator<Student.Exam>() {
                    @Override
                    public int compare(Student.Exam o1, Student.Exam o2) {
                        if (o1.sem == o2.sem) {
                            return o1.name.compareTo(o2.name);
                        }
                        return o1.sem - o2.sem;
                    }
                }) < 0) {
                    ans.add(new NonPass(student.getName(), exam));
                }
            }
        }

        return ans;
    }

    public void add(String s) throws NumberFormatException, NoSuchElementException, WrongIdException {
        StringTokenizer student = new StringTokenizer(s);
        String id = student.nextToken();
        String name = student.nextToken();
        int semester = Integer.parseInt(student.nextToken());
        String exam_name = student.nextToken();
        int grade = Integer.parseInt(student.nextToken());
        if (this.get(id) == null) {
            this.put((K) id, (T) new Student(name, id, new ArrayList<>()));
        }
        if (!this.get(id).getName().equals(name)) {
            throw new WrongIdException();
        }
        this.get(id).addExam(new Student.Exam(exam_name, semester, grade));
    }
}
