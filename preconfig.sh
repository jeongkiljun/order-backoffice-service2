#!/bin/sh

export SPRING_PROFILES_ACTIVE='dev,disable-discovery'
./gradlew clean $SERVICE_NAME:build --build-cache --info
