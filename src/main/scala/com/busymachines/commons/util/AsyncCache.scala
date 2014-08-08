package com.busymachines.commons.util

import com.busymachines.commons.CommonConfig
import spray.caching.{ExpiringLruCache, Cache => SprayCache}

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

class AsyncCacheConfig(baseName: String) extends CommonConfig(baseName) {
  val maxCapacity = int("maxCapacity") 
  val initialCapacity = int("initialCapacity") 
  val timeToLive = duration("timeToLive") 
  val timeToIdle = duration("timeToIdle") 
}

object AsyncCache {
  def expiringLru[K, V](initialCapacity: Int = 16, maxCapacity: Long = Long.MaxValue, timeToLive: Duration = Duration.Inf, timeToIdle: Duration = Duration.Inf) =
    new AsyncCache[K, V](new ExpiringLruCache[V](maxCapacity, initialCapacity, timeToLive, timeToIdle))
  def expiringLru[K, V](config: AsyncCacheConfig) =
    new AsyncCache[K, V](new ExpiringLruCache[V](config.maxCapacity, config.initialCapacity, config.timeToLive, config.timeToIdle))
}

/**
 * Asynchronous cache. Instead of values, this cache stores futures. It wraps a spray-cache, but it has a more
 * familiar interface.
 */
class AsyncCache[K, V](val cache: SprayCache[V]) {

  def apply(key: K) =
    cache.apply(key)

  def get(key: K): Option[Future[V]] =
    cache.get(key)

  def getOrElseUpdate(key: K, future: => Future[V])(implicit executor: ExecutionContext): Future[V] =
    cache(key)(future)

  def setOrUpdate(key: K, future: => Future[V])(implicit executor: ExecutionContext): Future[V] =
    (cache.remove(key) match {
      case Some(f) => f
      case None => Future.successful(Unit)
    }) flatMap { _ => cache(key)(future) }

  def remove(key: K): Option[Future[V]] =
    cache.remove(key)

  def remove(keys: Set[K])(implicit executor: ExecutionContext): Map[K, Future[V]] = {
    keys.flatMap(key => cache.remove(key).map(key -> _)).toMap
  }

  def clear() =
    cache.clear()
}