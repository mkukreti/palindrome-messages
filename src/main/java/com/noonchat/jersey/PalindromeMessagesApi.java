package com.noonchat.jersey;

import com.noonchat.jersey.dao.MessagesDao;
import com.noonchat.jersey.models.Message;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Jersey API to perform CRUD operations for palindrome messages
 *
 */
@Path("/palindrome")
public class PalindromeMessagesApi
{
    @GET
    @Path("/messages")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getMessages() throws Exception
    {
        return new MessagesDao().getAllMessages();
    }

    @POST
    @Path("/messages/{message}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Message postMessage(@PathParam("message") final String message) throws Exception
    {
       return new MessagesDao().addMessage(message);
    }

    @DELETE
    @Path("/messages/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Message deleteMessage(@PathParam("messageId") final Long messageId) throws Exception
    {
        return new MessagesDao().deleteMessage(messageId);
    }

    @POST
    @Path("/messages/{messageId}/{newMessage}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Message updateMessage(@PathParam("messageId") final Long messageId,
            @PathParam("newMessage") String newMessage) throws Exception
    {
        return new MessagesDao().updateMessage(messageId, newMessage);
    }
}
