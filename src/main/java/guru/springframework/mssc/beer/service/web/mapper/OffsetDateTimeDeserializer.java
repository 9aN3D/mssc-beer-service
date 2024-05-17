package guru.springframework.mssc.beer.service.web.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.OffsetDateTime;

public class OffsetDateTimeDeserializer extends StdDeserializer<OffsetDateTime> {

    public OffsetDateTimeDeserializer() {
        super(OffsetDateTime.class);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return OffsetDateTime.parse(jsonParser.readValueAs(String.class));
    }

}
