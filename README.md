# software-design-cli

Java 17
Gradle 8.4

## Задача
Реализовать простой интепретатор командной строки, поддерживающий команды

- `cat [FILE]` - вывести на экран содержимое файла
- `echo` - вывести на экран свой аргумент/-ы
- `wc [FILE]` - вывести количество строк, слов и байт в файле
- `pwd` - распечатать текущую директорию
- `exit` - выйти из интерпретатора
- Поддержка двойных и одинарных кавычек
- Окружение вида (`имя=значение`) и оператор `$`
- Вызов внешней программы
- Пайплайн `|`

## Структурная диаграмма

![First iteration scheme](/images/architecure.drawio.svg)

## Команда

| Участники |
|-----------|
| Шкандюк Денис  |
| Мулюкова Амина  |
| Поляков Андрей  |
| Ворожбитов Никита  |
| Жарков Федор  |
