package com.dafttech.jasper

/**
  * Tesselates a model description into an VBO/VertexArray
  */
object ModelTesselator {
  val Triangle = new ModelTesselator()
}

class ModelTesselator {
  def getSize = 18

  def tesselate(model: Model): Unit = {
    if(model.vbLoc == null) throw new IllegalStateException("Can't tesselate without a scene")

    val vertices = Seq[Vertex]( new Vertex(Seq[Float](-1, -1, 0, 1, 0, 0)),
                                new Vertex(Seq[Float](1 , -1, 0, 0, 1, 0)),
                                new Vertex(Seq[Float](0,   1, 0, 0, 0, 1)))

    model.vbLoc.vertexBuffer.set(model.vbLoc, vertices)
  }
}
