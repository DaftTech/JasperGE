package com.dafttech.jasper.gl

import java.nio.ByteBuffer

abstract class BufferObject(size: Int) {
  protected var timesBound = 0

  val bufferId = gen
  val stride = getStride
  allocate(size)


  protected def gen: Int

  protected def getStride: Int

  protected def allocate(size: Int): Unit

  def put(buffer: ByteBuffer, length: Long, offset: Long = 0): Unit

  protected def bind: Boolean = {
    timesBound += 1
    timesBound == 1
  }

  protected def unbind: Boolean = {
    timesBound -= 1
    timesBound == 0
  }

  def silentBind = {
    timesBound -= 1
    bind
  }

  def silentUnbind = {
    timesBound += 1
    unbind
  }

  def close: Unit


  def use[A](block: => A): A = {
    bind
    val r: A = block
    unbind
    r
  }
}
