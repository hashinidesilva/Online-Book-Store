# Online-Book-Store

## API
```base URL = http://localhost:8080```
### Get List of Books
#### Request
```GET /books```
#### Response
```json
[
    {
        "isbn": "978-1617290657",
        "title": "Functional Programming in Scala",
        "author": "Paul Chiusano",
        "publisher": "Manning",
        "category": "Programming",
        "quantity": 4
    },
    {
        "isbn": "9781781102695",
        "title": "Harry Potter and the Deathly Hallows",
        "author": "JK Rowling",
        "publisher": "Bloomsbury",
        "category": "Fiction",
        "quantity": 2
    }
]
```
### Add book
#### Request
```POST /books/book```
##### Request Body
```json
{
    "isbn":"978-1617290657",
    "title":"Functional Programming in Scala",
    "author":"Paul Chiusano",
    "publisher":"Manning",
    "category":"Programming"
}
```
#### Response
```json
{
    "isbn": "978-1617290657",
    "title": "Functional Programming in Scala",
    "author": "Paul Chiusano",
    "publisher": "Manning",
    "category": "Programming",
    "quantity": 1
}
```
### Get book
#### Request
```GET /books/book/<<isbn>>```
#### Response
```json
{
    "isbn": "978-1617290657",
    "title": "Functional Programming in Scala",
    "author": "Paul Chiusano",
    "publisher": "Manning",
    "category": "Programming",
    "quantity": 1
}

```
### Search by Title
#### Request
```GET /books?title=<<title>>```
#### Response
```json
[
    {
        "isbn": "9781781102695",
        "title": "Harry Potter and the Deathly Hallows",
        "author": "JK Rowling",
        "publisher": "Bloomsbury",
        "category": "Fiction",
        "quantity": 1
    }
]
```
### Search by Author
#### Request
```GET /books?author=<<author>>```
#### Response
```json
[
    {
        "isbn": "9781781102695",
        "title": "Harry Potter and the Deathly Hallows",
        "author": "JK Rowling",
        "publisher": "Bloomsbury",
        "category": "Fiction",
        "quantity": 1
    }
]
```
### Search by Publisher
#### Request
```GET /books?publisher=<<publisher>>```
#### Response
```json
[
    {
        "isbn": "9781781102695",
        "title": "Harry Potter and the Deathly Hallows",
        "author": "JK Rowling",
        "publisher": "Bloomsbury",
        "category": "Fiction",
        "quantity": 1
    }
]
```
### Search by Category
#### Request
```GET /books?category=<<category>>```
#### Response
```json
[
    {
        "isbn": "9781781102695",
        "title": "Harry Potter and the Deathly Hallows",
        "author": "JK Rowling",
        "publisher": "Bloomsbury",
        "category": "Fiction",
        "quantity": 1
    }
]
```
## RabbitMQ
```directory: /src/main/scala/rabbitmq```
### Add book
```client-CreateSubscriber```
#### Request
```CreateBook(book:Book)```
### Get book
```client-GetSubscriber```
#### Request
```GetBook(isbn:String)```
### Search book
```client-SearchSubscriber```
#### Request
```SearchBooks(criteria:String,value:String)```
