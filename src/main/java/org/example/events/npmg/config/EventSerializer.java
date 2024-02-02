package org.example.events.npmg.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.events.npmg.models.Event;

import java.io.IOException;

public class EventSerializer extends JsonSerializer<Event> {
    @Override
    public void serialize(Event value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeNumber(value.getId());
        }
    }
}