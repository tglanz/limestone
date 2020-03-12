#!/bin/bash

should_rebuild=false
model_path="src/test/resources/model.jsonx"
query_path="src/test/resources/query1.sql"

function error {
    echo -e "\e[31m[EROR]\e[0m $1"
    exit 1
}

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
        *)
            error "unrecognized argument: $1"
    esac
done

if [[ $should_rebuild == true ]]; then
    mvn clean package
fi

main_class="org.tglanz.limestone.runners.PlanQuery"

java \
    -Dlog4j.configuration.file=log4j.xml \
    -cp "target/*" \
    $main_class $model_path $query_path
