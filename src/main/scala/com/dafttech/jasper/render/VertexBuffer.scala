package com.dafttech.jasper.render

import java.nio.{ByteBuffer, ByteOrder}

import com.dafttech.jasper.gl.{IndexBufferObject, Pointer, VertexBufferObject}
import com.dafttech.jasper.util.Vertex
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

case class VertexBufferLocation(vertexPosition: Int, indexPosition: Int, vertexBuffer: VertexBuffer)

class VertexBuffer {
  val vbo = new VertexBufferObject(65536*128, Seq(
    Pointer.Vertex(3),
    Pointer.Color(4),
    Pointer.Normal))

  val ibo = new IndexBufferObject(65536*128)

  val vboID = glGenBuffers()
  val iboID = glGenBuffers()

  private val vertexByteBuffer = ByteBuffer.allocateDirect(65536*128).order(ByteOrder.LITTLE_ENDIAN)
  val vertexBuffer = vertexByteBuffer.asFloatBuffer()

  private val indexByteBuffer = ByteBuffer.allocateDirect(65536*128).order(ByteOrder.LITTLE_ENDIAN)
  val indexBuffer = indexByteBuffer.asIntBuffer()

  @volatile var changed = false

  var vertexAllocPos = 0
  var indexAllocPos = 0

  def allocate(vtxCount: Int, idxCount: Int): VertexBufferLocation = {
    val r = new VertexBufferLocation(vertexAllocPos, indexAllocPos, this)
    vertexAllocPos += vtxCount * Vertex.VTX_FLOAT_COUNT
    indexAllocPos += idxCount

    return r
  }

  def setVertices(location: VertexBufferLocation, vertices: Seq[Vertex]) = {
    this.synchronized {
      vertexBuffer.rewind()
      vertexBuffer.position(location.vertexPosition)
      vertexBuffer.put(vertices.flatMap(_.values).toArray)

      changed = true
    }
  }

  def setIndices(location: VertexBufferLocation, indices: Seq[Int]) = {
    this.synchronized {
      indexBuffer.rewind()
      indexBuffer.position(location.indexPosition)
      indexBuffer.put(indices.map(_ + location.vertexPosition).toArray)

      changed = true
    }
  }

  def commit = {
    if(changed) {
      this.synchronized {
        vertexBuffer.rewind()
        vbo.put(vertexByteBuffer, vertexByteBuffer.capacity())

        glBindBuffer(GL_ARRAY_BUFFER, vboID)
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW)

        indexBuffer.rewind()
        ibo.put(indexByteBuffer, indexByteBuffer.capacity())

        glBindBuffer(GL_ARRAY_BUFFER, iboID)
        glBufferData(GL_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW)
      }
    }
    changed = false
  }

  def activate = {
    /*glEnableClientState(GL_VERTEX_ARRAY)
    glEnableClientState(GL_COLOR_ARRAY)
    glEnableClientState(GL_NORMAL_ARRAY)*/
    /*glBindBuffer(GL_ARRAY_BUFFER, vboID)

    glVertexPointer(3, GL_FLOAT, Vertex.VTX_FLOAT_COUNT * 4, 0)
    glColorPointer(4, GL_FLOAT, Vertex.VTX_FLOAT_COUNT * 4, 12)
    glNormalPointer(GL_FLOAT, Vertex.VTX_FLOAT_COUNT * 4, 28)

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID)*/
    ibo.activate(vbo)
  }
}
