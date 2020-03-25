import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noonchat.jersey.models.Message;
import com.noonchat.jersey.utils.Settings;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class PalindromeMessagesFunctionalTest
{
    private static final String BASE_URL = Settings.get("TEST_HOST_URL");

    @Test
    public void testGetMessages() throws IOException
    {
        String testMessage = "test";
        Message postedMessage = _postMessage(testMessage);
        assertNotNull(postedMessage);

        List<Message> messages = _getMessages();

        assertNotNull(messages);
        assertTrue(messages.size() >= 1);

        Message deletedMessage = _deleteMessage(postedMessage.getId());
        assertNotNull(deletedMessage);
    }

    @Test
    public void testPostMessage() throws IOException
    {
        String testMessage = "test";
        Message postedMessage = _postMessage(testMessage);
        assertNotNull(postedMessage);
        assertEquals(testMessage, postedMessage.getBody());

        Message deletedMessage = _deleteMessage(postedMessage.getId());
        assertNotNull(deletedMessage);
    }

    @Test
    public void testDeleteMessage() throws IOException
    {
        String testMessage = "test";
        Message postedMessage = _postMessage(testMessage);
        assertNotNull(postedMessage);

        Message deletedMessage = _deleteMessage(postedMessage.getId());
        assertNotNull(deletedMessage);
        assertEquals(deletedMessage.getId(), postedMessage.getId());
    }

    @Test
    public void testUpdateMessage() throws IOException
    {
        String testMessage = "test";
        Message postedMessage = _postMessage(testMessage);
        assertNotNull(postedMessage);

        Message updatedMessage = _updateMessage(postedMessage.getId(), "updated");
        assertNotNull(updatedMessage);
        assertEquals(postedMessage.getId(), updatedMessage.getId());
        assertEquals(updatedMessage.getBody(), "updated");

        Message deletedMessage = _deleteMessage(postedMessage.getId());
        assertNotNull(deletedMessage);
        assertEquals(deletedMessage.getId(), postedMessage.getId());
    }

    @Test
    public void testPalindromeMessage() throws IOException
    {
        String testMessage = "madam";
        Message postedMessage = _postMessage(testMessage);
        assertNotNull(postedMessage);
        assertTrue(postedMessage.isPalindrome());

        Message updatedMessage = _updateMessage(postedMessage.getId(), "Madam");

        System.out.println(updatedMessage.isPalindrome());
        assertNotNull(updatedMessage);
        assertTrue(!updatedMessage.isPalindrome());

        Message deletedMessage = _deleteMessage(postedMessage.getId());
        assertNotNull(deletedMessage);
    }

    private List<Message> _getMessages() throws JsonProcessingException, IOException
    {
        HttpUriRequest request = new HttpGet(BASE_URL + "rest/palindrome/messages" );
        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        String responeString = EntityUtils.toString(httpResponse.getEntity());
        return new ObjectMapper().readValue(responeString, List.class);
    }

    private Message _postMessage(String message) throws JsonProcessingException, IOException
    {
        HttpPost request = new HttpPost(BASE_URL + "rest/palindrome/messages/" + message);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);
        String responeString = EntityUtils.toString(response.getEntity());
        return new ObjectMapper().readValue(responeString, Message.class);
    }

    private Message _deleteMessage(String messageId) throws JsonProcessingException, IOException
    {
        HttpDelete request = new HttpDelete(BASE_URL + "rest/palindrome/messages/" + messageId);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);
        String responeString = EntityUtils.toString(response.getEntity());
        return new ObjectMapper().readValue(responeString, Message.class);
    }

    private Message _updateMessage(String messageId, String newMessage) throws JsonProcessingException, IOException
    {
        HttpPost request = new HttpPost(BASE_URL + "rest/palindrome/messages/" + messageId + "/" + newMessage);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);
        String responeString = EntityUtils.toString(response.getEntity());
        return new ObjectMapper().readValue(responeString, Message.class);
    }
}
