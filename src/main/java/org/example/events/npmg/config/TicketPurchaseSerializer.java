package org.example.events.npmg.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.example.events.npmg.models.TicketPurchase;

import java.io.IOException;

public class TicketPurchaseSerializer extends StdSerializer<TicketPurchase> {

    public TicketPurchaseSerializer() {
        this(null);
    }

    public TicketPurchaseSerializer(Class<TicketPurchase> t) {
        super(t);
    }

    @Override
    public void serialize(TicketPurchase value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeNumber(value.getId());
        }
    }
}