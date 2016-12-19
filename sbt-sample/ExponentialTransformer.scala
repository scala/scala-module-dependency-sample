import scala.xml._

object ExponentialTransformer extends App {

    var i = 0
    def translate(text: String): String = {
        i += 1
        return "!%s!".format(text)
    }
     
    val xmlNode = <a><b><c><h1>Hello Example</h1></c></b></a>
     
    new transform.RuleTransformer(new transform.RewriteRule {
        override def transform(n: Node): Seq[Node] = n match {
            case t: Text if !t.text.trim.isEmpty => Text(translate(t.text.trim))
            case _ => n
        }
    }).transform(xmlNode)
    
    println(s"scala.xml.transform.RuleTransformer called $i time(s)")
}
