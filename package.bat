REM Package application for windows

call mkdir out\package
call copy out\artifacts\sheets_jar\sheets.jar out\package
call copy sheet.properties out\package
jpackage --name sheets --input out\package --app-version 0.1 ^
    --main-jar sheets.jar --file-associations sheet.properties ^
    --win-shortcut --win-menu