/**
 * 파라미터 정의
 * */
/*
JPQL
select m from Member m
where m.username = :usernameParam
*/
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery cq = cb.createQuery(Member.class);
Root<Member> m = cq.from(Member.class);

// 정의
cq.select(m)
	.where(cb.equal(m.get("username"), cb.parameter(String.class, "usernameParam")));

List<Member> resultList = em.createQuery(cq)
	.setParameter("usernameParam", "회원1") // 바인딩
	.getREsultList();

