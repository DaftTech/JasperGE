package com.dafttech.jasper.render

import java.nio.{ByteBuffer, ByteOrder}

import com.dafttech.jasper.gl.{IndexBufferObject, Pointer}
import com.dafttech.jasper.util.Vertex

case class VertexBufferLocation(vertexPosition: Int, indexPosition: Int, vertexBuffer: VertexBuffer)

class VertexBuffer {
  val ibo = new IndexBufferObject(65536 * 128, 65536 * 128, Seq(
    Pointer.Vertex(3),
    Pointer.Color(4),
    Pointer.Normal))

  val vertexBuffer = ByteBuffer.allocateDirect(65536 * 128).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer()
  val indexBuffer = ByteBuffer.allocateDirect(65536 * 128).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer()

  @volatile var changed = false

  var vertexAllocPos = 0
  var indexAllocPos = 0

  def allocate(vtxCount: Int, idxCount: Int): VertexBufferLocation = {
    val r = new VertexBufferLocation(vertexAllocPos, indexAllocPos, this)
    vertexAllocPos += vtxCount * Vertex.VTX_FLOAT_COUNT
    indexAllocPos += idxCount

    return r
  }

  def setVertices(location: VertexBufferLocation, vertices: Seq[Vertex]) = {
    this.synchronized {
      vertexBuffer.position(location.vertexPosition)
      vertexBuffer.put(vertices.flatMap(_.values).toArray)
      vertexBuffer.position(0)

      changed = true
    }
  }

  def setIndices(location: VertexBufferLocation, indices: Seq[Int]) = {
    this.synchronized {
      indexBuffer.position(location.indexPosition)
      indexBuffer.put(indices.map(_ + location.vertexPosition).toArray)
      indexBuffer.position(0)

      changed = true
    }
  }

  def commit = {
    if (changed) {
      this.synchronized {
        ibo.putVertices(vertexBuffer, vertexBuffer.remaining())
        ibo.put(indexBuffer, indexBuffer.remaining())
      }
    }
    changed = false
  }

  def activate = {
    ibo.activate
  }
}
