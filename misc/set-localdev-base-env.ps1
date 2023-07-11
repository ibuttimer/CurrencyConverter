# powershell script to set environment variables for development on localhost
$env:SERVICE_URL_DEFAULT_ZONE='http://localhost:8761/eureka'
$env:CONFIG_SERVER_URL='http://localhost:8888/'
$env:API_GATEWAY_URL='http://localhost:8765/'
$env:ZIPKIN_SERVER_URL='localhost:9411/'

$vars = @('SERVICE_URL_DEFAULT_ZONE', 'CONFIG_SERVER_URL', 'API_GATEWAY_URL', 'ZIPKIN_SERVER_URL')
Foreach ($e in $vars) {
    $val = Get-Childitem Env:$e
    Write-Output ("$($e) : $($val.value)")
}

