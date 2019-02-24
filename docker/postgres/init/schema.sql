-- vim:sw=2:et:
\c todo;
CREATE EXTENSION pgcrypto;

-- activity {{{2
CREATE TABLE IF NOT EXISTS task (
  id          UUID NOT NULL PRIMARY KEY,
  title       VARCHAR(128) NOT NULL, 
  link        TEXT DEFAULT NULL, 
  due_date    DATE DEFAULT NULL,
  done        BOOLEAN NOT NULL DEFAULT FALSE,
  created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
  updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
