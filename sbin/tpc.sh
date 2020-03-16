#!/bin/bash

# Load common utilities
script_dir="$(realpath $(dirname $0))"
. $script_dir/common.sh

relative_script_path="./$(realpath --relative-to="." "$0")"

# Default parameters
should_rebuild=false

usage="
usage: "$(basename $0)" [OPTIONS] -- [RUNNER-OPTIONS]

OPTIONS
  -h, --help         show this mesage

  -r, --rebuild      rebuild project
                     default: $should_rebuild

RUNNER-OPTIONS
  -q, --query-path  path to sql query
                    default: $query_path

  -b, --benchmark    benchmark to use
                     default: $benchmark

  -s, --scale        benchmark scale
                     default: $scale

EXAMPLES
  $relative_script_path --rebuild -- \\
      --benchmark ds \\
      --scale 1 \\
      --query-path ./tpc/src/main/resources/ds-queries/4.sql
"

while [[ $# -gt 0 ]]; do
    case $1 in
        --rebuild|-r)
            should_rebuild=true
            shift
            ;;
        --help|-h)
            echo "$usage"
            exit 0
            ;;
        --)
            shift
            break
            ;;
        *)
            error "unrecognized argument: $1. use --help for usage"
    esac
done

if [[ $should_rebuild == true ]]; then
    mvn clean package
fi

java -cp "tpc/target/*" org.tglanz.limestone.tpc.TpcRunner $@
