/*******************************************************************************
 * Copyright (c) 2015 Sierra Wireless and Institute for Pervasive Computing, ETH Zurich.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sierra Wireless - initial API and implementation
 *     Matthias Kovatsch - standalone usage
 *******************************************************************************/
package ch.ethz.inf.vs.oma.ddf2json.json;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import ch.ethz.inf.vs.oma.ddf2json.ObjectSpec;
import ch.ethz.inf.vs.oma.ddf2json.ResourceSpec;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ObjectSpecDeserializer implements JsonDeserializer<ObjectSpec> {

    @Override
    public ObjectSpec deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json == null)
            return null;

        if (!json.isJsonObject())
            return null;

        JsonObject jsonObject = json.getAsJsonObject();
        if (!jsonObject.has("id"))
            return null;

        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();
        String instancetype = jsonObject.get("instancetype").getAsString();
        boolean mandatory = jsonObject.get("mandatory").getAsBoolean();
        String description = jsonObject.get("description").getAsString();
        ResourceSpec[] resourceSpecs = context.deserialize(jsonObject.get("resourcedefs"), ResourceSpec[].class);
        Map<Integer, ResourceSpec> resources = new HashMap<Integer, ResourceSpec>();
        for (ResourceSpec resourceSpec : resourceSpecs) {
            resources.put(resourceSpec.id, resourceSpec);
        }

        return new ObjectSpec(id, name, description, "multiple".equals(instancetype), mandatory, resources);
    }
}
