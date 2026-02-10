package Administrator;
import java.io.*;
import java.util.*;

public class Register{
    private static Map<String, Set<String>> classNames = new HashMap<>();
    private static Map<String, Map<String, Map<String, Integer>>> registers = new HashMap<>();
    private static Map<String, Map<String, Integer>> rollNumbers = new HashMap<>(); 
    private static String currentClassName = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean choiceProgram = false;
        while (!choiceProgram) { 
            System.out.println("<===Choose any input===>");
            System.out.println("1. Enter Class Name");
            System.out.println("2. Enter Date for Attendance");
            System.out.println("3. Enter Student Name");
            System.out.println("4. Mark Attendance");
            System.out.println("5. Show Class Report");
            System.out.println("6. Show All Reports");
            System.out.println("7. Generate .txt File");
            System.out.println("8. Exit from Class");
            System.out.println("9. Exit Program");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addClassName(scanner);
                    break;
                case 2:
                    addDateForAttendance(scanner);
                    break;
                case 3:
                    addStudentName(scanner);
                    break;
                case 4:
                    markAttendance(scanner);
                    break;
                case 5:
                    showClassReport(scanner);
                    break;
                case 6:
                    showAllReports(scanner);
                    break;
                case 7:
                    generateTxtFile(scanner);
                    break;
                case 8:
                    exitFromClass();
                    break;
                case 9:
                    System.out.println("Exiting the program...");
                    choiceProgram = true; 
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void addClassName(Scanner scanner) {
        if (currentClassName.isEmpty()) {
            System.out.println("Enter Class Name:");
            String className = scanner.nextLine();
            if (!classNames.containsKey(className)) {
                classNames.put(className, new HashSet<>());
                registers.put(className, new HashMap<>());
                currentClassName = className;
                System.out.println("Class name added successfully.");
            } else {
                System.out.println("Class name already exists.");
            }
        } else {
            System.out.println("You are currently working in class: " + currentClassName);
        }
    }

    private static void addDateForAttendance(Scanner scanner) {
        if (!currentClassName.isEmpty()) {
            System.out.println("Enter Date for Attendance (DD-MM-YYYY):");
            String date = scanner.nextLine();
            if (!registers.get(currentClassName).containsKey(date)) {
                registers.get(currentClassName).put(date, new HashMap<>());
                System.out.println("Date added successfully.");
            } else {
                System.out.println("Date already exists for this class.");
            }
        } else {
            System.out.println("Please enter a class name first.");
        }
    }

     private static void addStudentName(Scanner scanner) {
        if (!currentClassName.isEmpty()) {
            System.out.println("Enter Student Name (Comma-separated for multiple students):");
            String[] studentNames = scanner.nextLine().split(",");
            for (String studentName : studentNames) {
                studentName = studentName.trim();
                if (!studentName.isEmpty() && !classNames.get(currentClassName).contains(studentName)) {
                    classNames.get(currentClassName).add(studentName);

                    // Generate a roll number for the student
                    int rollNumber = generateRollNumber(currentClassName);
                    rollNumbers.computeIfAbsent(currentClassName, k -> new HashMap<>()).put(studentName, rollNumber);

                    System.out.println(studentName + " added successfully with roll number: " + studentName+"_" + currentClassName+"_" + String.format("%02d", rollNumber));
                } else {
                    System.out.println(studentName + " already exists in this class.");
                }
            }
        } else {
            System.out.println("Please enter a class name first.");
        }
    }
     
    private static int generateRollNumber(String className) {
        int rollNumber = 1;
        Map<String, Integer> classRollNumbers = rollNumbers.get(className);
        if (classRollNumbers != null) {
            while (classRollNumbers.containsValue(rollNumber)) {
                rollNumber++;
            }
        }
        return rollNumber;
    }
    private static void markAttendance(Scanner scanner) {
        if (!currentClassName.isEmpty()) {
            System.out.println("Enter Date for Attendance (DD-MM-YYYY):");
            String date = scanner.nextLine();
            if (registers.get(currentClassName).containsKey(date)) {
                Map<String, Integer> attendanceMap = registers.get(currentClassName).get(date);
                Set<String> studentNames = classNames.get(currentClassName);
                for (String studentName : studentNames) {
                    studentName = studentName.trim();
                    System.out.println("Mark Attendance for " + studentName + " (1 for Present, 0 for Absent):");
                    int attendance = scanner.nextInt();
                    attendanceMap.put(studentName, attendance);
                    System.out.println(studentName + " is marked as " + (attendance == 1 ? "Present" : "Absent") + " on " + date);
                }
            } else {
                System.out.println("Date does not exist for this class.");
            }
        } else {
            System.out.println("Please enter a class name first.");
        }
    }
    
    private static void showClassReport(Scanner scanner) {
        if (!currentClassName.isEmpty()) {
            System.out.println("Enter Date for Attendance (DD-MM-YYYY):");
            String date = scanner.nextLine();
            if (registers.get(currentClassName).containsKey(date)) {
                Map<String, Integer> attendanceMap = registers.get(currentClassName).get(date);
                System.out.println("Class: " + currentClassName + " Date: " + date);
                for (String studentName : classNames.get(currentClassName)) {
                    int attendance = attendanceMap.getOrDefault(studentName, 0);
                    System.out.println(studentName + " is " + (attendance == 1 ? "Present" : "Absent"));
                }
            } else {
                System.out.println("Date does not exist for this class.");
            }
        } else {
            System.out.println("Please enter a class name first.");
        }
    }

    private static void showAllReports(Scanner scanner) {
        System.out.println("All Class Reports:");

        // Read and display reports from the saved text file
        try {
            System.out.println("Enter the file name to read (e.g., myAttendance.txt):");
            Scanner fileScanner = new Scanner(new File(scanner.nextLine()));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                System.out.println(line);
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error while reading the file.");
        }
    }

    private static void generateTxtFile(Scanner scanner) {
        try {
            if (!currentClassName.isEmpty()) {
                System.out.println("Enter file name to save (e.g., myAttendance.txt):");
                String fileName = scanner.nextLine();
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

                String className = currentClassName;
                Map<String, Set<String>> classStudents = classNames;
                Map<String, Map<String, Integer>> classRegisters = registers.get(className);

                writer.write("Class: " + className);
                writer.newLine();

                for (String date : classRegisters.keySet()) {
                    Map<String, Integer> attendanceMap = classRegisters.get(date);
                    writer.write("Date: " + date);
                    writer.newLine();

                    // Create a sorted list of student names
                    List<String> sortedStudentNames = new ArrayList<>(classStudents.get(className));
                    Collections.sort(sortedStudentNames);

                    int totalStudents = sortedStudentNames.size();
                    int presentStudents = 0;
                    int absentStudents = 0;

                    for (String studentName : sortedStudentNames) {
                        int attendance = attendanceMap.getOrDefault(studentName, 0);
                        String rollNumber = studentName + className + String.format("%02d", rollNumbers.get(className).get(studentName));
                        writer.write(rollNumber + " - " + studentName + " is " + (attendance == 1 ? "Present" : "Absent"));
                        writer.newLine();

                        if (attendance == 1) {
                            presentStudents++;
                        } else {
                            absentStudents++;
                        }
                    }

                    writer.write("Total Students: " + totalStudents);
                    writer.newLine();
                    writer.write("Present Students: " + presentStudents);
                    writer.newLine();
                    writer.write("Absent Students: " + absentStudents);
                    writer.newLine();
                    writer.newLine();
                }

                writer.close();
                System.out.println("File saved successfully.");
            } else {
                System.out.println("Please enter a class name first.");
            }
        } catch (IOException e) {
            System.out.println("Error while saving the file.");
        }
    }
    private static void exitFromClass() {
        if (!currentClassName.isEmpty()) {
            System.out.println("Exiting from class: " + currentClassName);
            currentClassName = ""; 
        } else {
            System.out.println("You are not currently in any class.");
        }
    }
}

