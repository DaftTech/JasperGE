package com.dafttech.jasper.window

import java.nio.{ByteOrder, FloatBuffer, ByteBuffer}

import com.dafttech.jasper.render.SceneRenderer
import com.dafttech.jasper.scene.Scene
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.{GLFWErrorCallback, GLFWKeyCallback, GLFWVidMode}
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11._
import org.lwjgl.ovr.OVRMatrix4f
import org.lwjgl.system.MemoryUtil._

object GLFWHandler {
  if (glfwInit != GLFW_TRUE) throw new RuntimeException("Unable to initialize GLFW!")

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
  if (l_WID == NULL) throw new RuntimeException("Failed to create window!")

  val keyCallback = new GLFWKeyCallback() {
    def invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
      if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, GLFW_TRUE)
    }
  }
  glfwSetKeyCallback(l_WID, keyCallback)

  val vidmode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor)
  glfwSetWindowPos(l_WID, (vidmode.width - width) / 2, (vidmode.height - height) / 2)
  glfwMakeContextCurrent(l_WID)
  glfwSwapInterval(0)
  glfwShowWindow(l_WID)

  GL.createCapabilities()
  glClearColor(0.5f, 0.5f, 0.5f, 0.0f)


  var rot = 0.0f

  def render(sceneRenderer: SceneRenderer, scene: Scene) = {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    rot += 0.01f

    glMatrixMode(GL_PROJECTION)
    glLoadIdentity()

    val matrix = ByteBuffer.allocateDirect(4*16).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer()

    val fov = 60.0f
    val aspect = 800.0f/600.0f
    val zFar = 1000f
    val zNear = 0.001f

    val yScale = (1.0f / Math.tan(Math.toRadians(fov / 2))).toFloat
    val xScale = yScale / aspect
    val frustrumLength = zFar - zNear

    matrix.put(xScale); matrix.put(0);      matrix.put(0);                                      matrix.put(0)
    matrix.put(0);      matrix.put(yScale); matrix.put(0);                                      matrix.put(0)
    matrix.put(0);      matrix.put(0);      matrix.put(-((zFar + zNear) / frustrumLength));     matrix.put(-1)
    matrix.put(0);      matrix.put(0);      matrix.put(-((2 * zFar * zNear) / frustrumLength)); matrix.put(0)

    matrix.rewind()

    glLoadMatrixf(matrix)

    glTranslatef(0, -0.4f, 0)
    glRotatef(20, 1, 0, 0)

    glMatrixMode(GL_MODELVIEW)
    glLoadIdentity()

    scene.vertexBuffer.commit
    scene.vertexBuffer.activate

    sceneRenderer.render(scene)

    glfwSwapBuffers(l_WID)
    glfwPollEvents()
  }

  def shouldClose = glfwWindowShouldClose(l_WID) == GLFW_TRUE

  def destroy() = {
    keyCallback.release()
    glfwDestroyWindow(l_WID)
  }
}
