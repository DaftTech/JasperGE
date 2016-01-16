package com.dafttech.jasper

import java.nio.{ByteOrder, ByteBuffer, IntBuffer, FloatBuffer}

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

//A specific location in a vertex buffer (a tesselated model
class VertexBufferLocation(val index: Int, val vertexBuffer: VertexBuffer) {

}

object Vertex {
  val VTX_FLOAT_COUNT = 7
}

class Vertex(val values: Seq[Float]) {
  if(values.length != Vertex.VTX_FLOAT_COUNT) throw new IllegalArgumentException("Vertex size incorrect")
}

//All vertex Buffers of a scene
class VertexBuffer {
  val vboID = glGenBuffers()

  val vertexBuffer = ByteBuffer.allocateDirect(8192).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer()

  @volatile var changed = false

  var allocPos = 0
  def allocate(size: Int): VertexBufferLocation = {
    val r = new VertexBufferLocation(allocPos, this)
    allocPos += size * Vertex.VTX_FLOAT_COUNT

    return r
  }

  def set(location: VertexBufferLocation, vertices: Seq[Vertex]) = {
    this.synchronized {
      vertexBuffer.rewind()
      vertexBuffer.position(location.index)
      vertexBuffer.put(vertices.flatMap(_.values).toArray)
    }
  }

  def commit = {
    this.synchronized {
      vertexBuffer.rewind()
      glBindBuffer(GL_ARRAY_BUFFER, vboID)
      glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW)
    }
  }

  def activate = {
    glEnableClientState(GL_VERTEX_ARRAY)
    glEnableClientState(GL_COLOR_ARRAY)
    glBindBuffer(GL_ARRAY_BUFFER, this.vboID)

    glVertexPointer(3, GL_FLOAT, 7 * 4, 0)
    glColorPointer(4, GL_FLOAT, 7 * 4, 12)
  }
}
