package views.html.common

import com.wellnr.commons.Development

import play.api.data.Field
import play.api.templates.HtmlFormat.{Appendable => HtmlContent}
import views.html.helper.FieldConstructor

/**
 * Copyright 2014. Michael Wellner.
 *
 * Field constructor methods for all field types.
 *
 * @author info@michaelwellner.de
 * @since Feb 14, 2014
 */
trait FieldConstructors {
  
  def button(implicit handler: FieldConstructor, lang: play.api.i18n.Lang): ((Symbol, Any)*) => HtmlContent

  def checkbox(implicit handler: FieldConstructor, lang: play.api.i18n.Lang): (Field, (Symbol, Any)*) => HtmlContent

  def hidden(implicit handler: FieldConstructor, lang: play.api.i18n.Lang): (Field, (Symbol, Any)*) => HtmlContent
  
  def inputCheckboxGroup(implicit handler: FieldConstructor, lang: play.api.i18n.Lang): (Field, Seq[(String, String)], (Symbol, Any)*) => HtmlContent
  
  def inputDate(implicit handler: FieldConstructor, lang: play.api.i18n.Lang): (Field, (Symbol, Any)*) => HtmlContent
  
  def inputEmail(implicit handler: FieldConstructor, lang: play.api.i18n.Lang): (Field, (Symbol, Any)*) => HtmlContent

  def inputFile(implicit handler: FieldConstructor, lang: play.api.i18n.Lang): (Field, (Symbol, Any)*) => HtmlContent

  def inputPassword(implicit handler: FieldConstructor, lang: play.api.i18n.Lang): (Field, (Symbol, Any)*) => HtmlContent

  def inputRadioGroup(implicit handler: FieldConstructor, lang: play.api.i18n.Lang): (Field, Seq[(String, String)], (Symbol, Any)*) => HtmlContent

  def inputText(implicit handler: FieldConstructor, lang: play.api.i18n.Lang): (Field, (Symbol, Any)*) => HtmlContent

  def select(implicit handler: FieldConstructor, lang: play.api.i18n.Lang): (Field, Seq[(String, String)], (Symbol, Any)*) => HtmlContent

  def textarea(implicit handler: FieldConstructor, lang: play.api.i18n.Lang): (Field, (Symbol, Any)*) => HtmlContent
  
}

/**
 * Copyright 2014. Michael Wellner.
 *
 * Specific field constructor which redirects to the play default elements. 
 *
 * @author info@michaelwellner.de
 * @since Feb 14, 2014
 */
object PlayFieldConstructors extends FieldConstructors {
  
  def button(implicit handler: FieldConstructor, lang: play.api.i18n.Lang) = views.html.common.form.button.apply

  def checkbox(implicit handler: FieldConstructor, lang: play.api.i18n.Lang) = views.html.helper.checkbox.apply

  def hidden(implicit handler: FieldConstructor, lang: play.api.i18n.Lang) = views.html.common.form.hidden.apply
  
  def inputCheckboxGroup(implicit handler: FieldConstructor, lang: play.api.i18n.Lang) = Development.NotImplemented

  def inputDate(implicit handler: FieldConstructor, lang: play.api.i18n.Lang) = views.html.helper.inputDate.apply
  
  def inputEmail(implicit handler: FieldConstructor, lang: play.api.i18n.Lang) = views.html.common.form.inputEmail.apply

  def inputFile(implicit handler: FieldConstructor, lang: play.api.i18n.Lang) = views.html.helper.inputFile.apply

  def inputPassword(implicit handler: FieldConstructor, lang: play.api.i18n.Lang) = views.html.helper.inputPassword.apply

  def inputRadioGroup(implicit handler: FieldConstructor, lang: play.api.i18n.Lang) = views.html.helper.inputRadioGroup.apply

  def inputText(implicit handler: FieldConstructor, lang: play.api.i18n.Lang) = views.html.helper.inputText.apply

  def select(implicit handler: FieldConstructor, lang: play.api.i18n.Lang) = views.html.helper.select.apply

  def textarea(implicit handler: FieldConstructor, lang: play.api.i18n.Lang) = views.html.helper.textarea.apply

}