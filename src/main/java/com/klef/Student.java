package com.klef;

import jakarta.persistence.*;

@Entity
@Table(name="student")
public class Student {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String name;
private String department;
private Double marks;
private Integer age;

public Student(){}

public Student(String name,String department,Double marks,Integer age){
this.name=name;
this.department=department;
this.marks=marks;
this.age=age;
}

public Long getId(){ return id; }

public String getName(){ return name; }
public void setName(String name){ this.name=name; }

public String getDepartment(){ return department; }
public void setDepartment(String department){ this.department=department; }

public Double getMarks(){ return marks; }
public void setMarks(Double marks){ this.marks=marks; }

public Integer getAge(){ return age; }
public void setAge(Integer age){ this.age=age; }

@Override
public String toString(){
return id+" | "+name+" | "+department+" | "+marks+" | "+age;
}

}
