CREATE TABLE IF NOT EXISTS Authors (
    id INT GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT PK_AUTHOR PRIMARY KEY (id)
)