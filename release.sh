#!/usr/bin/env bash

#rbenv install 2.1.2
#gem install github_changelog_generator

read -p "Have you set \$GITHUB_TOKEN? (y/n) " OK
case "$OK" in
  y|Y ) ;;
  * ) echo "Set it and relaunch"; exit 1;
esac

read -p "Is the gem 'github_changelog_generator' available? (y/n) " OK
case "$OK" in
  y|Y ) ;;
  * ) echo "Install it and relaunch"; exit 1;
esac

read -p "Version number to bump (ex: 1.3.1): " VERSION

git tag ${VERSION}
git push --tags

github_changelog_generator -t ${GITHUB_TOKEN} --no-unreleased

sed -i '' "s/^version \:=.*\$/version := \"${VERSION}\"/" build.sbt
git add build.sbt
git commit -m"Bump version ${VERSION}"

git add CHANGELOG.md
git commit -m"Changelog for v${VERSION}"

git push origin master

git tag -f ${VERSION}
git push --tags --force

sbt publishSigned
sbt sonatypeRelease

read -p "Next version number, without SNAPSHOT (ex: 1.4.0): " NEXT_VERSION

sed -i '' "s/^version \:=.*\$/version := \"${NEXT_VERSION}-SNAPSHOT\"/" build.sbt
git add build.sbt
git commit -m"Bump snapshot ${NEXT_VERSION}"

git push origin master