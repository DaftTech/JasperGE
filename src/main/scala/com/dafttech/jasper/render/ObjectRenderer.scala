package com.dafttech.jasper.render

import java.nio.{ByteBuffer, ByteOrder}

import com.dafttech.jasper.scene.{Entity, RenderingEntity, RenderingGroup}
import com.dafttech.jasper.util.Vertex
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL12
import org.lwjgl.opengl.GL12._
import org.lwjgl.opengl.GL15._

object ObjectRenderer {
  val objMatBuffer = ByteBuffer.allocateDirect(16 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
  val groupMatBuffer = ByteBuffer.allocateDirect(16 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()

  val Triangles = new ObjectRenderer[RenderingEntity] {
    override def render(input: RenderingEntity): Unit = {
      groupMatBuffer.rewind()
      objMatBuffer.rewind()
      glLoadMatrixf(groupMatBuffer)
      glMultMatrixf(input.transformation.get(objMatBuffer))

      glDrawElements(GL_TRIANGLES, input.getTesselator.getIdxCount(input), GL_UNSIGNED_INT, input.vbLoc.indexPosition * 4)
    }
  }

  val RenderingGroup = new ObjectRenderer[RenderingGroup] {
    override def render(input: RenderingGroup): Unit = {
      input.transformation.get(groupMatBuffer)

      for (e <- input.childs) {
        e.getRenderer.render(e)
      }
    }
  }
}

abstract class ObjectRenderer[T <: Entity] {
  def render(input: T): Unit
}