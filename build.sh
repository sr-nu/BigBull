android update project --name BigBull --path ../ -s
android update test-project -m ../ -p ./test/
cd test
ant run-tests
#ant debug
#adb install ./bin/BigBull-debug.apk
#cd test
#ant debug
#adb install ./bin/BigBullTest-debug.apk
