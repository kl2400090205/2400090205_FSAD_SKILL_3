package com.klef;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class StudentHqlDemo {

private static final SessionFactory factory=new Configuration()
.configure("hibernate.cfg.xml")
.addAnnotatedClass(Student.class)
.buildSessionFactory();

public static void main(String[] args){

seedData();
runQueries();

factory.close();
}

private static void seedData(){

List<Student> students=List.of(
new Student("Vinay","CSE",85.5,20),
new Student("Rahul","ECE",78.0,21),
new Student("Ankit","AI",90.2,22),
new Student("Divya","CSE",88.0,21),
new Student("Kiran","ECE",72.5,23),
new Student("Sneha","AI",95.0,20)
);

Session session=factory.openSession();
Transaction tx=session.beginTransaction();

session.createMutationQuery("delete from Student").executeUpdate();

students.forEach(session::persist);

tx.commit();
session.close();

System.out.println("Students Inserted\n");
}

private static void runQueries(){

Session session=factory.openSession();

System.out.println("--- First 3 Students ---");

session.createQuery("from Student s order by s.id",Student.class)
.setFirstResult(0)
.setMaxResults(3)
.list()
.forEach(System.out::println);

System.out.println("\n--- Next 3 Students ---");

session.createQuery("from Student s order by s.id",Student.class)
.setFirstResult(3)
.setMaxResults(3)
.list()
.forEach(System.out::println);

Long total=session.createQuery("select count(s) from Student s",Long.class).uniqueResult();

System.out.println("\nTotal Students: "+total);

System.out.println("\n--- Group By Department ---");

session.createQuery(
"select s.department,count(s) from Student s group by s.department",
Object[].class)
.list()
.forEach(r->System.out.println(r[0]+" -> "+r[1]));

Object[] minmax=session.createQuery(
"select min(s.marks),max(s.marks) from Student s",
Object[].class)
.uniqueResult();

System.out.println("\nMin Marks: "+minmax[0]);
System.out.println("Max Marks: "+minmax[1]);

System.out.println("\n--- Marks Between 80 and 95 ---");

session.createQuery(
"from Student s where s.marks between :min and :max",
Student.class)
.setParameter("min",80.0)
.setParameter("max",95.0)
.list()
.forEach(System.out::println);

session.close();

}

}