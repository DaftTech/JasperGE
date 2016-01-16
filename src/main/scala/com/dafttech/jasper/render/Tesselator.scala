package com.dafttech.jasper.render

import com.dafttech.jasper.scene.PlacedModel
import com.dafttech.jasper.util.Vertex

import scala.collection.mutable

object Tesselator {
  val Triangles = new TesselatorTriangles()
}

abstract class Tesselator {
  def getVtxCount: Int

  def getIdxCount: Int

  def tesselate(model: PlacedModel): Unit
}

class TesselatorTriangles extends Tesselator {
  def getVtxCount = vtxC

  def getIdxCount = idxC

  var vtxC = 0
  var idxC = 0

  def tesselate(obj: PlacedModel): Unit = {
    if (obj.vbLoc == null) throw new IllegalStateException("Can't tesselate without a scene")

    val vertices = obj.model.getVertices
    val indices = obj.model.getIndices

    vtxC = vertices.length
    idxC = indices.length

    obj.vbLoc.vertexBuffer.setVertices(obj.vbLoc, vertices)
    obj.vbLoc.vertexBuffer.setIndices(obj.vbLoc, indices)
  }
}