package SharedClasses.Communication;

import com.google.gson.Gson;

import SharedClasses.Communication.Exceptions.KeyNotMappedException;

/**
 * Created by tiber on 4/15/2016.
 */
public class ResponseFactory extends MessageFactory{

    public Response create(RequestedAction requestedAction, Object data) throws KeyNotMappedException {
        return new Response(requestedAction,data);
    }

    public Response create(String jsonRequest){
        return new Gson().fromJson(jsonRequest, Response.class);
    }

}
