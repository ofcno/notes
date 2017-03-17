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
        configuration.addAnnotatedClass(NoteEntity.class);
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
    public int create(NoteEntity entity) {
        return (Integer) getCurrentSession().save(entity);
    }

    @Override
    public void update(NoteEntity entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public NoteEntity findById(Integer id) {
        return getCurrentSession().get(NoteEntity.class, id);
    }

    @Override
    public void delete(NoteEntity entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NoteEntity> getAllNotes() {
        return (List<NoteEntity>) getCurrentSession().createQuery("FROM NoteEntity").list();
    }
}
