// 나이가 평균보다 많은 회원
select m from Member m
where m.age > (select avg(m2.age) from Member m2)

// 한 건이라도 주문한 고객
select m from Member m
where (select count(o) from Order o where m = o.member) > 0
// == 위아래 같은 쿼리 발생 == 
select m from Member m
where m.orders.size > 0

/*
1. [NOT] EXISTS (subquery)
2. {ALL | ANY | SOME} (subquery)
3. [NOT] IN (subquery)
*/

// 1. [NOT] EXISTS (subquery)
// 서브쿼리에 결과가 존재하면 참. NOT은 반대
// ex: 팀 A에 소속된 회원
select m from Member m
where exists (select t from m.team t where tmname = '팀A')



// 2. {ALL | ANY | SOME} (subquery)
// 비교 연산자와 같이 사용.
// ALL: 조건을 모두 만족하면 참이다
// ANY 혹은 SOME: 둘 다 같은 의미. 조건을 하나라도 만족하면 참이다.
// ex: 전체 상품 각각의 재고보다 주문량이 많은 주문들
select o from Order o
where o.orderAmount > ALL (select p.stockAmount from Product p)

// ex) 어떤 팀이든 팀에 소속된 회원
select m from Member m
where m.team = ANY(select t from Team t)

// 3. [NOT] IN (subquery)
// ex: 20세 이상을 보유한 팀
select t from Team t
where t IN (select t2 From Team t2 JOIN t2.members m2 where m2.age >= 20)


