package views.html.common

import play.api.data.Form
import play.api.i18n.Lang
import views.html.helper.FieldConstructor
import views.html.helpers.simpleFormConstructor

/**
 * Copyright 2014. Michael Wellner.
 *
 * Creates forms and form elements with bootstrap classes.
 *
 * @author info@michaelwellner.de
 * @since Feb 14, 2014
 */
trait BootstrapFormControls extends FormConstructor {
  /* class names for bootstrap form controls */
  private val bootstrapFormControl = "form-control form-control-square"

  /* class name for select input type */
  private val selectpicker = "selectpicker"
    
  private val datepicker = "datepicker"

  /* button class names */
  private val bootstrapButton = "btn btn-square"
  private val bootstrapPrimButton = "btn-primary"

  override def inputDate(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.inputDate(field, args: _*)(label, css.appendClass(bootstrapFormControl).appendClass(datepicker))
  }

  override def inputEmail(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.inputEmail(field, args: _*)(label, css.appendClass(bootstrapFormControl))
  }

  override def inputFile(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.inputEmail(field, args: _*)(label, css.appendClass(bootstrapFormControl))
  }

  override def inputPassword(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.inputPassword(field, args: _*)(label, css.appendClass(bootstrapFormControl))
  }

  override def inputText(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.inputText(field, args: _*)(label, css.appendClass(bootstrapFormControl))
  }

  override def select(field: String, options: Seq[(String, String)], args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.select(field, options, args: _*)(label, css.appendClass(selectpicker))
  }

  override def textarea(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.textarea(field, args: _*)(label, css.appendClass(bootstrapFormControl))
  }

  override def button(buttonType: String, label: String, args: (Symbol, Any)*)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.button(buttonType, label, args.addClass(bootstrapButton): _*)
  }

  override def submit(label: String, args: (Symbol, Any)*)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.submit(label, args.addClass(bootstrapPrimButton): _*)
  }
}

/**
 * Copyright 2014. Michael Wellner.
 *
 * Creates a simple form with bootstrap class, displaying labels as placeholder.
 *
 * @author info@michaelwellner.de
 * @since Feb 14, 2014
 */
object SimpleBootstrapForm extends FormConstructor with BootstrapFormControls {

  def template(implicit lang: Lang, form: Form[_]) = views.html.common.form.form.apply

  val fieldconstructor = FieldConstructor(views.html.common.form.simpleFieldConstructor.apply)

  val fieldtemplates = PlayFieldConstructors

  override def inputDate(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.inputDate(field, args.addPlaceholder(label): _*)(label, css)
  }

  override def inputEmail(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.inputEmail(field, args.addPlaceholder(label): _*)(label, css)
  }

  override def inputFile(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.inputEmail(field, args.addPlaceholder(label): _*)(label, css)
  }

  override def inputPassword(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.inputPassword(field, args.addPlaceholder(label): _*)(label, css)
  }

  override def inputText(field: String, args: (Symbol, Any)*)(label: Option[String] = None, css: Option[String] = None)(implicit form: Form[_], lang: play.api.i18n.Lang) = {
    super.inputText(field, args.addPlaceholder(label): _*)(label, css)
  }

}

/**
 * Copyright 2014. Michael Wellner.
 *
 * Creates a form using bootstraps columns and bootstrap inputs.
 *
 * @author info@michaelwellner.de
 * @since Feb 14, 2014
 */
object BootstrapForm extends FormConstructor with BootstrapFormControls {

  def template(implicit lang: Lang, form: Form[_]) = views.html.common.form.form.apply

  val fieldconstructor = FieldConstructor(views.html.common.form.bootstrapFieldConstructor.apply)

  val fieldtemplates = PlayFieldConstructors

}