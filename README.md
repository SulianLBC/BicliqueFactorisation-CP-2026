# How to Build the Project

1. Clone the repository to your local machine.
2. Open a Terminal and run `mvn clean install -DskipTests` to build the project and skip tests. 
3. Navigate to `./parsers/target/` directory, a JAR file named [choco-solver-5.0.0-light.jar](parsers/target/choco-solver-5.0.0-light.jar) will be generated.

# How to Run the Project

## Parameters

The JAR file can be executed with the following parameters.
### JVM Parameters
- `-Dbcfa=bool` to enable biclique factorisation for the AllDifferent constraint.
- `-Dbcfc=bool` to enable biclique factorisation for the Cumulative constraint.
- `-DfailLimit=int` to set the fail limit for the search.

### Command Line

```bash
#!/usr/bin/env bash
# Classpath and main class for the JAR file
JAR="-cp .:./parsers/target/choco-solver-5.0.0-light.jar org.chocosolver.parser.flatzinc.ChocoFZN"
# JVM parameters, add more if needed
JARGS="-server -Xmx8g -Xss128m"
# Default paramaters for JSON output, modify as needed
PARAMS="-lcg -lvl JSON"
# FlatZinc instance file, replace with your own file path
FILE="parsers/src/test/resources/flatzinc/alpha/alpha.fzn"
export fname=$(basename "$FILE")
java ${JARGS} -Dbcfa=true -Dbcfc=true $JAR $FILE $PARAMS > "./"${fname}.json 2> "./"${fname}.err
```
