package HandleStrategy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import SharedClasses.Communication.ResponseEnum;
import SharedClasses.Objects.Client;

public class AddClientStrategy implements HandlingStrategy {

	@Override
	public Object handle(Object dataFromClient) {
		Client client = (Client)(dataFromClient);//client id is not valid!
		
		String sql = "SELECT * FROM CLIENTS WHERE SUPERVISOR_ID = " + client.getSupervisorId() +
				" AND NAME = \"" + client.getName() + "\"";
		ResultSet resultSet;
		
		
		try {
			Statement statement = db.getStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next())
				return ResponseEnum.DUPLICATE_CLIENT_NAME;
			
			sql = "INSERT INTO CLIENTS (SUPERVISOR_ID,NAME) VALUES( "+
					client.getSupervisorId()+", \""+client.getName() + "\")";
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ResponseEnum.OK;
	}

}
