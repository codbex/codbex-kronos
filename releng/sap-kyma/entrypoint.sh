#!/bin/bash

set -e

chown -R kronos:kronos $CATALINA_HOME

exec gosu kronos $@
