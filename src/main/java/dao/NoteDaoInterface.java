package dao;

import java.util.List;

public interface NoteDaoInterface {

    public int create(NoteEntity note);

    public void delete(NoteEntity note);

    public void update(NoteEntity note);

    public NoteEntity findById(Integer id);

    public List<NoteEntity> getAllNotes();

}