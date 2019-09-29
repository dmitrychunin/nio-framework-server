package server.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.otus.framework.RequestContext;
import ru.otus.framework.pipeline.handler.ChannelHandler;

@Slf4j
public class InverseLetterCaseHandler implements ChannelHandler {
    @Override
    public Object handle(RequestContext ctx, Object message) {
        log.info("{}: before inverse: {} ", ctx.getWorkerName(), message);
        message = StringUtils.swapCase((String) message);
        log.info("{}: after inverse: {}", ctx.getWorkerName(), message);
        return message;
    }
}
