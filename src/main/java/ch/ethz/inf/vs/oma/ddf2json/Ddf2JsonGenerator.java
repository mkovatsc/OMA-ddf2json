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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import ch.ethz.inf.vs.oma.ddf2json.json.ObjectSpecSerializer;
import ch.ethz.inf.vs.oma.ddf2json.json.ResourceSpecSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Ddf2JsonGenerator {

    private Gson gson;

    public Ddf2JsonGenerator() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ObjectSpec.class, new ObjectSpecSerializer());
        gsonBuilder.registerTypeAdapter(ResourceSpec.class, new ResourceSpecSerializer());
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    private void generate(Map<Integer, ObjectSpec> objectSpecs, OutputStream output) throws IOException {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(output)) {
            gson.toJson(objectSpecs, outputStreamWriter);
        }
    }

    private void generate(File input, OutputStream output) throws IOException {
        // check input exists
        if (!input.exists())
            throw new FileNotFoundException(input.toString());

        // get input files.
        File[] files;
        if (input.isDirectory()) {
            files = input.listFiles();
        } else {
            files = new File[] { input };
        }

        // parse DDF file
        SortedMap<Integer, ObjectSpec> objectSpecs = new TreeMap<Integer, ObjectSpec>();
        DDFFileParser ddfParser = new DDFFileParser();
        for (File f : files) {
            if (f.canRead()) {
                ObjectSpec objectSpec = ddfParser.parse(f);
                if (objectSpec != null) {
                    objectSpecs.put(objectSpec.id, objectSpec);
                }
            }
        }

        // generate json
        generate(objectSpecs, output);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // default value
        String inputPath = "ddf";
        String outputFilename = "objects.json";

		System.out.println("OMA DDF to JSON Converter");
		System.out.println("(c) 2015 Sierra Wireless and Institute for Pervasive Computing, ETH Zurich");
		System.out.println("-------------------------");
        
        // use arguments if they exit
        if (args.length >= 1)
            inputPath = args[0]; // the path to a DDF file or a folder which contains DDF files.
        if (args.length >= 2)
            outputFilename = args[1]; // the path of the output file.

        System.out.println("Input:   " + inputPath);
        System.out.println("Output:  " + outputFilename);
        System.out.println("-------------------------");

        // generate object spec file
        Ddf2JsonGenerator ddfJsonGenerator = new Ddf2JsonGenerator();
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilename)) {
            ddfJsonGenerator.generate(new File(inputPath), fileOutputStream);
        } catch (FileNotFoundException e) {
        	System.err.println("Error: Could not find " + e.getMessage());
        }
    }
}
