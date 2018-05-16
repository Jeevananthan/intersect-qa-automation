#!/bin/bash

echo "Invoking Maven Install lifecycle"
echo "Profile: SM-QA"
echo "Browser: GridDriver"

mvn -version
mvn install -P SM-FamilyConnection -Dbrowser=GridDriver
