package com.protocol7.angsnycklar



trait FileSystem {

  def write(keyValue: KeyValue)

  def read(key: Key): Option[KeyValue]

}