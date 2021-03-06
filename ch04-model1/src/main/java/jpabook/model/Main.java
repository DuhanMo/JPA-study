package jpabook.model;

import jpabook.model.entity.*;
import jpabook.model.entity.item.Album;

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
//        member.setCity("인천");
//        member.setStreet("솔샘로");
//        member.setZipcode("123");
        member.setAddress(new Address("인천", "솔샘로", "123"));
        em.persist(member);

        Album item = new Album();
        item.setName("맥북");
        item.setPrice(3400000);
        item.setStockQuantity(21);
        item.setArtist("jobs");
        item.setEtc("기타 등등");
        em.persist(item);

        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.READY);
//        delivery.setCity("인천");
//        delivery.setZipcode("123");
        delivery.setAddress(new Address("인천", "솔샘로", "123"));

        OrderItem orderItem = new OrderItem();
        int count = 2;
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice() * count);

        Order order = new Order();
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(new Date());
        order.setMember(member);
        order.setDelivery(delivery);
        order.addOrderItem(orderItem);
        em.persist(order);

        System.out.println("롬복을 뚫고 직접 작성한 세터가 작동하는가? : " + delivery.getOrder());
    }
}
