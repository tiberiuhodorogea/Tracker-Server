package HandleStrategy;

import java.sql.Statement;

import Framework.DataBaseManager;

public interface HandlingStrategy {
	DataBaseManager db = DataBaseManager.get();
	public Object handle(Object dataFromClient);
}
