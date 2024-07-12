package org.example;

import java.io.*;
import java.util.*;

// Student class to represent a student
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private String id;

    // Constructor to initialize Student object
    public Student(String name, int age, String id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    // getters for Student attributes
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getId() {
        return id;
    }

    // Override toString() method to print student details
    @Override
    public String toString() {
        return "Student [Name: " + name + ", Age: " + age + ", ID: " + id + "]";
    }
}

// Class to manage students
class StudentManager {
    private List<Student> students;

    // Constructor to initialize student manager
    public StudentManager() {
        students = new ArrayList<>();
        loadStudents();
    }

    // Method to add a student
    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
    }

    // Method to remove the student by ID
    public void removeStudent(String id) {
        students.removeIf(student -> student.getId().equals(id));
        saveStudents();
    }

    // method to view all students
    public List<Student> viewStudents() {
        return new ArrayList<>(students);
    }

    // Method to save students to a file
    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("error saving students: " + e.getMessage());
        }
    }

    // Method to load students from the file
    private void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            students = (List<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing student data found. Starting new.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }
}

public class StudentManagementSystem {
    private static StudentManager manager = new StudentManager();

    // Method to display menu options
    private static void displayMenu() {
        System.out.println("\nStudent Management System");
        System.out.println("1. Add Student");
        System.out.println("2. Remove Student");
        System.out.println("3. View Students");
        System.out.println("4. Exit");
        System.out.println("Enter your choice: ");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Add a new student
                    System.out.println("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.println("enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter ID: ");
                    String id = scanner.nextLine();
                    Student student = new Student(name, age, id);
                    manager.addStudent(student);
                    System.out.println("Student added successfully.");
                    break;
                case 2:
                    // removing a student
                    System.out.println("Enter ID of student to remove: ");
                    id = scanner.nextLine();
                    manager.removeStudent(id);
                    System.out.println("Student removed successfully");
                    break;
                case 3:
                    // Viewing all students
                    System.out.println("Listing all students:");
                    List<Student> students = manager.viewStudents();
                    if (students.isEmpty()) {
                        System.out.println("NBo students recorded");
                    } else {
                        for (Student stud : students) {
                            System.out.println(stud);
                        }
                    }
                    break;
                case 4:
                    // Exiting the program
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
