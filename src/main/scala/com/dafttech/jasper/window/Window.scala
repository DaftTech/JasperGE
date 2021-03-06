package com.dafttech.jasper.window

import java.nio.{ByteBuffer, ByteOrder, FloatBuffer}

import com.dafttech.jasper.render.SceneRenderer
import com.dafttech.jasper.scene.{Camera, Scene}
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.{GLFWErrorCallback, GLFWKeyCallback, GLFWVidMode}
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11._
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
  glfwSwapInterval(1)
  glfwShowWindow(l_WID)

  GL.createCapabilities()
  glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

  glMatrixMode(GL_PROJECTION)
  glLoadIdentity()

  val cam = new Camera(width, height, new Vector3f(0, 0, 1), new Vector3f(0, 0, 0))
  val matrix = ByteBuffer.allocateDirect(4 * 16).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer()
  cam.matrix.get(matrix)
  matrix.rewind()
  glLoadMatrixf(matrix)

  val temp = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder()).asFloatBuffer()

  val lightAmbient = Array(1.0f, 1.0f, 1.0f, 1.0f)
  val lightDiffuse = Array(0.5f, 0.5f, 0.5f, 1.0f)
  val lightPosition = Array(1.0f, 1.0f, 1.0f, 0.0f)

  glLightfv(GL_LIGHT1, GL_AMBIENT, temp.put(lightAmbient).flip().asInstanceOf[FloatBuffer])
  glLightfv(GL_LIGHT1, GL_DIFFUSE, temp.put(lightDiffuse).flip().asInstanceOf[FloatBuffer])
  glLightfv(GL_LIGHT1, GL_POSITION, temp.put(lightPosition).flip().asInstanceOf[FloatBuffer])

  glEnable(GL_LIGHT1)
  glEnable(GL_LIGHTING)
  glEnable(GL_COLOR)

  glEnable(GL_DEPTH_TEST)

  glEnableClientState(GL_VERTEX_ARRAY)
  glEnableClientState(GL_COLOR_ARRAY)
  glEnableClientState(GL_TEXTURE_COORD_ARRAY)
  glEnableClientState(GL_NORMAL_ARRAY)

  def render(sceneRenderer: SceneRenderer, scene: Scene) = {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    glMatrixMode(GL_MODELVIEW)

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
