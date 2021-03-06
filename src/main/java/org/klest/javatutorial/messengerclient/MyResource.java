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
    	String message;
    	int id;
    	int commentid;
    	
    	Scanner in = new Scanner(System.in);
    	
    	System.out.println("Welcome to messenger");
    	System.out.println("Please type your username");
    	author = in.nextLine();
    	
    	
    	
    	
    	while(true){
    		System.out.println("Welcome " + author + " You can type one of the following: \n");
    		System.out.println("\nm - see all messages \na - add new messages \nType c - to see comments \nd - Delete message");
        	
	    	if (in.nextLine().equals("m")){
	    		
	    		webclient.getMessages(messageList);
	    		
	    		for(int i = 0; i < messageList.size(); i++){
	    	    	System.out.println(messageList.get(i).toString());
	    		}
	    	}
	    	
	    	else if (in.nextLine().equals("a")){
	    		
	    		System.out.println("Pleasse enter your message: ");
	    		message = in.nextLine();
	    		
	    		webclient.addMessage(new Message(messageList.size(), message, author));
	    	}
	    	
	    	else if (in.nextLine().equals("c")){
	    		
	    		System.out.println("Please enter the ID of the message");
	    		id = in.nextInt();
	    		
	    		webclient.getComments(id, commentList);
	    		
	    		for(int i = 0; i < commentList.size(); i++)
	    	    	System.out.println(commentList.get(i).toString());
	    		
	    		while(true){
	    			System.out.println("\nPlease choose one of the following: \na - Add Comment \nd - Delete Comment");
	    			
	    			if (in.nextLine().equals("a")){
	    				
	    				System.out.println("please enter yout message:");
	    				message = in.nextLine();
	    				
	    				webclient.addComment(new Comment(commentList.size(), message, author), id);
	    			}
	    			
	    			else if (in.nextLine().equals("d")){
	    				//delete comment
	    				System.out.println("Please enter the comment's ID...");
	    				commentid = in.nextInt();
	    				
	    				webclient.deleteComment(id, commentid);
	    				
	    			}
	    			
	    			else if (in.nextLine().equals("exit")){
	    				break;
	    			}
	    			else {
	    				System.out.println("the command does not exist");
	    			}
	    		}
	    		
	    	}
	    	
	    	else if (in.nextLine().equals("d")){
	    		
	    		System.out.println("Please enter the ID of the message you want to delete...");
	    		id = in.nextInt();
	    		
	    		webclient.deleteMessages(id);
	    		
	    	}
	    	
	    	else if (in.nextLine().equals("exit")){
	    		break;
	    	}
	    	
	    	else {
	    		System.out.println("The command does not exist");
	    	}
    	}
    	
    	
    	
    }
    
    private void getMessages(List <Message> message){
    	
    	GenericType<List<Message>> list = new GenericType<List<Message>>() {};
    	
    	message = client
    			.target(RestServiceURL)
    			.path("/messages")
    			.request(MediaType.APPLICATION_JSON)
    			.get(list);
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
    
    
    private void getComments (int messageId, List<Comment> comments){
    	
    	GenericType<List<Comment>> list = new GenericType<List<Comment>>() {};
    	
    	comments = client
    			.target(RestServiceURL)
    			.path("/messages/{messageId}/comments")
    			.resolveTemplate("messageId", messageId)
    			.request(MediaType.APPLICATION_JSON)
    			.get(list);
    	
    }
    
    private void addComment(Comment comment, int messageID){
    	
    	Response addcomment = client
    			.target(RestServiceURL)
    			.path("/messages/{messageId}")
    			.resolveTemplate("messageId", messageID)
    			.request(MediaType.APPLICATION_JSON)
    			.post(Entity.entity(comment, MediaType.APPLICATION_JSON));
    	}
    
    private void deleteComment(int messageID, int commentID){
    	
    	String delete = client
    			.target(RestServiceURL)
    			.path("/messages/{messageId}/{commentId}")
    			.resolveTemplate("messageId", messageID)
    			.resolveTemplate("commentId", commentID)
    			.request(MediaType.APPLICATION_JSON)
    			.delete(String.class);
    }
    
}
