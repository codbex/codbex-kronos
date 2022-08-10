## Kronos Buildpack

1. Set the Kronos version:
    > Replace the `#{KronosVersion}#` placeholder (e.g. `latest`, `0.7.1`, `1.0.0`) in `buildpack/*.toml` files.

1. Build `Kronos Stack`:

    ```
    docker build -t ghcr.io/codbex/kronos-buildpacks-stack-base . --target base
    docker push ghcr.io/codbex/kronos-buildpacks-stack-base

    docker build -t ghcr.io/codbex/kronos-buildpacks-stack-run . --target run
    docker push ghcr.io/codbex/kronos-buildpacks-stack-run

    docker build -t ghcr.io/codbex/kronos-buildpacks-stack-build . --target build
    docker push ghcr.io/codbex/kronos-buildpacks-stack-build
    ```

1. Build `Kronos Buildpack`:

    ```
    cd buildpack/

    pack buildpack package ghcr.io/codbex/kronos-buildpacks --config ./package.toml
    docker push ghcr.io/codbex/kronos-buildpacks

    pack builder create ghcr.io/codbex/kronos-buildpacks-builder --config ./builder.toml
    docker push ghcr.io/codbex/kronos-buildpacks-builder
    ```

1. Usage with `pack`:

    ```
    pack build --builder ghcr.io/codbex/kronos-buildpacks-builder <my-org>/<my-repository>
    ```