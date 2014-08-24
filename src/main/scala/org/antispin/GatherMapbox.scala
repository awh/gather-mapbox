package org.antispin

import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO
import java.io.File

/**
 * Created by awh on 24/08/2014.
 */
object GatherMapbox {

  val urlPrefix = "http://tiles.example.com"
  val xRange = 0 to 63
  val yRange = 1 to 63
  val tileSize = 256

  def fetchTile(x: Int, y: Int): BufferedImage =
    ImageIO.read(new URL(s"$urlPrefix/$x/$y.png"))

  def main(args: Array[String]) {
    val map = new BufferedImage(
      xRange.length * tileSize,
      yRange.length * tileSize,
      BufferedImage.TYPE_INT_RGB)

    val mapContext = map.createGraphics()

    for {
      x <- xRange
      y <- yRange
    } {
      println(s"Processing tile $x, $y")
      Thread.sleep(100)
      mapContext.drawImage(
        fetchTile(x, y),
        x * tileSize, (y - 1) * tileSize,
        null, null)
    }

    ImageIO.write(map, "png", new File("map.png"))
  }

}
