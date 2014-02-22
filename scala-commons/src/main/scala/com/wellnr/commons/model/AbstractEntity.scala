package com.wellnr.commons.model

import scala.language.experimental.macros

case class ID[E <: AbstractEntity[E]](id: Long)

/**
 * Copyright 2013. Michael Wellner.
 *
 * This class defines an abstract entity within your business model.
 *
 * @author Michael Wellner
 * @since 2013/10/17
 */
trait AbstractEntity[E <: AbstractEntity[E]] {

  /**
   * Defines the id which can be used to identify an instance of the entity (primary key, technical key).
   */
  val id: Option[ID[E]]

  def withId(id: ID[E]): E

  /**
   * This method returns the id as Long value. This method should only be called if id is not set to scala.None.
   */
  def ID = id.get.id

}

/**
 * This object offers a method to create a new copy of the entity with an new id.
 * Basically this would work on any object which has an member id.
 *
 *  Also see: http://stackoverflow.com/questions/13446528/howto-model-named-parameters-in-method-invocations-with-scala-macros/13447439#13447439
 */
object EntityWithId {
  import scala.reflect.macros.Context

  def withId[T, I](entity: T, id: I): T = macro withIdImpl[T, I]

  def withIdImpl[T: c.WeakTypeTag, I: c.WeakTypeTag](c: Context)(
    entity: c.Expr[T], id: c.Expr[I]): c.Expr[T] = {
    import c.universe._

    val tree = reify(entity.splice).tree
    val copy = entity.actualType.member(newTermName("copy"))

    val params = copy match {
      case s: MethodSymbol if (s.paramss.nonEmpty) => s.paramss.head
      case _ => c.abort(c.enclosingPosition, "No eligible copy method!")
    }

    c.Expr[T](Apply(
      Select(tree, copy),
      params.map {
        case p if p.name.decoded == "id" => reify(id.splice).tree
        case p => Select(tree, p.name)
      }))
  }
}