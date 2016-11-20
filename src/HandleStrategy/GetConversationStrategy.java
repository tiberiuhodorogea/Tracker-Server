package HandleStrategy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import SharedClasses.Objects.Contact;
import SharedClasses.Objects.ReceivedSmsData;
import SharedClasses.Objects.SentSmsData;
import SharedClasses.Objects.SmsData;

public class GetConversationStrategy implements HandlingStrategy {
	
	ArrayList<SmsData> ret = new ArrayList<SmsData>();
	@Override
	public Object handle(Object dataFromClient) {
		Contact contact = (Contact)dataFromClient;
		ret.clear();
		
		String sql = "SELECT ID,DATE,MESSAGE,RECEIVED_OR_SENT,DELETED FROM SMS WHERE CLIENT_ID = " + contact.getClientId() +
					" AND NUMBER = " +"\"" + contact.getNumber() + "\"";
		ResultSet resultSet = null;
		try {
			Statement statement = db.getStatement();
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()){
				int id;
				int date;
				String message;
				String receivedOrSent;
				int deleted;
				
				id = resultSet.getInt(1);
				date = resultSet.getInt(2);
				message = resultSet.getString(3);
				receivedOrSent = resultSet.getString(4);
				deleted = resultSet.getInt(5);
				
				if(deleted == 0){
					SmsData sms = new SmsData(date,"X",contact.getClientId());
					sms.setMessage(message);
					sms.setId(id);
					sms.setReceivedOrSent(receivedOrSent);
					ret.add(sms);
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return ret;
	}

}
