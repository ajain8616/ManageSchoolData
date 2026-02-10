package Administrator;
import java.io.*;
import java.util.*;

public class NewRegister {
    private static String[] classNames = new String[100];
    private static int classCount = 0;
    private static String[] dates = new String[100];
    private static int dateCount = 0;
    private static String[][] studentNames = new String[100][100];
    private static int[] studentCount = new int[100];
    private static int[][][] registers = new int[100][100][100];
    private static int[] currentClassIndex = new int[100];

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
            System.out.println("9. Generate Roll Numbers");
            System.out.println("10. Exit Program");
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
                    showAllReports();
                    break;
                case 7:
                    generateTxtFile(scanner);
                    break;
                case 8:
                    exitFromClass();
                    break;
                    case 9:
                    generateRollNumbers(scanner);
                    break;
                 case 10:
                    System.out.println("Exiting the program...");
                    choiceProgram = true; 
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addClassName(Scanner scanner) {
        if (classCount < 100) {
            System.out.println("Enter Class Name:");
            String className = scanner.nextLine();
            boolean classExists = false;
            for (int i = 0; i < classCount; i++) {
                if (classNames[i].equalsIgnoreCase(className)) {
                    classExists = true;
                    break;
                }
            }
            if (!classExists) {
                classNames[classCount] = className;
                classCount++;
                System.out.println("Class name added successfully.");
            } else {
                System.out.println("Class name already exists.");
            }
        } else {
            System.out.println("Maximum number of classes reached.");
        }
    }

    private static void addDateForAttendance(Scanner scanner) {
        if (classCount > 0) {
            int currentClass = classCount - 1;
            if (currentClassIndex[currentClass] < dateCount) {
                currentClassIndex[currentClass]++;
                return;
            }

            System.out.println("Enter Date for Attendance (DD-MM-YYYY):");
            String date = scanner.nextLine();
            boolean dateExists = false;
            for (int i = 0; i < dateCount; i++) {
                if (dates[i].equalsIgnoreCase(date)) {
                    dateExists = true;
                    break;
                }
            }
            if (!dateExists) {
                dates[dateCount] = date;
                dateCount++;
                System.out.println("Date added successfully.");
            } else {
                System.out.println("Date already exists for this class.");
            }
        } else {
            System.out.println("Please enter a class name first.");
        }
    }

    private static void addStudentName(Scanner scanner) {
        if (classCount > 0) {
            int currentClass = classCount - 1;
            if (currentClassIndex[currentClass] < studentCount[currentClass]) {
                currentClassIndex[currentClass]++;
                return;
            }

            System.out.println("Enter Student Name (Comma-separated for multiple students):");
            String[] studentNamesArray = scanner.nextLine().split(",");
            for (String studentName : studentNamesArray) {
                studentName = studentName.trim();
                if (!studentName.isEmpty()) {
                    boolean studentExists = false;
                    for (int i = 0; i < studentCount[currentClass]; i++) {
                        if (studentNames[currentClass][i].equalsIgnoreCase(studentName)) {
                            studentExists = true;
                            break;
                        }
                    }
                    if (!studentExists) {
                        studentNames[currentClass][studentCount[currentClass]] = studentName;
                        studentCount[currentClass]++;
                        System.out.println(studentName + " added successfully.");
                    } else {
                        System.out.println(studentName + " already exists in this class.");
                    }
                }
            }
        } else {
            System.out.println("Please enter a class name first.");
        }
    }

    private static void markAttendance(Scanner scanner) {
        if (classCount > 0) {
            int currentClass = classCount - 1;
            int currentDate = currentClassIndex[currentClass];

            if (currentDate < dateCount) {
                System.out.println("Mark Attendance for " + classNames[currentClass] + " on " + dates[currentDate]);
                for (int i = 0; i < studentCount[currentClass]; i++) {
                    System.out.println("Mark Attendance for " + studentNames[currentClass][i] + " (1 for Present, 0 for Absent):");
                    int attendance = scanner.nextInt();
                    registers[currentClass][currentDate][i] = attendance;
                    System.out.println(studentNames[currentClass][i] + " is marked as " + (attendance == 1 ? "Present" : "Absent") + " on " + dates[currentDate]);
                }
            } else {
                System.out.println("Please enter a date for attendance first.");
            }
        } else {
            System.out.println("Please enter a class name first.");
        }
    }

    private static void showClassReport(Scanner scanner) {
        if (classCount > 0) {
            int currentClass = classCount - 1;
            int currentDate = currentClassIndex[currentClass];

            if (currentDate < dateCount) {
                System.out.println("Class: " + classNames[currentClass] + " Date: " + dates[currentDate]);
                for (int i = 0; i < studentCount[currentClass]; i++) {
                    int attendance = registers[currentClass][currentDate][i];
                    System.out.println(studentNames[currentClass][i] + " is " + (attendance == 1 ? "Present" : "Absent"));
                }
            } else {
                System.out.println("Please enter a date for attendance first.");
            }
        } else {
            System.out.println("Please enter a class name first.");
        }
    }

    private static void showAllReports() {
        System.out.println("All Class Reports:");
        if (classCount > 0) {
            int currentClass = classCount - 1;
            System.out.println("Class: " + classNames[currentClass]);
            for (int j = 0; j < dateCount; j++) {
                System.out.println("Date: " + dates[j]);
                for (int k = 0; k < studentCount[currentClass]; k++) {
                    int attendance = registers[currentClass][j][k];
                    System.out.println(studentNames[currentClass][k] + " is " + (attendance == 1 ? "Present" : "Absent"));
                }
            }
        } else {
            System.out.println("Please enter a class name first.");
        }
    }
    

    private static void generateTxtFile(Scanner scanner) {
        try {
            if (classCount > 0) {
                System.out.println("Enter file name to save (e.g., myAttendance.txt):");
                String fileName = scanner.nextLine();
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
    
                // Create a TreeMap to store student names and roll numbers
                TreeMap<String, String> studentRollNumbers = new TreeMap<>();
    
                for (int i = 0; i < classCount; i++) {
                    writer.write("Class: " + classNames[i]+"\n");
                    writer.newLine();
                    for (int j = 0; j < dateCount; j++) {
                        writer.write("Date: " + dates[j]+"\n");
                        writer.newLine();
                        int totalStudents = studentCount[i];
                        int presentStudents = 0;
                        int absentStudents = 0;
                        for (int k = 0; k < totalStudents; k++) {
                            int attendance = registers[i][j][k];
                            String studentName = studentNames[i][k];
                            // Store student names and roll numbers in the TreeMap
                            studentRollNumbers.put(studentName, generateFormattedRollNumber(studentName, classNames[i], k + 1));
    
                            writer.write(studentName + " is " + (attendance == 1 ? "Present" : "Absent"));
                            writer.newLine();
                            if (attendance == 1) {
                                presentStudents++;
                            } else {
                                absentStudents++;
                            }
                        }
                        writer.write("Total Students: " + totalStudents+"\n");
                        writer.newLine();
                        writer.write("Present Students: " + presentStudents+"\n");
                        writer.newLine();
                        writer.write("Absent Students: " + absentStudents+"\n");
                        writer.newLine();
                        writer.newLine();
                    }
                }
    
                // Sort and write student names and roll numbers to the file
                for (Map.Entry<String, String> entry : studentRollNumbers.entrySet()) {
                    writer.write(entry.getKey() + " - Roll Number: " + entry.getValue()+"\n");
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
        if (classCount > 0) {
            System.out.println("Exiting from class: " + classNames[classCount - 1]);
            currentClassIndex[classCount - 1] = 0;
        } else {
            System.out.println("You are not currently in any class.");
        }
    }
    private static void generateRollNumbers(Scanner scanner) {
        if (classCount > 0) {
            System.out.println("Generating Roll Numbers for Class: " + classNames[classCount - 1]);
            String className = classNames[classCount - 1];
            int currentClass = classCount - 1;
            int rollNumberCounter = 1;
    
            // Iterate through the students in the current class
            for (int i = 0; i < studentCount[currentClass]; i++) {
                String studentName = studentNames[currentClass][i];
                String formattedRollNumber = generateFormattedRollNumber(studentName, className, rollNumberCounter);
                System.out.println("Generated Roll Number: " + formattedRollNumber);
                rollNumberCounter++;
            }
        } else {
            System.out.println("Please enter a class name first.");
        }
    }
    
    private static String generateFormattedRollNumber(String studentName, String className, int rollNumberCounter) {
        String formattedRollNumber = studentName.toUpperCase().replaceAll(" ", "") + "_" +
                className.toUpperCase().replaceAll(" ", "") + "_" +
                String.format("%02d", rollNumberCounter);
        return formattedRollNumber;
    }
    
}