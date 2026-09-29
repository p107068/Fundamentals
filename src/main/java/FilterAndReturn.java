import java.util.*;

public class FilterAndReturn {
//	Your team has just received a raw, messy dataset of user activity. Before it can be used for analytics or stored in the database, you need to write a script to validate, and process the data.
//	You are given a list of dictionaries (or a JSON string) representing user activity logs. Each dictionary is supposed to have the following keys: 
//	- user_id (integer)
//	- event_type (string)
//	- timestamp (integer, Unix epoch)
//	However, the data is unreliable and may contain errors. Your task is to write a function process_logs(log_data) that does the following:    
//	Filters out invalid logs: Remove any logs that are missing one of the required keys or have a user_id that is not a positive integer.
//	Validates event_type: Ensure the event_type is a valid one from a predefined list (e.g., ['login', 'logout', 'purchase']) 
//	Validates time: Make sure time is not in future.
//	Returns the processed data: The function should return a new list of dictionaries containing only the filtered logs.
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> inputObject = List.of(
			    Map.of("user_id", 101, "event_type", "login", "timestamp", 1665792000L),
			    Map.of("user_id", 102, "event_type", "purchase", "timestamp", 1665792120L),
			    Map.of("user_id", -1, "event_type", "logout", "timestamp", 1665792180L),
			    Map.of("user_id", 103, "timestamp", 1665792240L),
			    Map.of("user_id", 104, "event_type", "purchase", "timestamp", 1665792300L, "additional_data", "extra"),
			    Map.of("user_id", 105, "event_type", "invalid_event", "timestamp", 1665792360L),
			    Map.of("user_id", 105, "event_type", "invalid_event", "timestamp", 4916942417L)
			);
		List<Map<String, Object>> outputObject = new ArrayList<Map<String,Object>>(); 
		for (Map<String, Object> eachObj: inputObject) {
			boolean valid = checkValidity(eachObj);
			if (valid) {
				outputObject.add(eachObj);
			}
		}
		System.out.println(outputObject);
	}

	private static boolean checkValidity(Map<String, Object> eachObj) {
		// TODO Auto-generated method stub
		for (String key: eachObj.keySet()) {
			Object value = eachObj.get(key);
			if (key == "user_id" && Integer.parseInt(String.valueOf(value)) <= 0) {
				return false;
			}
			if (key == "event_type") {
				String eventVal = String.valueOf(value);
				if (eventVal != "login" && eventVal != "logout" && eventVal != "purchase") {
					return false;
				}
				
			}
			if (key == "timestamp") {
				String timeStampVal = String.valueOf(value);
				// check if timestamp < now
				
			}
		}
		return true;
	}

}
