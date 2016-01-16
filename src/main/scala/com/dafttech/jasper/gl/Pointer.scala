package com.dafttech.jasper.gl

import org.lwjgl.opengl.GL11._

abstract class Pointer {
  def setup(stride: Int, offset: Int): Unit

  def stride(pointers: Seq[Pointer]): Int
}

object Pointer {
  def setup(pointers: Seq[Pointer]) = {
    val stride = pointers.foldLeft(0)((last, e) => last + e.stride(pointers))

    pointers.foldLeft(0) { (last, e) =>
      e.setup(stride, last)
      last + e.stride(pointers)
    }

    stride
  }

  class VertexPointer(size: Int) extends Pointer {
    override def setup(stride: Int, offset: Int): Unit = {
      glEnableClientState(GL_VERTEX_ARRAY)
      glVertexPointer(size, GL_FLOAT, stride, offset)
    }

    override def stride(pointers: Seq[Pointer]): Int = 4 * size
  }

  class ColorPointer(size: Int) extends Pointer {
    override def setup(stride: Int, offset: Int): Unit = {
      glEnableClientState(GL_COLOR_ARRAY)
      glColorPointer(size, GL_FLOAT, stride, offset)
    }

    override def stride(pointers: Seq[Pointer]): Int = 4 * size
  }

  class TexCoordPointer(size: Int) extends Pointer {
    override def setup(stride: Int, offset: Int): Unit = {
      glEnableClientState(GL_TEXTURE_COORD_ARRAY)
      glTexCoordPointer(size, GL_FLOAT, stride, offset)
    }

    override def stride(pointers: Seq[Pointer]): Int = 4 * size
  }

  class NormalPointer extends Pointer {
    override def setup(stride: Int, offset: Int): Unit = {
      glEnableClientState(GL_NORMAL_ARRAY)
      glNormalPointer(GL_FLOAT, stride, offset)
    }

    override def stride(pointers: Seq[Pointer]): Int = pointers.foldLeft[Option[Int]](None) { (last, e) =>
      e match {
        case vertexPointer: VertexPointer => Some(vertexPointer.stride(pointers))
        case _ => last
      }
    }.getOrElse(0)
  }

}
