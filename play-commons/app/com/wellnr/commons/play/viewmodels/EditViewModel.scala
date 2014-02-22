package com.wellnr.commons.play.viewmodels

import com.wellnr.commons.model.AbstractEntity
import play.api.data.Form
import play.api.data.Forms.longNumber
import play.api.data.Mapping
import com.wellnr.commons.Development
import javax.annotation.Generated
import com.wellnr.commons.model.ID
import play.api.db.slick.Config.driver.simple._

/**
 * Copyright 2014. Michael Wellner.
 *
 * Interface for Edit View Models
 *
 * @author info@michaelwellner.de
 * @since Feb 9, 2014
 */
trait EditViewModel[E] extends ViewModel {

  import EditViewModel._
  
  def id[E <: AbstractEntity[E]]: Mapping[ID[E]] = longNumber.transform(ID[E], _.id)

  val formviewmodel: FormViewModel[E]

  lazy val form = formviewmodel.asForm

}

object EditViewModel {

  import play.api.data.Form._
  import play.api.data.Forms._

  trait FormViewModel[T] {

    /**
     * Creates a play.api.data.Form based on the view model.
     */
    def asForm: Form[T]

    /**
     * Returns a list of all fields.
     */
    def fields: List[FormField[_]]

  }

  object FormViewModel {
    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1](a1: FormField[A1])(apply: (A1) => R)(unapply: R => Option[(A1)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping)(apply)(unapply))
      def fields = List(a1)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2](a1: FormField[A1], a2: FormField[A2])(apply: (A1, A2) => R)(unapply: R => Option[(A1, A2)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping)(apply)(unapply))
      def fields = List(a1, a2)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3])(apply: (A1, A2, A3) => R)(unapply: R => Option[(A1, A2, A3)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4])(apply: (A1, A2, A3, A4) => R)(unapply: R => Option[(A1, A2, A3, A4)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5])(apply: (A1, A2, A3, A4, A5) => R)(unapply: R => Option[(A1, A2, A3, A4, A5)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6])(apply: (A1, A2, A3, A4, A5, A6) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6, A7](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6], a7: FormField[A7])(apply: (A1, A2, A3, A4, A5, A6, A7) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6, A7)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping, a7.name -> a7.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6, a7)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6, A7, A8](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6], a7: FormField[A7], a8: FormField[A8])(apply: (A1, A2, A3, A4, A5, A6, A7, A8) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6, A7, A8)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping, a7.name -> a7.mapping, a8.name -> a8.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6, a7, a8)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6, A7, A8, A9](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6], a7: FormField[A7], a8: FormField[A8], a9: FormField[A9])(apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping, a7.name -> a7.mapping, a8.name -> a8.mapping, a9.name -> a9.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6, a7, a8, a9)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6], a7: FormField[A7], a8: FormField[A8], a9: FormField[A9], a10: FormField[A10])(apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping, a7.name -> a7.mapping, a8.name -> a8.mapping, a9.name -> a9.mapping, a10.name -> a10.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6], a7: FormField[A7], a8: FormField[A8], a9: FormField[A9], a10: FormField[A10], a11: FormField[A11])(apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping, a7.name -> a7.mapping, a8.name -> a8.mapping, a9.name -> a9.mapping, a10.name -> a10.mapping, a11.name -> a11.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6], a7: FormField[A7], a8: FormField[A8], a9: FormField[A9], a10: FormField[A10], a11: FormField[A11], a12: FormField[A12])(apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping, a7.name -> a7.mapping, a8.name -> a8.mapping, a9.name -> a9.mapping, a10.name -> a10.mapping, a11.name -> a11.mapping, a12.name -> a12.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6], a7: FormField[A7], a8: FormField[A8], a9: FormField[A9], a10: FormField[A10], a11: FormField[A11], a12: FormField[A12], a13: FormField[A13])(apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping, a7.name -> a7.mapping, a8.name -> a8.mapping, a9.name -> a9.mapping, a10.name -> a10.mapping, a11.name -> a11.mapping, a12.name -> a12.mapping, a13.name -> a13.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6], a7: FormField[A7], a8: FormField[A8], a9: FormField[A9], a10: FormField[A10], a11: FormField[A11], a12: FormField[A12], a13: FormField[A13], a14: FormField[A14])(apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping, a7.name -> a7.mapping, a8.name -> a8.mapping, a9.name -> a9.mapping, a10.name -> a10.mapping, a11.name -> a11.mapping, a12.name -> a12.mapping, a13.name -> a13.mapping, a14.name -> a14.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6], a7: FormField[A7], a8: FormField[A8], a9: FormField[A9], a10: FormField[A10], a11: FormField[A11], a12: FormField[A12], a13: FormField[A13], a14: FormField[A14], a15: FormField[A15])(apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping, a7.name -> a7.mapping, a8.name -> a8.mapping, a9.name -> a9.mapping, a10.name -> a10.mapping, a11.name -> a11.mapping, a12.name -> a12.mapping, a13.name -> a13.mapping, a14.name -> a14.mapping, a15.name -> a15.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6], a7: FormField[A7], a8: FormField[A8], a9: FormField[A9], a10: FormField[A10], a11: FormField[A11], a12: FormField[A12], a13: FormField[A13], a14: FormField[A14], a15: FormField[A15], a16: FormField[A16])(apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping, a7.name -> a7.mapping, a8.name -> a8.mapping, a9.name -> a9.mapping, a10.name -> a10.mapping, a11.name -> a11.mapping, a12.name -> a12.mapping, a13.name -> a13.mapping, a14.name -> a14.mapping, a15.name -> a15.mapping, a16.name -> a16.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6], a7: FormField[A7], a8: FormField[A8], a9: FormField[A9], a10: FormField[A10], a11: FormField[A11], a12: FormField[A12], a13: FormField[A13], a14: FormField[A14], a15: FormField[A15], a16: FormField[A16], a17: FormField[A17])(apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping, a7.name -> a7.mapping, a8.name -> a8.mapping, a9.name -> a9.mapping, a10.name -> a10.mapping, a11.name -> a11.mapping, a12.name -> a12.mapping, a13.name -> a13.mapping, a14.name -> a14.mapping, a15.name -> a15.mapping, a16.name -> a16.mapping, a17.name -> a17.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17)
    }

    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18](a1: FormField[A1], a2: FormField[A2], a3: FormField[A3], a4: FormField[A4], a5: FormField[A5], a6: FormField[A6], a7: FormField[A7], a8: FormField[A8], a9: FormField[A9], a10: FormField[A10], a11: FormField[A11], a12: FormField[A12], a13: FormField[A13], a14: FormField[A14], a15: FormField[A15], a16: FormField[A16], a17: FormField[A17], a18: FormField[A18])(apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) => R)(unapply: R => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18)]) = new FormViewModel[R] {
      def asForm = Form(mapping(a1.name -> a1.mapping, a2.name -> a2.mapping, a3.name -> a3.mapping, a4.name -> a4.mapping, a5.name -> a5.mapping, a6.name -> a6.mapping, a7.name -> a7.mapping, a8.name -> a8.mapping, a9.name -> a9.mapping, a10.name -> a10.mapping, a11.name -> a11.mapping, a12.name -> a12.mapping, a13.name -> a13.mapping, a14.name -> a14.mapping, a15.name -> a15.mapping, a16.name -> a16.mapping, a17.name -> a17.mapping, a18.name -> a18.mapping)(apply)(unapply))
      def fields = List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18)
    }
  }

  trait FormViewMapping[T]

  sealed trait FormField[T] {
    val label: String
    val name: String
    val mapping: Mapping[T]
    val optional: Boolean
  }

  case class Text(label: String, name: String, mapping: Mapping[String], optional: Boolean = false) extends FormField[String]
  case class Textarea(label: String, name: String, mapping: Mapping[String], optional: Boolean = false) extends FormField[String]
  case class Password(label: String, name: String, mapping: Mapping[String], optional: Boolean = false) extends FormField[String]
  case class EMail(label: String, name: String, mapping: Mapping[String], optional: Boolean = false) extends FormField[String]
  case class SingleSelect[T](label: String, name: String, mapping: Mapping[T], options: List[(Any, String)], optional: Boolean = false) extends FormField[T]
  case class MultiSelect[T](label: String, name: String, mapping: Mapping[List[T]], options: List[(Any, String)], optional: Boolean = false) extends FormField[List[T]]
  case class Hidden[T](name: String, mapping: Mapping[T]) extends FormField[T] { val label = ""; val optional = false }
  case class DateSelect[T](label: String, name: String, mapping: Mapping[T], optional: Boolean = false) extends FormField[T]
  
}

/**
 * Creates the FormViewModel apply methods.
 */
private[viewmodels] object FormViewModelCodeGenerator extends App {

  def createMethod(num: Int) = {
    val types = (1 to num).map(i => s"A$i").mkString(", ")
    val parameters = (1 to num).map(i => s"a$i: FormField[A$i]").mkString(", ")
    val mappings = (1 to num).map(i => s"a$i.name -> a$i.mapping").mkString(", ")
    val fields = (1 to num).map(i => s"a$i").mkString(", ")

    s"""
    @Generated(Array[String]("Generated with FormViewModelCodeGenerator"))
    def apply[R, $types]($parameters)(apply: ($types) => R)(unapply: R => Option[($types)]) = new FormViewModel[R] {
    def asForm = Form(mapping($mappings)(apply)(unapply))
    def fields = List($fields)
  }"""
  }

  println((1 to 18).map(createMethod).mkString("\n\n"))

}