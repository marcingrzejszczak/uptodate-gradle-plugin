package com.toomuchcoding.uptodate.finder

import com.toomuchcoding.uptodate.UptodatePluginExtension
import com.toomuchcoding.uptodate.dependency.Dependency

interface NewVersionFinderFactory {

    NewVersionFinder create(UptodatePluginExtension uptodatePluginExtension, List<Dependency> dependencies)
}
