public class Order {

	private final String name;
	private final Long tableNumber;

	public Order(final String name, final Long tableNumber) {
		this.name = name;
		this.tableNumber = tableNumber;
	}

	public String getName() {
		return name;
	}

	public Long getTableNumber() {
		return tableNumber;
	}
}