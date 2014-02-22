package views.html.common

import play.api.data.Form
import play.api.i18n.Lang
import views.html.helper.FieldConstructor
import views.html.helpers.simpleFormConstructor

/**
 * Copyright 2014. Michael Wellner.
 *
 * Simple Form Constructor which uses the play default templates without modifications.
 *
 * @author info@michaelwellner.de
 * @since Feb 14, 2014
 */
object SimpleForm extends FormConstructor {

  def template(implicit lang: Lang, form: Form[_]) = views.html.helper.form.apply

  val fieldconstructor = FieldConstructor(views.html.helper.defaultFieldConstructor.apply)
  
  val fieldtemplates = PlayFieldConstructors 

}