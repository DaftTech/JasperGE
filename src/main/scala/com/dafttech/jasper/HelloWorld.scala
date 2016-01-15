import org.lwjgl._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl._
import org.lwjgl.system.MemoryUtil._

object HelloWorld {
  def main(args: Array[String]) {
    new HelloWorld().run
  }
}

class HelloWorld {
  private var errorCallback: GLFWErrorCallback = null
  private var keyCallback: GLFWKeyCallback = null
  private var window: Long = 0L

  def run {
    System.out.println("Hello LWJGL " + Version.getVersion + "!")
    try {
      init
      loop
      glfwDestroyWindow(window)
      keyCallback.release
    } finally {
      glfwTerminate
      errorCallback.release
    }
  }

  private def init {
    errorCallback = GLFWErrorCallback.createPrint(System.err)
    glfwSetErrorCallback(errorCallback)
    if (glfwInit != GLFW_TRUE) throw new IllegalStateException("Unable to initialize GLFW")
    glfwDefaultWindowHints
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
    val WIDTH: Int = 300
    val HEIGHT: Int = 300
    window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL)
    if (window == NULL) throw new RuntimeException("Failed to create the GLFW window")
    keyCallback = new GLFWKeyCallback() {
      def invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, GLFW_TRUE)
      }
    }
    glfwSetKeyCallback(window, keyCallback)
    val vidmode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor)
    glfwSetWindowPos(window, (vidmode.width - WIDTH) / 2, (vidmode.height - HEIGHT) / 2)
    glfwMakeContextCurrent(window)
    glfwSwapInterval(1)
    glfwShowWindow(window)
  }

  private def loop {
    GL.createCapabilities
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    while (glfwWindowShouldClose(window) == GLFW_FALSE) {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
      glfwSwapBuffers(window)
      glfwPollEvents
    }
  }
}