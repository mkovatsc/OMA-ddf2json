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

import ch.ethz.inf.vs.oma.ddf2json.ResourceSpec;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ResourceSpecSerializer implements JsonSerializer<ResourceSpec> {

    @Override
    public JsonElement serialize(ResourceSpec resource, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject element = new JsonObject();

        element.addProperty("id", resource.id);
        element.addProperty("name", resource.name);
        element.addProperty("operations", resource.operations.toString());
        element.addProperty("instancetype", resource.multiple ? "multiple" : "single");
        element.addProperty("mandatory", resource.mandatory);
        element.addProperty("type", resource.type.toString().toLowerCase());
        element.addProperty("range", resource.rangeEnumeration);
        element.addProperty("units", resource.units);
        element.addProperty("description", resource.description);

        return element;
    }
}
