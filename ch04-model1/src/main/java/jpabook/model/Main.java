package jpabook.model;

import jpabook.model.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(EntityManager em) {
        Member member = new Member();
        member.setName("모두한");
        member.setCity("인천");
        member.setStreet("솔샘로");
        member.setZipcode("123");
        em.persist(member);

        Item item = new Item();
        item.setName("맥북");
        item.setPrice(3400000);
        item.setStockQuantity(21);
        em.persist(item);

        Order order = new Order();
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(new Date());
        order.setMember(member);
        em.persist(order);

        OrderItem orderItem = new OrderItem();
        int count = 2;
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice() * count);
        em.persist(orderItem);
    }
}
