package com.geekbrains.app.entities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.stream.StreamSupport;

public class StudentDaoImpl implements StudentDao{
    private SessionFactory sessionFactory;

    public StudentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Student findById(Long id) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Student student = session.get(Student.class, id);
            session.getTransaction().commit();
            return student;
        }
    }

    @Override
    public List<Student> findAll() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Student> students = session.createQuery("select s from Student s").getResultList();
            session.getTransaction().commit();
            return students;
        }
    }

    @Override
    public void save(Student student) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(student);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from Student s where s.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE students").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void save(List<Student> students) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Student student : students) {
                session.saveOrUpdate(student);
            }
            session.getTransaction().commit();
        }
    }
}
