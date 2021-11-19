package practice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.List;

public class StudentManager {
    static StudentParser studentParser = new StudentParser();
    static MySqlHelper mySqlHelper; //= new MySqlHelper();
    static HibernateHelper hibernateHelper;
    public static void main(String [] args) throws Exception{
        mySqlHelper = new MySqlHelper();
        hibernateHelper = new HibernateHelper();
        File file = new File("src/main/resources/StudentDataFile.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        System.out.println("Logging in");
        String st;
        long startTime = System.currentTimeMillis();

        while ((st = br.readLine()) != null) {
            SingleLineHandler handler = new SingleLineHandler(st);
            handler.start();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken : "+(endTime-startTime));

    }

    static class SingleLineHandler extends Thread{
        String line;

        public SingleLineHandler(String line){
            this.line = line;
        }

        @Override
        public void run() {
            try{
                Object[] taskAndStudent = studentParser.splitTask(line);
                String operation = String.valueOf(taskAndStudent[0]);
                if(operation.equalsIgnoreCase("ADD") || operation.equalsIgnoreCase("DELETE")) {
                    Student student = (Student) taskAndStudent[1];
                    hibernateHelper.performDatabaseOperation(student,operation);
                }
                else if(operation.equalsIgnoreCase("Find"))
                {
                    String key = String.valueOf(taskAndStudent[1]);
                    String value = String.valueOf(taskAndStudent[2]);

                    List<Student> results = mySqlHelper.performDatabaseSearch(key, value);
                    for (Student thisStudent : results) {
                        System.out.println(thisStudent);
                    }
                }
                else if(operation.equalsIgnoreCase("update")){
                    hibernateHelper.performDatabaseUpdate(taskAndStudent[1].toString(),taskAndStudent[2].toString(),taskAndStudent[3].toString());
                }
            } catch (SQLException throttles) {
                throttles.printStackTrace();
            }
        }
    }
}