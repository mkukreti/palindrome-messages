DROP TABLE IF EXISTS t_palindrome_messages;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE t_palindrome_messages
(
  id                    BIGSERIAL                  PRIMARY KEY,
  message               TEXT                       NOT NULL,
  is_palindrome         BOOLEAN                    NOT NULL,
  occurred              TIMESTAMP WITH TIME ZONE   NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_palindrome_messages ON t_palindrome_messages (is_palindrome);

COMMENT ON TABLE t_palindrome_messages IS 'Table to store palindrome messages';
COMMENT ON COLUMN t_palindrome_messages.id IS 'Message id';
COMMENT ON COLUMN t_palindrome_messages.is_palindrome IS 'Whether the message is a palindrome or not';
COMMENT ON COLUMN t_palindrome_messages.occurred IS 'Timestamp for the message';
