package com.dafttech.jasper

import java.nio.{ByteBuffer, ByteOrder}

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

case class VertexBufferLocation(vertexPosition: Int, indexPosition: Int, vertexBuffer: VertexBuffer)

object Vertex {
  val VTX_FLOAT_COUNT = 7
}

class Vertex(val values: Seq[Float]) {
  if (values.length != Vertex.VTX_FLOAT_COUNT) throw new IllegalArgumentException("Vertex size incorrect")
}

class VertexBuffer {
  val vboID = glGenBuffers()
  val iboID = glGenBuffers()

  val vertexBuffer = ByteBuffer.allocateDirect(8192).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer()
  val indexBuffer = ByteBuffer.allocateDirect(8192).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer()

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
      vertexBuffer.rewind()
      vertexBuffer.position(location.vertexPosition)
      vertexBuffer.put(vertices.flatMap(_.values).toArray)

      println(s"${vertices.length} vertices at ${location.vertexPosition}")
    }
  }

  def setIndices(location: VertexBufferLocation, indices: Seq[Int]) = {
    this.synchronized {
      indexBuffer.rewind()
      indexBuffer.position(location.indexPosition)
      indexBuffer.put(indices.map(_ + location.vertexPosition).toArray)

      println(s"${indices.length} indices at ${location.indexPosition}")
    }
  }

  def commit = {
    this.synchronized {
      vertexBuffer.rewind()
      glBindBuffer(GL_ARRAY_BUFFER, vboID)
      glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW)

      indexBuffer.rewind()
      glBindBuffer(GL_ARRAY_BUFFER, iboID)
      glBufferData(GL_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW)
    }
  }

  def activate = {
    glEnableClientState(GL_VERTEX_ARRAY)
    glEnableClientState(GL_COLOR_ARRAY)
    glBindBuffer(GL_ARRAY_BUFFER, vboID)

    glVertexPointer(3, GL_FLOAT, Vertex.VTX_FLOAT_COUNT * 4, 0)
    glColorPointer(4, GL_FLOAT, Vertex.VTX_FLOAT_COUNT * 4, 12)

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID)
  }
}
