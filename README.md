# OMA-ddf2json

A command line tool that converts OMA XML Object definitions to JSON

## About
The project is based on code by the [Leshan](https://www.eclipse.org/leshan) project. It was changed to function as standalone tool and export JSON definitions compatible with the [OMA LWM2M DevKit](https://github.com/OpenMobileAlliance/OMA-LWM2M-DevKit).

If you are interested in harmonizing (and eventually standardizing) the JSON structure, for instance to use the same format for the DevKit, Leshan Web frontend, and other LWM2M Client or Server configuration, please contact [Matthias Kovatsch](mailto:kovatsch@inf.ethz.ch).

## Usage
Build the ddf2json JAR file:
```
> mvn clean install
```
Optionally move JAR to convenient location and run as follows:
```
> java -jar ddf2json.jar [input directory with DDF files] [output JSON file]
```
The input and output arguments are optional and default to `ddf` and `objects.json`.
