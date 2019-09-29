package server.handler;

import ru.otus.framework.RequestContext;
import ru.otus.framework.pipeline.handler.ChannelHandler;

public class PackagePayloadWithWorkerNameHandler implements ChannelHandler {
    @Override
    public Object handle(RequestContext ctx, Object message) {
        return ctx.getWorkerName() + ": echo: " + message;
    }
}
