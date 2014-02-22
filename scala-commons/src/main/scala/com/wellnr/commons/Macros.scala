package com.wellnr.commons

import scala.language.experimental.macros
import scala.reflect.macros.Context

/**
 * Copyright 2014. Michael Wellner.
 *
 * An Object which contains some (useful) Scala macros.
 *
 * @author info@michaelwellner.de
 * @since Jan 19, 2014
 */
object Macros {

  /**
   * This method takes an instance of an case class and creates an instance of another case class with exactly the same parameters + an extra one.
   * I know, this method makes absolutely no sense. But it's cool.
   *
   * {{{
   * case class A(s: String, i: Int)
   * case class B(s: String, i: Int, l: List[String])
   *
   * val b: B = Macros.extend(A("A", 3), B, List("foo"))
   * }}}
   */
  def extend[C, D](entity: C, companion: Any, p: Any): D = macro extend_impl[C, D]

  /**
   * The implementation of the extend macro.
   */
  def extend_impl[C: c.WeakTypeTag, D: c.WeakTypeTag](c: Context)(
    entity: c.Expr[C], companion: c.Expr[Any], p: c.Expr[Any]): c.Expr[D] = {
    import c.universe._

    val treeEntity = reify(entity.splice).tree
    val treeCompanion = reify(companion.splice).tree

    val apply = companion.actualType.member(newTermName("apply"))

    val applyparams = apply match {
      case s: MethodSymbol if (s.paramss.nonEmpty) => s.paramss.head
      case _ => c.abort(c.enclosingPosition, "No eligible apply method!")
    }

    val newParams = (applyparams.take(2).map {
      case p => Select(treeEntity, p.name)
    }) ++ List(reify(p.splice).tree)

    c.Expr[D](Apply(
      Select(treeCompanion, apply),
      newParams))
  }

}