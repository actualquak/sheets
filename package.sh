#!/usr/bin/env bash

# Package application

mkdir -p out/package
cp out/artifacts/sheets_jar/sheets.jar out/package
cp sheet.properties out/package
jpackage --name sheets --input out/package --app-version 0.1 \
  --main-jar sheets.jar --file-associations sheet.properties