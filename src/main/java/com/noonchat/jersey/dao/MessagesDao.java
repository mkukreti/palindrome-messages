package com.noonchat.jersey.dao;

import com.noonchat.jersey.models.Message;
import com.noonchat.jersey.utils.Settings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object to fetch data entities from connected database
 *
 * The required database schema resides in - palindrome-messages-webapp/src/main/webapp/schema/palindrome-messages.ddl
 */
public class MessagesDao
{
    private static String JDBC_CONNECTION_URL;

    public MessagesDao() throws Exception
    {
        JDBC_CONNECTION_URL = Settings.get("JDBC_CONNECTION_URL");
        Class.forName("org.postgresql.Driver");
    }

    public List<Message> getAllMessages() throws Exception
    {
        String sql = "SELECT * FROM t_palindrome_messages";
        List<Message> messages = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_URL);
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            try (ResultSet rs = ps.executeQuery())
            {
                while(rs.next())
                {
                    messages.add(new Message(rs));
                }
            }

        }
        catch (SQLException e)
        {
            throw new Exception("Unable to fetch messages", e);
        }

        return messages;
    }

    public Message addMessage(String message) throws Exception
    {
        boolean isPalindrome = _isPalindrome(message);

        String sql = "INSERT INTO t_palindrome_messages (message, is_palindrome) VALUES (?, ?) RETURNING *";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_URL);
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, message);
            ps.setBoolean(2, isPalindrome);

            try (ResultSet rs = ps.executeQuery())
            {
                if(rs.next())
                {
                    return new Message(rs);
                }

                throw new Exception("Unable to submit message");
            }
        }
        catch (SQLException e)
        {
            throw new Exception("Unable to submit message", e);
        }
    }

    public Message deleteMessage(Long messageId) throws Exception
    {
        String sql = "DELETE FROM t_palindrome_messages WHERE id = ? RETURNING *";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_URL);
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, messageId);

            try (ResultSet rs = ps.executeQuery())
            {
                if(rs.next())
                {
                    return new Message(rs);
                }

                throw new Exception("Unable to delete message");
            }
        }
        catch (SQLException e)
        {
            throw new Exception("Unable to delete message", e);
        }
    }

    public Message updateMessage(Long messageId, String newMessage) throws Exception
    {
        String sql = "UPDATE t_palindrome_messages SET message = ?, is_palindrome = ?, occurred = ? WHERE id = ? RETURNING *";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_URL);
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, newMessage);
            ps.setBoolean(2, _isPalindrome(newMessage));
            ps.setTimestamp(3, Timestamp.from(Instant.now()));
            ps.setLong(4, messageId);

            try (ResultSet rs = ps.executeQuery())
            {
                if(rs.next())
                {
                    return new Message(rs);
                }

                throw new Exception("Unable to update message");
            }
        }
        catch (SQLException e)
        {
            throw new Exception("Unable to update message", e);
        }
    }

    private boolean _isPalindrome(String message)
    {
        int i = 0, j = message.length() - 1;

        while (i < j)
        {
            if (message.charAt(i) != message.charAt(j))
            {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }
}
