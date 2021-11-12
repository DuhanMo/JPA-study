package jpabook.model;

import jpabook.model.entity.Member;
import jpabook.model.entity.Order;
import jpabook.model.entity.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

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

        Order order = new Order();
        order.setMemberId(1L);
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(new Date());
        em.persist(order);

        Order findOrder = em.find(Order.class, order.getId());
        // Member findMember = findOrder.getMember(); 이렇게 해야 객체지향적인 방법
        Member findMember = em.find(Member.class, findOrder.getMemberId());

        System.out.println(findOrder);
        System.out.println(findMember);
    }
}
