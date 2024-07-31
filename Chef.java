import java.util.concurrent.BlockingQueue;

import javax.management.RuntimeErrorException;

public class Chef implements Runnable {
	private final BlockingQueue<Order> orderBlockingQueue;
	private Long ordersServed = 0L;

	public Chef(final BlockingQueue<Order> orderBlockingQueue) {
		this.orderBlockingQueue = orderBlockingQueue;
	}

	@Override
	public void run() {
		while (true) {
			//Wait for an order
			final Order order;
			try {
				order = orderBlockingQueue.take();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			int sleepTime = (int) (Math.random() * 20000 + 10000);

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}


			System.out.println("Order for " + order.getName() + 
			" at table" + order.getTableNumber() + " is ready!");

			ordersServed++;
		}
	}

	public Long getOrdersServed() {
		return ordersServed;
	}
}
