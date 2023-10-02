package sha3

import chisel3._

class Theta(val w: Int = 64) extends Module {
  val io = IO(new Bundle {
		val state_in    = Input(Vec(5*5, UInt(w.W)))
		val state_out   = Output(Vec(5*5, UInt(w.W)))
		// can be parameterized by 
		// val state_in = Vec(5*5, Bits(INPUT, width: Int = W))
		// val state_out = Vec(5*5, Bits(OUTPUT, width: Int = W))
	})

	val bc = Wire(Vec(5, UInt(w.W)))
	for(i <- 0 until 5) {
		bc(i) := io.state_in(i) ^ io.state_in(i+5) ^ io.state_in(i+10) ^ io.state_in(i+15) ^ io.state_in(i+20)
	}

	for(i <- 0 until 5) {
		val t = Wire(UInt(w.W))
		t := bc((i+4)%5) ^ common.ROTL(bc((i+1)%5), 1.U, w.U)
		for(j <- 0 until 5) {
				io.state_out(i+j*5) := io.state_in(i+j*5) ^ t
		}
	}
}