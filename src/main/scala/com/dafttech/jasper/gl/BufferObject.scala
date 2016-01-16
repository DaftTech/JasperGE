package com.dafttech.jasper.gl

import java.nio.ByteBuffer

abstract class BufferObject(size: Int) {
  val bufferId = gen
  val stride = getStride
  allocate(size)


  protected def gen: Int

  protected def getStride: Int

  protected def allocate(size: Int): Unit

  def put(buffer: ByteBuffer, length: Long, offset: Long = 0): Unit

  protected def bind: Unit

  protected def unbind: Unit

  def close: Unit


  def use[A](block: => A): A = {
    bind
    val r: A = block
    unbind
    r
  }
}
