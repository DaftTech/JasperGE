package com.dafttech.jasper.scene

import com.dafttech.jasper.render.{VertexBuffer, VertexBufferLocation, Tesselator, ObjectRenderer}
import com.dafttech.jasper.util.Vertex
import org.joml.Matrix4f

import scala.collection.mutable

abstract class Entity {
  def isRendering = this.isInstanceOf[Rendering]
}

trait Rendering {
  def getTransformation: Matrix4f
  def getTesselator: Tesselator[_]
  def getRenderer: ObjectRenderer[_]
}

abstract class RenderingEntity(val transformation: Matrix4f) extends Entity with Rendering {
  override def getTransformation = transformation

  override def getTesselator = Tesselator.Triangles
  override def getRenderer = ObjectRenderer.Triangles

  def getVertices: Seq[Vertex]
  def getIndices: Seq[Int]

  var vbLoc: VertexBufferLocation = null
}

trait Node[AcceptedChilds] {
  val childs = new mutable.MutableList[AcceptedChilds]

  def initSub(entity: AcceptedChilds) = {

  }

  def addSub(entity: AcceptedChilds) = {
    childs += entity

    initSub(entity)

    entity
  }
}

abstract class Group extends Entity

class GroupingGroup extends Group with Node[Group]

class RenderingGroup(val transformation: Matrix4f) extends Group with Rendering with Node[RenderingEntity] {
  override def getTransformation = transformation

  override def getTesselator = Tesselator.RenderingGroup
  override def getRenderer = ObjectRenderer.RenderingGroup

  val vertexBuffer = new VertexBuffer()

  override def initSub(entity: RenderingEntity): Unit = {
    entity.getTesselator.tesselate(entity, vertexBuffer)
  }
}
