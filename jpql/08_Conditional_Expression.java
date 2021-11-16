// 1. Between
select m from Member m
where m.age between 10 and 20

// 2. IN (서브쿼리 이용가능)
select m from Member m
where m.username in ('회원1', '회원2')

// 3. Like

// 중간에 원이라는 단어가 들어간 회원(좋은회원, 회원, 원)
select m from Member m
where m.username like '%원%'

// 처음에 회원이라는 단어가 포함(회원1, 회원ABC)
where m.username like '회원%'

// 마지막에 회원이라는 단어가 포함(좋은회원, A회원)
where m.username like '%회원'

// 회원A, 회원1
where m.username like '회원_'

// 회원3
where m.username like '__3'

// 회원%
where m.username like '회원\%' EXCAPE '\'


// 컬렉션 식
// jpql: 주문이 하나라도 있는 회원 조회
select m from Member m
where m.orders is not empty

// 실행된 SQL
select m.* from Member m
where
	exists (
		select o.member_id
		from Orders o
		where m.id=o.member_id
	)

