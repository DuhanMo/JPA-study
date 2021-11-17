/**
 * 그룹
 * */
// groupBy() 사용
query.from(item)
	.groupBy(item.price)
	.having(item.price.gt(1000))
	.list(item);

/**
 * 조인
 * */

// 기본 조인
QOrder order = QOrder.order;
QMember member = QMember.member;
QOrderItem orderItem = QOrderItem.orderItem;

query.from(order)
	.join(order.member, member)
	.leftJoin(order.orderItems, orderItem)
	.list(order);


// on 사용
query.from(order)
	.leftJoin(order.orderItems, orderItem)
	.on(orderItem.count.gt(2))
	.list(order);

// 페치 조인 사용
query.from(order)
	.innerJoin(order.member, member).fetch()
	.leftJoin(order.orderItems, orderItem).fetch()
	.list(order)

// from절에 여러 조인을 사용하는 세타 조인
QOrder order = QOrder.order;
QMember member = QMember.member;

query.from(order, member)
	.where(order.member.eq(member))
	.list(order);
