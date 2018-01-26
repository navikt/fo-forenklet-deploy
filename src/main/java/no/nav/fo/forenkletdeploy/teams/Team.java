package no.nav.fo.forenkletdeploy.teams;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;

import java.util.List;

public interface Team {
    String getId();
    String getDisplayName();
    List<ApplicationConfig> getApplicationConfigs();
}