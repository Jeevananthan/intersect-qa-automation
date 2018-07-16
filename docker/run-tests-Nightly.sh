#!/bin/bash

echo "Invoking CURL requests to run nightly tests"

echo "Calling BDD Tests for HE"
curl -H 'Authorization: '"$APIKey"'' https://turbo.hesos.net/v1/apps/26/buildjobs/4992/now/
echo "Calling BDD Tests for HS"
curl -H 'Authorization: '"$APIKey"'' https://turbo.hesos.net/v1/apps/26/buildjobs/4994/now/
echo "Calling BDD Tests for Support"
curl -H 'Authorization: '"$APIKey"'' https://turbo.hesos.net/v1/apps/26/buildjobs/4996/now/
echo "Calling BDD Tests for Community"
curl -H 'Authorization: '"$APIKey"'' https://turbo.hesos.net/v1/apps/26/buildjobs/4995/now/
echo "Calling BDD Tests for SuperMatch"
curl -H 'Authorization: '"$APIKey"'' https://turbo.hesos.net/v1/apps/26/buildjobs/5635/now/
echo "Calling BDD Tests for SuperMatch Embedded"
curl -H 'Authorization: '"$APIKey"'' https://turbo.hesos.net/v1/apps/26/buildjobs/6258/now/
echo "Calling BDD Tests for HUBS"
curl -H 'Authorization: '"$APIKey"'' https://turbo.hesos.net/v1/apps/26/buildjobs/4997/now/