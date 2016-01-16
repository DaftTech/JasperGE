package com.dafttech.jasper

/**
  * Tesselates a model description into an VBO/VertexArray
  */
object ModelTesselator {
  val Triangle = new ModelTesselator()
}

class ModelTesselator {
  def getSize = 4

  var n = 0

  def tesselate(model: Model): Unit = {
    if(model.vbLoc == null) throw new IllegalStateException("Can't tesselate without a scene")

    n += 1

    val vertices = Seq[Vertex]( new Vertex(Seq[Float](100, 100, 0)),
                                new Vertex(Seq[Float](300+0.01f*n, 100, 0)),
                                new Vertex(Seq[Float](300, 300, 0)),
                                new Vertex(Seq[Float](100, 300, 0)))

    //println("Ich habe hart tesseliert")

    model.vbLoc.vertexBuffer.set(model.vbLoc, vertices)
  }
}