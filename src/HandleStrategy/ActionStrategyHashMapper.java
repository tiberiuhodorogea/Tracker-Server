package HandleStrategy;

import java.util.Hashtable;

import SharedClasses.Communication.RequestedAction;
import SharedClasses.Communication.Exceptions.KeyNotMappedException;
import SharedClasses.Communication.Exceptions.NullHashKeyException;

public class ActionStrategyHashMapper {

	private static Hashtable<RequestedAction,HandlingStrategy> actionStrategyMapper =
            new Hashtable<RequestedAction,HandlingStrategy>();

	static {
        // maps the requested action
        // to the strategy object responsible with handling the associated request
    	// example : actionStrategyMapper.put(RequestedAction.CHECK_ACCESS,new CheckUserStrategy());
    	actionStrategyMapper.put(RequestedAction.GIVE_LOCATION, new GiveLocationStrategy());
    	actionStrategyMapper.put(RequestedAction.GET_CLIENTS_FOR_SUPERVISOR, new GetClientsForSupervisorStrategy());
    	actionStrategyMapper.put(RequestedAction.GET_LATEST_LOCATION_OF_CLIENT , new GetLatestLocationOfClient());
    	actionStrategyMapper.put(RequestedAction.GIVE_RECEIVED_SMS, new GiveReceivedSmsStrategy());
    	actionStrategyMapper.put(RequestedAction.GET_SMS_GROUPS_OF_CLIENT, new GetSmsGroupsStrategy());
    	actionStrategyMapper.put(RequestedAction.GIVE_SENT_SMS, new GiveSentSmsStrategy());
    	actionStrategyMapper.put(RequestedAction.ADD_CLIENT, new AddClientStrategy());
    	actionStrategyMapper.put(RequestedAction.DEACTIVATE_CLIENT, new DeactivateClientStrategy());
    	actionStrategyMapper.put(RequestedAction.GET_CONVERSATION, new GetConversationStrategy());
    	//////ADD more when implement new request - response
    }
	
	
	public static HandlingStrategy getHandlingStrategyFor(RequestedAction key) throws KeyNotMappedException, NullHashKeyException{
		if(key == null)
			throw new NullHashKeyException("Key is null");
		HandlingStrategy strategy = null;
		strategy = actionStrategyMapper.get(key);
		if(strategy == null)
			throw new KeyNotMappedException("Key : " + key.toString()+ " not mapped to any strategy");
		return actionStrategyMapper.get(key);
	}

    
}
