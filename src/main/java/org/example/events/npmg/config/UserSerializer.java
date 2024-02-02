package org.example.events.npmg.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.example.events.npmg.models.User;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {
	public UserSerializer() {
		this(null);
	}

	public UserSerializer(Class<User> t) {
		super(t);
	}

	@Override
	public void serialize(User value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if (value == null) {
			gen.writeNull();
		} else {
			gen.writeNumber(value.getId());
		}
	}
}
