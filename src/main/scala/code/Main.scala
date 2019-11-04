package code

import code.syntax._
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{Cursor, MongoConnection, MongoDriver}
import reactivemongo.bson.BSONDocument

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object Main extends App {
  val driver = MongoDriver()

  def collection: Future[BSONCollection] =
    for {
      uri  <- Future.fromTry(MongoConnection.parseURI(s"mongodb://localhost:27017"))
      conn <- Future.fromTry(driver.connection(uri, strictUri = true))
      db   <- conn.database("olympics")
    } yield db.collection[BSONCollection]("medals")

  val query: Query =
    "team" === "GBR" && "gold" === 1

  def program: Future[List[BSONDocument]] =
    collection.flatMap { collection =>
      collection.find(query, None).cursor[BSONDocument]().collect[List](-1, Cursor.FailOnError())
    }

  println(Await.result(program, 10.seconds))
}
