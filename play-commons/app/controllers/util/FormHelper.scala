package controllers.util

import play.api.data.{ Form => PlayForm }
import views.html.helper.FieldConstructor
import views.html.helpers
import views.html.helpers.simpleFormConstructor
import play.api.templates.Html
import com.wellnr.commons.Text._
import com.wellnr.commons.logging.LoggingCapabilities
import com.wellnr.commons.play.viewmodels.EditViewModel._
import com.wellnr.commons.Development

/**
 * Copyright 2013. Michael Wellner.
 *
 * This helper object provides several methods for building HTML templates.
 *
 * @author Michael Wellner
 * @since Dec 1, 2013
 */
// TODO: REMOVE
@deprecated("Use views.html.commons.FormConstructur", "1.0")
object FormHelper extends LoggingCapabilities {

  /**
   * This map defines the i18n keys for play default error messages.
   */
  private val i18nMappings = Map(
    "error.email" -> "E-Mail!")

  /**
   * Implicit field constructor which should be used in our templates.
   */
  implicit val defaultFieldConstructor = FieldConstructor(views.html.helpers.simpleFormConstructor.f)

  class SymbolString(str: String) {
    def s = Symbol(str)
  }

  implicit def str2symstr(str: String) = new SymbolString(str)

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
  def form(action: play.api.mvc.Call, args: (Symbol, String)*)(body: => Html)(implicit form: play.api.data.Form[_]) = {
    helpers.htmlform(action, args: _*)(body)(form)
  }

  /**
   * Creates a HTML input element of type email.
   *
   * @param field
   * 	The name of the field in the related play.api.data.Form object.
   * @param label
   * 	The label of the field.
   * @param args
   * 	Additional attributes and values of the HTML Element.
   */
  def inputEMail(field: String, label: String, args: (Symbol, Any)*)(implicit form: PlayForm[_], handler: FieldConstructor, lang: play.api.i18n.Lang) = {
    helpers.inputText.f("email", label, form(field), args.toArray)(handler, lang)
  }

  /**
   * Creates a HTML input element of type text.
   *
   * @param field
   * 	The name of the field in the related play.api.data.Form object.
   * @param label
   * 	The label of the field.
   * @param args
   * 	Additional attributes and values of the HTML Element.
   */
  def inputText(field: String, label: String, args: (Symbol, Any)*)(implicit form: PlayForm[_], handler: FieldConstructor, lang: play.api.i18n.Lang) = {
    val t = List(views.html.viewmodelsviewer.tableheader)
    helpers.inputText.f("text", label, form(field), args.toArray)(handler, lang)
  }

  /**
   * Creates a HTML input element of type password.
   *
   * @param field
   * 	The name of the field in the related play.api.data.Form object.
   * @param label
   * 	The label of the field.
   * @param args
   * 	Additional attributes and values of the HTML Element.
   */
  def inputPassword(field: String, label: String, args: (Symbol, Any)*)(implicit form: PlayForm[_], handler: FieldConstructor, lang: play.api.i18n.Lang) = {
    helpers.inputText.f("password", label, form(field), args.toArray)(handler, lang)
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
  def checkbox(field: String, label: String, value: Any, args: (Symbol, Any)*)(implicit form: PlayForm[_], handler: FieldConstructor, lang: play.api.i18n.Lang) = {
    val checked = form.data.filter(_._1.matches(s"$field\\[[0-9]+]")).map(_._2).toList.contains(value.asString)
    helpers.checkbox(label, value.asString, s"${field}_${value.asString.camelify}", checked, form(field + "[]"), args: _*)(handler, lang)
  }

  /**
   * Creates a HTML checkbox for yes/ no values.
   */
  def singlecheckbox(field: String, label: String, args: (Symbol, Any)*)(implicit form: PlayForm[_], handler: FieldConstructor, lang: play.api.i18n.Lang) = {
    val checked = form.data.find(_._1.equals(field)) match {
      case Some((_, value)) => value.equals("true")
      case None => false
    }

    helpers.checkbox(label, "true", field, checked, form(field), args: _*)(handler, lang)
  }

  private def prepareSelectValues(field: String, values: List[(Any, String)], multiple: Boolean)(implicit form: PlayForm[_]) = {    
    multiple match {
      case true =>
        values.map {
          case (key, label) =>
            val selected = form.data.filter(_._1.matches(s"$field\\[[0-9]+]")).map(_._2).toList.contains(key.asString)
            (key.asString, label, selected)
        }
      case false =>
        values.map {
          case (key, label) =>
            val selected = form.data.find(_._1.equals(field)) match {
              case Some((_, value)) => value.equals(key.asString)
              case None => false
            }
            (key.asString, label, selected)
        }
    }
  }
  
  def select(field: String, label: String, values: List[(Any, String)], multiple: Boolean, args: (Symbol, Any)*)(implicit form: PlayForm[_], handler: FieldConstructor, lang: play.api.i18n.Lang) = {
    val valueslist = prepareSelectValues(field, values, multiple)
    helpers.selection(label, field, valueslist, multiple, form(field), args: _*)(handler, lang)
  }
  
  def select(field: String, label: String, values: Map[String, List[(Any, String)]], multiple: Boolean, args: (Symbol, Any)*)(implicit form: PlayForm[_], handler: FieldConstructor, lang: play.api.i18n.Lang) = {
    val valuelist = values.map { case (category, values) => (category, prepareSelectValues(field, values, multiple)) }
    helpers.selectioncategories(label, field, valuelist, multiple, form(field), args: _*)(handler, lang)
  }

  /**
   * Craetes a HTML radio button.
   *
   * @param field
   * 	The name of the field in the related play.api.data.Form object.
   * @param label
   * 	The label of the field.
   *  @param value
   *  	The value this radio button represents.
   * @param args
   * 	Additional attributes and values of the HTML Element.
   */
  def radiobutton(field: String, label: String, value: Any, args: (Symbol, Any)*)(implicit form: PlayForm[_], handler: FieldConstructor, lang: play.api.i18n.Lang) = {
    val checked = (form.data.find { case (name, fieldvalue) => name.equals(field) && fieldvalue.equals(value.asString) }).isDefined
    helpers.radiobox(label, value.asString, s"${field}_${value.asString.camelify}", checked, form(field), args: _*)(handler, lang)
  }

  def hidden(field: String, args: (Symbol, Any)*)(implicit form: PlayForm[_], handler: FieldConstructor, lang: play.api.i18n.Lang) = {
    helpers.hidden.f(form(field), args.toArray)(handler, lang)
  }

  /**
   * Creates a HTML submit button.
   *
   * @param label
   * 	The label of the button.
   *  @param classes
   * 	The CSS classes to be added to the button.
   */
  def submit(label: String = "Submit", classes: String = "", name: String = "", buttonValue: String = "") = helpers.button("submit", label, (classes + " btn-primary").trim, name, buttonValue)

  /**
   * Create a HTML reset button.
   *
   * @param label
   * 	The label of the button.
   * @param classes
   * 	The CSS classes to be added to the button.
   */
  def reset(label: String = "Reset", classes: String = "", name: String = "", buttonValue: String = "") = helpers.button("reset", label, classes, name, buttonValue)
  
  def input(field: String, label: String, classes: String, args: (Symbol, Any)*)(implicit form: PlayForm[_], handler: FieldConstructor, lang: play.api.i18n.Lang) = {
    form(field).format
  }
  
//  def genericinput(field: FormField)(implicit form: PlayForm[_], handler: FieldConstructor, lang: play.api.i18n.Lang) = {
////    field match {
////      case Text(label, name) =>
////        inputText(name, label)(form, handler, lang)
////      case EMail(label, name) =>
////        inputEMail(name, label)(form, handler, lang)
////      case _ => Development.TODO
////    }
//  }

  /**
   * Replaces a message with a translated version if available.
   *
   * @param message
   * 	The message which should be translated.
   */
  def i18n(message: String) = i18nMappings.getOrElse(message, message)

}