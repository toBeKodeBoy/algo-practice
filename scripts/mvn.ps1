param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$MvnArgs
)

$originalJavaHome = $env:JAVA_HOME
$env:JAVA_HOME = "D:\develop\Java\jdk17"

try {
    & "mvn" @MvnArgs
    $exitCode = $LASTEXITCODE
} finally {
    $env:JAVA_HOME = $originalJavaHome
}

exit $exitCode
