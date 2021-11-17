/**
 * SELECT 절에 조회할 대상을 '프로젝션'이라고 함
 * */

// 1. 엔티티 프로젝션 -> 영속성 컨텍스트에서 관리한다.
SELECT m FROM Member m
SELECT m.team FROM Member m

// 2. 임베디드 타입 프로젝션 -> 영속성 컨텍스트에서 관리하지 않는다.
String query = "SELECT a FROM address a"; // 잘못된 쿼리이다. 임베디드타입은 조회의 시작점이 될 수 없다.

String query = "SELECT O.address FROM order o";
List<Address> address = em.createQuery(query, Address.class).getResultList();
 
// 3. 여러 프로젝션 -> TypedQuery를 이용할 수 없다.
Query query = em.createQuery("SELECT m.username, m.age FROM Member m");
List resultList = query.getResultList();

Iterator iterator = resultList.iterator();
while (iterator.hasNext()) {
	Object[] row = (Object[]) iterator.next(0);
 	String username = (String) row[0];
 	Integer age = (Integer) row[1]
 }

// 3-1. 여러 프로젝션 Object[]로 조회
List<Object[]> resultList = em.createQuery("SELECT m.username, m.age FROM Member m");

for (Object[] row : resultList) {
	String username = (String) row[0];
	Integer age = (Integer) row[1];
}

// 3-2. 여러 프로젝션 엔티티 타입 조회
List<Object[]> resultList = em.createQuery("SELECT o.member, o.product, o.orderAmount FROM Order o").getResultList();

for (Object[] row : resultList) {
	Member member = (Member) row[0]; 	// 엔티티
	Product product = (Product) row[1];	// 엔티티
	int orderAmount = (Integer) row[2];	// 스칼라
}

// new 명령어 사용
public class UserDTO {
	private String username;
	private int age;
	public UserDTO(String username, int age) {
		this.username = username;
		this.age = age;
	}
}

TypedQuery<UserDTO> query = em.createQuery("SELECT new jpabook...UserDTO(m.username, m.age) FROM Member m", UserDTO.class);
List<UserDTO> resultList = query.getResultList();
