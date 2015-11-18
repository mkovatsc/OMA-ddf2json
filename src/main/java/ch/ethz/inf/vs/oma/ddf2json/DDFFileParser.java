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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import ch.ethz.inf.vs.oma.ddf2json.ResourceSpec.Operations;
import ch.ethz.inf.vs.oma.ddf2json.ResourceSpec.Type;

/**
 * A parser for Object DDF files.
 */
public class DDFFileParser {

    private final DocumentBuilderFactory factory;

    public DDFFileParser() {
        factory = DocumentBuilderFactory.newInstance();
    }

    public ObjectSpec parse(File ddfFile) {
        System.out.println("Parsing: " + ddfFile.getName());

        ObjectSpec result = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(ddfFile);

            Node object = document.getDocumentElement().getElementsByTagName("Object").item(0);
            result = this.parseObject(object);

        } catch (SAXException | IOException | ParserConfigurationException e) {
            System.err.println("Error: Could not parse the resource definition file " + ddfFile.getName() + ": " + e.getMessage());
        }

        return result;
    }

    private ObjectSpec parseObject(Node object) {

        Integer id = null;
        String name = null;
        String description = null;
        boolean multiple = false;
        boolean mandatory = false;
        Map<Integer, ResourceSpec> resources = new HashMap<>();

        for (int i = 0; i < object.getChildNodes().getLength(); i++) {
            Node field = object.getChildNodes().item(i);
            switch (field.getNodeName()) {
            case "ObjectID":
                id = Integer.valueOf(field.getTextContent());
                break;
            case "Name":
                name = field.getTextContent();
                break;
            case "Description1":
                description = field.getTextContent();
                break;
            case "MultipleInstances":
                multiple = "Multiple".equals(field.getTextContent());
                break;
            case "Mandatory":
                mandatory = "Mandatory".equals(field.getTextContent());
                break;
            case "Resources":
                for (int j = 0; j < field.getChildNodes().getLength(); j++) {
                    Node item = field.getChildNodes().item(j);
                    if (item.getNodeName().equals("Item")) {
                        ResourceSpec res = this.parseResource(item);
                        resources.put(res.id, res);
                    }
                }
                break;
            }
        }

        return new ObjectSpec(id, name, description, multiple, mandatory, resources);

    }

    private ResourceSpec parseResource(Node item) {

        Integer id = Integer.valueOf(item.getAttributes().getNamedItem("ID").getTextContent());
        String name = null;
        Operations operations = Operations.NONE;
        boolean multiple = false;
        boolean mandatory = false;
        Type type = Type.STRING;
        String rangeEnumeration = null;
        String units = null;
        String description = null;

        for (int i = 0; i < item.getChildNodes().getLength(); i++) {
            Node field = item.getChildNodes().item(i);
            switch (field.getNodeName()) {
            case "Name":
                name = field.getTextContent();
                break;
            case "Operations":
                String strOp = field.getTextContent();
                if (strOp != null && !strOp.isEmpty()) {
                    operations = Operations.valueOf(strOp);
                }
                break;
            case "MultipleInstances":
                multiple = "Multiple".equals(field.getTextContent());
                break;
            case "Mandatory":
                mandatory = "Mandatory".equals(field.getTextContent());
                break;
            case "Type":
                switch (field.getTextContent()) {
                case "String":
                    type = Type.STRING;
                    break;
                case "Integer":
                    type = Type.INTEGER;
                    break;
                case "Float":
                    type = Type.FLOAT;
                    break;
                case "Boolean":
                    type = Type.BOOLEAN;
                    break;
                case "Opaque":
                    type = Type.OPAQUE;
                    break;
                case "Time":
                    type = Type.TIME;
                    break;
                }
                break;
            case "RangeEnumeration":
                rangeEnumeration = field.getTextContent();
                break;
            case "Units":
                units = field.getTextContent();
                break;
            case "Description":
                description = field.getTextContent();
                break;
            }

        }

        return new ResourceSpec(id, name, operations, multiple, mandatory, type, rangeEnumeration, units, description);
    }

}
