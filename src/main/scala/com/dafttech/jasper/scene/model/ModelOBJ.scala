package com.dafttech.jasper.scene.model

import com.dafttech.jasper.util.Vertex


class ModelOBJ(path: String) extends Model {
  case class Point(x: Float, y: Float, z: Float)
  case class Normal(x: Float, y: Float, z: Float)
  case class TexCoord(u: Float, v: Float)
  case class Face(i1p: Int, i1n: Int, i1t: Int, i2p: Int, i2n: Int, i2t: Int, i3p: Int, i3n: Int, i3t: Int)

  val file = io.Source.fromFile(path).getLines()

  val fParsed = file.map[Any] {
    case s if s.startsWith("vn ") => {
      val d = s.stripPrefix("vn ")
      val ds = d.split(" ").map(_.toFloat)

      new Normal(ds(0), ds(1), ds(2))
    }
    case s if s.startsWith("v ") => {
      val d = s.stripPrefix("v ")
      val ds = d.split(" ").map(_.toFloat)

      new Point(ds(0), ds(1), ds(2))
    }
    case s if s.startsWith("vt ") => {
      val d = s.stripPrefix("vt ")
      val ds = d.split(" ").map(_.toFloat)

      new TexCoord(ds(0), ds(1))
    }
    case s if s.startsWith("f ") => {
      val d = s.stripPrefix("f ")
      val ds = d.split(" ")

      val dm = ds.map(_.split("/").map(_.toInt))

      if(dm(0).length == 1) {
        new Face(dm(0)(0), dm(0)(0), dm(0)(0), dm(1)(0), dm(1)(0), dm(1)(0), dm(2)(0), dm(2)(0), dm(2)(0))
      }
      else
      {
        new Face(dm(0)(0), dm(0)(1), dm(0)(2), dm(1)(0), dm(1)(1), dm(1)(2), dm(2)(0), dm(2)(1), dm(2)(2))
      }
    }

    case _ => null
  }

  val fPoints = fParsed.filter(_.isInstanceOf[Point]).map(_.asInstanceOf[Point]).toArray
  val fNormals = fParsed.filter(_.isInstanceOf[Normal]).map(_.asInstanceOf[Normal]).toArray
  val fTexCoords = fParsed.filter(_.isInstanceOf[TexCoord]).map(_.asInstanceOf[TexCoord]).toArray
  val fFaces = fParsed.filter(_.isInstanceOf[Face]).map(_.asInstanceOf[Face]).toArray

  case class OBJVertex(pointIndex: Int, normalsIndex: Int, texcoordIndex: Int)

  val fRawVertices = fFaces.flatMap { case f: Face =>
    Seq(
      new OBJVertex(f.i1p, f.i1n, f.i1t),
      new OBJVertex(f.i2p, f.i2n, f.i2t),
      new OBJVertex(f.i3p, f.i3n, f.i3t)
    )
  }

  val fDistinctVertices = fRawVertices.distinct

  val vertices = fDistinctVertices.map { v =>
    new Vertex(Seq[Float](
      fPoints(v.pointIndex).x,
      fPoints(v.pointIndex).y,
      fPoints(v.pointIndex).z,
      0,
      0,
      0,
      0,
      fNormals(v.normalsIndex).x,
      fNormals(v.normalsIndex).y,
      fNormals(v.normalsIndex).z //TODO add texcoords
    ))
  }

  val indices = fRawVertices.map(f => fDistinctVertices.indexOf(f))

  override def getVertices: Seq[Vertex] = vertices

  override def getIndices: Seq[Int] = indices
}
