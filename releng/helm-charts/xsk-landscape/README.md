# Kronos

[Kronos](https://github.com/codbex/codbex-kronos) is compatible environment for SAP HANA Extended Application Services (XS) based applications outside of SAP HANA instance running in a container deployed on Kubernetes.

## Overview

This chart bootstraps a [Kronos](https://github.com/codbex/codbex-kronos) deployment on a [Kubernetes](http://kubernetes.io) cluster using the [Helm](https://helm.sh) package manager.

### Prerequisites

- Kubernetes 1.19+
- Helm 3+

#### Setup

Add the Kronos chart repository:

```commands
helm repo add kronos https://codbex.github.io/codbex-kronos
helm repo update
```

## Deployment

1. Deploy Kronos in the target namespace/landscape.

```yaml
helm upgrade <demo-..> kronos/kronos-landscape \
--set namespace.name=<kronos-demo-..> \
--set secretHana.hanaUsername=<HANA-Username> \
--set secretHana.hanaPassword=<HANA-Password> \
--set secretHana.hanaUrl=<HANA-Url> \
--set secretXsuaa.url=<XSUAA-Url> \
--set secretXsuaa.clientId='<XSUAA-client-id>' \
--set secretXsuaa.clientSecret='<XSUAA-client-secret>' \
--set secretXsuaa.verificationKey="<XSUAA-verification-key>" \
--set secretXsuaa.xsappname='<XSUAA-xsappname>' \
--set deployment.landscapeDomain=<landscape-domain-name> \
--set dnsentry.targets=<dnsentry>
```

## Manual Helm Charts Update

1. Navigate to the `helm-chart` folder:

    ```
    cd releng/helm-charts/
    ```

1. Set the Kronos version in `kronos/Chart.yaml`:

    > Replace the `#{KronosVersion}#` placeholder.

1. Package Helm Chart and sign with gpg key:

    Obtain gpg key for Kronos and convert keyring to the legacy gpg format.

    `gpg --export-secret-keys > ~/.gnupg/secring.gpg`

    Package with gpg key.

    `helm package --sign --key 'Kronos-gpg' --keyring ~/.gnupg/secring.gpg <chart>`

    Verify the package.

    helm verify --keyring ~/.gnupg/pubring.gpg kronos-<version>.tgz

1. Copy the `kronos-<chart>-<version>>.tgz` and `kronos-<chart>-<version>.tgz.prov` somewhere outside the Git repository.

1. Reset all changes:

    ```
    git add .
    git reset --hard
    cd ../../
    ```

1. Switch to the `gh-pages` branch:

    ```
    git checkout gh-pages
    git pull origin gh-pages
    ```

1. Paste the `kronos-<chart>-<version>.tgz` and `kronos-<version>.tgz.prov` chart into the `charts` directory.

1. Build Helm Index:

    ```
    helm repo index charts/ --url https://codbex.github.io/codbex-kronos/charts
    ```

1.  Move the `charts/index.yaml` to the root folder:

    ```
    mv charts/index.yaml .
    ```

1. Push the changes:

    ```
    git add index.yaml
    git add charts/

    git commit -m "Helm Charts Updated"

    git push origin gh-pages
    ```