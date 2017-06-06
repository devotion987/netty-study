package com.devotion.netty.chapter6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by wugy on 2017/6/6 13:27
 */
@ChannelHandler.Sharable
public class NotSharableHandler extends ChannelInboundHandlerAdapter {

    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead(...) called the " + count + " time");
        ctx.fireChannelRead(msg);
    }
}
