package com.devotion.netty.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by lenovo on 2017/6/7 16:12
 */
public class HttpAggregatorInitializer2 extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpAggregatorInitializer2(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("codec", client ? new HttpClientCodec() : new HttpServerCodec())
                .addLast("decompressor", new HttpContentDecompressor());
    }
}
