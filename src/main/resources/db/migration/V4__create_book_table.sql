CREATE TABLE IF NOT EXISTS Books (
    id INT PRIMARY KEY,
    title VARCHAR(100),
    author_id INT,
    publisher_id INT,
    genre_id INT,
    page_count INT,
    release_date DATE,
    isbn VARCHAR(13),
    CONSTRAINT FK_BOOK_AUTHOR FOREIGN KEY (author_id) REFERENCES Authors(id),
    CONSTRAINT FK_BOOK_PUBLISHER FOREIGN KEY (publisher_id) REFERENCES Publishers(id),
    CONSTRAINT FK_BOOK_GENRE FOREIGN KEY (genre_id) REFERENCES Genres(id)
)