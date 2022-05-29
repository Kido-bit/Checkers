package checkers.database.dao;

import checkers.database.model.MoveEntity;
import checkers.utils.HibernateFactory;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@AllArgsConstructor
public class MoveDao {
    private HibernateFactory hibernateFactory;

    public void add(MoveEntity move) {
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(move);
        transaction.commit();
        session.close();
    }

    public List<MoveEntity> getAll() {
        Session session = hibernateFactory.getSessionFactory().openSession();
        List<MoveEntity> fromMove = session.createQuery("FROM MoveEntity", MoveEntity.class).list();
        session.close();
        return fromMove;
    }

    public void reset() {
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM MoveEntity").executeUpdate();
        transaction.commit();
        session.close();
    }
}
