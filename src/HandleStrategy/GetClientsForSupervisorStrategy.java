package HandleStrategy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import SharedClasses.Objects.Client;
import SharedClasses.Objects.Supervisor;

public class GetClientsForSupervisorStrategy implements HandlingStrategy {

	
	
	@Override
	public Object handle(Object dataFromClient) {
		ArrayList<Client> ret = new ArrayList<Client>();
		Supervisor supervisor = (Supervisor) dataFromClient;
		try {
			Statement statement = db.get().getStatement();
			String sql = "SELECT * FROM CLIENTS WHERE SUPERVISOR_ID = "+ supervisor.getId();
			
			ResultSet resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()){
				ret.add(new Client( resultSet.getInt(1), resultSet.getString(3), resultSet.getInt(2)));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

}
