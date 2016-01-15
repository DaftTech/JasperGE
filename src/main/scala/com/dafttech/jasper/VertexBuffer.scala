package com.dafttech.jasper

import java.nio.{IntBuffer, FloatBuffer}

import org.lwjgl.opengl.GL15._

//A specific location in a vertex buffer (a tesselated model
class VertexBufferLocation(val index: Int, val vertexBuffer: VertexBuffer) {

}

object Vertex {
  val VTX_FLOAT_COUNT = 6
}

class Vertex(val values: Seq[Float]) {
  if(values.length != Vertex.VTX_FLOAT_COUNT) throw new IllegalArgumentException("Vertex size incorrect")
}

//All vertex Buffers of a scene
class VertexBuffer {
  val vboID = glGenBuffers()

  glBindBuffer(GL_ARRAY_BUFFER, vboID)
  glBufferData(GL_ARRAY_BUFFER, 512, GL_DYNAMIC_DRAW)
  val vertexBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY).asFloatBuffer()

  @volatile var changed = false

  var allocPos = 0
  def allocate(size: Int): VertexBufferLocation = {
    val r = new VertexBufferLocation(allocPos, this)
    allocPos += size * Vertex.VTX_FLOAT_COUNT

    return r
  }

  def set(location: VertexBufferLocation, vertices: Seq[Vertex]) = {
    vertexBuffer.synchronized {
      vertexBuffer.position(location.index)
      vertexBuffer.put(vertices.flatMap(_.values).toArray)
    }
  }
}
