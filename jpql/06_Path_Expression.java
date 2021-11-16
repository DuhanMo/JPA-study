/**
 * 
 * 경로 표현식의 용어 정리
 * 1. 상태필드
 * 	: 단순히 값을 저장하기 위한 필드
 * 2. 연관필드 : 연관관계를 위한 필드, 임베디드 타입 포함(필드or프로퍼티)
 * 	2-1) 단일 값 연관 필드: @ManyToOne, @OneToOne, 대상이 인테티
 * 	2-2) 컬렉션 값 연관 필드: @OneToMany, @ManyToMany
 */
// 상태 필드, 연관 필드 설명 예제 코드
@Entity
public class Member {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name")
	private String username; // 상태 필드

	private Integer age; // 상태 필드

	@ManyToOne(..)
	private Team team; // 연관 필드 (단일 값 연관 필드)

	@OneToMany
	private List<Order> orders; // 연관 필드 (컬렉션 값 연관 필드)
}

/**
 * 위 예제에서 나타난 경로 표현식
 * 상태필드 : t.username, t.age
 * 단일 값 연관 필드  m.team
 * 컬렉션 값 연관 필드  m.orders
 */

// 컬렉션 값에서 경로탐색을 할 순 없다. -> 컬렉션에 새로운 별칭을 얻어서 경로탐색을 진행해야 한다.

// jpql
select o.member.team
from Order o
where o.product.name = 'productA' and o.address.city = 'JINJU'

// 실행된 SQL
select t.*
from Orders o
inner join Member m on o.member_id=m.id
inner join Team t on m.team_id=t.id
inner join Product p on o.product_id=p.id
where p.name='productA' and o.city='JINJU'

// 경로탐색은 묵시적 조인이 발생 (항상 내부조인)
// 한눈에 파악하기 어렵기 때문에 명시적조인을 사용하자