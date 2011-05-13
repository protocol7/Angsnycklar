package com.protocol7.angsnycklar

import  scala.collection.mutable.HashMap

class InMemoryFileSystem extends FileSystem {

  private val keyValues = new HashMap[String, KeyValue]()

  def read(key: Key): Option[KeyValue] = {
    return keyValues.get(key.key)
  }

  def write(keyValue: KeyValue) = {
    keyValues(keyValue.key.key) = keyValue
  }
}