package sha3

import chisel3._
import scala.util.Random

object common {
    val rand     = new Random(2023)
    def generate_random_int(size: Int) = math.abs(rand.nextInt()) % (1 << size) //128
    def generate_test_matrix(size: Int) = Array.fill(5*5)(generate_random_int(size))
    def ROTL(x: UInt, y: UInt, W: UInt) = (((x) << (y)) | ((x) >> (W - (y))))
}