package com.dafttech.jasper

import java.nio.IntBuffer

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

//Renders a scene into a 2D Image
abstract class SceneRenderer {
  def render(scene: Scene)
}

object ObjectRenderer {
  val Triangles = new ObjectRenderer {
    override def render(obj: Object): Unit = {
      glDrawArrays(GL_QUADS, 0, 1)
      //glDrawElements(GL_TRIANGLES, 2, GL_INT, 0)
    }
  }
}

//Renders a tesselated model into an 2D Image
abstract class ObjectRenderer {
  def render(model: Object)
}