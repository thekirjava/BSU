package com.company;

import java.util.StringTokenizer;

public class Person implements Comparable<Person> {
    Person() {
        name = "John";
        surname = "Doe";
    }

    Person(String s) {
        StringTokenizer st = new StringTokenizer(s);
        name = st.nextToken();
        surname = st.nextToken();
    }

    @Override
    public int compareTo(Person p) {
        if (surname.compareTo(p.surname) != 0) {
            return surname.compareTo(p.surname);
        }
        return name.compareTo(p.name);
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    String name;
    String surname;
}
