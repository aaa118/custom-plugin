package com.gradle.plugin

import org.gradle.api.Project

class SVersion {
    final def versionMajor
    final def versionMinor
    final def versionPatch

    // Branch name used to help determine version name (i.e. "release" in the full version name
    // versus "snapshot"
    final def isRelease

    // Single integer representation of the semantic version (major, minor, and patch)
    // See "generate
    final def versionCode

    // The base version name, which corresponds to the version code and the semantic version
    // components (major, minor patch)
    def baseVersionName

    private static def buildNumber

    SVersion(Project project, int versionMajor, int versionMinor, int versionPatch, boolean
            isRelease) {
        this.versionMajor = versionMajor
        this.versionMinor = versionMinor
        this.versionPatch = versionPatch

        this.isRelease = isRelease

        versionCode = generateVersionCode(versionMajor, versionMinor, versionPatch)
        baseVersionName = generateVersionName(versionMajor, versionMinor, versionPatch)

        buildNumber = readBuildNumber(project)
    }

    def getAppVersionName() {
        if(isRelease) {
            return baseVersionName
        } else {
            return "${baseVersionName}.${buildNumber}"
        }
    }

    def getArtifactVersionName() {
        if (isRelease) {
            return baseVersionName
        } else {
            return "${baseVersionName}-SNAPSHOT"
        }
    }

    def getLibraryVersionName() {
        if (isRelease) {
            return "${baseVersionName}.${buildNumber}.RELEASE"
        } else {
            return "${baseVersionName}.${buildNumber}.SNAPSHOT"
        }
    }

    private static def readBuildNumber(Project project) {
        return getPropertyFromFile(VersionPlugin.getVersionsPropertiesFile(project),"${project.name}_build")
    }

    private int generateVersionCode(versionMajor, versionMinor, versionPatch) {
        // Generates a version code where 0's are in the same places as "." in the version name.
        // Example: 1.2.24 would be 102024, where versionMajor is 1, versionMinor is 2,
        // versionPatch is 2, versionBuild is 4.
        return versionMajor * 100000 + versionMinor * 1000 + versionPatch * 10
    }

    private String generateVersionName(versionMajor, versionMinor, versionPatch) {
        return "${versionMajor}.${versionMinor}.${versionPatch}"
    }
}