package SharedClasses.Communication;

import com.google.gson.Gson;

import SharedClasses.Communication.Exceptions.KeyNotMappedException;

/**
 * Created by tiber on 4/15/2016.
 */
public class RequestFactory extends MessageFactory {

    public Request create(RequestedAction requestedAction,Object data) throws KeyNotMappedException {
        return new Request(requestedAction,data);
    }

    public Request create(String jsonRequest){
        return new Gson().fromJson(jsonRequest, Request.class);
    }
}
