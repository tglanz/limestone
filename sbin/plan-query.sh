#!/bin/bash

# Load common utilities
script_dir="$(realpath $(dirname $0))"
. $script_dir/common.sh

relative_script_path="./$(realpath --relative-to="." "$0")"

# Default parameters
should_rebuild=false
model_path="core/src/test/resources/model.jsonx"
query_path="core/src/test/resources/query4.sql"

usage="
usage: "$(basename $0)" [OPTIONS]

OPTIONS
  -h, --help         show this mesage

  -r, --rebuild      rebuild project
                     default: $should_rebuild

  -m, --model-path   path to model file
                     default: $model_path

  -q, --query-path   path to sql query
                     default: $query_path

EXAMPLES
  $relative_script_path --rebuild --query-path ./core/src/test/resources/query3.sql
  $relative_script_path -q ./core/src/test/resources/query2.sql
"

while [[ $# -gt 0 ]]; do
    case $1 in
        --rebuild|-r)
            should_rebuild=true
            shift
            ;;
        --model-path|-m)
            model_path=$2
            shift
            shift
            ;;
        --query-path|-q)
            query_path=$2
            shift
            shift
            ;;
        --help|-h)
            echo "$usage"
            exit 0
            ;;
        *)
            error "unrecognized argument: $1. use --help for usage"
    esac
done

if [[ $should_rebuild == true ]]; then
    mvn clean package
fi

main_class="org.tglanz.limestone.core.runners.PlanQuery"

java \
    -Dlog4j.configuration.file=log4j.xml \
    -cp "target/*:core/target/*" \
    $main_class $model_path $query_path
