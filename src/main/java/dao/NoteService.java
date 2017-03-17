package dao;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class NoteService {
    private static NoteDao NoteDao;

    public NoteService() {
        NoteDao = new NoteDao();
    }

    public int create(NoteEntity entity) {
        NoteDao.openCurrentSessionWithTransaction();
        int id = NoteDao.create(entity);
        NoteDao.closeCurrentSessionWithTransaction();

        return id;
    }

    public void update(NoteEntity entity) {
        NoteDao.openCurrentSessionWithTransaction();
        NoteDao.update(entity);
        NoteDao.closeCurrentSessionWithTransaction();
    }

    public NoteEntity findById(Integer id) {
        NoteDao.openCurrentSession();
        NoteEntity entity = NoteDao.findById(id);
        NoteDao.closeCurrentSession();

        return entity;
    }

    public void delete(Integer id) {
        NoteDao.openCurrentSessionWithTransaction();
        NoteEntity entity = NoteDao.findById(id);
        NoteDao.delete(entity);
        NoteDao.closeCurrentSessionWithTransaction();
    }

    public List<NoteEntity> findAll() {
        NoteDao.openCurrentSession();
        List<NoteEntity> notes = NoteDao.getAllNotes();
        NoteDao.closeCurrentSession();
        return notes;
    }
}