resource "helm_release" "telemetry-app" {
  name       = "telemetry-app"
  namespace  = "default"
  chart      = "../charts/telemetry-app"
}