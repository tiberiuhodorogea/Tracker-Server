package HandleStrategy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GetClientsForSupervisorStrategy implements HandlingStrategy {

	ArrayList<String> ret = new ArrayList<String>();
	
	@Override
	public Object handle(Object dataFromClient) {
		String supervisor = (String) dataFromClient;
		try {
			Statement statement = db.get().getStatement();
			String sql = "SELECT NAME FROM CLIENTS WHERE SUPERVISOR_ID = "
					+ "( SELECT ID FROM SUPERVISORS WHERE NAME = \""+ supervisor + "\" )";
			
			ResultSet resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()){
				ret.add(resultSet.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

}
