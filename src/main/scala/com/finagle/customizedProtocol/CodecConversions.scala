package com.finagle.customizedProtocol

import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}
import org.jboss.netty.channel.{Channel, ChannelHandlerContext}
import org.jboss.netty.handler.codec.oneone.{OneToOneDecoder, OneToOneEncoder}
import scodec.Codec
import scodec.bits.BitVector

trait CodecConversions {
  /**
    * Converts an scodec codec into a Netty encoder.
    */
  protected def encoder[A: Codec] = new OneToOneEncoder {
    override def encode(ctx: ChannelHandlerContext, channel: Channel, msg: Object) =
      ChannelBuffers.wrappedBuffer(
        Codec.encodeValid(msg.asInstanceOf[A]).toByteBuffer
      )
  }

  /**
    * Converts an scodec codec into a Netty decoder.
    */
  protected def decoder[A: Codec] = new OneToOneDecoder {
    override def decode(ctx: ChannelHandlerContext, channel: Channel, msg: Object) =
      msg match {
        case cb: ChannelBuffer =>
          Codec.decodeValidValue[A](BitVector(cb.toByteBuffer)).asInstanceOf[Object]
        case other => other
      }
  }
}
