package com.wellnr.commons

import scala.language.implicitConversions

/**
 * Copyright 2013. Michael Wellner.
 *
 * This object contains simple helper methods for text handling.
 *
 * @author Michael Wellner
 * @since 2013/10/18
 */
object Text {

  /**
   * Returns toString of pObject if it's not null. Otherwise "null".
   */
  implicit class AsStringHelper(iObject: Any) {
    def asString: String = {
      if (iObject == null) "null" else iObject.toString
    }
  }

  /**
   * Creates a printable list of the given arguments surrounded with braces.
   */
  implicit class ToArgListHelper(iList: Seq[Any]) {
    def asArgumentList: String = {
      iList.map(_.asString).mkString("(", ", ", ")")
    }
  }

  /**
   * Case class which wraps a string to offer some useful functions.
   */
  implicit class StringHelper(s: String) {

    /**
     * Creates an MD5 Hash of a string.
     */
    def md5hash = {
      val m = java.security.MessageDigest.getInstance("MD5")
      val b = s.getBytes("UTF-8")
      m.update(b, 0, b.length)
      new java.math.BigInteger(1, m.digest()).toString(16)
    }

    /**
     * Returns the string, or a default if null.
     *
     * @param default
     */
    def orDefault(default: String) = if (s == null) default else s

    /**
     * Turns a string of format "foo bar" into camel case "fooBar"
     *
     * @return
     * 		The CamelCased string
     */
    def camelify: String = {
      s.split(" ").map(word => word.toList match {
        case firstLetter :: rest => (firstLetter.toUpper :: rest).mkString("")
        case Nil => ""
      }).mkString("").replaceAll("[^\\p{L}\\p{Nd}]", "").toList match {
        case firstLetter :: rest => (firstLetter.toLower :: rest).mkString("")
        case Nil => ""
      }
    }

    /**
     * Makes the first letter a upper case, all others lower case.
     */
    def capitalizeFirst = { s(0).toString.toUpperCase + s.substring(1, s.length).toLowerCase }

    /**
     * Capitalizes every first letter of each word
     */
    def capitalizeFirstLetters = s.split(" ").toList.map(_.capitalizeFirst).mkString(" ")

    /**
     * Creates the plural form of an English noun.
     */
    def pluralize = {
      val endsWithY = "([a-zA-Z0-9_\\s]+)[y|Y]".r
      val endsWithS = "([a-zA-Z0-9_\\s]+)[s|S]".r

      (s match {
        case endsWithY(s) => s"${s}ies"
        case endsWithS(s) => s"${s}s"
        case _ => s"${s}s"
      }).toLowerCase
    }

    /**
     * Returns an array of paragraphs contained in the string.
     *
     * Paragraphs are separated by two line breaks.
     */
    def paragraphs = s.split("\\n[\\s]*\\n")

  }

}