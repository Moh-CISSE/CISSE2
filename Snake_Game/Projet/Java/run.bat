@echo off
cd /d %~dp0

echo =========================
echo     SNAKE GAME
echo =========================
echo.
echo Lancement du jeu...
echo.

gradlew.bat lwjgl3:run

pause
