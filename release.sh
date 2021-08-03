#!/usr/bin/env bash
set -e

version=$(polyglot git next-version)
polyglot changelog "$version"
sed -i '' "s/^version in ThisBuild \:=.*\$/version in ThisBuild := \"${version}\"/" version.sbt
git add CHANGELOG.md version.sbt
git commit -m "chore: Release version $version"
git tag "$version"
