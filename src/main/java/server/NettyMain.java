package server;

import ru.otus.framework.Router;
import ru.otus.framework.el.BossEventLoop;
import ru.otus.framework.el.EventLoop;
import ru.otus.framework.pipeline.ChannelPipeline;
import server.handler.InverseLetterCaseHandler;
import server.handler.PackagePayloadWithWorkerNameHandler;
import server.handler.ReplaceAWithHeyHandler;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyMain {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        ExecutorService bossExecutor = Executors.newSingleThreadExecutor();
//        ExecutorService workerExecutor = Executors.newFixedThreadPool(2);
//        ExecutorService workerExecutor = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());

        //todo add ServerBootstrap class for client simple configuration
        final ChannelPipeline payloadPipeline = new ChannelPipeline();
        payloadPipeline.addLast(new InverseLetterCaseHandler());
        payloadPipeline.addLast(new ReplaceAWithHeyHandler());
        payloadPipeline.addLast(new PackagePayloadWithWorkerNameHandler());
        Router payloadRouter = Router.route(payloadPipeline).path("/some");

        List<Router> routers = Collections.singletonList(payloadRouter);
        final EventLoop bossEventLoop = new BossEventLoop(PORT, 2, routers);
        bossExecutor.submit(bossEventLoop::go);
    }
}
