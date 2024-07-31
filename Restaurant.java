import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
	final static int NUMBER_OF_CUSTOMERS = 100;
	final static int NUMBER_OF_CHEFS = 7;

	public static void main(String[] args) {
		final BlockingQueue<Order> orderBlockingQueue = new LinkedBlockingQueue<>();
		final CountDownLatch restarauntLatch = new CountDownLatch(1);

		final Thread[] customers = new Thread[NUMBER_OF_CUSTOMERS];
		for (int i = 0; i < NUMBER_OF_CUSTOMERS; i++) {
			customers[i] = new Thread(
				new Customer(List.of("Pizza", "Pasta", "Salad"),
				(long) i,
				orderBlockingQueue,
				restarauntLatch));
			customers[i].start();
		}

		final Thread[] chefs = new Thread[NUMBER_OF_CHEFS];
		for (int i = 0; i < NUMBER_OF_CHEFS; i++) {
			chefs[i] = new Thread(new Chef(orderBlockingQueue));
			chefs[i].start();
		}

		restarauntLatch.countDown();


        for (int i = 0; i < NUMBER_OF_CUSTOMERS; i++) {
            try {
                customers[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < NUMBER_OF_CHEFS; i++) {
            chefs[i].interrupt();
        }

	}
}
