package sha3

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec
import chisel3.experimental.BundleLiterals._

class ThetaSpec extends AnyFreeSpec with ChiselScalatestTester {

  "Theta test" in {
    test(new Theta(64)) { dut =>
      def ROTL(x: Int, y: Int, W: Int) = (((x) << (y)) | ((x) >> (W - (y))))

      val w = 8

      // val test_matrix = common.generate_test_matrix(w)
      // // val test_matrix = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 
      // // 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 
      // // 21, 22, 23, 24, 25)
      // val bc = Array.fill(5)(0)
      // for(i <- 0 until 5) {
      //   // bc(i) = test_matrix(i*5+0) ^ test_matrix(i*5+1) ^ test_matrix(i*5+2) ^ test_matrix(i*5+3) ^ test_matrix(i*5+4)
      //   bc(i) = test_matrix(i) ^ test_matrix(i+5) ^ test_matrix(i+10) ^ test_matrix(i+15) ^ test_matrix(i+20)
      // }
      // val result_matrix = Array.fill(5*5)(0)
      // for(i <- 0 until 5) {
      //   val t = bc((i+4)%5) ^ ROTL(bc((i+1)%5), 1, w)
      //   for(j <- 0 until 25 by 5) {
      //     result_matrix(i+j) = test_matrix(i+j) ^ t
      //   }
      // }

      // println("result matrix = ")
      // for(i <- 0 until 25) {
      //   print(s"${result_matrix(i)}, ")
      //   if (i%4 == 3) {
      //     println("")
      //   }
      // }
      // println("");
      
      for(l <- 0 until 5) {
        val test_matrix = common.generate_test_matrix(w)
        // println("test matrix = ")
        // for(i <- 0 until 25) {
        //   print(s"${test_matrix(i)}, ")
        //   if (i%4 == 3) {
        //     println("")
        //   }
        // }
        // println("");
        val bc = Array.fill(5)(0)
        for(i <- 0 until 5) {
          bc(i) = test_matrix(i) ^ test_matrix(i+5) ^ test_matrix(i+10) ^ test_matrix(i+15) ^ test_matrix(i+20)
        }
        val result_matrix = Array.fill(5*5)(0)
        for(i <- 0 until 5) {
          val t = bc((i+4)%5) ^ ROTL(bc((i+1)%5), 1, 64)
          for(j <- 0 until 25 by 5) {
            result_matrix(i+j) = test_matrix(i+j) ^ t
          }
        }
        println("result matrix = ")
        for(i <- 0 until 25) {
          print(s"${result_matrix(i)}, ")
          if (i%4 == 3) {
            println("")
          }
        }
        println("");

        for(i <- 0 until 25) {
          dut.io.state_in(i).poke(test_matrix(i).U)
        }
        for(j <- 0 until 25) {
          dut.io.state_out(j).expect(result_matrix(j).U)
          // println(dut.io.state_out(j).peek())
        }
      }
    }
  }
}