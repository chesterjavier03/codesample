#!/bin/bash

curl -X POST -H \
"Content-Type: application/json" --data \
'{"title": "The Catcher in the Rye", "author": "J. D. Salinger", "year": "1951"}' \
http://localhost:8080/api/v1/books