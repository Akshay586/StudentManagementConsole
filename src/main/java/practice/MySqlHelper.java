package practice;
import org.hibernate.SessionFactory;
import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MySqlHelper {

    static String filePath = "C:\\IntelliJ\\StudentDatabase\\src\\Resources\\StudentDataFile.txt";
    static Connection con;

    public MySqlHelper(){
        try{
            con = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/sys", "root", "Akshat@27");
        }
        catch(SQLException e){
            System.out.print("MYSQL helper error : connection can't be acquired");
        }
    }

    public static void insertStudentInDatabase(Student student) throws SQLException {

        String insertQuery = "INSERT INTO StudentData(Name, Stu_ID, Age, Grade, Father_Name, Mother_Name) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = con.prepareStatement(insertQuery);
        pstmt.setString(1, student.getName());
        pstmt.setString(2, student.getId().trim());
        pstmt.setInt(3, student.getAge());
        pstmt.setInt(4, student.getGrade());
        pstmt.setString(5, student.getFatherName());
        pstmt.setString(6, student.getMotherName());

        pstmt.execute();
        //con.close();
    }
    public static void deleteStudentFromDatabase(Student stu) throws SQLException {

        String deleteQuery = "DELETE FROM StudentData WHERE stu_id like ?";
        PreparedStatement prepStmt = con.prepareStatement(deleteQuery);
        prepStmt.setString(1, "%"+stu.getId()+"%");
        prepStmt.execute();
    }

    public Student findStudentById(String stuId) throws SQLException {
        String findStudentQuery = "select * from StudentData where stu_id like ?";
        PreparedStatement preparedStatement = con.prepareStatement(findStudentQuery);
        preparedStatement.setString(1, "%" + stuId + "%");
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        Student student = new Student( rs.getString("Name"),
                rs.getString("StuId"),
                rs.getInt("Age"),
                rs.getInt("Grade"),
                rs.getString("FatherName"),
                rs.getString("MotherName"));

        return student;
    }
/*    public List<Student> findStudentsById(String stuId) throws SQLException
    {
        String findStudentQuery = "select * from StudentData where stu_id like ?";
        PreparedStatement preparedStatement = con.prepareStatement(findStudentQuery);
        preparedStatement.setString(1, "%" + stuId + "%");
        ResultSet rs = preparedStatement.executeQuery();
        List <Student> studentList = new ArrayList<>();
        while (rs.next()){
            Student student = new Student( rs.getString("Name"),
                    rs.getString("StuId"),
                    rs.getInt("Age"),
                    rs.getInt("Grade"),
                    rs.getString("FatherName"),
                    rs.getString("MotherName"));
            studentList.add(student);
        }
        return studentList;
    }*/

    //make a find students by name method
    public List<Student> findStudentByName (String name) throws SQLException
    {
        String findStudentQuery = "select * from StudentData where name like ?";
        PreparedStatement preparedStatement = con.prepareStatement(findStudentQuery);
        preparedStatement.setString(1, "%" + name + "%");
        ResultSet rs = preparedStatement.executeQuery();
        List <Student> studentList = new ArrayList<>();
        while(rs.next())
        {
            Student student = new Student( rs.getString("Name"),
                    rs.getString("StuId"),
                    rs.getInt("Age"),
                    rs.getInt("Grade"),
                    rs.getString("FatherName"),
                    rs.getString("MotherName"));
            studentList.add(student);
        }
        return studentList;
    }



    public void performDatabaseOperation(Student student, String operation) throws SQLException {
        if (operation.equalsIgnoreCase("ADD")){
            try{
                insertStudentInDatabase(student);
            }
            catch(SQLIntegrityConstraintViolationException e){
                System.out.println(student.name+" already exists");
            }

        }
        else if (operation.equalsIgnoreCase("DELETE")){
            deleteStudentFromDatabase((student));
        }
    }

    public void performDatabaseUpdate(String id, String key, String value){
        String updateQuery="";
        switch (key){
            case "name":
                updateQuery =   "UPDATE StudentData SET Name = ? WHERE StuId=?";
                break;
            case "stu_id":
                updateQuery =   "UPDATE StudentData SET StuId = ? WHERE StuId=?";
                break;
        }

        try{
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Updated "+id+" successfully");
        }
        catch (SQLException e){
            System.out.println("Could not execute update for "+id);
        }
    }

    public List<Student> performDatabaseSearch(String key, String value) throws SQLException {
        if(key.equalsIgnoreCase("Name")){
            return findStudentByName(value);
        }
        else if(key.equalsIgnoreCase("StuId")){
            List<Student> returnList = new ArrayList<>();
            returnList.add(findStudentById(value));
            return returnList;
        }
        else{
            return Collections.emptyList();
        }
    }

    //make another performDBopsWithStu that takes criteria, operation and key

    public Connection getCon() {
        return con;
    }
}