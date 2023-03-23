package com.reify.common.validation;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class JsonValidator {

    public static Set<ValidationMessage> validateJson(String inputJson, String schemaName) throws IOException {

        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance( SpecVersion.VersionFlag.V7);

        InputStream inputStream = new ClassPathResource(
                "jsonSchema/" + schemaName).getInputStream();

        JsonSchema jsonSchema = schemaFactory.getSchema(inputStream);

        ObjectMapper map = new ObjectMapper();
        JsonNode jsonNode = map.readTree(inputJson);

        Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);

        return errors;
    }
}
