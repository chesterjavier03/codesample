#!/bin/bash

if [ -z "$1" ]
  then
    echo "Provide the id of the book to be deleted"
    exit 1
fi

curl -X DELETE http://localhost:8080/api/v1/books/$1
