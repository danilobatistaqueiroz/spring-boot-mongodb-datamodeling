#!/usr/bin/env bash

mongosh -u root -p 123456 --host mongodb --eval "db = db.getSiblingDB('NerdStoreDb');db.createUser({user: 'nerd', pwd: 'nerd', roles: ['readWrite']});" admin

mongoimport --authenticationDatabase=admin --username root --password 123456 --host mongodb --db NerdStoreDb --collection product --type json --file /mongo/seeds/product.json --jsonArray
mongoimport --authenticationDatabase=admin --username root --password 123456 --host mongodb --db NerdStoreDb --collection category --type json --file /mongo/seeds/category.json --jsonArray

#mongoimport --authenticationDatabase=admin --uri "mongodb://root:123456@mongo/NerdStoreDb:27017?authSource=admin" --collection product --file /mongo/seeds/product.json --jsonArray