package dao;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class NoteService {
    private static NoteDao NoteDao;

    public NoteService() {
        NoteDao = new NoteDao();
    }

    public int create(Note entity) {
        NoteDao.openCurrentSessionWithTransaction();
        int id = NoteDao.create(entity);
        NoteDao.closeCurrentSessionWithTransaction();

        return id;
    }

    public void update(Note entity) {
        NoteDao.openCurrentSessionWithTransaction();
        NoteDao.update(entity);
        NoteDao.closeCurrentSessionWithTransaction();
    }

    public Note findById(Integer id) {
        NoteDao.openCurrentSession();
        Note entity = NoteDao.findById(id);
        NoteDao.closeCurrentSession();

        return entity;
    }

    public void delete(Integer id) {
        NoteDao.openCurrentSessionWithTransaction();
        Note entity = NoteDao.findById(id);
        NoteDao.delete(entity);
        NoteDao.closeCurrentSessionWithTransaction();
    }

    public List<Note> findAll() {
        NoteDao.openCurrentSession();
        List<Note> notes = NoteDao.getAllNotes();
        NoteDao.closeCurrentSession();
        return notes;
    }
}