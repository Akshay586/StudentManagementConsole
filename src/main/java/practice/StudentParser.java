package practice;
public class StudentParser {

    public Student getStudentFromText(String line) {

        String[] array = line.split(",");
        Student stu;
        stu = new Student(array[0], array[1].trim(),
                Integer.parseInt(array[2].trim()),
                Integer.parseInt(array[3].trim()),
                array[4].trim(),
                array[5]);
        return stu;
    }

    public Object[] splitTask(String line){
        Object[] studentObjects = new Object[4];
        studentObjects[0] = line.substring(0,line.indexOf(","));
        if ("Find".equalsIgnoreCase(studentObjects[0].toString())){
            //FIND, Stu_id, 0704CS181017
            String tempString = line.substring(line.indexOf(",")+1).trim();
            //Stu_id, 0704CS181017
            studentObjects[1] = tempString.substring(0,tempString.indexOf(",")).trim();
            studentObjects[2] = tempString.substring(tempString.indexOf(",")+1).trim();
        }
        else if("Update".equalsIgnoreCase(studentObjects[0].toString())){
            //UPDATE, 0770AC181716, name, Rachel Adams
            String tempString = line.substring(line.indexOf(",")+1).trim();
            //0770AC181716, name, Rachel Adams
            studentObjects[1] = tempString.substring(0,tempString.indexOf(",")).trim();
            tempString = tempString.substring(tempString.indexOf(",")+1).trim();
            //name, Rachel Adams
            studentObjects[2] = tempString.substring(0,tempString.indexOf(",")).trim();
            studentObjects[3]=tempString.substring(tempString.indexOf(",")+1).trim();
        }

        else {
            studentObjects[1] = getStudentFromText(line.substring(line.indexOf(",")+1).trim());
        }
        return studentObjects;
    }
}

