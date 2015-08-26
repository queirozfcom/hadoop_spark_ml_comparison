import scala.math.random
import org.apache.spark._
import org.apache.spark.SparkContext._

object WordCount{
	
	def main(args:Array[String]){

		if(args.length < 1){
			System.err.println("Please set arguments for <s3_input_dir> <s3_output_dir>")
			System.exit(1)
		}

		val startNano = System.nanoTime

		val inputDir = args(0)
		val outputDir = args(1)	

		val cnf      = new SparkConf().setAppName("Spark Distributed WordCount")
		val sc       = new SparkContext(cnf)
		
	    val textFile = sc.textFile(inputDir)
		val counts   = textFile.flatMap(line => line.split("\\s+")) 
                       .map(word => (word, 1)) 
                       .reduceByKey( (a,b) => a+b )

		counts.saveAsTextFile(outputDir)	
		sc.stop()
		
		val endNano = System.nanoTime - startNano
		val endSeconds = endNano / 10e-9

		System.out.println("\n\n\n")
		System.out.println("========================================")	
		System.out.println("========================================")
		System.out.println(s"Total time elapsed: $endSeconds seconds")
		System.out.println("========================================")	
		System.out.println("========================================")
		System.out.println("\n\n\n") 
		

	}
}
