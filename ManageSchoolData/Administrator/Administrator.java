// 1 school object
// administrator
//?school.startSession("2023-24")
//? school.CurrentSession
//? school.createClass("grade", "section")
//? school.enrollStudent("name","other details", "standard") => roll number
//? school.assignStudentToClass(rollNumber, "grade" , "section")
//? school.getStorageLocation("attendance")

// class teacher
//! school.getMyClass("grade", "section")
//! myClass.createAttendenceRegisterIfNotAvailable("class" , "section" , "academic-session")
//! myClassRegister.addStudentNames(school.enrolledStudentList("grade", "section"))
//! add student names sorted by their names
//! if two students have same name, give them serial number
//todo myClassRegister.recordAttendence("date", "roll number", "status")
//todo myClassRegister.giveMeAttendenceSummary()
//todo [{date: 11 sep, p: 3, a: 4}, {date: 11 sep, p: 3, a: 4} , {date: 11 sep, p: 3, a: 4}]
//todo 2023-24/10a/studentsLists.txt
//todo 2023-24/10a/attendance-2023-09-12.txt

package Administrator;
import java.util.*;

class Persister {
   private HashMap < String, String > datastore = new HashMap < String, String > ();

   public Persister() {}

   public void Store(String id, String data, boolean append) {
      if (append) {
         if (datastore.containsKey(id)) {
            String oldData = datastore.get(id);
            String newData = oldData + data;
            datastore.put(id, newData);
         } else {
            datastore.put(id, data);
         }
      } else {
         datastore.put(id, data);
      }
   }

   public String Retrieve(String id) {
      return "";
   }
}

class School {
   private static School school = new School();
   private String session = null;
   private String classRoom = null;
   private List < String > enrollStudentsList = new ArrayList < > ();
   private String assignStudentToClass = null;

   private School() {}

   public static School getInstanceSchoolClass() {
      return school;
   }

   public String getSession() {
      return session;
   }

   public void StartNewSession() {
      session = "2023-24";
   }

   public String getClassroom() {
      return classRoom;
   }

   public void setClassroom(String grade, String section) {
      classRoom = grade + " " + section;
   }

   public String getEnrollStudent() {
      return enrollStudentsList.toString();
   }

   public void setEnrollStudent(String name, String otherDetails, String rollNumber) {
      String standard = getStandardFromRollNumber(rollNumber);
      String enrollStudent = rollNumber + " " + name + " " + otherDetails + " " + standard;
      enrollStudentsList.add(enrollStudent);
   }

   private String getStandardFromRollNumber(String rollNumber) {
      if (rollNumber.startsWith("10")) {
         return "10th";
      } else if (rollNumber.startsWith("11")) {
         return "11th";
      } else if (rollNumber.startsWith("12")) {
         return "12th";
      } else {
         return "Unknown";
      }
   }

   public String getAssignStudentToClass() {
      return assignStudentToClass;
   }

   public void setAssignStudentToClass(String rollNumber, String grade, String section) {
      assignStudentToClass = rollNumber + " " + grade + " " + section;
   }

   public List < String > getEnrolledStudentsList() {
      return enrollStudentsList;
   }
}

class Teacher {
   private static Teacher teacher = new Teacher();
   private String className = null;
   private boolean attendanceRegisterExists = false;

   private Teacher() {}

   public static Teacher getInstanceTeacherClass() {
      return teacher;
   }

   public String getMyClass() {
      return className;
   }

   public void setMyClass(String grade, String session) {
      className = grade + "," + session;
   }

   public Boolean getCreateAttendanceRegisterIfNotAvailable() {
      return !attendanceRegisterExists;
   }

   public void setCreateAttendanceRegisterIfNotAvailable(String className, String section, String academicSession) {
      if (className != null && section != null && academicSession != null) {
         attendanceRegisterExists = true;
      } else {
         attendanceRegisterExists = false;
      }
   }

}

class Register {
   private static Register register = new Register();
   private String classStudentsNames = null;

   private Register() {}

   public static Register getInstanceRegisterClass() {
      return register;
   }

   public String getMyClassName() {
      return classStudentsNames;
   }

   public void setMyClassName(String grade, String section) {
      School mySchool = School.getInstanceSchoolClass();
      List < String > enrolledStudents = mySchool.getEnrolledStudentsList();
      List < String > filteredStudents = new ArrayList < > ();
      for (String student: enrolledStudents) {
         String[] studentDetails = student.split(" ");
         String studentGrade = studentDetails[2];
         String studentSection = studentDetails[3];
         if (studentGrade.equals(grade) && studentSection.equals(section)) {
            filteredStudents.add(student);
         }
      }
      Collections.sort(filteredStudents);
      int serialNumber = 1;
      StringBuilder sb = new StringBuilder();
      for (String student: filteredStudents) {
         sb.append(serialNumber).append(". ").append(student).append("\n");
         serialNumber++;
      }
      classStudentsNames = sb.toString();
   }

   public List < String > getSortedStudentNames() {
      School mySchool = School.getInstanceSchoolClass();
      List < String > enrolledStudents = mySchool.getEnrolledStudentsList();
      List < String > studentNames = new ArrayList < > ();

      for (String student: enrolledStudents) {
         String[] studentDetails = student.split(" ");
         String name = studentDetails[1];
         studentNames.add(name);
      }

      Collections.sort(studentNames);

      return studentNames;
   }

   public String getSerialNumber() {
      List < String > studentNames = getSortedStudentNames();
      Map < String, Integer > nameCountMap = new HashMap < > ();
      StringBuilder sb = new StringBuilder();

      for (String name: studentNames) {
         int count = nameCountMap.getOrDefault(name, 0) + 1;
         nameCountMap.put(name, count);
         sb.append(count).append(". ").append(name).append("\n");
      }

      return sb.toString();
   }
}

class ConsoleApp
{
   public static Scanner scan =new Scanner(System.in);
   public ConsoleApp()
   {}
   

   private void SetSchoolName()
   {
      
      
   }

   private void ShowMenu()
   {
      System.out.println();
      System.out.println("<===Choose any input===>");
            System.out.println("1. Set School Name");
            System.out.println("2. GetSchool Name");
            System.out.println("3. Start Academic Session");
            System.out.println("4. Create New Class");
            System.out.println("5. Enroll Student");
            System.out.println("6. Mark Attendance");
            System.out.println("7. Show Attendance Report");
            System.out.println("0. Exit Program");
            System.out.println();
   }
   public int GetUserChoice()
   {
      ShowMenu();
      System.out.print("Enter your choice: ");
      int userInput=scan.nextInt();
      System.out.println();
      return userInput;
   }

   public void run()
   {
      int choice;
      while ( (choice = GetUserChoice()) != 0 )
      {
      System.out.println(choice);
      }
   }

}


public class Administrator {
   public static void main(String[] args) {
      System.out.println("<=====================Persister Class Work=============================>");
      System.out.println();
      Persister persister = new Persister();
      persister.Store("1", "Hello", false);
      System.out.println();

      System.out.println("<=====================School Class Work=============================>");
      System.out.println();
      School mySchool = School.getInstanceSchoolClass();
      mySchool.StartNewSession();
      mySchool.setClassroom("10th", "A");
      mySchool.setEnrollStudent("Ram", "personName", "10A01");
      mySchool.setAssignStudentToClass("10A01", "10th", "A");
      System.out.println(mySchool.getEnrolledStudentsList());
      System.out.println(mySchool.getAssignStudentToClass());
      System.out.println(mySchool.getClassroom());
      System.out.println(mySchool.getSession());
      System.out.println();

      System.out.println("<=====================Teacher Class Work=============================>");
      System.out.println();
      Teacher myClass = Teacher.getInstanceTeacherClass();
      String grade = mySchool.getClassroom().split(" ")[0];
      String session = mySchool.getSession();
      String className = mySchool.getClassroom();
      myClass.setMyClass(grade, session);
      myClass.setCreateAttendanceRegisterIfNotAvailable(className, grade, session);
      System.out.println(myClass.getCreateAttendanceRegisterIfNotAvailable());
      System.out.println(myClass.getMyClass());
      System.out.println();

      System.out.println("<=====================Register Class Work=============================>");
      System.out.println();
      Register myRegister = Register.getInstanceRegisterClass();
      String section = mySchool.getClassroom().split(" ")[1];
      myRegister.setMyClassName(grade, section);
      System.out.println(myRegister.getMyClassName());
      System.out.println(myRegister.getSortedStudentNames());
      System.out.println(myRegister.getSerialNumber());
      System.out.println();

      System.out.println("<=====================ConsoleApp Class Work=============================>");
      System.out.println();
      ConsoleApp consoleApp =new ConsoleApp();
      consoleApp.run();
    }
}