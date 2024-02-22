package no.ntnu.service;



import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import no.ntnu.database.DatabaseManager;
import no.ntnu.database.Query;

public class AdminService {
    private final DatabaseManager databaseManager;

    @Autowired
    public AdminService(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    public boolean addCourse(String courseName, String description, int createdByUserId) {
        try {
            int rowsAffected = databaseManager.executeUpdate(Query.INSERT_COURSE, statement -> {
                statement.setString(1, courseName);
                statement.setString(2, description);
                statement.setInt(3, createdByUserId);
            });
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeCourse(int courseId) {
        try {
            int rowsAffected = databaseManager.executeUpdate(Query.DELETE_COURSE, statement -> {
                statement.setInt(1, courseId);
            });
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCourse(int courseId, String newCourseName, String newDescription) {
        try {
            int rowsAffected = databaseManager.executeUpdate(Query.UPDATE_COURSE, statement -> {
                statement.setString(1, newCourseName);
                statement.setString(2, newDescription);
                statement.setInt(3, courseId);
            });
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}

