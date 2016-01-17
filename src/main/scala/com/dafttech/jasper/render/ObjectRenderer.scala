package com.dafttech.jasper.render

import java.nio.{ByteBuffer, ByteOrder}

import com.dafttech.jasper.scene.{Entity, RenderingEntity, RenderingGroup}
import org.lwjgl.opengl.GL11._

object ObjectRenderer {
  val objMatBuffer = ByteBuffer.allocateDirect(16 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
  val groupMatBuffer = ByteBuffer.allocateDirect(16 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()

  val Triangles = new ObjectRenderer[RenderingEntity] {
    override def render(obj: RenderingEntity): Unit = {
      groupMatBuffer.rewind()
      objMatBuffer.rewind()
      glLoadMatrixf(groupMatBuffer)
      glMultMatrixf(obj.transformation.get(objMatBuffer))

      glDrawElements(GL_TRIANGLES, obj.getTesselator.getIdxCount(obj), GL_UNSIGNED_INT, obj.vbLoc.indexPosition)
    }
  }

  val RenderingGroup = new ObjectRenderer[RenderingGroup] {
    override def render(obj: RenderingGroup): Unit = {
      obj.transformation.get(groupMatBuffer)

      for (e <- obj.childs) {
        e.getRenderer.render(e)
      }
    }
  }
}

abstract class ObjectRenderer[T <: Entity] {
  def render(input: T): Unit
}