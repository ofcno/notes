package dao;

import java.util.List;

public interface NoteDaoInterface {

    public int create(Note note);

    public void delete(Note note);

    public void update(Note note);

    public Note findById(Integer id);

    public List<Note> getAllNotes();

}