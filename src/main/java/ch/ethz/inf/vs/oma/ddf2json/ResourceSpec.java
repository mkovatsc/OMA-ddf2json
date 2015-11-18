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
package ch.ethz.inf.vs.oma.ddf2json;

/**
 * A resource description
 */
public class ResourceSpec {

    public enum Operations {
        NONE, R, W, RW, E, RE, WE, RWE;

        public String toString() {
            if (this == Operations.NONE) {
                // DevKit uses .indexOf('R') etc. on string to check Operations
                return "-";
            } else {
                return this.name();
            }
        }
    }

    public enum Type {
        STRING, INTEGER, FLOAT, BOOLEAN, OPAQUE, TIME
    }

    public final int id;
    public final String name;
    public final Operations operations;
    public final boolean multiple;
    public final boolean mandatory;
    public final Type type;
    public final String rangeEnumeration;
    public final String units;
    public final String description;

    public ResourceSpec(int id, String name, Operations operations, boolean multiple, boolean mandatory, Type type,
            String rangeEnumeration, String units, String description) {
        this.id = id;
        this.name = name;
        this.operations = operations;
        this.multiple = multiple;
        this.mandatory = mandatory;
        this.type = type;
        this.rangeEnumeration = rangeEnumeration;
        this.units = units;
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResourceDesc [id=").append(id).append(", name=").append(name).append(", operations=")
                .append(operations).append(", multiple=").append(multiple).append(", mandatory=").append(mandatory)
                .append(", type=").append(type).append(", rangeEnumeration=").append(rangeEnumeration)
                .append(", units=").append(units).append(", description=").append(description).append("]");
        return builder.toString();
    }
}
