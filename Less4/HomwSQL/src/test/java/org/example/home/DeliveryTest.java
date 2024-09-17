package org.example.home;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {
    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    static void setup() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @BeforeEach
    void beforeEach() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void afterEach() {
        if (session.getTransaction().isActive()) {
            session.getTransaction().commit();
        }
        session.close();
    }

    @AfterAll
    static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    void testCreateReadUpdateDelete() {
        // Create
        DeliveryEntity delivery = new DeliveryEntity();
        delivery.setDeliveryId((short) 1);
        delivery.setDateArrived("2023-10-10");
        delivery.setTaken("Y");
        delivery.setPaymentMethod("Credit Card");

        session.save(delivery);
        session.getTransaction().commit();

        // Read
        session.beginTransaction();
        DeliveryEntity fetchedDelivery = session.get(DeliveryEntity.class, (short) 1);
        assertNotNull(fetchedDelivery);
        assertEquals("2023-10-10", fetchedDelivery.getDateArrived());

        // Update
        fetchedDelivery.setDateArrived("2023-10-11");
        session.update(fetchedDelivery);
        session.getTransaction().commit();

        session.beginTransaction();
        DeliveryEntity updatedDelivery = session.get(DeliveryEntity.class, (short) 1);
        assertEquals("2023-10-11", updatedDelivery.getDateArrived());

        // Delete
        session.delete(updatedDelivery);
        session.getTransaction().commit();

        session.beginTransaction();
        DeliveryEntity deletedDelivery = session.get(DeliveryEntity.class, (short) 1);
        assertNull(deletedDelivery);
        session.getTransaction().commit();
    }
}