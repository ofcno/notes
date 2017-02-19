package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class NoteDao implements NoteDaoInterface {

    private Session currentSession;
    private Transaction currentTransaction;

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Note.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        return sessionFactory;
    }

    Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();

        return currentSession;
    }

    void closeCurrentSession() {
        currentSession.close();
    }

    Session openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();

        return currentSession;
    }

    void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private Session getCurrentSession() {
        return currentSession;
    }

    private Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    @Override
    public int create(Note entity) {
        return (Integer) getCurrentSession().save(entity);
    }

    @Override
    public void update(Note entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Note findById(Integer id) {
        return getCurrentSession().get(Note.class, id);
    }

    @Override
    public void delete(Note entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Note> getAllNotes() {
        return (List<Note>) getCurrentSession().createQuery("FROM Note").list();
    }
}
