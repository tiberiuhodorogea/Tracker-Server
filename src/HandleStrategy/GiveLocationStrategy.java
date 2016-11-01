package HandleStrategy;

import java.sql.SQLException;
import java.sql.Statement;

import SharedClasses.Communication.ResponseEnum;
import SharedClasses.Objects.LocationData;

public class GiveLocationStrategy implements HandlingStrategy {

	LocationData locationData;
	@Override
	public ResponseEnum handle(Object dataFromClient) {
		locationData = (LocationData)dataFromClient;
		try {
			Statement statement = db.get().getStatement();
			String sql = "INSERT INTO LOCATIONS(CLIENT_ID,DATE,LATITUDE,LONGITUDE) ";
			      sql += "VALUES("+locationData.getClientId()+","+locationData.getDate()+","
			    		 +locationData.getLatitude()+","+locationData.getLongitude()+")";
			      
			      statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ResponseEnum.OK;
	}

}
