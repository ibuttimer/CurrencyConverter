# powershell script to set environment variables for development on localhost
$env:NAMING_PORT=8761
$env:CONFIG_PORT=8888
$env:API_PORT=8765
$env:ZIPKIN_PORT=9411
$env:EXCHANGE_PORT=8000
$env:CONVERT_PORT=8100

$env:SERVICE_URL_DEFAULT_ZONE='http://localhost:'+$env:NAMING_PORT+'/eureka'
$env:CONFIG_SERVER_URL='http://localhost:'+$env:CONFIG_PORT+'/'
$env:API_GATEWAY_URL='http://localhost:'+$env:API_PORT+'/'
$env:ZIPKIN_SERVER_URL='http://localhost:'+$env:ZIPKIN_PORT+'/api/v2/spans'

$vars = @('SERVICE_URL_DEFAULT_ZONE', 'CONFIG_SERVER_URL', 'API_GATEWAY_URL', 'ZIPKIN_SERVER_URL', 'EXCHANGE_PORT', 'CONVERT_PORT')
Foreach ($e in $vars) {
    $val = Get-Childitem Env:$e
    Write-Output ("$($e) : $($val.value)")
}

