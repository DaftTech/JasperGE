package com.dafttech.jasper

/**
  * Created by LolHens on 15.01.2016.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val win = new Window(800, 600)

    while(!win.shouldClose) {
      win.render()
    }
  }
}
