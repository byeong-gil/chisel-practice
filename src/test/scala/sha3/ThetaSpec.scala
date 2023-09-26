package sha3

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

class ThetaSpec extends AnyFreeSpec with ChiselScalatestTester {

  "Theta test" in {
    test(new Theta(64)) { dut =>
      dut.input.initSource()
      dut.input.setSourceClock(dut.clock)
      dut.output.initSink()
      dut.output.setSinkClock(dut.clock)

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