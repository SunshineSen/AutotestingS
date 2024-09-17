package org.example.home;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomersTest {
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
        CustomersEntity customer = new CustomersEntity();
        customer.setCustomerId((short) 1);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPhoneNumber("1234567890");
        customer.setDistrict("District 1");
        customer.setStreet("Street 1");
        customer.setHouse("House 1");
        customer.setApartment("Apartment 1");

        session.save(customer);
        session.getTransaction().commit();

        // Read
        session.beginTransaction();
        CustomersEntity fetchedCustomer = session.get(CustomersEntity.class, (short) 1);
        assertNotNull(fetchedCustomer);
        assertEquals("John", fetchedCustomer.getFirstName());

        // Update
        fetchedCustomer.setFirstName("Jane");
        session.update(fetchedCustomer);
        session.getTransaction().commit();

        session.beginTransaction();
        CustomersEntity updatedCustomer = session.get(CustomersEntity.class, (short) 1);
        assertEquals("Jane", updatedCustomer.getFirstName());

        // Delete
        session.delete(updatedCustomer);
        session.getTransaction().commit();

        session.beginTransaction();
        CustomersEntity deletedCustomer = session.get(CustomersEntity.class, (short) 1);
        assertNull(deletedCustomer);
        session.getTransaction().commit();
    }
}