package com.dafttech.jasper.render

import com.dafttech.jasper.scene.{RenderingGroup, RenderingEntity, PlacedModel}
import com.dafttech.jasper.util.Vertex

import scala.collection.mutable

object Tesselator {
  val Triangles = new TesselatorTriangles()
}

abstract class Tesselator {
  def getVtxCount: Int

  def getIdxCount: Int

  def tesselate(model: RenderingEntity, vertexBuffer: VertexBuffer): Unit
  def tesselate(group: RenderingGroup, vertexBuffer: VertexBuffer): Unit
}

class TesselatorTriangles extends Tesselator {
  def getVtxCount = vtxC

  def getIdxCount = idxC

  var vtxC = 0
  var idxC = 0

  override def tesselate(obj: RenderingEntity, vertexBuffer: VertexBuffer): Unit = {
    val vertices = obj.getVertices
    val indices = obj.getIndices

    vtxC = vertices.size
    idxC = indices.size

    if(obj.vbLoc != null) {
      if(obj.vbLoc.vertexBuffer != vertexBuffer) throw new IllegalStateException("Tesselation of object in foreign buffer")
    }
    else
    {
      vertexBuffer.allocate(vertices.size, indices.size)
    }

    val mappedIndices = indices.map(_ + obj.vbLoc.vertexPosition)

    vertexBuffer.setVertices(obj.vbLoc, vertices)
    vertexBuffer.setIndices(obj.vbLoc, mappedIndices)
  }

  override def tesselate(group: RenderingGroup, vertexBuffer: VertexBuffer): Unit = {

  }
}