package com.dafttech.jasper

import scala.collection.mutable

object Tesselator {
  val Triangles = new TesselatorTriangles()
}

abstract class Tesselator {
  def getVtxCount: Int

  def getIdxCount: Int

  def tesselate(model: Object): Unit
}

class TesselatorTriangles extends Tesselator {
  def getVtxCount = 4

  def getIdxCount = 6

  def tesselate(obj: Object): Unit = {
    if (obj.vbLoc == null) throw new IllegalStateException("Can't tesselate without a scene")

    val vertices = new mutable.MutableList[Vertex]
    val indices = new mutable.MutableList[Int]

    for (m <- obj.models) {
      val vtxPos = vertices.length
      vertices ++= m.getVertices
      indices ++= m.getIndices.map(_ + vtxPos)
    }

    obj.vbLoc.vertexBuffer.setVertices(obj.vbLoc, vertices)
    obj.vbLoc.vertexBuffer.setIndices(obj.vbLoc, indices)
  }
}