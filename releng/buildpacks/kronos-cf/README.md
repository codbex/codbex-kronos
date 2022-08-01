## Kronos Cloud Foundry Buildpack

1. Set the Kronos version:
    > Replace the `#{KronosVersion}#` placeholder (e.g. `latest`, `0.7.1`, `1.0.0`) in `buildpack/*.toml` files.

1. Build `Kronos Cloud Foundry Stack`:

    ```
    docker build -t ghcr.io/codbex/kronos-buildpacks-stack-base-cf . --target base
    docker push ghcr.io/codbex/kronos-buildpacks-stack-base-cf

    docker build -t ghcr.io/codbex/kronos-buildpacks-stack-run-cf . --target run
    docker push ghcr.io/codbex/kronos-buildpacks-stack-run-cf

    docker build -t ghcr.io/codbex/kronos-buildpacks-stack-build-cf . --target build
    docker push ghcr.io/codbex/kronos-buildpacks-stack-build-cf
    ```

1. Build `Kronos Cloud Foundry Buildpack`:

    ```
    cd buildpack/

    pack buildpack package ghcr.io/codbex/kronos-buildpacks-cf --config ./package.toml
    docker push ghcr.io/codbex/kronos-buildpacks-cf

    pack builder create ghcr.io/codbex/kronos-buildpacks-builder-cf --config ./builder.toml
    docker push ghcr.io/codbex/kronos-buildpacks-builder-cf
    ```

1. Usage with `pack`:

    ```
    pack build --builder ghcr.io/codbex/kronos-buildpacks-builder-cf <my-org>/<my-repository>
    ```
