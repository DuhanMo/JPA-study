/**
 * 메소드 위임
 * */

// 검색 조건 정의
public class ItemExpression {

	@QueryDelegate(Item.class)
	public static BooleanExpression isExpensive(QItem item,
		Integer price) {
		return item.price.gt(price);
	}
}

