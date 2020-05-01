insert into author(id, name)
values (1, 'z');

insert into genre(id, name)
values (1, 'g');

insert into book(id, name, description, author, genre)
values (1, 'a', 'aa', 1, 1);

insert into book_comment(id, author, comment, book)
values (1, 'x', 'some_comment', 1);

insert into book_comment(id, author, comment, book)
values (2, 'y', 'other_comment', 1);

