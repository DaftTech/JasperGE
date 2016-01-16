package com.dafttech.jasper

import java.io.File

import com.dafttech.jasper.render.SceneRenderer
import com.dafttech.jasper.scene.model.{ModelOBJ, ModelQuad, Point3f}
import com.dafttech.jasper.scene.{PlacedModel, Scene}
import com.dafttech.jasper.window.Window
import org.joml.Matrix4f

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
  * Created by LolHens on 15.01.2016.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val win = new Window(800, 600)
    val scn = new Scene

    val teapot = new ModelOBJ("teapot.obj")

    val obj = scn.addObject(new PlacedModel(teapot, new Matrix4f().translate(0, -90, -250)))

    val scnR = new SceneRenderer {
      override def render(scene: Scene): Unit = {
        for (m <- scene.models) {
          m.objectRenderer.render(m)
        }
      }
    }

    Future {
      while (true) {
        obj.transformation.set(obj.transformation.rotateZ(1f))
      }
    }

    while (!win.shouldClose) {
      win.render(scnR, scn)
    }
  }
}
