#!/bin/bash

echo "Invoking Maven Install lifecycle"
echo "Profile: SM-FamilyConnection"
echo "Browser: GridDriver"

mvn -version
mvn -DSuperMatchEnv=FamilyConnection -P SM-FamilyConnection -Dbrowser=GridDriver install
