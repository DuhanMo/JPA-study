// 타입을 지정해주는 TypeQuery
TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);

List<Member> Result = query.getReulstList();
for (Member member L resultList) {
	System.out.println("member = " + member);
}

// 타입을 지정해주지 않는 Query
Query query = em.createQuery("SELECT m.username, m.age from Member m");
List result List = query.getReulstList();

for (Object o : resultList) {
	Object[] result = (Obeject[]) o; // 결과가 둘 이상이면 Object[] 반환
	System.out.println("username = " + result[0]);
	System.out.println("age = " + result[1]);
}

