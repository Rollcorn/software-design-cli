## Общие сведения
Простой интерпретатор командной строки: bash-like.

## Установка и использование

todo

## Описание функций
Поддерживает:

- команды: `cat, echo, wc, pwd, exit`
- одинарные и двойные кавычки
- переменные окружения
- вызов внешней команды
- пайплайны

## Пример использования
 
```bash
echo "Hello, world!"
# Hello, world!
```
```bash
FILE=example.txt
cat $FILE`
# Some example text
```
```bash
cat example.txt | wc
# 1 3 18
```
```bash
echo 123 | wc
# 1 1 3
```
```bash
x=ex
y=it
$x$y
# exit
```

## Структура программы

- Точка входа в программу `main`: запуск процесса работы программы (бесконечное считывание строк)
- Парсер строки
   - Токенизация строки и создание классов команд для каждого токена
   - Подстановка в переменные окружения значения 
- Глобальный scope переменных окружения
- Глобальный поток, который передается на вход каждой команде и изменяется при выходе из команды
- Executor команд

## Процесс работы программы

1. `Main.java` запускает процесс работы программы.
2. `ICliImpl.java` создает глобальный scope переменных и поток, 
запускает цикл считывания строк, в котором происходит парсинг строки и дальнейший вызов
Executor для выполнения команд.
   1. Глобальный scope переменныйх представляет собой HashMap<String, String>
   2. Глобальный поток - класс с полем String
   3. while - цикл, дожидающийся команды `exit`
3. `ParserImpl.java` - парсер, который предварительно делает подстановку из 
глобального scope переменных, токенизирует строку и создает вектор команд.
4. Каждая команда представлена своим классом, наследующимся от `ICommand`,
где у каждой команды есть функция execute и своя логика выполнения.

## Диаграмма архитектуры

![Архитектура программы](/images/architecure_fix.drawio.svg)

## Дополнительные материалы

Документация bash

1. [exit](https://www.gnu.org/software/bash/manual/bash.html#Exit-Status)
2. [echo](https://www.gnu.org/software/bash/manual/bash.html#index-echo)
3. [pwd](https://www.gnu.org/software/bash/manual/bash.html#index-pwd)
4. [cat](https://www.gnu.org/software/coreutils/manual/html_node/cat-invocation.html#cat-invocation)
5. [wc](https://www.gnu.org/software/coreutils/manual/html_node/wc-invocation.html#wc-invocation)