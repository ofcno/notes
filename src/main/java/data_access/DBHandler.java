package data_access;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.inject.Singleton
public class DBHandler implements DataLayer {

    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost/testdb";
    private final String USERNAME = "root";
    private final String PASSWORD = "pass";
    private final String TABLE = "table1";
    private Connection connection;

    public DBHandler() {
        this.makeConnection();
    }

    @Override
    protected void finalize() throws Throwable {
        this.closeConnection();
    }

    @Override
    public int addNewNote(String noteTitle, String noteContent) {
        String query = "INSERT INTO " + TABLE + " (table1title, table1content) " +
                "VALUES ('" + noteTitle + "', '" + noteContent + "');";
        int newNoteId = 0;
        try (Statement statement = this.getConnection().createStatement()) {
            statement.execute(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                newNoteId = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newNoteId;
    }

    @Override
    public void removeNote(int noteId) {
        String query = "DELETE FROM " + TABLE + " WHERE idtable1=" + noteId + ";";
        try (Statement statement = this.getConnection().createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeNote(int noteId, String title, String content) {
        String query;
        if (!title.equals("") && !content.equals(""))
            query = "UPDATE " + TABLE +
                    " SET table1title='" + title + "', table1content='" + content +
                    "' WHERE idtable1=" + noteId + ";";
        else if (!title.equals("") && content.equals(""))
            query = "UPDATE " + TABLE +
                    " SET table1title='" + title +
                    "' WHERE idtable1=" + noteId + ";";
        else if (title.equals("") && !content.equals(""))
            query = "UPDATE " + TABLE +
                    " SET table1content='" + content +
                    "' WHERE idtable1=" + noteId + ";";
        else throw new IllegalArgumentException();

        try (Statement statement = this.getConnection().createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<Integer, List<String>> getAllNotes() {
        Map<Integer, List<String>> notes = new HashMap<>();
        String query = "SELECT * FROM table1";
        try (Statement statement = this.getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    notes.put(resultSet.getInt("idtable1"), Arrays.asList
                            (resultSet.getString("table1title"), resultSet.getString("table1content")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    private void makeConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        if (connection == null)
            throw new IllegalStateException("connection is not established");
        return connection;
    }
}