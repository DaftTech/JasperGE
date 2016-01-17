package com.dafttech.jasper.gl

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

class IndexBufferObject(size: Int) extends BufferObject(size, 4, GL_ELEMENT_ARRAY_BUFFER) {
  def activate(vbo: VertexBufferObject) = {
    vbo.activate
    bind
  }

  def render(vbo: VertexBufferObject): Unit = render(vbo, size / stride)

  def render(vbo: VertexBufferObject, count: Int, offset: Int = 0): Unit = vbo.use {
    activate(vbo)

    this.use {
      glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, offset)
    }
  }
}
