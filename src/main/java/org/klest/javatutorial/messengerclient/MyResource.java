package org.klest.javatutorial.messengerclient;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.klest.javatutorial.messengerclient.model.Comment;
import org.klest.javatutorial.messengerclient.model.Message;



public class MyResource {

	private Client client;
	private String RestServiceURL = "http://localhost:8080/messenger/webapi/";
   
    private void initialise(){
    	this.client = ClientBuilder.newClient();
    }
    
    public static void main (String[] args){
    	
    	MyResource webclient = new MyResource();
    	
    	webclient.initialise();
    	
    	webclient.getMessages();
    	
    	webclient.getComments(2);
    	//TO DO
    }
    
    private void getMessages(){
    	
    	GenericType<List<Message>> list = new GenericType<List<Message>>() {};
    	
    	List<Message> messages = client
    			.target(RestServiceURL)
    			.path("/messages")
    			.request(MediaType.APPLICATION_JSON)
    			.get(list);
    	
    	for(int i = 0; i < messages.size(); i++)
    	System.out.println(messages.get(i).toString());
    }
    
    private void deleteMessages(int messageId){
    	
    	String delete = client
    			.target(RestServiceURL)
    			.path("/messages/{messageId}")
    			.resolveTemplate("messageId", messageId)
    			.request(MediaType.APPLICATION_JSON)
    			.delete(String.class);
    }
    
    
    private void getComments (int messageId){
    	
    	GenericType<List<Comment>> list = new GenericType<List<Comment>>() {};
    	
    	List<Comment> comments = client
    			.target(RestServiceURL)
    			.path("/messages/{messageId}/comments")
    			.resolveTemplate("messageId", messageId)
    			.request(MediaType.APPLICATION_JSON)
    			.get(list);
    	
    	for(int i = 0; i < comments.size(); i++)
    	System.out.println(comments.get(i).toString());
    }
    
  /*  private void addComment(String comment){
    	
    	String addcomment = client
    			.target(RestServiceURL)
    			.path("/messages/{messageId}")
    			.resolveTemplate("messageId", 1)
    			.request(MediaType.APPLICATION_JSON)
    			.post(comment);
    	
   */ 	
    	
    	
   // }
}
