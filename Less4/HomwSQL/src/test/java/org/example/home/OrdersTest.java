package org.example.home;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class OrdersTest {
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
        OrdersEntity order = new OrdersEntity();
        order.setOrderId((short) 1);
        order.setDateGet("2023-10-10");

        session.save(order);
        session.getTransaction().commit();

        // Read
        session.beginTransaction();
        OrdersEntity fetchedOrder = session.get(OrdersEntity.class, (short) 1);
        assertNotNull(fetchedOrder);
        assertEquals("2023-10-10", fetchedOrder.getDateGet());

        // Update
        fetchedOrder.setDateGet("2023-10-11");
        session.update(fetchedOrder);
        session.getTransaction().commit();

        session.beginTransaction();
        OrdersEntity updatedOrder = session.get(OrdersEntity.class, (short) 1);
        assertEquals("2023-10-11", updatedOrder.getDateGet());

        // Delete
        session.delete(updatedOrder);
        session.getTransaction().commit();

        session.beginTransaction();
        OrdersEntity deletedOrder = session.get(OrdersEntity.class, (short) 1);
        assertNull(deletedOrder);
        session.getTransaction().commit();
    }
}