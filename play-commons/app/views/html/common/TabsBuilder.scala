package views.html.common

import play.api.templates.Html

// TODO: Move to play-commons.

class TabsBuilder(templates: Vector[Html]) {
  def apply(html: Html) = new TabsBuilder(templates :+ html)
//  def map(f: Seq[Html] => Html) = f(templates)
  def map(f: Seq[Html] => List[Int] => Html, l: Int *) = f(templates)(l.toList)
}

object tabs {
  def apply(html: Html) = new TabsBuilder(Vector(html))
}