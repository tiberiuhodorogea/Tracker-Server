package HandleStrategy;

import java.sql.SQLException;
import java.sql.Statement;

import SharedClasses.Communication.ResponseEnum;
import SharedClasses.Objects.ReceivedSmsData;

public class GiveReceivedSmsStrategy implements HandlingStrategy {

	//DELETE \"
	@Override
	public ResponseEnum handle(Object dataFromClient) {
		
		ReceivedSmsData sms = (ReceivedSmsData) dataFromClient;
		
		String sql = "INSERT INTO SMS (CLIENT_ID,NUMBER,NAME,MESSAGE,RECEIVED_OR_SENT) VALUES "
				+"(" + sms.getClientId()  +",\""+sms.getNumber()+"\",\"" + sms.getName()+"\",\"" +
				sms.getMessage() +"\",\"RECEIVED\")";
		
		try {
			Statement statement = db.getStatement();
			//statement.execute(sql);
			System.out.println(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEnum.OK;
	}

}
