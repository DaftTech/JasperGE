package com.dafttech.jasper

import java.io.File

import com.dafttech.jasper.render.SceneRenderer
import com.dafttech.jasper.scene.model.{ModelOBJ, ModelQuad, Point3f}
import com.dafttech.jasper.scene.{RObject, Scene}
import com.dafttech.jasper.window.Window

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
  * Created by LolHens on 15.01.2016.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val win = new Window(800, 600)
    val scn = new Scene

    val path = new File("./")
    println(path.getAbsolutePath)

    val obj = new RObject

    obj.addModel(new ModelQuad(0, 0, 0.1f, 0.1f))
    for(x <- 1 to 15) {
      for(y <- 1 to 15) {
        obj.addModel(new ModelOBJ("teapot.obj", new Point3f((x-5)*10, (y-10)*10, -350)))
      }
    }

    scn.addObject(obj)

    val scnR = new SceneRenderer {
      override def render(scene: Scene): Unit = {
        for (m <- scene.models) {
          m.objectRenderer.render(obj)
        }
      }
    }

    Future {
      while (true) {
        Future.wait(50)
        obj.tesselator.tesselate(obj)
      }
    }

    while (!win.shouldClose) {
      win.render(scnR, scn)
    }
  }
}
