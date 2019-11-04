package code

import org.scalatest.Matchers
import reactivemongo.bson.{BSONArray, BSONDocument, BSONString}

class MainTest extends org.scalatest.WordSpec with Matchers {

  "EqualsQuery" should {
    "work" in {
      EqualsQuery("key", BSONString("value")).toBSONDocument shouldBe BSONDocument("key" -> BSONString("value"))
    }
  }

  "GreaterThanQuery" should {
    "work" in {
      GreaterThanQuery("key", BSONString("value")).toBSONDocument shouldBe BSONDocument("key" -> BSONDocument("$gt" -> BSONString("value")))
    }
  }

  "AndQuery" should {
    "work" in {
      AndQuery(EqualsQuery("key", BSONString("value")), GreaterThanQuery("key2", BSONString("value2"))).toBSONDocument shouldBe BSONDocument("$and" -> BSONArray(
          BSONDocument("key" -> BSONString("value")),
          BSONDocument("key2" -> BSONDocument("$gt" -> BSONString("value2")))
      ))
    }
  }

  "OrQuery" should {
    "work" in {
      OrQuery(EqualsQuery("key", BSONString("value")), GreaterThanQuery("key2", BSONString("value2"))).toBSONDocument shouldBe BSONDocument("$or" -> BSONArray(
        BSONDocument("key" -> BSONString("value")),
        BSONDocument("key2" -> BSONDocument("$gt" -> BSONString("value2")))
      ))
    }
  }

  "Fancy syntax" should {
    import code.syntax._

    "work" in {
      "key" === "value" shouldBe EqualsQuery("key", BSONString("value"))

    }
  }

}
