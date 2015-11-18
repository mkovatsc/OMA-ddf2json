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

import ch.ethz.inf.vs.oma.ddf2json.ObjectSpec;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ObjectSpecSerializer implements JsonSerializer<ObjectSpec> {

    @Override
    public JsonElement serialize(ObjectSpec object, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject element = new JsonObject();

        // sort resources value
        // List<ResourceSpec> resourceSpecs = new ArrayList<ResourceSpec>(object.resources.values());
        // Collections.sort(resourceSpecs, new Comparator<ResourceSpec>() {
        // @Override
        // public int compare(ResourceSpec r1, ResourceSpec r2) {
        // return r1.id - r2.id;
        // }
        // });

        // serialize fields
        element.addProperty("id", object.id);
        element.addProperty("name", object.name);
        element.addProperty("instancetype", object.multiple ? "multiple" : "single");
        element.addProperty("mandatory", object.mandatory);
        element.addProperty("description", object.description);
        element.add("resourcedefs", context.serialize(object.resources));

        return element;
    }

}
