package SharedClasses.Communication;

import SharedClasses.Communication.Exceptions.KeyNotMappedException;

/**
 * Created by tiber on 4/14/2016.
 */
public abstract class AbstractMessage {
    protected RequestedAction requestedAction = null;
    protected String jsonData = null;

    public abstract Object deserializeData() throws KeyNotMappedException;
    public abstract String toJson();

    public  RequestedAction getRequestedAction(){
       return this.requestedAction;
    }
}
