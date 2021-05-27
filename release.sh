#!/usr/bin/env bash
set -e

version=$(polyglot git next-version)
polyglot changelog "$version"
sed -i '' "s/^version \:=.*\$/version := \"${version}\"/" version.sbt
git add CHANGELOG.md build.sbt
git commit -m "chore: Release version $version"
git tag "$version"
