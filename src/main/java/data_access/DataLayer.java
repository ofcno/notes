package data_access;

import java.util.List;
import java.util.Map;

public interface DataLayer {
    // adds new note, returns id
    int addNewNote(String noteTitle, String noteContent);

    // remove note by id
    void removeNote(int noteId);

    // changes note title and/or content
    void changeNote(int noteId, String title, String content);

    // returns map with integer key and list with two elements: note's title and content
    Map<Integer, List<String>> getAllNotes();
}