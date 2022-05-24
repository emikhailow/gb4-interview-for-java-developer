package com.geekbrains.app.entities;

import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int RECORDS_COUNT = 1000;
    private static final Random random = new Random();
    private static final int MAX_MARK = 5;

    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        StudentDao studentDao = new StudentDaoImpl(sessionFactory);

        try{
            studentDao.deleteAll();

            List<Student> students = new ArrayList<>();
            for (int i = 0; i < RECORDS_COUNT; i++) {
                Student student = new Student(String.format("Student %d", i + 1), 1 + random.nextInt(MAX_MARK));
                students.add(student);
            }
            studentDao.save(students);

            Long id = random.nextLong((long) RECORDS_COUNT);
            System.out.printf("Random student id to be updated: %d%n", id);
            Student student = studentDao.findById(id);
            student.setMark(6);
            studentDao.save(student);

            id = random.nextLong((long) RECORDS_COUNT);
            System.out.printf("Random student id to be deleted: %d%n", id);
            studentDao.delete(id);

            List<Student> studentList = studentDao.findAll();
            System.out.printf("Quantity of students is %d%n", studentList.size());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }

    }
}
