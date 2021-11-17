/**
 * 프로젝션 결과 반환
 * */

// 프로젝션 대상이 하나
QItem item = QItem.item;
List<String> result = query.from(item).list(item.name);

for (String name : result) {
	System.out.println("name = " + name);
}



// 튜플 사용 예제
QItem item = QItem.item;
List<Tuple> result = query.from(item).list(item.name, item.price);
// List<Tuple> result = query.from(item).list(new QTuple(item.name, item.price)); 위와 같은 코드

for (Tuple tuple : result) {
	System.out.println("name = " + tuple.get(item.name));
	System.out.println("price = " + tuple.get(item.price));
}

/**
 * 빈 생성: 쿼리 결과를 엔티티가 아닌 특정 객체로 받고 싶을 때
 * - 프로퍼티 접근
 * - 필드 직접 접근
 * - 생성자 사용
 * */

public class ItemDTO {
	private String username;
	private int price;
	public ItemDTO() {}
	public ItemDTO(String username, int price) {
		this.username = username;
		this.price = price;
	}
	// getter, setter
}

// 프로퍼티 접근
QItem item = QItem.item;
List<ItemDTO> result = query.from(item).list(
	Projections.bean(ItemDTO.class, item.name.as("username"), item.price)); // Projections.bean -> setter를 이용해서 값을 채움

// 필드 직접 접근
QItem item = QItem.item;
List<ItemDTO> result = query.from(item).list(
	Projections.fields(ItemDTO.class, item.name.as("username"),
		item.price));

// 생성자 사용
QItem item = QItem.item;
List<ItemDTO> result = query.from(item).list(
	Projections.constructor(ItemDTO.class, item.name, item.price)
);

// DISTINCT는 
// query.distinct().from(item)... 이런식으로 사용