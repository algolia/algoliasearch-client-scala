#!/usr/bin/env bash
set -e

version=$(polyglot git next-version)
polyglot changelog "$version"
sed -i '' "s/^version \:=.*\$/version := \"${version}\"/" build.sbt
git add CHANGELOG.md build.sbt
git commit -m "chore: Release version $version"
git tag "$version"