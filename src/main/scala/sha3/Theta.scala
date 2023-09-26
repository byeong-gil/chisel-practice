package sha3

import chisel3._

class Theta extends Module {
    val io = IO(new Bundle {
        val state_in    = Input(Vec(5*5, 64.W))
        val state_out   = Output(Vec(5*5, 64.W))
        // can be parameterized by 
        // val state_in = Vec(5*5, Bits(INPUT, width = W))
        // val state_out = Vec(5*5, Bits(OUTPUT, width = W))
    })

    val bc = Vec.fill(5){Wire(64.W)}
    for(i <- 0 until 5) {
        bc(i) := io.state_in(i*5+0) ^ io.state_in(i*5+1) ^ io.state_in(i*5+2) ^ io.state_in(i*5+3) ^ io.state_in(i*5+4)
    }

    for(i <- 0 until 5) {
        val t = Wire(64.W)
        t := bc((i+4)%5) ^ ROTL(bc((i+1)%5, i))
        for(j <- 0 until 5) {
            io.state_out(i*5+j) = io.state_in(i*5+j) ^ t
        }
    }
}