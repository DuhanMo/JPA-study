// QueryDSL 기본 쿼리기능
JPAQuery query = new JPAQuery(em);
QItem item = QItem.item; // 이 부분은 static import로 뺄 수 있다.
List<Item> list = query.from(item)
	.where(item.name.eq("좋은상품").and(item.price.gt(20000)))
	.list(item); // 조회할 프로젝션 지정

// 실행된 JPQL
select item
from Item item
where item.name = ?1 item.price > ?2


/**
 * 페이징과 정렬
 * */
QItem item = QItem.item;
query.from(item)
	.where(item.price.gt(20000))
	.orderBy(item.price.dexc(), item.stockQuantity.asc())
	.offset(10).limit(20)
	.list(item);

// 페이징과 정렬 QueryModifiers 사용
QueryModifiers queryModifiers = new QueryModifiers(20L, 10L); // limit, offset
List<Item> list = query.from(item)
	.restrict(queryModifiers)
	.list(item);

// 페이징과 정렬 listResults() 사용
searchResults<Item> result = query.from(item)
	.where(item.price.gt(10000))
	.offset(10).limit(20)
	.listResults(item); // 실제 페이징 처리를 하려면 검색된 전체 데이터 수를 알아야 한다. 이때는 list() 대신에 listResults()를 사용한다.

long total = result.getTotal(); // 검색된 전체 데이터 수
long limit = result.getLimit();
long offset = result.getOffset();
List<Item> results = result.getResults(); // 조회된 데이터
