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
        "isbn": "1-453-2",
        "title": "Functional Programming in Scala",
        "author": "Paul Chiusano",
        "publisher": "Manning",
        "category": "Programming",
        "quantity": 4
    },
    {
        "isbn": "2-34-567",
        "title": "Harry Potter and the Deathly Hallows",
        "author": "JK Rowling",
        "publisher": "Bloomsbury",
        "category": "Fiction",
        "quantity": 2
    }
]
```
### Add books
#### Request
```POST /books/book```
##### Request Body
```json
{
    "isbn":"1-453-2",
    "title":"Functional Programming in Scala",
    "author":"Paul Chiusano",
    "publisher":"Manning",
    "category":"Programming"
}
```
#### Response
```json
{
    "isbn": "1-453-2",
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
    "isbn": "1-453-2",
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
        "isbn": "2-34-567",
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
        "isbn": "2-34-567",
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
        "isbn": "2-34-567",
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
        "isbn": "2-34-567",
        "title": "Harry Potter and the Deathly Hallows",
        "author": "JK Rowling",
        "publisher": "Bloomsbury",
        "category": "Fiction",
        "quantity": 1
    }
]
```

