package org.example.events.npmg.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.example.events.npmg.models.Ticket;

import java.io.IOException;

public class TicketSerializer extends StdSerializer<Ticket> {

    public TicketSerializer() {
        this(null);
    }

    public TicketSerializer(Class<Ticket> t) {
        super(t);
    }

    @Override
    public void serialize(Ticket value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeNumber(value.getId());
        }
    }
}