package com.toomuchcoding.uptodate

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class ConfigurationFilter {
    private final Set<Configuration> projectConfigurations

    ConfigurationFilter(Project project) {
        projectConfigurations = project.configurations.findAll()
    }

    Set<Configuration> getConfigurations(FiltersHolder configurationsHolder) {
        Set<Configuration> configurations = getIncludedConfigurations(configurationsHolder.included)
        return configurations.findAll { !configurationsHolder.excluded.contains(it.name) }
    }

    private Set<Configuration> getIncludedConfigurations(Set<String> configurationNames) {
        return configurationNames.isEmpty() ? projectConfigurations : projectConfigurations.findAll { configurationNames.contains(it.name) }
    }
}
