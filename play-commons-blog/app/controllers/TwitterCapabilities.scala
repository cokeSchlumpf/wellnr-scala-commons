package controllers

import scala.collection.JavaConversions._
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import com.wellnr.commons.DateTime
import twitter4j.GeoLocation
import twitter4j.Place
import twitter4j.User

import play.api.Play

/**
 * Copyright 2014. Michael Wellner.
 *
 * This trait contains functions to access the twitter API.
 *
 * @author info@michaelwellner.de
 * @since Jan 30, 2014
 */
trait TwitterCapabilities {

  /**
   * Instance of the twitter API to access twitter data.
   */
  lazy val twitter = {
    val config = new ConfigurationBuilder
    config
      .setDebugEnabled(false)
      .setOAuthConsumerKey(Play.current.configuration.getString("wb.twitter.consumerKey").getOrElse(""))
      .setOAuthConsumerSecret(Play.current.configuration.getString("wb.twitter.consumerSecret").getOrElse(""))
      .setOAuthAccessToken(Play.current.configuration.getString("wb.twitter.accessToken").getOrElse(""))
      .setOAuthAccessTokenSecret(Play.current.configuration.getString("wb.twitter.accessTokenSecret").getOrElse(""))

    new TwitterFactory(config.build).getInstance
  }

  lazy val latestTweets = twitter.getUserTimeline.toList

  lazy val latestTweet = latestTweets.head

}