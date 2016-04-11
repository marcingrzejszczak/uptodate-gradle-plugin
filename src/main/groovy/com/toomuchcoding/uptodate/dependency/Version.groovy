package com.toomuchcoding.uptodate.dependency

import groovy.transform.EqualsAndHashCode
import com.toomuchcoding.uptodate.dependency.maven.ArtifactVersion
import com.toomuchcoding.uptodate.dependency.maven.DefaultArtifactVersion

@EqualsAndHashCode
class Version implements Comparable<Version> {

    private final String unparsedVersion
    final ArtifactVersion artifactVersion

    Version(String unparsedVersion) {
        this.unparsedVersion = unparsedVersion
        this.artifactVersion = new DefaultArtifactVersion(unparsedVersion)
    }

    @Override
    int compareTo(Version other) {
        return this.artifactVersion <=> other.artifactVersion
    }

    @Override
    String toString() {
        return unparsedVersion
    }
}
