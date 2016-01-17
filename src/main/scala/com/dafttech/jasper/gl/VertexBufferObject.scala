package com.dafttech.jasper.gl

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

class VertexBufferObject(size: Int, pointers: Seq[Pointer]) extends BufferObject(size, Pointer.stride(pointers), GL_ARRAY_BUFFER) {
  override def activate: Unit = {
    super.activate
    Pointer.setup(pointers, stride)
  }

  def render: Unit = render(size / stride)

  def render(count: Int, offset: Int = 0): Unit = this.use {
    activate
    glDrawArrays(GL_TRIANGLES, offset, count)
  }
}