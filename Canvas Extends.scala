import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.HTMLImageElement




def getElem[T](id: String) = dom.document.getElementById(id).asInstanceOf[T]
val canvas = getElem[html.Canvas]("canvas")

sealed trait TConfig {
  def cellSize = 64
  def cellCountX = 5
  def cellCountY = 5
}
object WebConfig extends TConfig  {
  override val cellSize = 42
  override val cellCountX = 9
  override val cellCountY = 9
}

class RichCanvas(c:html.Canvas)(implicit t:TConfig) {
  type Ctx2D = dom.CanvasRenderingContext2D
  val ctx = c.getContext("2d").asInstanceOf[Ctx2D]
  val crdX = (0 to t.cellCountX).map(_=>t.cellSize).zipWithIndex.map(a=>a._1*a._2).toList
  val crdY = (0 to t.cellCountY).map(_=>t.cellSize).zipWithIndex.map(a=>a._1*a._2).toList
  
  def grid()={
    ctx.lineWidth = 0.5
    ctx.strokeStyle = "#AA0055"
    ctx.beginPath()
    crdX.foreach(x=>{ctx.moveTo(x, 0);ctx.lineTo(x, crdY.last)})
    crdY.foreach(y=>{ctx.moveTo(0, y);ctx.lineTo(crdX.last,y)})
    ctx.stroke
  }
  
  
}

implicit def canvasToCanvas(c:html.Canvas) = new RichCanvas(c)
implicit val twc = ScalaFiddle.WebConfig

canvas.grid()
