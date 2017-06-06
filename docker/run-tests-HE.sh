#!/bin/bash

if [ -z "$ENVIRONMENT" ]; then
    ENVIRONMENT="qaEnvHE"
fi

if [ -z "$BROWSER" ]; then
    BROWSER="GridDriver"
fi

echo "Environment: $ENVIRONMENT"
echo "Browser: $BROWSER"

if [ -z "$TAGS" ]; then
    mvn install -Denvironment="$ENVIRONMENT" -Dbrowser="$BROWSER" --activate-profiles HE QA
else
    echo "Tags: $TAGS"
    mvn install -Denvironment="$ENVIRONMENT" -Dbrowser="$BROWSER" -Dtags="$TAGS" --activate-profiles HE QA
fi


