package com.ArcSoftware;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner inputScanner = new Scanner(System.in);
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS todos (id IDENTITY, text VARCHAR, is_done BOOLEAN)");

        while (true) {
            System.out.println("1. Create to-do item");
            System.out.println("2. Toggle to-do item");
            System.out.println("3. List to-do items");

            String option = inputScanner.nextLine();

            if (option.contains("1") || option.equalsIgnoreCase("create")) {
                System.out.println("Enter your to-do item:");
                String text = inputScanner.nextLine();

//                ToDoItem item = new ToDoItem(text, false);
//                items.add(item);
                ToDoItem.insertToDo(conn, text);
            } else if (option.contains("2") || option.equalsIgnoreCase("toggle")) {
                System.out.println("Enter the number of the item you want to toggle:");
                int itemNum = Integer.valueOf(inputScanner.nextLine());
//                ToDoItem item = items.get(itemNum - 1);
//                item.isDone = !item.isDone;
                ToDoItem.toggleToDo(conn, itemNum);

            } else if (option.contains("3") || option.equalsIgnoreCase("list")) {
                ArrayList<ToDoItem> items = ToDoItem.selectToDos(conn);
//              int i = 1;
                for (ToDoItem item : items) {
                    String checkbox = "[ ] ";
                    if (item.isDone) {
                        checkbox = "[X]";
                    }
                    System.out.printf("%s %s. %s\n", checkbox, item.id, item.text);
//                    i++;
                }
            } else {
                System.out.println("Invalid Option.");
            }

        }

    }
}
