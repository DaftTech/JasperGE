package com.dafttech.jasper.gl

import java.nio.ByteBuffer

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

class VBO(size: Int, pointers: Seq[Pointer]) {
  private var timesBound = 0

  val vboId = gen
  val stride = Pointer.setup(pointers)
  allocate(size)

  private def gen: Int = glGenBuffers()

  private def allocate(size: Int) = {
    bind
    glBufferData(GL_ARRAY_BUFFER, size, null, GL_DYNAMIC_DRAW)
    unbind
  }

  def put(buffer: ByteBuffer, offset: Long, length: Int): Unit = {
    bind
    glBufferSubData(GL_ARRAY_BUFFER, offset, length, buffer)
    unbind
  }

  def bind: Unit = {
    timesBound += 1
    if (timesBound == 1) glBindBuffer(GL_ARRAY_BUFFER, vboId)
  }

  def unbind: Unit = {
    timesBound -= 1
    if (timesBound == 0) glBindBuffer(GL_ARRAY_BUFFER, 0)
  }

  def render: Unit = render(0, size)

  def render(offset: Int, length: Int): Unit = {
    bind
    glDrawArrays(GL_TRIANGLES, offset, length / stride)
    unbind
  }

  def close = glDeleteBuffers(vboId)
}