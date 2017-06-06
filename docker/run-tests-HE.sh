#!/bin/bash

echo "Invoking Maven Install lifecycle"
echo "Profile: HE-QA"
echo "Browser: GridDriver"

mvn -version
mvn install -P HE-QA -Dbrowser=GridDriver
