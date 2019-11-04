package code

import reactivemongo.bson.{BSONInteger, BSONString, BSONValue, BSONWriter}

object syntax {
  def and(q1: Query, q2: Query*): Query =
    q2.foldLeft(q1)(AndQuery.apply)

  def eql[T, B <: BSONValue](key: String, value: T)(implicit writer: BSONWriter[T, B]): Query =
    EqualsQuery(key, writer.write(value))

  def str(value: String): BSONString = BSONString(value)
  def int(value: Int): BSONInteger = BSONInteger(value)

  implicit class StringOops(fieldName: String) {
    def ===[T, B <: BSONValue](value: T)(implicit writer: BSONWriter[T, B]): Query = {
      eql(fieldName, value)
    }
  }

  implicit class QueryOops(val q1: Query) extends AnyVal {
    def &&(q2: Query): Query = and(q1,q2)
  }
}
