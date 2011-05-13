package com.protocol7.angsnycklar

import akka.actor.Actor

class FileSystemActor(val actorId: String, val fs: FileSystem) extends Actor {

  self.id = actorId

  def receive = {
    case WriteKeyValue(keyValue) => {
       // write and reply
      write(keyValue)
      //self reply true
    }
    case ReadKeyValue(key) => {
      // read and reply
      val keyValue = fs.read(key)
      println("Reading key-value at node " + self.id)
      self reply ReadResult(keyValue)
    }
  }

  private def write(keyValue: KeyValue) = {
    println("Writing key-value at node " + self.id)
    fs.write(keyValue)
  }
}