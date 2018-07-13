#!/bin/bash

echo "Invoking CURL requests to run nightly tests"

#Call BDD Tests for SuperMatch
curl -H "Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJoZXNvczpIZXNvcy1UdXJibyIsImlhdCI6MTUzMTUwMDM2MiwianRpIjoiMjM5Nzc2ZGE3ZjE3YmEwMiIsInN1YiI6ImJiYXJ0aXplayJ9.DsUBM2d5WcnyQ1irR8XgTtKxD0BH8ekTsNIXMwHsGH8" https://turbo.hesos.net/v1/apps/26/buildjobs/5635/now/
#Call BDD Tests for SuperMatch Embedded
curl -H "Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJoZXNvczpIZXNvcy1UdXJibyIsImlhdCI6MTUzMTUwMDM2MiwianRpIjoiMjM5Nzc2ZGE3ZjE3YmEwMiIsInN1YiI6ImJiYXJ0aXplayJ9.DsUBM2d5WcnyQ1irR8XgTtKxD0BH8ekTsNIXMwHsGH8" https://turbo.hesos.net/v1/apps/26/buildjobs/6258/now/
