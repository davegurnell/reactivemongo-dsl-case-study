# ReactiveMongo Query DSL

Let's build a library to simplify querying MongoDB using ReactiveMongo.
As usual, we'll use mob programming and TDD practices.

Specifically, when we ask for data from ReactiveMongo,
we have to specify a `query` object like this:

```scala
collection
  .find(query, projection)
  .cursor[ResultType]()
```

Depending on the collection type, the `query` parameter is either
a `BSONDocument` or `JsValue` representing a query to run.
For example:

```scala
val query: BSONDocument =
  BSONDocument(List(
    "$and" -> BSONArray(List(
      BSONDocument(List("team" -> BSONString("GBR"))),
      BSONDocument(List("gold" -> BSONInteger(1))),
    ))
  ))
```

We'd like to get this down to something more maintainable.

## Objectives

Create a simple query language that supports,
at minimum, the following types of query conditions:

- equality, inequality, and comparison operators 
  (*equals*, *does not equal*, *greater than*, and so on);

- boolean logic 
  (*and*, *or*, and *not*).

The user should be able to write query conditions using a simple DSL, 
and convert them to a BSONDocument to pass them to `collection.find()`:

```scala
val query = // ...DSL goes here...

collection.find(query.toBSONDocument, projection).cursor()
```

## Strategy

1. Before you start coding, 
   consider the structure of the query objects you want to build.
   Think about simple queries first, 
   then about how to combine them to form more complex queries.
   Try to think in terms of *algebraic data types* -- "ands" and "ors"
   that you can encode in Scala using `sealed traits` and `case classes`.

2. Create Scala data types to represent queries.
   Write a method to convert a query to a `BSONDocument`
   so you can pass it to ReactiveMongo.
   Focus on the structure of the queries first.
   "Nice" syntax can come later.

3. Finally, build a nice syntax to create query object.
   Create an object called `syntax` next to your data types
   and populate it with methods and extension methods.
   
   Function-like syntaxes tend to be easier to build:
   
   ```scala
   and(eql("team", str("GBR")), eql("gold", int(1)))
   ``` 
   
   but with a few tricks like extension methods and implicit parameters,
   you can create more advanced things like infix operators:
   
   ```scala
   "team" === "GBR" && "gold" === 1
   ``` 
