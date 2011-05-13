package com.protocol7.angsnycklar

import org.apache.commons.codec.digest.DigestUtils


case class Key(val key: String) {

  val hash = Hash.hash(key)
}


case class KeyValue(val key: Key, val value: String, val timestamp: Long, val realNode: String) {

  def this(key: Key, value: String, timestamp: Long) = this(key, value, timestamp, null)

}