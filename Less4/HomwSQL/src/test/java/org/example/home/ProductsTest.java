package org.example.home;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductsTest {
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
        ProductsEntity product = new ProductsEntity();
        product.setProductId((short) 1);
        product.setMenuName("Cheeseburger");
        product.setPrice("5.99");

        session.save(product);
        session.getTransaction().commit();

        // Read
        session.beginTransaction();
        ProductsEntity fetchedProduct = session.get(ProductsEntity.class, (short) 1);
        assertNotNull(fetchedProduct);
        assertEquals("Cheeseburger", fetchedProduct.getMenuName());

        // Update
        fetchedProduct.setMenuName("Double Cheeseburger");
        session.update(fetchedProduct);
        session.getTransaction().commit();

        session.beginTransaction();
        ProductsEntity updatedProduct = session.get(ProductsEntity.class, (short) 1);
        assertEquals("Double Cheeseburger", updatedProduct.getMenuName());

        // Delete
        session.delete(updatedProduct);
        session.getTransaction().commit();

        session.beginTransaction();
        ProductsEntity deletedProduct = session.get(ProductsEntity.class, (short) 1);
        assertNull(deletedProduct);
        session.getTransaction().commit();
    }
}
