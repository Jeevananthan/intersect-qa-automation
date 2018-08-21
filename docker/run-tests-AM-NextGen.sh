#!/bin/bash

echo "Invoking Maven Install lifecycle"
echo "Profile: AM-NextGen-QA"
echo "Browser: GridDriver"

mvn -version
mvn install -P AM-NextGen-QA -Dbrowser=GridDriver
