#!/usr/bin/env bash

#rbenv install 2.1.2
#gem install github_changelog_generator

echo "Version number to bump:"
read VERSION

rbenv shell 2.1.2

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

echo "Next version number:"
read NEXT_VERSION

sed -i '' "s/^version \:=.*\$/version := \"${NEXT_VERSION}-SNAPSHOT\"/" build.sbt
git add build.sbt
git commit -m"Bump snapshot ${NEXT_VERSION}"

git push origin master