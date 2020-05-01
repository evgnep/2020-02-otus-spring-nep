create table author
(
    id   int primary key,
    name varchar(200)
);

create table genre
(
    id   int primary key,
    name varchar(200)
);

create table book
(
    id          int primary key,
    name        varchar(200),
    description varchar(1000),
    author      int,
    genre       int,
    foreign key (author) references author (id) on delete cascade on update restrict,
    foreign key (genre) references genre (id) on delete cascade on update restrict
);
