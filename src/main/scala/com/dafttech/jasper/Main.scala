package com.dafttech.jasper

import java.io.File
import java.nio.file.Path

import org.lwjgl.opengl.GL11

import scala.concurrent.Future
import scala.reflect.io.Directory

import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by LolHens on 15.01.2016.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val win = new Window(800, 600)
    val scn = new Scene

    val path = new File("./")
    println(path.getAbsolutePath)

    val obj = new Object

    obj.addModel(new ModelQuad(200, 200, 100, 100))

    scn.addModel(obj)

    val scnR = new SceneRenderer {
      override def render(scene: Scene): Unit = {
        for(m <- scene.models) {
          m.objectRenderer.render(obj)
        }
      }
    }

    Future {
      while(true) {
        obj.tesselator.tesselate(obj)
      }
    }

    while(!win.shouldClose) {
      win.render(scnR, scn)
    }
  }
}
