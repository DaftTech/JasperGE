package com.dafttech.jasper

import org.lwjgl.Version
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.{GLFWVidMode, GLFWKeyCallback, GLFWErrorCallback}
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11._
import org.lwjgl.system.MemoryUtil._


object GLFWHandler {
  if(glfwInit != GLFW_TRUE) throw new RuntimeException("Unable to initialize GLFW!")

  val errorCallback = GLFWErrorCallback.createPrint(System.err)
  glfwSetErrorCallback(errorCallback)

  def init = {

  }

  def exit = {
    errorCallback.release()
    glfwTerminate()
  }

}

class Window(val width: Int, val height: Int) {
  GLFWHandler.init

  glfwDefaultWindowHints()
  glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
  glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

  private val l_WID = glfwCreateWindow(width, height, "Hello World!", NULL, NULL)
  if(l_WID == NULL) throw new RuntimeException("Failed to create window!")

  val keyCallback = new GLFWKeyCallback() {
    def invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
      if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, GLFW_TRUE)
    }
  }
  glfwSetKeyCallback(l_WID, keyCallback)

  val vidmode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor)
  glfwSetWindowPos(l_WID, (vidmode.width - width) / 2, (vidmode.height - height) / 2)
  glfwMakeContextCurrent(l_WID)
  glfwSwapInterval(1)
  glfwShowWindow(l_WID)

  GL.createCapabilities()
  glClearColor(1.0f, 0.0f, 0.0f, 0.0f)

  def render() = {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    glfwSwapBuffers(l_WID)
    glfwPollEvents()
  }

  def shouldClose = glfwWindowShouldClose(l_WID) == GLFW_TRUE

  def destroy() = {
    keyCallback.release()
    glfwDestroyWindow(l_WID)
  }
}
