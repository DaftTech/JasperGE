package com.dafttech.jasper

import java.nio.IntBuffer

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

//Renders a scene into a 2D Image
abstract class SceneRenderer {
  def render(scene: Scene)
}

object ModelRenderer {
  val Quads = new ModelRenderer {
    override def render(model: Model): Unit = {
      glDrawArrays(GL_QUADS, 0, 4)
    }
  }
}

//Renders a tesselated model into an 2D Image
abstract class ModelRenderer {
  def render(model: Model)
}