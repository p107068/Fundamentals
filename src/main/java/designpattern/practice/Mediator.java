package designpattern.practice;

import java.util.ArrayList;
import java.util.List;

public class Mediator {
	
	private static interface ChatMediator {
		void addUser(ChatUser user);
		void sendMessage(ChatUser sender, String message);
	}
	
	private static class ChatUser {
		String name;
		ChatMediator mediator;
		
		ChatUser(String name, ChatMediator mediator) {
			this.name = name;
			this.mediator = mediator;
		}

		public void receiveMessage(String message, ChatUser sender) {
			System.out.println("Message recieved from " + sender.name + " to: " + this.name + ". Content: " + message);			
		}
		
	}
	
	private static class ChatRoom implements ChatMediator {
		List<ChatUser> users;
		
		ChatRoom() {
			users = new ArrayList<ChatUser>();
		}

		@Override
		public void addUser(ChatUser user) {
			// TODO Auto-generated method stub
			users.add(user);
		}

		@Override
		public void sendMessage(ChatUser sender, String message) {
			// TODO Auto-generated method stub
			for (ChatUser user: users) {
				if (user != sender) {
					user.receiveMessage(message, sender);
				}
			}
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChatMediator room = new ChatRoom();
		ChatUser ram = new ChatUser("Ram", room);
		ChatUser shayam = new ChatUser("Shyam", room);
		ChatUser golu = new ChatUser("Golu", room);
		room.addUser(ram);
		room.addUser(shayam);
		room.addUser(golu);
		
		room.sendMessage(golu, "My name is golu.");
	}

}
