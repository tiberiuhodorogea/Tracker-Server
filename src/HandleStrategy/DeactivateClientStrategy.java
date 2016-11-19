package HandleStrategy;

import java.sql.SQLException;
import java.sql.Statement;

import SharedClasses.Communication.ResponseEnum;
import SharedClasses.Objects.Client;

public class DeactivateClientStrategy implements HandlingStrategy {

	@Override
	public Object handle(Object dataFromClient) {
		Client client = (Client)dataFromClient;
		String sql = "UPDATE CLIENTS SET ACTIVE = 0 WHERE ID = " + client.getId()
				+" AND SUPERVISOR_ID = " + client.getSupervisorId();
		try {
			Statement statement = db.getStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEnum.OK;
	}

}
