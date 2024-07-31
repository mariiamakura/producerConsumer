import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Customer implements Runnable {
	
	private final List<String> menuItems;
	private final Long tableNumber;
	private final BlockingQueue<Order> orderBlockingQueue;
	private final CountDownLatch countDownLatch;

	public Customer(final List<String> menuItems,
					final Long tableNumber,
					final BlockingQueue<Order> orderBlockingQueue,
					 final CountDownLatch countDownLatch) {
		this.menuItems = menuItems;
        this.tableNumber = tableNumber;
        this.orderBlockingQueue = orderBlockingQueue;
        this.countDownLatch = countDownLatch;
					 }

	@Override 
	public void run() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		int sleepTime = (int) (Math.random() * 60000 + 1000);
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		final String foodName = menuItems.get((int) (Math.random() * menuItems.size()));
		final Order order = new  Order(foodName, tableNumber);

		//place order
		try {
			orderBlockingQueue.put(order);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
