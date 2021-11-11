package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // [엔티티 매니저 팩토리] - 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        // [엔티티 매니저] - 생성
        EntityManager em = emf.createEntityManager();
        // [트랜잭션] - 획득
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // [트랜잭션] - 시작
            logic(em); // 비즈니스 로직 실행
            tx.commit(); // [트랜잭션] - 커밋
        } catch (Exception e) {
            tx.rollback(); // [트랜잭션] - 롤백
        } finally {
            em.close(); // [엔티티 매니저] - 종료
        }
        emf.close(); // [엔티티 매니저 팩토리] - 종료
    }

    // 비즈니스 로직
    private static void logic(EntityManager em) {
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setId("1");
        member1.setUsername("모두한");
        member1.setAge(27);
        em.persist(member1);
        member2.setId("2");
        member2.setUsername("dubi");
        member2.setAge(223);
        em.persist(member2);

        // set으로 값만 변경해도 업데이트가 된다. -> em이 엔티티를 추적하고 있기 때문에 가능하다.
        member1.setAge(30);
        Member findMember = em.find(Member.class, "1");
        System.out.println("findMember = " + findMember.toString());
        // 목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size = " + members.size());
        // 삭제
        em.remove(member1);
    }
}
