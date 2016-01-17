package com.dafttech.jasper.scene

import org.joml.{Matrix4f, Vector3f}

class Camera(vpWidth: Int, vpHeight: Int, position: Vector3f, lookAt: Vector3f) {
  val matrix = new Matrix4f().perspective((Math.PI.toFloat / 180f) * 75f, vpWidth.toFloat / vpHeight.toFloat, 0.1f, 1000f).lookAt(position, lookAt, new Vector3f(0, 1, 0))
}
