package com.noonchat.jersey.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Entity class mapping to a single message row.
 *
 * Database schema - palindrome-messages-webapp/src/main/webapp/schema/palindrome-messages.ddl
 */
public class Message
{
    private String id;
    private String body;
    @JsonProperty("palindrome")
    private boolean isPalindrome;
    private String  timestamp;

    public Message()
    {
        // For jackson deserialization
    }

    public Message(ResultSet rs) throws SQLException
    {
        id = String.valueOf(rs.getLong("id"));
        body = rs.getString("message");
        isPalindrome = rs.getBoolean("is_palindrome");
        timestamp = rs.getDate("occurred").toString();
    }

    public String getBody()
    {
        return body;
    }

    public String getId()
    {
        return id;
    }

    public boolean isPalindrome()
    {
        return isPalindrome;
    }

    public String getTimestamp()
    {
        return timestamp;
    }


}
