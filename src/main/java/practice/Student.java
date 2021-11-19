package practice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//POJO/bean - Plain old java object
@Entity
@Table(name = "StudentData")
public class Student {
    String name;

    @javax.persistence.Id
            @Column(name="StuId")
    String Id;
    @Column(name="Age")
    int age;
    @Column(name="Grade")
    int grade;

    @Column(name="FatherName")
    String fatherName;

    @Column(name="MotherName")
    String MotherName;

    public Student(String name, String Id, int age, int grade, String fatherName, String MotherName){
        this.name = name;
        this.Id = Id;
        this.age = age;
        this.grade = grade;
        this.fatherName = fatherName;
        this.MotherName = MotherName;
    }

    public Student() {

    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getId() { return Id; }

    public void setId(String id) { Id = id; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public int getGrade() { return grade; }

    public void setGrade(int grade) { this.grade = grade; }

    public String getFatherName() { return fatherName; }

    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getMotherName() { return MotherName; }

    public void setMotherName(String motherName) { MotherName = motherName; }

    @Override
    public String toString() {

        return "Student{" +
                "name='" + name + '\'' +
                ", Id='" + Id + '\'' +
                ", age=" + age +
                ", grade=" + grade +
                ", fatherName='" + fatherName + '\'' +
                ", MotherName='" + MotherName + '\'' +
                '}';
    }
}

