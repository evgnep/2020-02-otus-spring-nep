insert into author(id, name)
values (1, 'Иванов');
insert into author(id, name)
values (2, 'Петров');

insert into genre(id, name)
values (1, 'Детектив');
insert into genre(id, name)
values (2, 'История');

insert into book(id, name, description, author, genre)
values (1, 'Убийство в саду', 'Очень интересный детектив', 1, 1);
insert into book(id, name, description, author, genre)
values (2, 'Петр 1', 'Не очень интересная история', 2, 2);

