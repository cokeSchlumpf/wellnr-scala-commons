package views.html.common

import scala.Option.option2Iterable

import com.wellnr.commons.Text.AsStringHelper

import play.api.data.{ Form => PlayForm }
import play.api.mvc.Call
import play.api.templates.Html
import views.html.helper.FieldConstructor
import play.api.templates.HtmlFormat.{ Appendable => HtmlContent }

/**
 * Copyright 2014. Michael Wellner.
 *
 * General interface for form constructors.
 *
 * @author info@michaelwellner.de
 * @since Feb 14, 2014
 */
trait FormConstructor {

  /**
   * Implicit field constructor which should be used in our templates.
   */
  val fieldconstructor: FieldConstructor

  /**
   * This class allows to create a symbol in an easy way.
   */
  implicit class SymbolString(str: String) {
    def s = Symbol(str)
  }

  /**
   * Helper class to append symbols
   */
  protected implicit class ArgumentsHelper(args: Seq[(Symbol, Any)]) {
    /**
     * Appends a symbol if value is present.
     */
    def addSymbol[T](symbol: String, value: Option[T]) = {
      args ++ value.toSeq.map(l => symbol.s -> l)
    }

    /**
     * Appends a class name to the symbols, if class is already existing it will be appended to the existing one.
     */
    def addClass(className: String) = {
      if (args.toMap.contains("class".s)) {
        (args.toMap + ("class".s -> (args.toMap.get("class".s).get.asString + " " + className))).toSeq
      } else {
        args :+ ("class".s -> className)
      }
    }

    /**
     * Appends a class name if it is defined.
     */
    def addClass(className: Option[String]): Seq[(Symbol, Any)] = {
      if (className.isDefined) {
        addClass(className.get)
      } else {
        args
      }
    }

    /**
     * Adds a "_label" to the symbols if it is defined.
     */
    def addLabel(label: Option[String]) = addSymbol("_label", label)

    /**
     * Adds a "_text" to the symbols if it is defined.
     */
    def addText(text: Option[String]) = addSymbol("_text", text)

    /**
     * Adds a placeholder attribute to symbols if defined.
     */
    def addPlaceholder(placeholder: Option[String]) = addSymbol("placeholder", placeholder)
  }

  protected implicit class StringOptionHelper(option: Option[String]) {

    def append(s: String, seperator: Option[String]) = {
      Some((option.getOrElse("") + seperator.getOrElse("") + s).trim)
    }

    def appendClass(className: String) = {
      append(className, Some(" "))
    }

  }

  implicit def string2option(s: String): Option[String] = Some(s)

  /**
   * The view of the form
   */
  def template(implicit lang: play.api.i18n.Lang, form: PlayForm[_]): (Call, (Symbol, String)*) => (=> Html) => play.api.templates.HtmlFormat.Appendable

  /**
   * The view for all input elements.
   */
  val fieldtemplates: FieldConstructors

  /**
   * Creates a HTML Form.
   *
   * @param action
   * 	The controller method which should be called when submitting the form.
   * @param args
   *  	Additional attributes and values of the tag.
   * @param body
   * 	The HTML content of the form.
   * @param form
   * 	The {{{play.api.data.Form}}} on which the HTML form is based.
   */
  def form(action: play.api.mvc.Call, args: (Symbol, String)*)(body: => Html)(implicit form: play.api.data.Form[_], lang: play.api.i18n.Lang) = {
    template(lang, form)(action, args)(body)
  }

  /**
   * Creates a HTML5 button.
   *
   * @param buttonType
   * 	The type of the button.
   * @param label
   *  	The label of the button.
   */
  def button(buttonType: String, label: String, args: (Symbol, Any)*)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = {
    fieldtemplates.button(fieldconstructor, lang)(args.addSymbol("type", buttonType).addLabel(label): _*)
  }

  /**
   * Creates a HTML input element of type checkbox.
   *
   * @param field
   * 	The name of the field in the related play.api.data.Form object.
   * @param label
   * 	The label of the field.
   *  @param value
   *  	The value this checkbox represents.
   * @param args
   * 	Additional attributes and values of the HTML Element.
   */
  def checkbox(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = {
    fieldtemplates.checkbox(fieldconstructor, lang)(form(field), args.addLabel(label).addText(label).addClass(css): _*)
  }

  /**
   * Creates a hidden HTML input element.
   *
   * @param field
   * 	The name of the field in the related play.api.data.Form object.
   * @param args
   * 	Additional attributes and values of the HTML Element.
   */
  def hidden(field: String, args: (Symbol, Any)*)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = {
    fieldtemplates.hidden(fieldconstructor, lang)(form(field), args: _*)
  }

  def inputCheckboxGroup(field: String, options: Seq[(String, String)], args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = {
    fieldtemplates.inputCheckboxGroup(fieldconstructor, lang)(form(field), options, args.addLabel(label).addClass(css): _*)
  }

  /**
   * Creates a HTML input element of type date.
   *
   * @param field
   * 	The name of the field in the related play.api.data.Form object.
   * @param args
   * 	Additional attributes and values of the HTML Element.
   *
   * @param label
   * 	The label of the field.
   * @param css
   *  	Additional css class of the field.
   */
  def inputDate(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = {
    fieldtemplates.inputDate(fieldconstructor, lang)(form(field), args.addLabel(label).addClass(css): _*)
  }

  /**
   * Creates a HTML input element of type date.
   *
   * @param field
   * 	The name of the field in the related play.api.data.Form object.
   * @param args
   * 	Additional attributes and values of the HTML Element.
   *
   * @param label
   * 	The label of the field.
   * @param css
   *  	Additional css class of the field.
   */
  def inputEmail(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = {
    fieldtemplates.inputEmail(fieldconstructor, lang)(form(field), args.addLabel(label).addClass(css): _*)
  }

  /**
   * Creates a HTML input element of type file.
   *
   * @param field
   * 	The name of the field in the related play.api.data.Form object.
   * @param args
   * 	Additional attributes and values of the HTML Element.
   *
   * @param label
   * 	The label of the field.
   * @param css
   *  	Additional css class of the field.
   */
  def inputFile(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = {
    fieldtemplates.inputFile(fieldconstructor, lang)(form(field), args.addLabel(label).addClass(css): _*)
  }

  /**
   * Creates a HTML input element of type password.
   *
   * @param field
   * 	The name of the field in the related play.api.data.Form object.
   * @param args
   * 	Additional attributes and values of the HTML Element.
   *
   * @param label
   * 	The label of the field.
   * @param css
   *  	Additional css class of the field.
   */
  def inputPassword(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = {
    fieldtemplates.inputPassword(fieldconstructor, lang)(form(field), args.addLabel(label).addClass(css): _*)
  }

  def inputRadioGroup(field: String, options: Seq[(String, String)], args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = {
    fieldtemplates.inputRadioGroup(fieldconstructor, lang)(form(field), options, args.addLabel(label).addClass(css): _*)
  }

  /**
   * Creates a HTML input element of type text.
   *
   * @param field
   * 	The name of the field in the related play.api.data.Form object.
   * @param args
   * 	Additional attributes and values of the HTML Element.
   *
   * @param label
   * 	The label of the field.
   * @param css
   *  	Additional css class of the field.
   */
  def inputText(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = {
    fieldtemplates.inputText(fieldconstructor, lang)(form(field), args.addLabel(label).addClass(css): _*)
  }

  def select(field: String, options: Seq[(String, String)], args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = {
    fieldtemplates.select(fieldconstructor, lang)(form(field), options, args.addLabel(label).addClass(css): _*)
  }
  
  /**
   * Creates an HTML5 submit button.
   * 
   * @param label
   * 	The label of the button.
   */
  def submit(label: String, args: (Symbol, Any)*)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = this.button("submit", label, args: _*)

  /**
   * Creates a HTML textarea element.
   *
   * @param field
   * 	The name of the field in the related play.api.data.Form object.
   * @param args
   * 	Additional attributes and values of the HTML Element.
   *
   * @param label
   * 	The label of the field.
   * @param css
   *  	Additional css class of the field.
   */
  def textarea(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: PlayForm[_], lang: play.api.i18n.Lang) = {
    fieldtemplates.textarea(fieldconstructor, lang)(form(field), args.addLabel(label).addClass(css): _*)
  }

}

/**
 * Companion object for Form constructor.
 */
object FormConstructor {

  /**
   * Replaces a message with a translated version if available.
   *
   * @param message
   * 	The message which should be translated.
   */
  def i18n(message: String)(lang: play.api.i18n.Lang) = {
    play.api.i18n.Messages(message)
  }

}