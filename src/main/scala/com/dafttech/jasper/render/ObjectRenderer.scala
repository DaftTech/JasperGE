package com.dafttech.jasper.render

import java.nio.{FloatBuffer, ByteOrder, ByteBuffer}

import com.dafttech.jasper.scene.PlacedModel
import org.lwjgl.opengl.GL11._

object ObjectRenderer {
  val matBuffer = ByteBuffer.allocateDirect(16*4).order(ByteOrder.nativeOrder()).asFloatBuffer()

  val Triangles = new ObjectRenderer {
    override def render(obj: PlacedModel): Unit = {
      glLoadIdentity()
      matBuffer.rewind()
      glLoadMatrixf(obj.transformation.get(matBuffer))
      glDrawElements(GL_TRIANGLES, obj.tesselator.getIdxCount, GL_UNSIGNED_INT, 0)
    }
  }
}

abstract class ObjectRenderer {
  def render(model: PlacedModel)
}