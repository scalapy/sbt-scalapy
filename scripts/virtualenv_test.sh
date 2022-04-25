#!/bin/sh

VENV_DIR=$(mktemp -d)

echo "Virtualenv created at ${VENV_DIR}"

python3 -m venv "${VENV_DIR}"
"${VENV_DIR}"/bin/python -m pip install examples/python-package

CI_VIRTUALENV=true \
CI_PYTHON="${VENV_DIR}/bin/python" \
  sbt "sbt-scalapy-test/scripted sbt-scalapy/*"

CI_VIRTUALENV=true \
CI_PYTHON="${VENV_DIR}/bin/python" \
SCALAPY_PYTHON_PROGRAMNAME="${VENV_DIR}/bin/python" \
  sbt "sbt-scalapy-test/scripted sbt-scalapy-native/*"

echo "Delete virtualenv ${VENV_DIR}"
rm -rf "${VENV_DIR}"
