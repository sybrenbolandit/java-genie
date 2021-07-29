package @packageName@;

import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import javax.inject.Singleton;

@Singleton
public class ServerBuilderListener implements BeanCreatedEventListener<ServerBuilder<?>> {

	@Override
	public ServerBuilder<?> onCreated(BeanCreatedEvent<ServerBuilder<?>> event) {
		final ServerBuilder<?> builder = event.getBean();
		builder.addService(ProtoReflectionService.newInstance());
		return builder;
	}
}
