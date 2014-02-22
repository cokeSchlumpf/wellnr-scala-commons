package viewmodels

import models._
import twitter4j.Status
import com.wellnr.commons.DateTime._
import com.twitter.Autolink

/**
 * Copyright 2014. Michael Wellner.
 *
 * This file contains general view models for the blog application.
 *
 * @author info@michaelwellner.de
 * @since Jan 30, 2014
 */
class BlogViewModel(

  /** List of BlogEntries to be shown on page */
  _entries: List[BlogEntryViewModel],

  /** Twitter status to be shown on front page */
  val twitterStatus: TwitterStatusViewModel,

  /** List of available topics */
  val topics: List[Topic]) extends DefaultBlogEntriesViewModel(_entries)

/**
 * Companion object to create BlogViewModel more easily.
 */
object BlogViewModel {

  /**
   * Creates a new BlogViewModel
   *
   * @param entries
   * 	List of entries to be shown on front page.
   * @param twitterStatus
   * 	Twitter status to be shown on front page.
   */
  def apply(entries: Seq[BlogEntry], twitterStatus: Status, topics: List[Topic]) = new BlogViewModel(entries.toList.map(BlogEntryViewModel), new TwitterStatusViewModel(twitterStatus), topics)

}

/**
 * View model for a single twitter status.
 *
 * @param status
 * 		The actual twitter status.
 */
class TwitterStatusViewModel(status: Status) {

  private val autolink = new Autolink
  autolink.setUrlTarget("_blank")

  /**
   * Returns the creation date formatted as string.
   */
  lazy val created = status.getCreatedAt.asDateTimeString

  /**
   * Returns the status text.
   */
  lazy val text = autolink.autoLink(status.getText)

  /**
   * Returns the user of the tweet.
   */
  lazy val user = autolink.autoLink(s"@${status.getUser.getScreenName}")

}