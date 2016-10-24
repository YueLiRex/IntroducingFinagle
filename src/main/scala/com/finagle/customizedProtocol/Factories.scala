package com.finagle.customizedProtocol

import com.twitter.finagle.{Codec => FinagleCodec, CodecFactory}
import org.jboss.netty.channel.{ChannelPipelineFactory, Channels}
import scodec.Codec

trait Factories {
  this: CodecConversions =>

  /**
    * Creates a Netty channel pipeline factory given input and output types.
    */
  private[this] def pipeline[I: Codec, O: Codec] = new ChannelPipelineFactory {
    def getPipeline = {
      val pipeline = Channels.pipeline()
      pipeline.addLast("encoder", encoder[I])
      pipeline.addLast("decoder", decoder[O])
      pipeline
    }
  }

  /**
    * Creates a Finagle codec factory given input and output types.
    */
  protected def codecFactory[I: Codec, O: Codec] = new CodecFactory[I, O] {
    def server = Function.const {
      new FinagleCodec[I, O] {
        def pipelineFactory = pipeline[O, I]
      }
    }

    def client = Function.const {
      new FinagleCodec[I, O] {
        def pipelineFactory = pipeline[I, O]
      }
    }
  }
}