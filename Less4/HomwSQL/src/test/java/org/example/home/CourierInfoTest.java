package org.example.home;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CourierInfoTest {
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
        CourierInfoEntity courier = new CourierInfoEntity();
        courier.setCourierId((short) 1);
        courier.setFirstName("John");
        courier.setLastName("Doe");
        courier.setPhoneNumber("1234567890");
        courier.setDeliveryType("Express");

        session.save(courier);
        session.getTransaction().commit();

        // Read
        session.beginTransaction();
        CourierInfoEntity fetchedCourier = session.get(CourierInfoEntity.class, (short) 1);
        assertNotNull(fetchedCourier);
        assertEquals("John", fetchedCourier.getFirstName());

        // Update
        fetchedCourier.setFirstName("Jane");
        session.update(fetchedCourier);
        session.getTransaction().commit();

        session.beginTransaction();
        CourierInfoEntity updatedCourier = session.get(CourierInfoEntity.class, (short) 1);
        assertEquals("Jane", updatedCourier.getFirstName());

        // Delete
        session.delete(updatedCourier);
        session.getTransaction().commit();

        session.beginTransaction();
        CourierInfoEntity deletedCourier = session.get(CourierInfoEntity.class, (short) 1);
        assertNull(deletedCourier);
        session.getTransaction().commit();
    }
}
