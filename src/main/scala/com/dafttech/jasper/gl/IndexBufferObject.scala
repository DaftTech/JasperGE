package com.dafttech.jasper.gl

import java.nio.Buffer

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

class IndexBufferObject(indexSize: Int, vertexSize: Int, pointers: Seq[Pointer]) extends BufferObject(indexSize, 4, GL_ELEMENT_ARRAY_BUFFER) {
  val vertexBufferObject = new VertexBufferObject(vertexSize, pointers)

  def putVertices(b: Buffer, length: Int, offset: Long = 0) = vertexBufferObject.put(b, length, offset)

  override def activate = {
    vertexBufferObject.activate
    super.activate
  }

  def render: Unit = render(size / stride)

  def render(count: Int, offset: Int = 0): Unit = vertexBufferObject.use {
    activate

    this.use {
      glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, offset)
    }
  }
}
