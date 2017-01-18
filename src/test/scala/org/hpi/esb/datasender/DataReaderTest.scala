package org.hpi.esb.datasender

import java.io.{BufferedReader, File, FileNotFoundException}
import org.scalatest._

/**
	* Created by guenterhesse on 18/01/2017.
	*/
class DataReaderTest extends FlatSpec with Matchers with PrivateMethodTester with BeforeAndAfter {

		var tmpFile: File = null
		var dataReader: DataReader = null
		val tmpFilePrefix = "esb_datareader_testfile"
		val tmpFileSuffix = ".tmp"

	before {
		tmpFile = File.createTempFile(tmpFilePrefix, tmpFileSuffix)
		tmpFile.deleteOnExit
		dataReader = new DataReader(tmpFile.getAbsolutePath)
	}

	after {
	}

	"A DataReader" should "contain a buffered reader" in {
		val privateBufferedReader = PrivateMethod[BufferedReader]('br)
		val br = dataReader invokePrivate privateBufferedReader()
		assert(br.isInstanceOf[BufferedReader])
	}

	it should "fail w/ FileNotFoundException if path to file is wrong / file does not exist" in {
		assertThrows[FileNotFoundException] {
			new DataReader("/bla/bla.csv")
		}
	}

	it should "need a data input path for initialization" in {
		assertDoesNotCompile("val a = new DataReader()")
	}

}
