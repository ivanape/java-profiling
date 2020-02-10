# Java Profiling Tools

All required tools are downloaded on `src` directory. Oracle JDK 8 is required, you must get an installer from Oracle web page (a RPM installer) and change this line `sudo rpm -U /vagrant/temp/jdk-8u241-linux-x64.rpm` in `Vagranfile` before you create VM. 

## javaFlameGraph

Run `gs-rest-service` demo application with following options:

- `java -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -jar gs-rest-service-0.1.0.jar`

Generate flame graph with javaFlameGraph tool.

- `./flame-gen.sh <PID>`

You can get your Java application PID using `jps` command.

## jfr-report-tool

Oracle JDK 8 is required. OpenJDK 8 does not include Java Flight Recorder.

Run `gs-rest-service` demo application with following options:

- `java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -XX:FlightRecorderOptions=stackdepth=1024 -jar gs-rest-service-0.1.0.jar`

Now generate a new JFR recording with `jcmd` command.

- `jcmd <PID> JFR.start name=myrecording settings=profile`
- `jcmd <PID> JFR.dump name=myrecording filename=$PWD/mydump.jfr`
- `jcmd <PID> JFR.stop name=myrecording`

Generate flame graph with jfr-report-tool.

- `./jfr-report-tool -e none mydump.jfr`

## perf and perf-map-agent

Run `gs-rest-service` demo application with following options:

- `java -XX:+PreserveFramePointer -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -jar gs-rest-service-0.1.0.jar`
- `./create-java-perf-map.sh <PID>`
- `perf record -F 99 -p <PID> -g`
- `perf script > out.perf`
- `./FlameGraph/stackcollapse-perf.pl out.perf > out.folded`
- `./FlameGraph/flamegraph.pl --color=java out.folded > graph.svg`

