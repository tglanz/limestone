## prerequisites

- [jdk 8](https://openjdk.java.net/install/)
- [maven](https://maven.apache.org/)

## project layout

#### core

provides core constructs to other software modules

#### tpc

provides ease access to tpc benchmarks using the core infrastructure

## general workflow

### tests

each module contain it's own unit tests.

run test by executing

    # all tests for all modules
    mvn test

    # core module's test
    mvn test core

    # run a specific suite
    mvn -Dtest=ExampleTest test

### package

package all modules

    # with tests
    mvn clean package

    # without tests
    mvn -DskipTests clean package

## Logging

we use [log4j](https://logging.apache.org/log4j/2.x/) for our logging api.

to configure log4j with a specific configuration file use the VMOption ```-Dlog4j.configuration.file=somepath```.

specifically, there is a default logging configuration file at ./logj4.xml  
    
##  Execution

this is java, and maven, the standard executions follows.

still, there are specific scripts in _sbin_, this is useful for documentation especially

- ./sbin/plan-query.sh
