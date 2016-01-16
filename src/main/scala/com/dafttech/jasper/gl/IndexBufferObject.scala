package com.dafttech.jasper.gl

import java.nio.ByteBuffer

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

class IndexBufferObject(size: Int) extends BufferObject(size) {
  override protected def gen: Int = glGenBuffers()

  override def put(buffer: ByteBuffer, length: Long, offset: Long = 0): Unit = this.use {
    glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, length, buffer)
  }

  override protected def getStride: Int = 4

  override protected def allocate(size: Int): Unit = this.use {
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, size, null, GL_DYNAMIC_DRAW)
  }

  override protected def bind: Boolean = {
    val value = super.bind
    if (value) glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferId)
    value
  }

  override protected def unbind: Boolean = {
    val value = super.unbind
    if (value) glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    value
  }

  def render(vbo: VertexBufferObject): Unit = render(vbo, size / stride)

  def render(vbo: VertexBufferObject, count: Int, offset: Int = 0): Unit = vbo.use {
    this.use {
      glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, offset)
    }
  }

  override def close: Unit = glDeleteBuffers(bufferId)
}
