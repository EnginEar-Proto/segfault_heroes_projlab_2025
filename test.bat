@echo off
setlocal EnableDelayedExpansion

:: Tesztek betoltese
set /a index=0
for %%f in (*_in.txt) do (
    set "test[!index!]=%%f"
    set /a index+=1
)
set /a total=!index!

:: Forditas
echo Compiling Java files...
javac -d out *.java
if errorlevel 1 (
    echo Compilation failed.
    exit /b
)

:: Fomenuciklus
:menu
echo.
echo ====== TEST RUNNER ======
echo Available tests:
for /L %%i in (0,1,%total%) do (
    if defined test[%%i] (
        set /a displayIndex=%%i+1
        echo !displayIndex!: !test[%%i]!
    )
)

set /p choice=Type a test number, 'all' to run all, or 'exit' to quit:

if /I "%choice%"=="exit" goto :eof

if /I "%choice%"=="all" goto run_all

:: Ellenőrzés + 1-el csökkentés
set /a choiceIndex=%choice%-1

if not defined test[%choiceIndex%] (
    echo Invalid selection.
    goto menu
)

call :run_single %choiceIndex%
goto menu

:: Egy teszt futtatasa
:run_single
set "infile=!test[%1]!"
set "basename=!infile:_in.txt=!"
set "expectedfile=!basename!_out.txt"
set "tempfile=!basename!_temp_out.txt"

echo Running test: !basename!
java -cp out Main !infile! !tempfile!

fc /W "!tempfile!" "!expectedfile!" > nul
if errorlevel 1 (
    echo [FAIL] Test failed: !basename!
) else (
    echo [PASS] Test passed: !basename!
)

del "!tempfile!" > nul 2>&1
goto :eof

:: Osszes teszt futtatasa
:run_all
for /L %%i in (0,1,%total%) do (
    if defined test[%%i] (
        call :run_single %%i
        echo.
    )
)
goto menu
