package guru.springframework.mssc.beer.service.infrastructure;

import guru.cfg.brewery.model.messages.Message;
import guru.springframework.mssc.beer.service.service.MessageHandler.MessageHandlerMethod;

public interface MessageDispatcher {

    <T extends Message> void registerHandler(Class<T> type, MessageHandlerMethod<T> handlerMethod);

    void dispatch(Message message);

}
