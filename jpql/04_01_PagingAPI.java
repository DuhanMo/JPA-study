TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m ORDER BY m.username DESC", Member.class);
query.setFirstResult(10); // 시작이 10이라는 뜻, 11번째부터 20건의 데이터를 조회한다.
query.setMaxResults(20);
query.getResultList();