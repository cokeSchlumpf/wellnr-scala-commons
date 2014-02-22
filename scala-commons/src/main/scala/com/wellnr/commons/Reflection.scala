package com.wellnr.commons

import scala.language.{ existentials, implicitConversions }

/**
 * This object contains utility methods for Java/ Scala reflection.
 *
 * @author Michael Wellner
 * @since 2013/10/22
 */
object Reflection {

  case class ReflectionHelper(iClass: Class[_]) {

    /**
     * Returns a list of all parent classes. If pFinalClass is set the list will end
     * with this class in the hierarchy.
     */
    def superclasses(pFinalClass: Option[Class[_]]): List[Class[_]] = {
      def superclassesRecursion(pClass: Class[_]): List[Class[_]] = {
        val break = pClass == null || (pFinalClass match {
          case Some(clazz) => clazz.equals(pClass)
          case None => false
        })

        if (break) Nil else pClass :: superclassesRecursion(pClass.getSuperclass)
      }

      superclassesRecursion(iClass)
    }

    def superclasses: List[Class[_]] = superclasses(None)

  }

  /**
   * Implicit helper method to create ReflectionHelper
   */
  implicit def any2reflectionHelper(pAny: Any): ReflectionHelper = ReflectionHelper(pAny.getClass)

}