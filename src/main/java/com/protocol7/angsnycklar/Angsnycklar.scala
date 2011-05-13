package com.protocol7.angsnycklar

import akka.actor.Actor._
import akka.actor.Actor

object Angsnycklar {

  def main(args : Array[String]) {
    println("Angsnycklar starting up")

    System.setProperty("akka.config", "akka.conf")

    val key = Key("bar")

    for(i <- 1 until 100) {
      val fs = new InMemoryFileSystem
      val fsActor = actorOf(new FileSystemActor("node-" + i,fs))
      fsActor.start
    }


    println("#############")
    new HashRing().getNextNActors(key, 3).foreach(actor => println(actor.id))
    println("#############")


    val kv = KeyValue(key, "somevalue", System.currentTimeMillis, null)

    val keyValueActor = actorOf(new KeyValueActor())
    keyValueActor.start

    keyValueActor ! WriteKeyValue(kv)

    Thread.sleep(1000)

    Actor.registry.actorsFor("node-45").head.stop


    val future = keyValueActor !!! ReadKeyValue(key)
    println("Read: " + future.get)

    Actor.registry.shutdownAll
  }

}