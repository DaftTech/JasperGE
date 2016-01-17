package com.dafttech.jasper.gl

import java.nio._

import org.lwjgl.opengl.GL15._

abstract class BufferObject(val size: Int, val stride: Int, buffer: Int) {
  val bufferId = gen
  allocate(size)


  protected def gen: Int = glGenBuffers()

  protected def allocate(size: Int): Unit = this.use {
    glBufferData(buffer, size, null, GL_DYNAMIC_DRAW)
  }

  def put(b: Buffer, length: Int, offset: Long = 0): Unit = this.use {
    val oldLimit = b.limit()
    b.limit(length)

    b match {
      case byteBuffer: ByteBuffer => glBufferSubData(buffer, offset, byteBuffer)
      case doubleBuffer: DoubleBuffer => glBufferSubData(buffer, offset, doubleBuffer)
      case floatBuffer: FloatBuffer => glBufferSubData(buffer, offset, floatBuffer)
      case intBuffer: IntBuffer => glBufferSubData(buffer, offset, intBuffer)
      case shortBuffer: ShortBuffer => glBufferSubData(buffer, offset, shortBuffer)
      case _ => throw new IllegalArgumentException("Wrong Buffer type!")
    }

    b.limit(oldLimit)
  }

  protected def bind: Unit = glBindBuffer(buffer, bufferId)

  protected def unbind: Unit = glBindBuffer(buffer, 0)

  def close: Unit = glDeleteBuffers(bufferId)

  def activate: Unit = {
    bind
  }


  def use[A](block: => A): A = {
    bind
    val r: A = block
    unbind
    r
  }
}
