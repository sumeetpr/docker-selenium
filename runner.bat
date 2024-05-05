@echo off
REM This script expects the following environment variables:
REM    HUB_HOST
REM    BROWSER
REM    THREAD_COUNT
REM    TEST_SUITE

REM Let's print what we have received
echo -------------------------------------------
echo HUB_HOST      : %HUB_HOST:~0,4%
echo BROWSER       : %BROWSER:~0,7%
echo THREAD_COUNT  : %THREAD_COUNT%
echo TEST_SUITE    : %TEST_SUITE%
echo -------------------------------------------

REM Do not start the tests immediately. Hub has to be ready with browser nodes
echo Checking if hub is ready..!
set count=0
:wait_hub_ready
curl -s http://%HUB_HOST%:4444/status | jq -r .value.ready > temp.txt
set /p ready=<temp.txt
del temp.txt
if "%ready%"=="true" (
  goto hub_ready
) else (
  set /a count+=1
  echo Attempt: %count%
  if %count% geq 30 (
    echo **** HUB IS NOT READY WITHIN 30 SECONDS ****
    exit /b 1
  )
  timeout /t 1 >nul
  goto wait_hub_ready
)

:hub_ready
REM At this point, Selenium Grid should be up!
echo Selenium Grid is up and running. Running the test....

REM Start the java command
java -cp "libs/*" -Dselenium.grid.enabled=true -Dselenium.grid.hubHost="%HUB_HOST%" -Dbrowser="%BROWSER%" org.testng.TestNG -threadcount %THREAD_COUNT% test-suites\%TEST_SUITE%
