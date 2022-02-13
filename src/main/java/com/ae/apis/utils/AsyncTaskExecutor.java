package com.ae.apis.utils;

import java.util.concurrent.ForkJoinPool;

public class AsyncTaskExecutor {
    private static final ForkJoinPool executor = ForkJoinPool.commonPool();

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
