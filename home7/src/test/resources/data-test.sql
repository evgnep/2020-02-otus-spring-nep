delete
from author;
delete
from genre;
delete
from book;

insert into author(id, name)
values (1, 'z');

insert into genre(id, name)
values (1, 'g');

insert into book(id, name, description, author, genre)
values (1, 'a', 'aa', 1, 1);

