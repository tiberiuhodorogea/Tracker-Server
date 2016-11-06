package HandleStrategy;

import java.sql.SQLException;
import java.sql.Statement;

import SharedClasses.Communication.ResponseEnum;
import SharedClasses.Objects.SentSmsData;

public class GiveSentSmsStrategy implements HandlingStrategy {

	@Override
	public Object handle(Object dataFromClient) {
		SentSmsData sms = (SentSmsData)dataFromClient;

		String sql = "INSERT INTO SMS (CLIENT_ID,DATE,NUMBER,NAME,MESSAGE,RECEIVED_OR_SENT) VALUES "
				+"(" + sms.getClientId()+"," + sms.getDate()  +",\""+sms.getNumber()+"\",\"" + sms.getName()+"\",\"" +
				replaceTokensForDB(sms.getMessage()) +"\",\"SENT\")";
		
		try {
			Statement statement = db.getStatement();
			statement.execute(sql);
			System.out.println(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEnum.OK;
	}

	String replaceTokensForDB(String text){
		String ret = text.replaceAll("\'", "quote");
		ret = ret.replaceAll("\"", "dquote");
		return ret;
	}
	
}
