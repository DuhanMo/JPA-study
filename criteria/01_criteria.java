/**
 * Criteria는 결과적으로 jpql생성을 도와주는 클래스이다.
 * 하지만 장황해서 직관적으로 이해하기가 힘들다는 단점이 있다.
 * */

// JPQL: select m from Member m
// Criteria 쿼리 시작
CriteriaBuilder cb = em.getCriteriaBuilder(); // Criteria 쿼리 빌더

// Criteria 생성, 반환 타입 지정
CriteriaQuery<Member> cq = cb.createQuery(Member.class);

Root<Member> m = cq.from(Member.class); // FROM 절
cq.select(m); // SELECT 절

TypedQuery<Member> query = em.createQuery(cq);
List<Member> members = query.getResultList();

/**
 * JPQL 
 * select m from Member m
 * where m.username='회원1'
 * order by m.age desc
 * */

CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Member> cq = cb.createQuery(Member.class);
Root<Member> m = cq.from(Member.class);
// 검색조건 정의
Predicate usernameEqual = cb.equal(m.get("username"), "회원1");
// 정렬 조건 정의
javax.persistence.criteria.Order ageDesc = cb.desc(m.get("age"));
// 쿼리 생성
cq.select(m)
	.where(usernameEqual) 	// WHERE 절 생성
	.orderBy(ageDesc);		// ORDER BY 절 생성

List<Member> resultList = em.createQuery(cq).getResultList();


// 10살을 초과하는 회원을 조회 후 나이 역순으로 정렬
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Member> cq = cb.createQuery(Member.class);
Root<Member> m = cq.from(Member.class);

// Predicate ageOver = cb.greaterThan(m.get("age"), 10); << age의 타입을 알지못한다 에러발생
Predicate ageOver = cb.greaterThan(m.<Integer>get("age"), 10);
javax.persistence.criteria.Order ageDesc = cb.desc(m.get("age"));

cq.select(m);
cq.where(ageOver);
cq.orderBy(ageDesc);
