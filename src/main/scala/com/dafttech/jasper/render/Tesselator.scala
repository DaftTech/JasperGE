package com.dafttech.jasper.render

import com.dafttech.jasper.scene.{Entity, RenderingGroup, RenderingEntity, PlacedModel}
import com.dafttech.jasper.util.Vertex

import scala.collection.mutable

object Tesselator {
  val Triangles = new TesselatorTriangles
  val RenderingGroup = new TesselatorRenderingGroup
}

abstract class Tesselator[T <: Entity] {
  def getVtxCount(obj: T): Int
  def getIdxCount(obj: T): Int

  def tesselate(obj: T, vertexBuffer: VertexBuffer): Unit
}

class TesselatorRenderingGroup extends Tesselator[RenderingGroup] {
  override def getVtxCount(obj: RenderingGroup) = obj.childs.map(_.getVertices.size).sum
  override def getIdxCount(obj: RenderingGroup) = obj.childs.map(_.getIndices.size).sum

  override def tesselate(obj: RenderingGroup, vertexBuffer: VertexBuffer): Unit = {
    obj.childs.foreach { e =>
      e.getTesselator.tesselate(e, vertexBuffer)
    }
  }
}

class TesselatorTriangles extends Tesselator[RenderingEntity] {
  def getVtxCount(obj: RenderingEntity) = obj.getVertices.size
  def getIdxCount(obj: RenderingEntity) = obj.getIndices.size

  override def tesselate(obj: RenderingEntity, vertexBuffer: VertexBuffer): Unit = {
    val vertices = obj.getVertices
    val indices = obj.getIndices

    if (obj.vbLoc != null) {
      if (obj.vbLoc.vertexBuffer != vertexBuffer) throw new IllegalStateException("Tesselation of object in foreign buffer")
    }
    else {
      obj.vbLoc = vertexBuffer.allocate(vertices.size, indices.size)
    }

    vertexBuffer.setVertices(obj.vbLoc, vertices)
    vertexBuffer.setIndices(obj.vbLoc, indices)
  }
}