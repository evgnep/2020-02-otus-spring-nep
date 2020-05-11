insert into author(id, name)
values (1, 'Иванов');
insert into author(id, name)
values (2, 'Петров');

insert into genre(id, name)
values (1, 'Детектив');
insert into genre(id, name)
values (2, 'История');

insert into book(id, name, description, author_id, genre_id)
values (1, 'Убийство в саду', 'Очень интересный детектив', 1, 1);
insert into book(id, name, description, author_id, genre_id)
values (2, 'Петр 1', 'Не очень интересная история', 2, 2);

insert into book_comment(id, book_id, author, comment)
values (1, 1, 'Васечкин', 'Супер!');

