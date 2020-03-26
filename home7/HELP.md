Домашнее задание - хранение информации о книгах, авторах и жанрах


Структура БД:

Таблица book
* id
* name
* description

Таблица author
* id
* name

Таблица genre
* id
* name

Таблица book_author (организация связи многие ко многим)
* book
* author
* no

Таблица book_genre (организация связи многие ко многим)
* book
* genre

