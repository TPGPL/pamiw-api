CREATE TABLE IF NOT EXISTS Books (
    id INT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author_id INT NOT NULL,
    publisher_id INT NOT NULL,
    page_count INT NOT NULL,
    release_date DATE NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    CONSTRAINT FK_BOOK_AUTHOR FOREIGN KEY (author_id) REFERENCES Authors(id),
    CONSTRAINT FK_BOOK_PUBLISHER FOREIGN KEY (publisher_id) REFERENCES Publishers(id)
)