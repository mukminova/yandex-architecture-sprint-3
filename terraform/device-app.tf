resource "helm_release" "device-app" {
  name       = "device-app"
  namespace  = "default"
  chart      = "../charts/device-app"
}