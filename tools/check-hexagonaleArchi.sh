#!/usr/bin/env bash

readonly SRC_PATH="src/main/java/com/happytown"

readonly ENTITIES_PACKAGE_PATH="${SRC_PATH}/domain/entities"
readonly USE_CASES_PACKAGE_PATH="${SRC_PATH}/domain/use_cases"

readonly UNAUTHORIZED_PACKAGES_USAGE_IN_USE_CASES="infrastructure|application"
readonly UNAUTHORIZED_PACKAGES_USAGE_IN_ENTITIES="${UNAUTHORIZED_PACKAGES_USAGE_IN_USE_CASES}|use_cases|springframework|javax"

readonly UNAUTHORIZED_PACKAGES_USAGE_COUNT_IN_ENTITIES=$(find ${ENTITIES_PACKAGE_PATH} -name "*.java" -exec egrep -w ${UNAUTHORIZED_PACKAGES_USAGE_IN_ENTITIES} {} \; | wc -l)
readonly UNAUTHORIZED_PACKAGES_USAGE_COUNT_IN_USE_CASES=$(find ${USE_CASES_PACKAGE_PATH} -name "*.java" -exec egrep -w ${UNAUTHORIZED_PACKAGES_USAGE_IN_USE_CASES} {} \; | wc -l)

if [ "${UNAUTHORIZED_PACKAGES_USAGE_COUNT_IN_ENTITIES}" -eq 0 ] && [ "${UNAUTHORIZED_PACKAGES_USAGE_COUNT_IN_USE_CASES}" -eq 0 ]; then
  echo "Hexagonale Archi OK"
  exit 0
fi

echo "${UNAUTHORIZED_PACKAGES_USAGE_COUNT_IN_ENTITIES} unauthorized packages in ${ENTITIES_PACKAGE_PATH}:"
find ${ENTITIES_PACKAGE_PATH} -name "*.java" -exec egrep -lw ${UNAUTHORIZED_PACKAGES_USAGE_IN_ENTITIES} {} \;
echo ""
echo "${UNAUTHORIZED_PACKAGES_USAGE_COUNT_IN_USE_CASES} unauthorized packages in ${USE_CASES_PACKAGE_PATH}:"
find ${USE_CASES_PACKAGE_PATH} -name "*.java" -exec egrep -lw ${UNAUTHORIZED_PACKAGES_USAGE_IN_USE_CASES} {} \;
exit 1
