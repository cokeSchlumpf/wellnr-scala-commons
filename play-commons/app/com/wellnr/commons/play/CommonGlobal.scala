package com.wellnr.commons.play

import play.api._
import play.api.db.slick._
import play.api.Play.current
import c10n.C10N
import c10n.annotations.DefaultC10NAnnotations
import java.util.Locale
import models._

trait CommonGlobal extends GlobalSettings {

  override def onStart(app: Application) {
    C10NInit.init
  }

}

trait C10NInitCapabilities {
  /*
   * Configure c10n with default local bindings.
   */
  C10N.setProxyClassloader(play.api.Play.classloader)
  C10N.configure(new DefaultC10NAnnotations())

  /*
   * Set default locale
   */
  Locale.setDefault(Locale.forLanguageTag("en"))
}

object C10NInit extends C10NInitCapabilities {
  
  def init = { }
  
}