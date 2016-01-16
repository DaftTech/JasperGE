package com.dafttech.jasper.model

import com.dafttech.jasper.render.Vertex

/**
  * Created by Fabian on 16.01.2016.
  */
abstract class Model {
  def getVertices: Seq[Vertex]

  def getIndices: Seq[Int]
}

class ModelQuad(val x: Float, val y: Float, val w: Float, val h: Float) extends Model {
  val vertices = Seq[Vertex](new Vertex(Seq[Float](x, y, 0, 1, 0, 0, 0)),
    new Vertex(Seq[Float](x + w, y, 0, 0, 1, 0, 0)),
    new Vertex(Seq[Float](x + w, y + h, 0, 0, 0, 1, 0)),
    new Vertex(Seq[Float](x, y + h, 0, 1, 1, 1, 0)))

  def getVertices = vertices

  val indices = Seq[Int](0, 1, 2, 0, 2, 3)

  def getIndices = indices
}