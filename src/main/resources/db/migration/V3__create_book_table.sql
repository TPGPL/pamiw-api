CREATE TABLE IF NOT EXISTS Books (
    id INT GENERATED BY DEFAULT AS IDENTITY,
    title VARCHAR(100) NOT NULL,
    author_id INT NOT NULL,
    publisher_id INT NOT NULL,
    page_count INT NOT NULL,
    release_date DATE NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    CONSTRAINT PK_BOOKS PRIMARY KEY (id),
    CONSTRAINT FK_BOOK_AUTHOR FOREIGN KEY (author_id) REFERENCES Authors(id),
    CONSTRAINT FK_BOOK_PUBLISHER FOREIGN KEY (publisher_id) REFERENCES Publishers(id)
)