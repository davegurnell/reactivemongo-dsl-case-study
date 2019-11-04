package code

import reactivemongo.api.{MongoConnection, MongoDriver}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object Main extends App {
  val driver = MongoDriver()

  def collection: Future[BSONCollection] =
    for {
      uri  <- Future.fromTry(MongoConnection.parseURI(s"mongodb://localhost:27017"))
      conn <- Future.fromTry(driver.connection(uri, strictUri = true))
      db   <- conn.database("olympics")
    } yield db.collection[BSONCollection]("medals")

  val query: Nothing =
    ???

  def program: Future[List[BSONDocument]] =
    collection.flatMap { collection =>
      collection.find(query, None).cursor[BSONDocument]().collect[List](-1)
    }

  println(Await.result(program, 10.seconds))
}
