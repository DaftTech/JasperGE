package com.dafttech.jasper

import org.lwjgl.opengl.GL11

/**
  * Created by LolHens on 15.01.2016.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val win = new Window(800, 600)
    val scn = new Scene

    val model = new Model
    scn.addModel(model)

    val scnR = new SceneRenderer {
      override def render(scene: Scene): Unit = {
        for(m <- scene.models) {
          m.modelRenderer.render(model)
        }
      }
    }

    while(!win.shouldClose) {
      win.render(scnR, scn)
    }
  }
}
