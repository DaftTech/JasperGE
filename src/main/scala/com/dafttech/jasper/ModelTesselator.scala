package com.dafttech.jasper

/**
  * Tesselates a model description into an VBO/VertexArray
  */
object ModelTesselator {
  val Quads = new ModelTesselatorQuads()
}

abstract class ModelTesselator {
  def getSize: Int
  def tesselate(model: Model): Unit
}

class ModelTesselatorQuads extends ModelTesselator {
  def getSize = 4

  def tesselate(model: Model): Unit = {
    if(model.vbLoc == null) throw new IllegalStateException("Can't tesselate without a scene")

    val vertices = Seq[Vertex]( new Vertex(Seq[Float](100, 100, 0, 1, 0, 0, 0)),
                                new Vertex(Seq[Float](300, 100, 0, 0, 1, 0, 0)),
                                new Vertex(Seq[Float](300, 300, 0, 0, 0, 1, 0)),
                                new Vertex(Seq[Float](100, 300, 0, 1, 1, 1, 0)))

    //println("Ich habe hart tesseliert")

    model.vbLoc.vertexBuffer.set(model.vbLoc, vertices)
  }
}