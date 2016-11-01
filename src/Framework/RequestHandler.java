package Framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import HandleStrategy.ActionStrategyHashMapper;
import HandleStrategy.HandlingStrategy;
import SharedClasses.Communication.*;
import SharedClasses.Communication.Exceptions.*;


public class RequestHandler {

	private Response response = null;
	private Request request = null;
	private String requestJson = null;
	private Socket client = null;
	private HandlingStrategy strategy;
	DataBaseManager db;
	PrintWriter pr = null;
	
	public RequestHandler(String requestJson,Socket client){
		this.requestJson = requestJson;
		this.client = client;
		try {
			pr = new PrintWriter(client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void execute() {
		boolean requestIsJsonValidFormat = true;
		try{
			request = new RequestFactory().create(requestJson);
			}catch(com.google.gson.JsonSyntaxException ex){
				System.out.println("json request format not supported, Not handling it");
				requestIsJsonValidFormat = false;
			}catch(java.lang.IllegalStateException ex){
				System.out.println("json request format not supported, Not handling it");
				requestIsJsonValidFormat = false;
			}
		if(requestIsJsonValidFormat){
			db = DataBaseManager.get();
			try {
				Handle(request);
				
				pr.write(this.response.toJson()+"\n");
				System.out.println("RESPONSE: "+ this.response.toJson()+"\n\n");
				pr.flush();
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				client.close();
				
			} catch (NullHashKeyException e1) {
				System.out.println(e1.getMessage());
				System.out.println("Request format not supported");
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	private void Handle(Request request) throws NullHashKeyException{
		RequestedAction requestedAction = request.getRequestedAction();
		try {
			this.strategy = ActionStrategyHashMapper.getHandlingStrategyFor(requestedAction);
			Object responseData= this.strategy.handle(request.deserializeData());
			this.response = new ResponseFactory().create(requestedAction,responseData);
		} catch (KeyNotMappedException e) {
			e.printStackTrace();
		}
	}
	}

