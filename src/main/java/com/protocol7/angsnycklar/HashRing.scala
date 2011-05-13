package com.protocol7.angsnycklar

import akka.actor.{Actor, ActorRef}

class HashRing {

  private def fileSystemActorsOrderByHash(): Array[ActorRef] = {
    // TODO optimize
    val allActors = Actor.registry.actorsFor(classOf[FileSystemActor])

    val allActorsSorted = allActors.sortWith((a1, a2) => Hash.hash(a1.id) < Hash.hash(a2.id))

    return allActorsSorted
  }

  def getNextActor(key: Key): ActorRef = {
    val allActorsSorted = fileSystemActorsOrderByHash

    val actorOption = allActorsSorted.find(a => Hash.hash(a.id) > key.hash && a.isRunning)

    var actor = actorOption.getOrElse(allActorsSorted.head)
    println("Found file system actor: " + actor.id)

    return actor
  }

  def getNextNActors(key: Key, n: Int): Array[ActorRef] = {
    val allActorsSorted = fileSystemActorsOrderByHash

    var counter = 0
    var actors = allActorsSorted.filter(a => Hash.hash(a.id) > key.hash && a.isRunning)


    actors = Array.concat(actors, allActorsSorted.slice(0, n - actors.length))

    return actors.slice(0, n)

  }

}