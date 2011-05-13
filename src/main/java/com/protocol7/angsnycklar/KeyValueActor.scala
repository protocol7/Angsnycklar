package com.protocol7.angsnycklar

import akka.actor.{ActorRef, Actor}


/**
 * Gets a write request, find the responsible FileSystemActor and writes to it
 */
class KeyValueActor extends Actor {

  private val ring = new HashRing

  def receive = {
    case WriteKeyValue(keyValue) => {
      ring.getNextNActors(keyValue.key, 3).foreach(actor => actor ! WriteKeyValue(keyValue))
    }
    case ReadKeyValue(key) => {
      // TODO butt-ugly code

      val results = ring.getNextNActors(key, 3).map(_ !! ReadKeyValue(key))

      // quorum, pick last updated
      var bestKv: KeyValue = null
      var lastTimestamp = 0L
      for(result <- results) {
        val readResult = result.asInstanceOf[Option[ReadResult]]
        if(readResult.isDefined) {
          val kv = readResult.head.keyValue
          if(kv.isDefined && kv.head.timestamp > lastTimestamp) {
            lastTimestamp = kv.head.timestamp
            bestKv = kv.head
          }
        }
      }

      self reply bestKv
    }
  }
}