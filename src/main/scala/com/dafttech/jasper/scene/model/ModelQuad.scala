package com.dafttech.jasper.scene.model

import com.dafttech.jasper.util.Vertex

class ModelQuad(val x: Float, val y: Float, val w: Float, val h: Float) extends Model {
  val vertices = Seq[Vertex] (
    new Vertex(Seq[Float](x,     y,     0, 1, 0, 0, 0, 0, 0, 0)),
    new Vertex(Seq[Float](x + w, y,     0, 0, 1, 0, 0, 0, 0, 0)),
    new Vertex(Seq[Float](x + w, y + h, 0, 0, 0, 1, 0, 0, 0, 0)),
    new Vertex(Seq[Float](x,     y + h, 0, 1, 1, 1, 0, 0, 0, 0))
  )

  def getVertices = vertices

  val indices = Seq[Int](0, 1, 2, 0, 2, 3)

  def getIndices = indices
}