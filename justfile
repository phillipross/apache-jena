#!/usr/bin/env just --justfile
## Licensed under the terms of http://www.apache.org/licenses/LICENSE-2.0

# NOTE: The just recipes defined below assume sdkman is installed and used for java and maven selection.
#       Recipes that utilize docker containers assume the existence of the specific docker image existing locally

export SDKMAN_JAVA_11 := "11.0.15-zulu"
export SDKMAN_JAVA_17 := "17.0.3-zulu"
export DOCKER_CMD := "docker container run --rm -it"
export VOL_NAME := "apache-jena"
export M2_REPO := "/root/.m2/repository"
export BLD_DIR := "/usr/src/build"
export IMG := "llsem-ubuntu-maven"

clean-install: clean-install-11

clean-install-11:
  #!/usr/bin/env bash -l
  sdk use java ${SDKMAN_JAVA_11}
  mvn clean install

clean-install-17:
  #!/usr/bin/env bash -l
  sdk use java ${SDKMAN_JAVA_17}
  mvn clean install

docker-clean-install: docker-clean-install-11

docker-clean-install-11:
  ${DOCKER_CMD} -v ${VOL_NAME}-11:${M2_REPO} -v "$(pwd)":${BLD_DIR} -w "${BLD_DIR}" "${IMG}:11" mvn clean install

docker-clean-install-17:
  ${DOCKER_CMD} -v ${VOL_NAME}-17:${M2_REPO} -v "$(pwd)":${BLD_DIR} -w "${BLD_DIR}" "${IMG}:17" mvn clean install

clean: clean-11

clean-11:
  #!/usr/bin/env bash -l
  sdk use java ${SDKMAN_JAVA_11}
  mvn clean

clean-17:
  #!/usr/bin/env bash -l
  sdk use java ${SDKMAN_JAVA_17}
  mvn clean

verify: verify-11

verify-11:
  #!/usr/bin/env bash -l
  sdk use java ${SDKMAN_JAVA_11}
  mvn verify

verify-17:
  #!/usr/bin/env bash -l
  sdk use java ${SDKMAN_JAVA_17}
  mvn verify

dev-verify: dev-verify-11

dev-verify-11:
  #!/usr/bin/env bash -l
  sdk use java ${SDKMAN_JAVA_11}
  mvn -Pdev verify

dev-verify-17:
  #!/usr/bin/env bash -l
  sdk use java ${SDKMAN_JAVA_17}
  mvn -Pdev verify

dependencies:
  #!/usr/bin/env bash -l
  sdk use java ${SDKMAN_JAVA_11}
  mvn dependency:tree -Dscope=compile | tee dependencies.txt

updates:
  #!/usr/bin/env bash -l
  sdk use java ${SDKMAN_JAVA_11}
  mvn versions:display-dependency-updates | tee updates.txt
