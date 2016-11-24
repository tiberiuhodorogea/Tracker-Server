package HandleStrategy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import SharedClasses.Objects.Contact;
import SharedClasses.Objects.SMSGroupDetails;

public class GetSmsGroupsStrategy implements HandlingStrategy{

	@Override
	public Object handle(Object dataFromClient) {
		int clientId = (int) dataFromClient;
		ArrayList<SMSGroupDetails> smsGroups = new ArrayList<SMSGroupDetails>();
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		String sql = "";
		ResultSet resultSet;
		try {
			Statement statement = db.getStatement();
			//first get all the numbers(phone numbers) for this client - the number is unique per contact, the name may not be unique
			sql = "SELECT DISTINCT NUMBER FROM SMS WHERE CLIENT_ID = " + clientId;
			resultSet = statement.executeQuery(sql);
			if(resultSet != null){
				while( resultSet.next() ){
					String number = resultSet.getString(1);
					Contact c = new Contact();
					c.setNumber(number);
					c.setClientId(clientId);
					contacts.add(c);
				}
			}
			
			if (!processDuplicateNumbers(contacts, statement))
				return new ArrayList<Contact>();
			
			sql = "SELECT NUMBER, MAX(DATE) FROM SMS WHERE CLIENT_ID = " + clientId +
					" GROUP BY NUMBER ORDER BY 2 DESC";
			
			contacts = new ArrayList<Contact>();
			ArrayList<Integer> latestActivityArray = new ArrayList<Integer>();//kinda hack fuck...
			
			resultSet = statement.executeQuery(sql);
			if(resultSet != null){
				while( resultSet.next() ){
					String number = resultSet.getString(1);
					int latestActivity = resultSet.getInt(2);
					
					latestActivityArray.add(latestActivity);
					Contact c = new Contact();
					c.setNumber(number);
					c.setClientId(clientId);
					contacts.add(c);
				}
			}
			
			// got the numbers, now get names for each number...
			//select received_or_Sent,count(*) from sms
			//where client_id and number = "+40769621937"
				//	group by received_or_sent
			//
			int i = 0;
			sql = "SELECT DISTINCT NAME FROM SMS WHERE CLIENT_ID = "+ clientId +" AND NUMBER = ";
			for(Contact c : contacts ){
				resultSet = statement.executeQuery(sql + "\"" + c.getNumber() + "\"");
				while(resultSet.next()){
					c.addName(resultSet.getString(1));
				}
				
				SMSGroupDetails smsGroup = new SMSGroupDetails();
				
				smsGroup.setLatesActivityTime(latestActivityArray.get(i++));
				smsGroup.setContact(c);
				smsGroup.setClientId(clientId);
				smsGroups.add(smsGroup);
			}
			
			sql = "SELECT RECEIVED_OR_SENT,COUNT(*) FROM SMS WHERE CLIENT_ID = "+ clientId + " AND NUMBER = ";
			String sqlGroupBy = " " + "GROUP BY RECEIVED_OR_SENT";
			for(SMSGroupDetails smsGroup : smsGroups){
				resultSet = statement.executeQuery(sql + "\"" + smsGroup.getContact().getNumber() + "\"" + sqlGroupBy);
				while ( resultSet.next() ){
					String receivedOrSent = resultSet.getString(1);
					switch(receivedOrSent){
					case "RECEIVED":
						smsGroup.setReceivedCount(resultSet.getInt(2));
						break;
					case "SENT":
						smsGroup.setSentCount(resultSet.getInt(2));
						break;
					default:
						smsGroup.setReceivedCount(resultSet.getInt(2));
						break;
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		removeNullContactNames(smsGroups);
		
		return smsGroups;
	}
	
	
	
	
	
	void removeNullContactNames(ArrayList<SMSGroupDetails> smsGroups){
		for(SMSGroupDetails smsGroup : smsGroups){
			ArrayList<String> names = smsGroup.getContact().getNames();
			if( names.size() == 1 && names.get(0).equals("null")){
				names.remove(0);
				names.add(smsGroup.getContact().getNumber());
			}
			
			else if(names.size() > 1){
				
				for( int i = 0; i< names.size();++i){
					if(names.get(i).equals("null")){
						names.remove(i);
						names.add(i,"no_name");
					}
				}
			}
			
		}
	}
	//!!!!! only numbers
	boolean processDuplicateNumbers(ArrayList<Contact> contacts, Statement statement){
		ArrayList<Contact> ret = new ArrayList<Contact>();
		for (Contact c: contacts){
			for (Contact d: contacts){
				if( d != c  &&
				    d.getNumber().length() == 12 &&
				    c.getNumber().equals(d.getNumber().substring(2)))
				{
					String sql = "UPDATE SMS SET NUMBER = \"" + d.getNumber() +"\" "+
							"WHERE NUMBER = \""+ c.getNumber()+"\"";
					try {
						statement.executeUpdate(sql);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
				}
			}
		}
		
		return true;
	}

}
