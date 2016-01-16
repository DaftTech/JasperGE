package com.dafttech.jasper.gl

import java.nio.ByteBuffer

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

class VertexBufferObject(size: Int, pointers: Seq[Pointer]) extends BufferObject(size) {
  override protected def gen: Int = glGenBuffers()

  override def put(buffer: ByteBuffer, length: Long, offset: Long = 0): Unit = this.use {
    glBufferSubData(GL_ARRAY_BUFFER, offset, length, buffer)
  }

  override protected def getStride: Int = Pointer.stride(pointers)

  override protected def allocate(size: Int): Unit = this.use {
    glBufferData(GL_ARRAY_BUFFER, size, null, GL_DYNAMIC_DRAW)
  }

  override protected def bind: Unit = {
    glBindBuffer(GL_ARRAY_BUFFER, bufferId)
  }

  override protected def unbind: Unit = {
    glBindBuffer(GL_ARRAY_BUFFER, 0)
  }

  def activate: Unit = {
    bind
    Pointer.setup(pointers, stride)
  }

  def render: Unit = render(size / stride)

  def render(count: Int, offset: Int = 0): Unit = this.use {
    activate
    glDrawArrays(GL_TRIANGLES, offset, count)
  }

  override def close: Unit = glDeleteBuffers(bufferId)
}