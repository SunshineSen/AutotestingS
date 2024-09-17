package org.example.home;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class OrdersProductsTest {
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
        // Create Primary Key
        OrdersProductsEntityPK pk = new OrdersProductsEntityPK();
        pk.setOrderId((short) 1);
        pk.setProductId((short) 1);

        // Create
        OrdersProductsEntity ordersProducts = new OrdersProductsEntity();
        ordersProducts.setOrderId(pk.getOrderId());
        ordersProducts.setProductId(pk.getProductId());
        ordersProducts.setQuantity((short) 10);

        session.save(ordersProducts);
        session.getTransaction().commit();

        // Read
        session.beginTransaction();
        OrdersProductsEntity fetchedOrdersProducts = session.get(OrdersProductsEntity.class, pk);
        assertNotNull(fetchedOrdersProducts);
        assertEquals(10, fetchedOrdersProducts.getQuantity());

        // Update
        fetchedOrdersProducts.setQuantity((short) 20);
        session.update(fetchedOrdersProducts);
        session.getTransaction().commit();

        session.beginTransaction();
        OrdersProductsEntity updatedOrdersProducts = session.get(OrdersProductsEntity.class, pk);
        assertEquals(20, updatedOrdersProducts.getQuantity());

        // Delete
        session.delete(updatedOrdersProducts);
        session.getTransaction().commit();

        session.beginTransaction();
        OrdersProductsEntity deletedOrdersProducts = session.get(OrdersProductsEntity.class, pk);
        assertNull(deletedOrdersProducts);
        session.getTransaction().commit();
    }
}
