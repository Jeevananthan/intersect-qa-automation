#!/bin/bash

echo "Invoking Maven Install lifecycle"
echo "Profile: HS-QA"
echo "Browser: GridDriver"

mvn -version
mvn install -P HS-QA -Dbrowser=GridDriver
