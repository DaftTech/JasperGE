package com.dafttech.jasper

import org.lwjgl.opengl.GL11._

//Renders a scene into a 2D Image
abstract class SceneRenderer {
  def render(scene: Scene)
}

object ObjectRenderer {
  val Triangles = new ObjectRenderer {
    override def render(obj: Object): Unit = {
      glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0)
    }
  }
}

//Renders a tesselated model into an 2D Image
abstract class ObjectRenderer {
  def render(model: Object)
}