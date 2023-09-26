package sha3

import chisel3._

object common {
    def ROTL(x: UInt, y: UInt, W: UInt) = (((x) << (y)) | ((x) >> (W - (y))))
}