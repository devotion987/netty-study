package com.devotion.netty.chapter1;

import java.util.concurrent.Future;

/**
 * Created by wugy on 2017/6/1.
 */
public interface Fetcher {
    Future<String> fetchData();
}
