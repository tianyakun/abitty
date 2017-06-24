package com.abitty.transport.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author litingpeng
 */
public class HttpFuture<T> implements Future<T> {
    private final com.ning.http.client.ListenableFuture<T> future;

    public static <U> HttpFuture<U> wrap(com.ning.http.client.ListenableFuture<U> future) {
        return new HttpFuture<U>(future);
    }

    private HttpFuture(com.ning.http.client.ListenableFuture<T> future) {
        this.future = future;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return future.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return future.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return future.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return future.get(timeout, unit);
    }

}
