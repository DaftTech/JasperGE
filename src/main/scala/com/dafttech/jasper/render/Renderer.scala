package com.dafttech.jasper.render

import com.dafttech.jasper.model
import com.dafttech.jasper.model.{RObject, Scene}
import org.lwjgl.opengl.GL11._

abstract class SceneRenderer {
  def render(scene: Scene)
}

object ObjectRenderer {
  val Triangles = new ObjectRenderer {
    override def render(obj: model.RObject): Unit = {
      glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0)
    }
  }
}

abstract class ObjectRenderer {
  def render(model: RObject)
}