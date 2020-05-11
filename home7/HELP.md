Домашнее задание - хранение информации о книгах, авторах и жанрах, shell-интерфейс

В рамках ДЗ-15 добавлен web-интерфейс

Выбрана БД H2. Структура БД:

Таблица book
* id
* name
* description
* author
* genre

Таблица author
* id
* name

Таблица genre
* id
* name

Таблица book_comment
* id
* author (автор комментария, просто текст)
* comment
* book (ссылка на книгу)
