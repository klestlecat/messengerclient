package org.klest.javatutorial.messengerclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    	
    	List <Message> messageList = new ArrayList<Message>();
    	List <Comment> commentList = new ArrayList<Comment>();
    	
    	String author;
    	Scanner in = new Scanner(System.in);
    	
    	System.out.println("Welcome to messenger");
    	System.out.println("Please type your username");
    	author = in.nextLine();
    	
    	System.out.println("Welcome " + author + " You can type one of the following: /n");
    	
    	
    	
    	System.out.println("/nm - see all messages /na - add new messages /nType c - to see comments /nd - Delete message");
    	
    	while(true){
	    	if (in.nextLine().equals("m")){
	    		//see all messages
	    		webclient.getMessages();
	    	}
	    	
	    	else if (in.nextLine().equals("a")){
	    		//add new message
	    	}
	    	
	    	else if (in.nextLine().equals("c")){
	    		// see comments
	    		//under menu comments
	    	}
	    	
	    	else if (in.nextLine().equals("d")){
	    		//delete messages
	    	}
	    	
	    	else if (in.nextLine().equals("exit")){
	    		break;
	    	}
	    	
	    	else {
	    		System.out.println("The command does not exist");
	    	}
    	}
    	
    	
    	
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
    
    private void addMessage(Message message){
    	
    	Response addmessage = client
    			.target(RestServiceURL)
    			.path("/messages")
    			.request(MediaType.APPLICATION_JSON)
    			.post(Entity.entity(message, MediaType.APPLICATION_JSON));
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
    
    private void addComment(Comment comment, int messageID){
    	
    	Response addcomment = client
    			.target(RestServiceURL)
    			.path("/messages/{messageId}")
    			.resolveTemplate("messageId", messageID)
    			.request(MediaType.APPLICATION_JSON)
    			.post(Entity.entity(comment, MediaType.APPLICATION_JSON));
    	}
    
}
