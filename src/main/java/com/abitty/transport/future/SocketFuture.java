package com.abitty.transport.future;

import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.listenable.ExecutionList;

import java.util.concurrent.*;

/**
 * @author litingpeng
 */
public class SocketFuture<T> implements ListenableFuture<T> {

    private int readTimeout;

    private TimeUnit timeUnit;

    private T result;

    private final CountDownLatch latch = new CountDownLatch(1);

    private final ExecutionList executionList = new ExecutionList();

    public SocketFuture(int readTimeout, TimeUnit timeUnit) {
        this.readTimeout = readTimeout;
        this.timeUnit = timeUnit;
    }

    @Override
    public void addListener(Runnable listener, Executor executor) {
        executionList.add(listener, executor);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        latch.countDown();
        runListeners();
        return true;
    }

    @Override
    public boolean isCancelled() {
        return latch.getCount() == 0 || result == null;
    }

    @Override
    public boolean isDone() {
        return latch.getCount() == 0;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        try {
            latch.await(readTimeout, timeUnit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        try {
            if (!latch.await(timeout, unit)) {
                throw new TimeoutException("Command timed out");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    protected void runListeners() {
        executionList.run();
    }

    public void setResultAndAwake(Object result){
        try{
            this.result = (T) result;
        } finally {
            latch.countDown();
            runListeners();
        }
    }
}
