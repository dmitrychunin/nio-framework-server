package server;

import ru.otus.framework.el.BossEventLoop;
import ru.otus.framework.el.EventLoop;
import ru.otus.framework.pipeline.ChannelPipeline;
import server.handler.InverseLetterCaseHandler;
import server.handler.PackagePayloadWithWorkerNameHandler;
import server.handler.ReplaceAWithHeyHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyMain {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        ExecutorService bossExecutor = Executors.newSingleThreadExecutor();
//        ExecutorService workerExecutor = Executors.newFixedThreadPool(2);
//        ExecutorService workerExecutor = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());

        //todo add ServerBootstrap class for client simple configuration
        final ChannelPipeline pipeline = new ChannelPipeline();
        pipeline.addLast(new InverseLetterCaseHandler());
        pipeline.addLast(new ReplaceAWithHeyHandler());
        pipeline.addLast(new PackagePayloadWithWorkerNameHandler());
        final EventLoop bossEventLoop = new BossEventLoop(PORT, 2);
        bossExecutor.submit(() -> bossEventLoop.go(pipeline));
    }
}
