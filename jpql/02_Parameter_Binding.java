// 1. 이름 기준 파라미터 메소드 체인방식으로도 가능
String usernameParam = "User1";

TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class);
query.setParamter("username", usernameParam);
List<Member> resultList = query.getResultList();


// 2. 위치 기준 파라미터 
List<Member> members = em.createQuery("SELECT m FROM Member m where m.username = ?1", Member.class)
							.setParamter(1, usernamePara)
							.getResultList();
/**
 * 2. 위치기준 파라미터보다는 1.이름기준 파라미터를 이용하자
 */

