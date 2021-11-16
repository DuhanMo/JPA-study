/**
 * Criteria를 사용하려면 CriteriaBuilder.createQuery() 메소드로
 * Criteria 쿼리(CriteriaQuery)를 생성하면 된다.
 * */
public interface CriteriaBuilder {
	CriteriaQuery<Object> createQuery(); // 조회할 반환 타입: Object

	// 조회값 반환 타입: 엔티티, 임베디드 타입, 기타
	<T> CriteriaQuery<T> createQuery(Class<T> resultClass);
	CriteriaQuery<Tuple> createTupleQuery(); // 조회값 반환 타입: Tuple
	...
}

// 1. 반환 타입 지정
CriteriaBuilder cb = em.getCriteriaBuilder();

// Member를 반환타입으로 지정
CriteriaQuery<Member> cq = cb.createQuery(Member.class);
...

// 위에서 Member를 타입으로 지정했으므로 지정하지 않아도 Member 타입을 반환
List<Member> resultList = em.createQuery(cq).getResultList();

// 2. Object로 조회
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Object> cq = cb.createQuery(); // 조회값 반환 타입: object
...
List<Object> resultList = em.createQuery(cq).getREsultList();

// 3.Object[]로 조회
CriteriaBuilder cb = em.getCriteriaBuilder();

// 조회값 반환 타입: Object[]
CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
...
List<Object[]> resultList = em.createQuery(cq).getResultList();


/**
 * 조회
 * */
cq.select(m) // JPQL: select m

// JPQL: select m.username, m.age
cq.multiselect(m.get("username"), m.get("age"));


// DISTINCT
// JPQL: select distinct m.username, m.age
cq.multiselect(m.get("username"), m.get("age")).distinct(true);

// JPQL: select distinct m.username, m.age from Member m
CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
Root<Member> m = cq.from(Member.class);
cq.multiselect(m.get("username"), m.get("age")).distinct(true);
// cq.select(cb.array(m.get("username"), m.get("age"))).distinct(true); 코드와 같음

TypedQuery<Object[]> query = em.createQuery(cq);
List<Object[]> resultList = query.getResultList();

/**
 * NEW, construct()
 * JPQL에서 select new 생성자() 구문을
 * Criteria에서는 cb.construct(클래스 타입, ...)로 사용한다.
 * */
// JPQL: select new jpabook.domain.MemberDTO(m.username, m.age)
// 			from Member m
CriteriaQuery<MemberDTO> cq = cb.createQuery(MemberDTO.class);
Root<Member> m = cq.from(Member.class);

cq.select(cb.construct(MemberDTO.calss, m.get("username"), m.get("age")));

TypedQuery<MemberDTO> query = em.createQuery(cq);
List<MemberDTO> resultList = query.getResultList();


/**
 * 튜플
 * */

// JPQL: select m.username, m.age from Member m
CriteriaBuilder cb = em.getCriteriaBuilder();

CriteriaQuery<Tuple> cq = cb.createTupleQuery();
// CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class); // 위와 같다.

Root<Member> m =cq.from(Member.class);
cq.multiselect(
		m.get("username").alias("username"), // 튜플에서 사용할 튜플 별칭
		m.get("age").alias("age")
);

TypedQuery<Tuple> query = em.createQuery(cq);
List<Tuple> resultList = query.getResultList();
for (Tuple tuple : resultList) {
	// 튜플 별칭으로 조회
	String username = tuple.get("username", String.class);
	Integer age = tuple.get("age", Integer.class);
}

// 튜플을 사용할 때는 별칭을 필수로 주어야 한다.
// 튜플과 엔티티 조회
CriteriaQuery<Tuple> cq = cb.createTupleQuery();
Root<Member> m = cq.from(Member.class);
cq.select(cb.tuple(
	m.alias("m"), // 회원 엔티티, 별칭 m
	m.get("username").alias("username") // 단순 값 조회, 별칭 username
));

TypedQuery<Tuple> query = em.createQuery(cq);
List<Tuple> resultList = query.getResultList();
for (Tuple tuple : resultList) {
	Member member = tuple.get("m", Member.class);
	String username = tuple.get("username", String.class);
}


/**
 * 집합
 * */

// GROUP BY
/*
JPQL:
select m.team.name, max(m.age), min(m.age)
from Member m
group by m.team.name
*/

CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
Root<Member> m = cq.from(Member.class);

Expression maxAge = cb.max(m.<Integer>get("age"));
Expression minAge = cb.min(m.<Integer>get("age"));

cq.multiselect(m.get("team").get("name"), maxAge, minAge);
cq.groupBy(m.get("team").get("name")); // GROUP BY

TypedQuery<Object[]> query = em.createQuery(cq);
List<Object[]> resultList = query.getResultList();


// HAVING
cq.multiselect(m.get("team").get("name"), maxAge, minAge);
cq.groupBy(m.get("team").get("name"));
cq.having(cb.gt(minAge, 10)); // HAVING


/**
 * 정렬
 * */
cq.select(m)
	.where(ageGt)
	.orderBy(cb.desc(m.get("age"))); // JPQL: order by m.age desc


/**
 * 조인
 * 조인은 join() 메소드와 JoinType 클래스를 사용한다.
 * */

/*
JPQL
select m, t from Member m
inner join m.team t
where t.name = '팀ㅁ'
*/
Root<Member> m = cq.from(Member.class);
Join<Member, Team> t = m.join("team", JoinType.INNER); // 내부 조인
cq.multiselect(m, t)
	.where(cb.equal(t.get("name"), "팀A"));

// 쿼리루트(m) 에서 바로 아래 메소드를 통한 조인
m.join("team") // 내부 조인
m.join("team", JoinType.INNER) // 내부 조인
m.join("team", JoinType.LEFT) // 외부 조인

// Fetch Join
Root<Member> m = cq.from(Member.class);
m.fetch("team", JoinType.LEFT);
cq.select(m);
// 페치 조인은 fetch(조인대상.JoinType)을 사용한다. 페치 조인 시 주의사항은 JPQL과 같다.

/**
 * 서브쿼리
 * */
/*
JPQL
select m from Member m
where m.ge >= 
	(select AVG(m2.age) from Member m2)
*/

CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Member> mainQuery = cb.createQuery(Member.class);

// 서브쿼리 생성
SubQuery<Double> subQuery = mainQuery.subquery(Double.class);
Root<Member> m2 = subQuery.from(Member.class);
subQuery.select(cb.avg(m2.<Integer>get("age")));
// 메인쿼리 생성
Root<Member> m = mainQuery.from(Member.class);
mainQuery.select(m)
	.where(cb.ge(m.<Integer>get("age"), subQuery));



