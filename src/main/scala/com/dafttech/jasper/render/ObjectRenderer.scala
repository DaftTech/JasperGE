package com.dafttech.jasper.render

import com.dafttech.jasper.scene.RObject
import org.lwjgl.opengl.GL11._

object ObjectRenderer {
  val Triangles = new ObjectRenderer {
    override def render(obj: RObject): Unit = {
      glDrawElements(GL_TRIANGLES, obj.tesselator.getIdxCount, GL_UNSIGNED_INT, 0)
    }
  }
}

abstract class ObjectRenderer {
  def render(model: RObject)
}