package com.wellnr.commons.play.viewmodels

/**
 * Copyright 2014. Michael Wellner.
 *
 * This trait is a helper to create view models for any grouped lists (e.g. selection lists)
 *
 * @author info@michaelwellner.de
 * @since Jan 25, 2014
 */
trait GroupedListViewModelCapabilities[GROUP_BY, ENTITY] extends ViewModel {

  val groupedmap: Map[GROUP_BY, List[ENTITY]]

  /**
   * Returns a list of all groups.
   */
  lazy val groups = groupedmap.map(_._1)

  /**
   * Returns a list of entities to be displayed for a group.
   *
   * @param group
   * 	The group of entities
   * @param firstColumn
   * 	Whether it should return the first half of all entities or the second
   */
  def entities(group: GROUP_BY, firstGroup: Boolean) = {
    groupedmap.get(group) match {
      case Some(all) =>
        val splitAt = Math.round(all.length.toDouble / 2).toInt
        all.splitAt(splitAt) match {
          case (first, _) if (firstGroup) => first
          case (_, second)                => second
        }
      case None =>
        // TODO
        throw new IllegalArgumentException("Group not found.")
    }
  }
  
  /**
   * Returns a list of entities to be displayed for a group.
   *
   * @param group
   * 	The group of entities
   */
  def entities(group: GROUP_BY) = {
    groupedmap.get(group) match {
      case Some(all) => all
      case None =>
        // TODO
        throw new IllegalArgumentException("Group not found.")
    }
  }

}