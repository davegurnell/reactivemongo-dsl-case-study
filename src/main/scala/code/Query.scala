package code

import reactivemongo.bson.{BSONArray, BSONDocument, BSONDocumentWriter, BSONValue}

sealed trait Query {
  def toBSONDocument: BSONDocument
}

object Query {
  implicit val writer: BSONDocumentWriter[Query] = {
    BSONDocumentWriter(_.toBSONDocument)
  }
}

case class EqualsQuery(fieldName: String, value: BSONValue) extends Query {
  override def toBSONDocument: BSONDocument = BSONDocument(fieldName -> value)
}

case class GreaterThanQuery(fieldName: String, value: BSONValue) extends Query {
  override def toBSONDocument: BSONDocument = BSONDocument(fieldName -> BSONDocument("$gt"->value))
}

case class AndQuery(q1: Query, q2: Query) extends Query {
  override def toBSONDocument: BSONDocument = BSONDocument("$and" ->
    BSONArray(
      q1.toBSONDocument,
      q2.toBSONDocument
    ))
}

case class OrQuery(q1: Query, q2: Query) extends Query {
  override def toBSONDocument: BSONDocument = BSONDocument("$or" ->
    BSONArray(
      q1.toBSONDocument,
      q2.toBSONDocument
    ))
}
