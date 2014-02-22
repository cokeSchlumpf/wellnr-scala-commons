package com.wellnr.commons.play.viewmodels

import com.wellnr.commons.model.{ AbstractEntity, ID }
import com.wellnr.commons.Development
import com.wellnr.commons.Text.StringHelper
import play.api.db.slick.Config.driver.simple._

/**
 * Copyright 2014. Michael Wellner.
 *
 * Represents a base model for displaying tables in a view.
 *
 * @author info@michaelwellner.de
 * @since Jan 22, 2014
 */
abstract class AbstractTableModel[T <% { def ID: Long }](data: Seq[T]) extends ViewModel {

  protected def column[CT](header: String, visible: Boolean = true, width: Option[Int] = None)(implicit mapper: CT => String) = ColumnModel[CT](header, visible, width)

  type TYPE = T

  /**
   * Returns the projection of a row.
   */
  def * : RowProjection[TYPE]

  /**
   * Returns the actions for each row
   */
  def actions: List[TableAction] = *.actions

  /**
   * Returns all rows as String columns
   */
  def rows: List[List[String]] = data.toList.map(e => *.project(e))
  
  /**
   * Returns all rows as String columns
   */
  def rowsMap: List[(Long, List[String])] = data.toList.map(e => (e.ID, *.project(e)))

  /**
   * Returns the data objects of the rows
   */
  def rowdata: Seq[TYPE] = data

  /**
   * Return the headers of the table
   */
  def headers: List[String] = *.headers

  def classNames: List[String] = headers.map(_.replace("#", "id").camelify)

  def widths: List[Option[Int]] = *.widths

  implicit def optionMapper[T](option: Option[T])(implicit f: T => String): String = option match {
    case Some(t) => f(t)
    case None => ""
  }

  implicit def doubleMapper(d: Double): String = d.toString

  implicit def listMapper[T](list: List[T])(implicit f: T => String): String = list.map(f).mkString(", ")

  implicit def idMapper2[T <: AbstractEntity[T]](id: ID[T]) = id.id.toString

  /**
   * Table specific actions
   */
  case class TemplateAction(template: TYPE => play.api.templates.HtmlFormat.Appendable) extends TableAction

}

trait TableAction
case class HyperlinkAction(label: String, href: Long => play.api.mvc.Call) extends TableAction

abstract class AbstractEntityTableModel[T <: AbstractEntity[T]](data: Seq[T]) extends AbstractTableModel[T](data) {

  /**
   * the standard id column
   */
  def id = column[Option[ID[TYPE]]]("#", width = Some(30))

}

/**
 * Case class to represent a column of a table model.
 */
case class ColumnModel[CT](header: String, visible: Boolean, width: Option[Int])(implicit val mapper: CT => String) {

  type COLUMN_TYPE = CT

  def ~[CT2](other: ColumnModel[CT2]) = new RowModel2(this, other)

  def apply(t: CT) = mapper(t)

}

/**
 * A general row model trait.
 */
trait RowModel[T] {

  type TUPLE = T

  def transform(t: TUPLE): List[String]

  def columns: List[ColumnModel[_]]

  def <>[T](f: T => Option[TUPLE]): RowProjection[T] = {
    val g: T => List[String] = { t =>
      f(t) match {
        case Some(t) => transform(t)
        case None => List()
      }
    }
    RowProjection[T](g, columns)
  }

  def ~[CT](other: ColumnModel[CT]): RowModel[_]

}

case class RowProjection[T](project: T => List[String], columns: List[ColumnModel[_]], actions: List[TableAction] = List()) {

  def columnsf = columns.filter(_.visible)

  def headers = columnsf.map(_.header)

  def widths = columnsf.map(_.width)

  def ~(action: TableAction) = this.copy(actions = actions :+ action)

}

/** Generated Row Model for 2 columns. */
case class RowModel2[CT1, CT2](val c1: ColumnModel[CT1], val c2: ColumnModel[CT2]) extends RowModel[(CT1, CT2)] {
  def columns = List(c1, c2)
  def transform(t: TUPLE) = List((c1, c1(t._1)), (c2, c2(t._2))).filter(_._1.visible).map(_._2)
  def ~[CT3](other: ColumnModel[CT3]) = RowModel3(c1, c2, other)
}

/** Generated Row Model for 3 columns. */
case class RowModel3[CT1, CT2, CT3](val c1: ColumnModel[CT1], val c2: ColumnModel[CT2], val c3: ColumnModel[CT3]) extends RowModel[(CT1, CT2, CT3)] {
  def columns = List(c1, c2, c3)
  def transform(t: TUPLE) = List((c1, c1(t._1)), (c2, c2(t._2)), (c3, c3(t._3))).filter(_._1.visible).map(_._2)
  def ~[CT4](other: ColumnModel[CT4]) = RowModel4(c1, c2, c3, other)
}

/** Generated Row Model for 4 columns. */
case class RowModel4[CT1, CT2, CT3, CT4](val c1: ColumnModel[CT1], val c2: ColumnModel[CT2], val c3: ColumnModel[CT3], val c4: ColumnModel[CT4]) extends RowModel[(CT1, CT2, CT3, CT4)] {
  def columns = List(c1, c2, c3, c4)
  def transform(t: TUPLE) = List((c1, c1(t._1)), (c2, c2(t._2)), (c3, c3(t._3)), (c4, c4(t._4))).filter(_._1.visible).map(_._2)
  def ~[CT5](other: ColumnModel[CT5]) = RowModel5(c1, c2, c3, c4, other)
}

/** Generated Row Model for 5 columns. */
case class RowModel5[CT1, CT2, CT3, CT4, CT5](val c1: ColumnModel[CT1], val c2: ColumnModel[CT2], val c3: ColumnModel[CT3], val c4: ColumnModel[CT4], val c5: ColumnModel[CT5]) extends RowModel[(CT1, CT2, CT3, CT4, CT5)] {
  def columns = List(c1, c2, c3, c4, c5)
  def transform(t: TUPLE) = List((c1, c1(t._1)), (c2, c2(t._2)), (c3, c3(t._3)), (c4, c4(t._4)), (c5, c5(t._5))).filter(_._1.visible).map(_._2)
  def ~[CT6](other: ColumnModel[CT6]) = RowModel6(c1, c2, c3, c4, c5, other)
}

/** Generated Row Model for 6 columns. */
case class RowModel6[CT1, CT2, CT3, CT4, CT5, CT6](val c1: ColumnModel[CT1], val c2: ColumnModel[CT2], val c3: ColumnModel[CT3], val c4: ColumnModel[CT4], val c5: ColumnModel[CT5], val c6: ColumnModel[CT6]) extends RowModel[(CT1, CT2, CT3, CT4, CT5, CT6)] {
  def columns = List(c1, c2, c3, c4, c5, c6)
  def transform(t: TUPLE) = List((c1, c1(t._1)), (c2, c2(t._2)), (c3, c3(t._3)), (c4, c4(t._4)), (c5, c5(t._5)), (c6, c6(t._6))).filter(_._1.visible).map(_._2)
  def ~[CT7](other: ColumnModel[CT7]) = RowModel7(c1, c2, c3, c4, c5, c6, other)
}

/** Generated Row Model for 7 columns. */
case class RowModel7[CT1, CT2, CT3, CT4, CT5, CT6, CT7](val c1: ColumnModel[CT1], val c2: ColumnModel[CT2], val c3: ColumnModel[CT3], val c4: ColumnModel[CT4], val c5: ColumnModel[CT5], val c6: ColumnModel[CT6], val c7: ColumnModel[CT7]) extends RowModel[(CT1, CT2, CT3, CT4, CT5, CT6, CT7)] {
  def columns = List(c1, c2, c3, c4, c5, c6, c7)
  def transform(t: TUPLE) = List((c1, c1(t._1)), (c2, c2(t._2)), (c3, c3(t._3)), (c4, c4(t._4)), (c5, c5(t._5)), (c6, c6(t._6)), (c7, c7(t._7))).filter(_._1.visible).map(_._2)
  def ~[CT8](other: ColumnModel[CT8]) = RowModel8(c1, c2, c3, c4, c5, c6, c7, other)
}

/** Generated Row Model for 8 columns. */
case class RowModel8[CT1, CT2, CT3, CT4, CT5, CT6, CT7, CT8](val c1: ColumnModel[CT1], val c2: ColumnModel[CT2], val c3: ColumnModel[CT3], val c4: ColumnModel[CT4], val c5: ColumnModel[CT5], val c6: ColumnModel[CT6], val c7: ColumnModel[CT7], val c8: ColumnModel[CT8]) extends RowModel[(CT1, CT2, CT3, CT4, CT5, CT6, CT7, CT8)] {
  def columns = List(c1, c2, c3, c4, c5, c6, c7, c8)
  def transform(t: TUPLE) = List((c1, c1(t._1)), (c2, c2(t._2)), (c3, c3(t._3)), (c4, c4(t._4)), (c5, c5(t._5)), (c6, c6(t._6)), (c7, c7(t._7)), (c8, c8(t._8))).filter(_._1.visible).map(_._2)
  def ~[CT9](other: ColumnModel[CT9]) = RowModel9(c1, c2, c3, c4, c5, c6, c7, c8, other)
}

/** Generated Row Model for 9 columns. */
case class RowModel9[CT1, CT2, CT3, CT4, CT5, CT6, CT7, CT8, CT9](val c1: ColumnModel[CT1], val c2: ColumnModel[CT2], val c3: ColumnModel[CT3], val c4: ColumnModel[CT4], val c5: ColumnModel[CT5], val c6: ColumnModel[CT6], val c7: ColumnModel[CT7], val c8: ColumnModel[CT8], val c9: ColumnModel[CT9]) extends RowModel[(CT1, CT2, CT3, CT4, CT5, CT6, CT7, CT8, CT9)] {
  def columns = List(c1, c2, c3, c4, c5, c6, c7, c8, c9)
  def transform(t: TUPLE) = List((c1, c1(t._1)), (c2, c2(t._2)), (c3, c3(t._3)), (c4, c4(t._4)), (c5, c5(t._5)), (c6, c6(t._6)), (c7, c7(t._7)), (c8, c8(t._8)), (c9, c9(t._9))).filter(_._1.visible).map(_._2)
  def ~[CT10](other: ColumnModel[CT10]) = RowModel10(c1, c2, c3, c4, c5, c6, c7, c8, c9, other)
}

/** Generated Row Model for 10 columns. */
case class RowModel10[CT1, CT2, CT3, CT4, CT5, CT6, CT7, CT8, CT9, CT10](val c1: ColumnModel[CT1], val c2: ColumnModel[CT2], val c3: ColumnModel[CT3], val c4: ColumnModel[CT4], val c5: ColumnModel[CT5], val c6: ColumnModel[CT6], val c7: ColumnModel[CT7], val c8: ColumnModel[CT8], val c9: ColumnModel[CT9], val c10: ColumnModel[CT10]) extends RowModel[(CT1, CT2, CT3, CT4, CT5, CT6, CT7, CT8, CT9, CT10)] {
  def columns = List(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10)
  def transform(t: TUPLE) = List((c1, c1(t._1)), (c2, c2(t._2)), (c3, c3(t._3)), (c4, c4(t._4)), (c5, c5(t._5)), (c6, c6(t._6)), (c7, c7(t._7)), (c8, c8(t._8)), (c9, c9(t._9)), (c10, c10(t._10))).filter(_._1.visible).map(_._2)
  def ~[CT11](other: ColumnModel[CT11]) = Development.NotImplemented
}

/*
 * Use the following Program to generate the lines above:
 */
object CodeCreator extends App {

  def createCaseClass(num: Int): String = {

    val lists = (1 to num).map(i =>
      (s"CT$i", s"val c$i: ColumnModel[CT$i]", s"(c$i, c$i(t._$i))", s"c$i", s"(c$i, c$i.header)"))

    s"""/** Generated Row Model for ${num} columns. */
case class RowModel${num}[${lists.map(_._1).mkString(", ")}](${lists.map(_._2).mkString(", ")}) extends RowModel[(${lists.map(_._1).mkString(", ")})] { 
  def columns = List(${lists.map(_._4).mkString(", ")})
  def transform(t: TUPLE) = List(${lists.map(_._3).mkString(", ")}).filter(_._1.visible).map(_._2)
  def ~[CT${num + 1}](other: ColumnModel[CT${num + 1}]) = RowModel${num + 1}(${lists.map(_._4).mkString(", ")}, other)
}"""
  }

  println((2 to 10).map(createCaseClass).mkString("\n\n"))

}
