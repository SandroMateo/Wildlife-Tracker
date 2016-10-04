# Wildlife Tracker

#### Epicodus Java exercise to practice advanced topics

#### By Sandro Alvarez

## Description

This is a wildlife tracker app that allows rangers to keep track of animals.

## Specifications

Input Behavior | Input | Output |
---------------|-------|--------|
creates a new ranger| "Sandro", "qwerty", "sandro@sandro.com" | name: "Sandro", badge #: 1, contact: sandro@sandro.com |
creates an animal | "bird", "flying animal" | name: "bird", description: "flying animal"|
creates an endangered animal| "bear", "grizzly animal", "healthy", "adult" | name: "bear", description: "grizzly animal", health: "healthy", age: "adult" |
creates an animal sighting | "Zone A" | location: Zone A, Date: Tuesday October 4th 2016, animal: bear, ranger badge #1|

## Setup

* Clone this repository

* In PSQL:
  CREATE DATABASE wildlife_tracker;

* In Terminal:
  psql wildlife_tracker < media.sql

* Type gradle run in terminal and go to localhost:4567 in browser

## Technologies Used

* Java

* Gradle

* JUnit

* Spark

* Postgres

* SQL

* VelocityTemplateEngine

## Support

If you run into any problems, contact me at sandromateo22@gmail.com

### Legal

Copyright (c) 2016 Sandro Alvarez.

Licensed under the MIT license
