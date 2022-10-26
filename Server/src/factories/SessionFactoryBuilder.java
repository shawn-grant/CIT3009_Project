package factories;

import models.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Malik Heron
 */
public class SessionFactoryBuilder {

    private static final Configuration configuration = new Configuration();
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        try {
            if (sessionFactory == null) {
                configuration.configure("xml/hibernate.cfg.xml")
                        .addAnnotatedClass(Employee.class)
                        .addAnnotatedClass(Customer.class)
                        .addAnnotatedClass(Product.class)
                        .addAnnotatedClass(Invoice.class)
                        .addAnnotatedClass(InvoiceItem.class)
                        .addAnnotatedClass(Department.class)
                        .addAnnotatedClass(Inventory.class)
                        .addAnnotatedClass(InventoryId.class);
                sessionFactory = configuration.buildSessionFactory();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        try {
            if (getSessionFactory() != null) {
                getSessionFactory().close();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
