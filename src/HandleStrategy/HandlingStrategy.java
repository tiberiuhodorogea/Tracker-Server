package HandleStrategy;

import Framework.DataBaseManager;

public interface HandlingStrategy {
	DataBaseManager db = DataBaseManager.get();
	public Object handle(Object dataFromClient);
}
