package com.dafttech.jasper

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

//Renders a scene into a 2D Image
abstract class SceneRenderer {
  def render(scene: Scene)
}

object ModelRenderer {
  val Triangle = new ModelRenderer {
    override def render(model: Model): Unit = {
      glBindBuffer(GL_ARRAY_BUFFER, model.vbLoc.vertexBuffer.vboID)
      glEnableClientState(GL_VERTEX_ARRAY)
      glEnableClientState(GL_COLOR_ARRAY)

      glVertexPointer(3, GL_FLOAT, 24, 0)
      glColorPointer(3, GL_FLOAT, 24, 12)

      println(model.vbLoc.index)
      glDrawArrays(GL_TRIANGLES, model.vbLoc.index, 1)

      println("Render")
    }
  }
}

//Renders a tesselated model into an 2D Image
abstract class ModelRenderer {
  def render(model: Model)
}