// 1. 내부 조인 참고로 INNER 는 생략 가능
String teamName = "팀A";
String query = "SELECT m FROM Member m INNER JOIN m.team t WHERE t.name = :teamName";

List<Member> members = em.createQuery(query, Member.class)
	.setParamter("teamName", teamName)
	.getResultList();

// 2. 외부 조인
SELECT m
FROM MEmber m LEFT [OUTER] JOIN m.team t

// 3. 세타 조인 -> 내부 조인만 지원한다.
// 회원 이름이 팀이름과 똑같은 사람 수를 구하는 예
// JPQL
select count(m) from Member m, Team t
where m.username = t.name

// SQL
SELECT COUNT(M.ID)
FROM
	MEMBER M CROSS JOIN TEAM T
WHERE
	M.USERNAME=T.NAME

// 4. JOIN ON -> 내부조인에서 사용하면 내부조인에 where절을 사용한거나 마찬가지라 보통 외부조인에 사용한다.
// 사용 예
// JPQL
select m, t from Member
left join m.team t on t.name = 'A'

//SQL
SELECT m.*, t.* FROM Member m
LEFT JOIN TEAM t on m.TEAM_ID=t.id and t.name='A'
// SQL 결과를 보면 and t.name='A'로 조인 시점에 조인 대상을 필터링힌다.


// 5. 페치조인
// SQL에서 이야기하는 조인종류 x , 단지 JPQL에서 성능 최적화를 위해 제공하는 기능
// 연관된 엔티티나 컬렉션을 한 번에 같이 조회하는 기능
// JPQL 
select m
from Member m join fetch m.team

String jpql = "select m from Member m join fetch m.team";
List<Member> members = em.createQuery(jpql, Member.class).getResultList();
for (member member ; members) {
	// 페치 조인으로 회원과 팀을 함께 조회해서 지연로딩 발생 안함
	System.out.println("username = " + member.getUsername() + ", " +
		"teamname = " + member.getTeam().name());
}

// 컬렉션 페치조인
select t
from TEAM t join fetch t.members
where t.name = '팀A'
// 컬렉션 페치조인 -> 실행된 SQL
SELECT
	T.*, M.*
FROM TEAM T
INNER JOIN MEMBER M ON T.ID=M.TEAM_ID
WHERE T.NAME = '팀A'
/*
일대다 조인은 결과가 증가할 수 있지만 일대일, 다대일 조인은 결과가 증가하지 않는다.
*/
// 컬렉션 페치조인 예제
String jpql = "select t from Team t join fetch t.members where t.name = '팀A'";
List<Team> teams = em.createQuery(jpql.Team.class).getResultList();

for (Team team : teams) {
	System.out.println("teamname = " + team.getName() +", team = " + team);
	for (Member member : team.getMembres()) {
		// 페치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안함
		system.out.println(
			"-> username = " + member.getUsername() + ", member = " + member);
	}
}


