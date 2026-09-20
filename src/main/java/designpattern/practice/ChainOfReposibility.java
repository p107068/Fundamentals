package designpattern.practice;

public class ChainOfReposibility {
	
	private static abstract class Approver {
		Approver next;
		
		abstract boolean canApprove(double amount);
		
		abstract String role();
		
		void approve(double amount) {
			if (canApprove(amount)) {
				System.out.println(role() + " approved the amount: " + amount);
			} else if (next != null) {
				next.approve(amount);
			} else {
				System.out.println("The current amount cannot be approved");
			}
		}
	}
	
	private static class ClerkApprover extends Approver {
		@Override
		boolean canApprove(double amount) {
			// TODO Auto-generated method stub
			return amount < 1000;
		}

		@Override
		String role() {
			// TODO Auto-generated method stub
			return "Clerk";
		}		
	}
	
	private static class ManagerApprover extends Approver {

		@Override
		boolean canApprove(double amount) {
			// TODO Auto-generated method stub
			return amount < 10000;
		}

		@Override
		String role() {
			// TODO Auto-generated method stub
			return "Team manager";
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Approver approver = new ClerkApprover();
		approver.next = new ManagerApprover();
		approver.approve(100);
		approver.approve(2000);
		approver.approve(100000);
	}

}
