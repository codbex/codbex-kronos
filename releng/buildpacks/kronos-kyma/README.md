## Kronos Kyma Buildpack

1. Set the Kronos version:
    > Replace the `#{KronosVersion}#` placeholder (e.g. `latest`, `0.7.1`, `1.0.0`) in `buildpack/*.toml` files.

1. Build `Kronos Kyma Stack`:

    ```
    docker build -t ghcr.io/codbex/kronos-buildpacks-stack-base-kyma . --target base
    docker push ghcr.io/codbex/kronos-buildpacks-stack-base-kyma

    docker build -t ghcr.io/codbex/kronos-buildpacks-stack-run-kyma . --target run
    docker push ghcr.io/codbex/kronos-buildpacks-stack-run-kyma

    docker build -t ghcr.io/codbex/kronos-buildpacks-stack-build-kyma . --target build
    docker push ghcr.io/codbex/kronos-buildpacks-stack-build-kyma
    ```

1. Build `Kronos Kyma Buildpack`:

    ```
    cd buildpack/

    pack buildpack package ghcr.io/codbex/kronos-buildpacks-kyma --config ./package.toml
    docker push ghcr.io/codbex/kronos-buildpacks-kyma

    pack builder create ghcr.io/codbex/kronos-buildpacks-builder-kyma --config ./builder.toml
    docker push ghcr.io/codbex/kronos-buildpacks-builder-kyma
    ```

1. Usage with `pack`:

    ```
    pack build --builder ghcr.io/codbex/kronos-buildpacks-builder-kyma <my-org>/<my-repository>
    ```

## Kpack Installation

1. [Install Pack](https://buildpacks.io/docs/tools/pack/#install)
1. [Install Kpack](https://github.com/pivotal/kpack/blob/main/docs/install.md)
1. [Install logging tool](https://github.com/pivotal/kpack/blob/main/docs/logs.md)
1. Create Docker Registry Secret:
    ```
    kubectl create secret docker-registry docker-registry-secret \
        --docker-username=<your-username> \
        --docker-password=<your-password> \
        --docker-server=https://index.docker.io/v1/ \
        --namespace default
    ```


1. Create Service Account
    ```
    kubectl apply -f service-account.yaml
    ```


1. Create `ClusterStore`, `ClusterStack` and `Builder`:

    ```
    kubectl apply -f kpack.yaml
    ```
    
    > _**Note:** Before creating the Kpack resources, replace the **`<tag>`** placeholder with a valid Kronos version (e.g. 0.5.0, 0.6.0, ...). All available Kronos versions could be found [here](https://github.com/codbex/codbex-kronos/releases) and the respective Docker images [here](https://github.com/orgs/codbex/packages?repo_name=codbex-kronos)._

## Image Building

1. Create Image:

    ```yaml
    apiVersion: kpack.io/v1alpha1
    kind: Image
    metadata:
      name: kronos-application
      namespace: default
    spec:
      tag: ghcr.io/codbex/kronos-application:<tag>
      serviceAccount: docker-registry-service-account
      imageTaggingStrategy: <tag>
      builder:
        name: kronos-builder
        kind: Builder
      source:
        blob:
          url: https://github.com/codbex/codbex-kronos/raw/main/samples/xsjs-simple.zip
    ```

    > _**Note:** Replace the **`<tag>`** placeholder with your Docker image tag._

1. Monitor Logs:

    ```
    logs -image kronos-application -namespace default
    ```
