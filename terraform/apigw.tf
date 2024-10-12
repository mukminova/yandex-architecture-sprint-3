resource "kubernetes_deployment" "kusk_api_gw" {
  metadata {
    name      = "kusk-api-gw"
    namespace = "default"
    labels = {
      app = "kusk-api-gw"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "kusk-api-gw"
      }
    }

    template {
      metadata {
        labels = {
          app = "kusk-api-gw"
        }
      }

      spec {
        container {
          name  = "kusk-api-gw"
          image = "kubeshop/kusk-api-gw:v1.0.0"
        }
      }
    }
  }
}

resource "kubernetes_service" "kusk_api_gw_svc" {
  metadata {
    name      = "smart-home-gw-svc"
    namespace = "default"
  }

  spec {
    selector = {
      app = "kusk-api-gw"
    }

    port {
      port        = 8080
      target_port = 8080
    }
  }
}
