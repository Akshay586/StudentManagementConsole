package practice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import javax.persistence.Id;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class HibernateHelper {

    Configuration configuration;
    SessionFactory factory;


    public HibernateHelper() {
        configuration = new Configuration();
        configuration.configure();
        factory = configuration.buildSessionFactory();
    }

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        t.commit();
        System.out.println("successfully saved");
        factory.close();
        session.close();
    }

    public void performDatabaseOperation(Student student, String operation) throws SQLException {
        if (operation.equalsIgnoreCase("ADD")) {
            insertStudentInDatabase(student);

        } else if (operation.equalsIgnoreCase("DELETE")) {
            deleteStudentFromDatabase((student));
        }
    }

    public void deleteStudentFromDatabase(Student student) {
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.delete(student);
        t.commit();
    }

    public void insertStudentInDatabase(Student student) {
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.save(student);
        t.commit();
    }

    public void performDatabaseUpdate(String id, String key, String value) {

        Student stu = new Student();
        stu.setId(id);
        switch (key) {
            case "name":
                stu.setName(value);
                break;

            case "Age":
                stu.setAge(Integer.parseInt(value));
                break;

            case "Grade":
                stu.setGrade(Integer.parseInt(value));
                break;

            case "FatherName":
                stu.setFatherName(value);
                break;

            case "motherName":
                stu.setMotherName(value);
                break;
        }
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.update(stu);
        t.commit();
    }

    public void performDatabaseSearch(String key, String value) {

        if (key.equalsIgnoreCase("Name")) {


        }

    }
}

