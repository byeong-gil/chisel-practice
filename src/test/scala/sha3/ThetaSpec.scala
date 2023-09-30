package sha3

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec
import chisel3.experimental.BundleLiterals._

class ThetaSpec extends AnyFreeSpec with ChiselScalatestTester {

  "Theta test" in {
    test(new Theta(64)) { dut =>
      def ROTL(x: Int, y: Int, W: Int) = (((x) << (y)) | ((x) >> (W - (y))))

      val W = 64

      // val test_matrix = common.generate_test_matrix(W)
      val test_matrix = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 
      11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 
      21, 22, 23, 24, 25)
      val bc = Array.fill(5)(0)
      for(i <- 0 until 5) {
        // bc(i) = test_matrix(i*5+0) ^ test_matrix(i*5+1) ^ test_matrix(i*5+2) ^ test_matrix(i*5+3) ^ test_matrix(i*5+4)
        bc(i) = test_matrix(i) ^ test_matrix(i+5) ^ test_matrix(i+10) ^ test_matrix(i+15) ^ test_matrix(i+20)
      }
      val result_matrix = Array.fill(5*5)(0)
      for(i <- 0 until 5) {
        val t = bc((i+4)%5) ^ ROTL(bc((i+1)%5), 1, W)
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

    //   val testValues = for { x <- 0 to 10; y <- 0 to 10} yield (x, y)
    //   val inputSeq = testValues.map { case (x, y) => (new GcdInputBundle(16)).Lit(_.value1 -> x.U, _.value2 -> y.U) }
    //   val resultSeq = testValues.map { case (x, y) =>
    //     (new GcdOutputBundle(16)).Lit(_.value1 -> x.U, _.value2 -> y.U, _.gcd -> BigInt(x).gcd(BigInt(y)).U)
    //   }

    //   fork {
    //     // push inputs into the calculator, stall for 11 cycles one third of the way
    //     val (seq1, seq2) = inputSeq.splitAt(resultSeq.length / 3)
    //     dut.input.enqueueSeq(seq1)
    //     dut.clock.step(11)
    //     dut.input.enqueueSeq(seq2)
    //   }.fork {
    //     // retrieve computations from the calculator, stall for 10 cycles one half of the way
    //     val (seq1, seq2) = resultSeq.splitAt(resultSeq.length / 2)
    //     dut.output.expectDequeueSeq(seq1)
    //     dut.clock.step(10)
    //     dut.output.expectDequeueSeq(seq2)
    //   }.join()

    }
  }
}

// This is the test from https://github.com/ucb-bar/sha3/blob/master/src/main/scala/theta.scala
// It seems like out-dated code. Sync it.

// class ThetaModuleTests(c: Theta) extends Tester(c, Array(c.io)) {
//   defTests {
//     var allGood = true
//     val vars    = new HashMap[Node, Node]()
//     val W       = 4
//     for (i <- 0 until 1) {
//       val state = Vec.fill(5*5){Bits(width = W)}
//       val out_state = Vec.fill(5*5){Bits(width = W)}
//       val matrix = common.generate_test_matrix(W)
//       var out_matrix = ArrayBuffer.empty[BigInt]
      
//       for (i <- 0 until 5) {
//         for (j <- 0 until 5) {
//           val word = matrix(i*5+j)
//           state(i*5+j) = Bits(word,width=W)
//           vars(c.io.state_i(i*5+j)) = state(i*5+j)
//         }
//       }

//       val bc = Vec.fill(5){Bits(width = W)}
//       for(i <- 0 until 5) {
//         bc(i) := state(0*5+i) ^ state(1*5+i) ^ state(2*5+i) ^ state(3*5+i) ^ state(4*5+i)
//       }

//       for(i <- 0 until 5) {
//         val t = Bits(width = W)
//         t := bc((i+4)%5) ^ common.ROTL(bc((i+1)%5), UInt(1), UInt(W))
//         for(j <- 0 until 5) {
//           out_state(i*5+j) := state(i*5+j) ^ t
//           vars(c.io.state_o(i*5+j)) = out_state(i*5+j)
//         }
//       }
//       allGood = step(vars) && allGood
//       common.print_matrix(matrix)
//       //common.print_bigmatrix(out_matrix.toArray)
//     }
//     printf("Test passed: " + allGood + "\n")
//     allGood
//   }
// }