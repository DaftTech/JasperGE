package com.dafttech.jasper.render

import java.nio.{ByteBuffer, ByteOrder}

import com.dafttech.jasper.util.Vertex
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

case class VertexBufferLocation(vertexPosition: Int, indexPosition: Int, vertexBuffer: VertexBuffer)

class VertexBuffer {
  val vboID = glGenBuffers()
  val iboID = glGenBuffers()

  val vertexBuffer = ByteBuffer.allocateDirect(65536).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer()
  val indexBuffer = ByteBuffer.allocateDirect(65536).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer()

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

      //println(s"${vertices.length} vertices")
    }
  }

  def setIndices(location: VertexBufferLocation, indices: Seq[Int]) = {
    this.synchronized {
      indexBuffer.rewind()
      indexBuffer.position(location.indexPosition)
      indexBuffer.put(indices.map(_ + location.vertexPosition).toArray)
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
    glEnableClientState(GL_NORMAL_ARRAY)
    glBindBuffer(GL_ARRAY_BUFFER, vboID)

    glVertexPointer(3, GL_FLOAT, Vertex.VTX_FLOAT_COUNT * 4, 0)
    glColorPointer(4, GL_FLOAT, Vertex.VTX_FLOAT_COUNT * 4, 12)
    glNormalPointer(GL_FLOAT, Vertex.VTX_FLOAT_COUNT * 4, 28)

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID)
  }
}
