package com.ArcSoftware;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Jake on 4/5/17.
 */
public class ToDoItem {
    public String text;
    public boolean isDone;
    public Integer id;



    public ToDoItem(Integer id, String text, boolean isDone) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
    }
    public static void insertToDo(Connection conn, String text) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO todos VALUES (NULL, ?, false)");
        stmt.setString(1, text);
        stmt.execute();
    }
    public static ArrayList<ToDoItem> selectToDos(Connection conn) throws SQLException {
        ArrayList<ToDoItem> items = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM todos");
        while (results.next()) {
            int id = results.getInt("id");
            String text = results.getString("text");
            boolean isDone = results.getBoolean("is_done");
            items.add(new ToDoItem(id, text, isDone));
        }
        return items;
    }
    public static void toggleToDo(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE todos SET is_done = NOT is_done WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }
}

