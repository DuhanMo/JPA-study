/**
 * JPQL 동적쿼리
 * */
Integer age = 10;
String username = null;
String teamName = "팀A";

// JPQL 동적 쿼리 생성
StringBuilder jpql = new StringBuilder("select m from Member m join m.team t)");
List<String> criteria = new ArrayList<String>();

if (age != null) criteria.add(" m.age = :age ");
if (username != null) criteria.add(" m.username = :username ");
if (teamName != null) criteria.add(" t.name = :teamName ");

if (criteria.size() > 0) jpql.append(" where ");

for (int i = 0; i < criteria.size(); i++) {
	if (i > 0) jpql.append(" and ");
	jpql.append(criteria.get(i));
}

TypedQuery<Member> query = em.createQuery(jpql.toString(), Member.class);
if (age != null) query.setParameter("age", age);
if (username != null) query.setParamter("username", username);
if (teamName != null) query.setParameter("teamName", teamName);

List<Member> resultList = query.getResultList();

// 어렵다. 타입세이프하지 않다. 버그가 발생할 확률이 높다.

/**
 * Criteria 동적쿼리
 * */

// 검색 조건
Integer age = 10;
String username = null;
String teamName = "팀A";

// Criteria 동적쿼리 생성
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Member> cq = cb.createQuery(Member.class);

Root<Member> m = cq.from(Member.class);
Join<Member, Team> t = m.join("team");
List<Predicate> criteria = new ArrayList<Predicate>();

if (age != null) criteria.add(cb.equal(m.<Integer>get("age"),
	cb.paramter(Integer.class, "age")));
if (username != null) criteria.add(cb.equal(m.get("username"),
	cb.paramter(String.class, "username")));
if (teamName != null) criteria.add(cb.equal(t.get("name"),
	cb.paramter(String.class, "teamName")));
cq.where(cb.and(critera.toArray(new Predicate[0])));

TypedQuery<Member> query = em.createQuery(jpql.toString(), Member.class);
if (age != null) query.setParameter("age", age);
if (username != null) query.setParamter("username", username);
if (teamName != null) query.setParameter("teamName", teamName);

List<Member> resultList = query.getResultList();