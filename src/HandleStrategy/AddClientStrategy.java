package HandleStrategy;

import java.sql.SQLException;
import java.sql.Statement;

import SharedClasses.Communication.ResponseEnum;
import SharedClasses.Objects.Client;

public class AddClientStrategy implements HandlingStrategy {

	@Override
	public Object handle(Object dataFromClient) {
		Client client = (Client)(dataFromClient);//client id is not valid!
		
		String sql = "INSERT INTO CLIENTS (SUPERVISOR_ID,NAME) VALUES( "+
					client.getSupervisorId()+", \""+client.getName() + "\")";
		try {
			Statement stement = db.getStatement();
			stement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ResponseEnum.OK;
	}

}
