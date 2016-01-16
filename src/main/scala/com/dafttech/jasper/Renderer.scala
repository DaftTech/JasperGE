package com.dafttech.jasper

import java.nio.IntBuffer

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

//Renders a scene into a 2D Image
abstract class SceneRenderer {
  def render(scene: Scene)
}

object ModelRenderer {
  val Triangle = new ModelRenderer {
    override def render(model: Model): Unit = {
      glEnableClientState(GL_VERTEX_ARRAY)
      glBindBuffer(GL_ARRAY_BUFFER, model.vbLoc.vertexBuffer.vboID)

      glVertexPointer(3, GL_FLOAT, 0, 0)

      glDrawArrays(GL_QUADS, 0, 4)

      println("Render")
    }
  }
}

//Renders a tesselated model into an 2D Image
abstract class ModelRenderer {
  def render(model: Model)
}