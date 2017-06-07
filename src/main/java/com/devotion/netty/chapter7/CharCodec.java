package com.devotion.netty.chapter7;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * Created by lenovo on 2017/6/7 11:23
 */
public class CharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {

    public CharCodec() {
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
