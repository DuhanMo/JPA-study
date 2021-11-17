/**
 * 수정, 삭제 배치 쿼리
 * */
 
// 수정 배치 쿼리 -> 영속성 컨텍스트를 무시하고 DB를 직접 쿼리한다.
QItem item = QItem.item;
JPAUpdateClause updateClause = new JPAUpdateClause(em, item);
long count = updateClause.where(item.name.eq("시골 개발자의 JPA 책"))
	.set(item.price, item.price.add(100))
	.execute();

// 삭제 배치 쿼리
QItem item = QItem.item;
JPADeleteClause deleteClause = new JPADeleteClause(em, item);
long count = deleteClause.where(item.name.eq("시골 개발자의 JPA 책"))
	.execute();