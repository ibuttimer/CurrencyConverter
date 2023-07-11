# powershell script to set environment variables for development on localhost
Invoke-Expression ../misc/set-localdev-base-env.ps1

# See currency-conversion-service/README.MD#"MySQL connection string" for information
# regarding the MySQL connection string
$env:MYSQL_CONVERSION_URL='//localhost:3306/currency_conversion?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC'
$env:CURRENCY_EXCHANGE_URL='http://localhost:8000'
# Enable/disable zipkin; set to true/false
$env:TRACING_ENABLED='false'

$vars = @('MYSQL_CONVERSION_URL', 'CURRENCY_EXCHANGE_URL', 'TRACING_ENABLED')
Foreach ($e in $vars) {
    $val = Get-Childitem Env:$e
    Write-Output ("$($e) : $($val.value)")
}
