package designpattern.tier1;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern
 *
 * Intent: Notify multiple objects automatically when a subject changes.
 *
 * Use when: One event must reach many listeners, such as notifications, stock prices,
 * pub/sub events, or parking availability.
 *
 * Examples: A new order notifying email and mobile subscribers; stock price updates;
 * a parking lot notifying users when spaces become available.
 *
 * Structure: The subject stores Observer objects and calls update on each one.
 *
 * Benefit: The subject does not need to know the concrete notification types.
 *
 * Tradeoff: Many observers can make event flow harder to trace; unsubscribe support
 * is important in long-running applications.
 */
public final class ObserverPatternExample {
	
	public static interface Subscriber {
		void update();
	}
	
	public static class EmailSubscriber implements Subscriber {

		@Override
		public void update() {
			// TODO Auto-generated method stub
			System.out.println("Email received!");
		}		
	}
	
	public static class MobileSubscriber implements Subscriber {

		@Override
		public void update() {
			// TODO Auto-generated method stub
			System.out.println("SMS received!");			
		}		
	}
	
	public static class NotificationSubject {
		
		List<Subscriber> subscribers;
		
		NotificationSubject() {
			subscribers = new ArrayList<ObserverPatternExample.Subscriber>();
		}

		public void subscribe(Subscriber subscriber) {
			// TODO Auto-generated method stub
			subscribers.add(subscriber);
		}

		public void publish(String message) {
			// TODO Auto-generated method stub
			for (Subscriber sub: subscribers) {
				sub.update();
			}
		}
		
	}

    public static void main(String[] args) {
        NotificationSubject notification = new NotificationSubject();
        notification.subscribe(new EmailSubscriber());
        notification.subscribe(new MobileSubscriber());
        notification.publish("Your order has shipped");
    }
}