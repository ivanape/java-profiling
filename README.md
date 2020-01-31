# java-profiling

## javaFlameGraph

- `java -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -jar gs-rest-service-0.1.0.jar`
- `./flame-gen.sh <PID>`

## jfr-report-tool
- `java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -XX:FlightRecorderOptions=stackdepth=1024 -jar gs-rest-service-0.1.0.jar`
- `jcmd <PID> JFR.start name=myrecording settings=profile`
- `jcmd <PID> JFR.dump name=myrecording filename=$PWD/mydump.jfr`
- `jcmd <PID> JFR.stop name=myrecording`
- `./jfr-report-tool -e none mydump.jfr`

## perf and perf-map-agent

- `java -XX:+PreserveFramePointer -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -jar gs-rest-service-0.1.0.jar`
- `bin/create-java-perf-map.sh <PID>`
- perf record -F 99 -p <PID> -g
- perf script > out.perf
- ./FlameGraph/stackcollapse-perf.pl out.perf > out.folded
- ./FlameGraph/flamegraph.pl --color=java out.folded > graph.svg

