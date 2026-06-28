@echo off
setlocal enabledelayedexpansion
set "SRC=D:\files\idea\algo-practice\review\weekly"
set "DST=%USERPROFILE%\Desktop"
copy "%SRC%\2026-06-22-*" "%DST%"
echo Done
