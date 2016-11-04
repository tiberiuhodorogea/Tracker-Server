package SharedClasses.Communication;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;
import com.google.gson.reflect.TypeToken;
import SharedClasses.Communication.Exceptions.KeyNotMappedException;
import SharedClasses.Objects.Client;
import SharedClasses.Objects.LocationData;
import SharedClasses.Objects.ReceivedSmsData;
import SharedClasses.Objects.Supervisor;


/**
 * Created by tiber on 4/14/2016.
 */
public class ActionTypesHashMapper {

    private static Hashtable<RequestedAction,TypePairContainer> messageActionTypesMapper =
            new Hashtable<RequestedAction,TypePairContainer>();

    static {
        // maps the requested action
        // to the type of  the encapsulated data on the request
        // and with the type of encapsulated data on response
        // example: messageActionTypesMapper.put(RequestedAction.CHECK_ACCESS,new TypePairContainer(Credentials.class, User.class));
    	messageActionTypesMapper.put(RequestedAction.GIVE_LOCATION, new TypePairContainer(LocationData.class, ResponseEnum.class));
    	messageActionTypesMapper.put(RequestedAction.GET_CLIENTS_FOR_SUPERVISOR, new TypePairContainer(Supervisor.class,new TypeToken<ArrayList<Client>>(){}.getType()));
        messageActionTypesMapper.put(RequestedAction.GET_LATEST_LOCATION_OF_CLIENT, new TypePairContainer(Client.class, LocationData.class));
        messageActionTypesMapper.put(RequestedAction.GIVE_RECEIVED_SMS,new TypePairContainer(ReceivedSmsData.class,ResponseEnum.class));
        //////ADD more when implement new request - response
    }

    public static Type getRequestDataClass(RequestedAction key) throws KeyNotMappedException {
        TypePairContainer typePairContainer = messageActionTypesMapper.get(key);
        if(null == typePairContainer)
            throw new KeyNotMappedException("Key : " + key.toString() + " not mapped to any types combination.");
        Type ret = typePairContainer.getRequestDataType();
        return ret;
    }


    public static Type getResponseDataClass(RequestedAction key) throws KeyNotMappedException {
        TypePairContainer typePairContainer = messageActionTypesMapper.get(key);
        if(null == typePairContainer)
            throw new KeyNotMappedException("Key : " + key.toString() + " not mapped to any types combination.");
        Type ret = typePairContainer.getResponseDataType();
        return ret;
    }


}
