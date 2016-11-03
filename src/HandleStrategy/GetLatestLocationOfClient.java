package HandleStrategy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import SharedClasses.Objects.LocationData;

public class GetLatestLocationOfClient implements HandlingStrategy {

	@Override
	public Object handle(Object dataFromClient) {
		LocationData ret = null;
		String selectedClient = (String)dataFromClient;
		
		String sqlClientIdInnerSelect = "( SELECT ID FROM CLIENTS WHERE NAME = " +"\""+ selectedClient+"\"" +")";
		String sqlSelectMaxDateInnerSql = "( SELECT MAX(DATE) FROM LOCATIONS WHERE CLIENT_ID = "+ sqlClientIdInnerSelect +" )";
		
		String sql = "SELECT * FROM LOCATIONS WHERE CLIENT_ID = " 
					+ sqlClientIdInnerSelect +" AND DATE = "+ sqlSelectMaxDateInnerSql;
		//System.out.println(sql);
		
		try {
			Statement statement = db.getStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			if(!resultSet.next())
			{
				return new LocationData(0,0,selectedClient,0);
			}
			
			double latitude = 0;
			double longitude = 0;
			int date = 0;
			
			latitude = resultSet.getDouble(4);
			longitude = resultSet.getDouble(5);
			date = resultSet.getInt(3);
			
			ret = new LocationData(date,latitude,longitude,selectedClient,0);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}

}
