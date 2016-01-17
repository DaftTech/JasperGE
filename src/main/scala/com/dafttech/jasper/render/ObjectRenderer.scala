package com.dafttech.jasper.render

import java.nio.{FloatBuffer, ByteOrder, ByteBuffer}

import com.dafttech.jasper.scene.{RenderingGroup, RenderingEntity, Entity, PlacedModel}
import org.lwjgl.opengl.GL11._

object ObjectRenderer {
  val matBuffer = ByteBuffer.allocateDirect(16*4).order(ByteOrder.nativeOrder()).asFloatBuffer()

  val Triangles = new ObjectRenderer[RenderingEntity] {
    override def render(obj: RenderingEntity): Unit = {
      glPushMatrix()
      matBuffer.rewind()
      glMultMatrixf(obj.transformation.get(matBuffer))
      glDrawElements(GL_TRIANGLES, obj.getTesselator.getIdxCount, GL_UNSIGNED_INT, 0)
      glPopMatrix()
    }
  }

  val RenderingGroup = new ObjectRenderer[RenderingGroup] {
    override def render(obj: RenderingGroup): Unit = {
      glLoadIdentity()
      matBuffer.rewind()
      glMultMatrixf(obj.transformation.get(matBuffer))

      val entities = obj.childs.map(_.asInstanceOf[RenderingEntity])
      for(e <- entities) {
        e.getRenderer.render(e)
      }
    }
  }
}

abstract class ObjectRenderer[T <: Entity] {
  def render(input: T)
}