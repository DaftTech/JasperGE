package com.dafttech.jasper.gl

import java.nio.ByteBuffer

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

class IBO(size: Int) {
  private var timesBound = 0

  val iboId = gen
  val stride = 4
  allocate(size)

  private def gen: Int = glGenBuffers()

  private def allocate(size: Int) = {
    bind
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, size, null, GL_DYNAMIC_DRAW)
    unbind
  }

  def put(buffer: ByteBuffer, offset: Long, length: Int): Unit = {
    bind
    glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, length, buffer)
    unbind
  }

  def bind: Unit = {
    timesBound += 1
    if (timesBound == 1) glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId)
  }

  def unbind: Unit = {
    timesBound -= 1
    if (timesBound == 0) glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
  }

  def render: Unit = render(size / stride)

  def render(count: Int, offset: Int = 0): Unit = {
    bind
    glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, offset)
    unbind
  }

  def close = glDeleteBuffers(iboId)
}
