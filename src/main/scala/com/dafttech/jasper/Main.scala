package com.dafttech.jasper

import java.io.File

import com.dafttech.jasper.render.SceneRenderer
import com.dafttech.jasper.scene.model.{ModelOBJ, ModelQuad, Point3f}
import com.dafttech.jasper.scene.{RenderingGroup, PlacedModel, Scene}
import com.dafttech.jasper.window.Window
import org.joml.Matrix4f

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
  * Created by LolHens on 15.01.2016.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val win = new Window(1440, 900)

    val teapot = new ModelOBJ("teapot.obj")


    val scn = new Scene
    val grp = new RenderingGroup(new Matrix4f().identity())

    scn.addSub(grp)

    val obj1 = grp.addSub(new PlacedModel(teapot, new Matrix4f().translation(-50, -90, -250)))
    val obj2 = grp.addSub(new PlacedModel(teapot, new Matrix4f().translation(0, -90, -250)))
    val obj3 = grp.addSub(new PlacedModel(teapot, new Matrix4f().translation(50, -90, -250)))
    val obj4 = grp.addSub(new PlacedModel(teapot, new Matrix4f().translation(-50, -90, -250)))
    val obj5 = grp.addSub(new PlacedModel(teapot, new Matrix4f().translation(0, -90, -250)))
    val obj6 = grp.addSub(new PlacedModel(teapot, new Matrix4f().translation(50, -90, -250)))

    val scnR = new SceneRenderer {
      override def render(scene: Scene): Unit = {
        for (m <- scene.childs) {
          m.asInstanceOf[RenderingGroup].vertexBuffer.commit
          m.asInstanceOf[RenderingGroup].vertexBuffer.activate
          m.asInstanceOf[RenderingGroup].getRenderer.render(m.asInstanceOf[RenderingGroup])
        }
      }
    }

    Future {
      var rot = 0.0f
      while (true) {
        Thread.sleep(500)
        rot += 0.50f
        rot = rot % (2 * Math.PI.toFloat)
        obj1.transformation.set(new Matrix4f().translation(-165, -40, -300).rotateZ(rot))
      }
    }

    Future {
      var rot = 0.0f
      while (true) {
        Thread.sleep(250)
        rot += 0.25f
        rot = rot % (2 * Math.PI.toFloat)
        obj2.transformation.set(new Matrix4f().translation(0, -40, -300).rotateZ(rot))
      }
    }

    Future {
      var rot = 0.0f
      while (true) {
        Thread.sleep(100)
        rot += 0.1f
        rot = rot % (2 * Math.PI.toFloat)
        obj3.transformation.set(new Matrix4f().translation(165, -40, -300).rotateZ(rot))
      }
    }

    Future {
      var rot = 0.0f
      while (true) {
        Thread.sleep(50)
        rot += 0.05f
        rot = rot % (2 * Math.PI.toFloat)
        obj4.transformation.set(new Matrix4f().translation(-165, -160, -200).rotateZ(rot))
      }
    }

    Future {
      var rot = 0.0f
      while (true) {
        Thread.sleep(20)
        rot += 0.02f
        rot = rot % (2 * Math.PI.toFloat)
        obj5.transformation.set(new Matrix4f().translation(0, -160, -200).rotateZ(rot))
      }
    }

    Future {
      var rot = 0.0f
      while (true) {
        Thread.sleep(10)
        rot += 0.01f
        rot = rot % (2 * Math.PI.toFloat)
        obj6.transformation.set(new Matrix4f().translation(165, -160, -200).rotateZ(rot))
      }
    }

    while (!win.shouldClose) {
      win.render(scnR, scn)
    }
  }
}
