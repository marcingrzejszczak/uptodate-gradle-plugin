package com.toomuchcoding.uptodate.finder

import com.toomuchcoding.uptodate.UptodatePluginExtension
import com.toomuchcoding.uptodate.finder.http.HttpConnectionSettings
import com.toomuchcoding.uptodate.finder.http.ProxySettingsResolver

class FinderConfiguration {

    final boolean ignore
    final HttpConnectionSettings httpConnectionSettings
    final List<String> excludedVersionPatterns

    FinderConfiguration(RepositorySettings repositorySettings,
                        UptodatePluginExtension uptodatePluginExtension,
                        int numberOfDependencies) {

        ignore = repositorySettings.ignoreRepo
        httpConnectionSettings = new HttpConnectionSettings(
                url: repositorySettings.repoUrl,
                proxySettings: new ProxySettingsResolver(uptodatePluginExtension).resolve(),
                poolSize: Math.min(numberOfDependencies, uptodatePluginExtension.simultaneousHttpConnections),
                timeout: uptodatePluginExtension.connectionTimeout
        )
        excludedVersionPatterns = uptodatePluginExtension.excludedVersionPatterns
    }
}
