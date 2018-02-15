package no.nav.fo.forenkletdeploy.teams;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

import static no.nav.json.JsonUtils.fromJson;
import static no.nav.sbl.rest.RestUtils.withClient;


public class TeamSoknad implements Team {

    @Override
    public String getId() {
        return "sd";
    }

    @Override
    public String getDisplayName() {
        return "Team Søknad";
    }

    @Override
    public List<ApplicationConfig> getApplicationConfigs() {
        String json = withClient(c -> c.target("https://raw.githubusercontent.com/navikt/jenkins-dsl-scripts/master/team_soknad/config.json").request().get(String.class));
        Map<String, Map<String, String>> map = fromJson(json, Map.class);
        List<ApplicationConfig> applications = map.entrySet().stream()
                .map(e -> ApplicationConfig.builder()
                        .name(e.getKey())
                        .gitUrl(e.getValue().get("gitUrl"))
                        .build()
                )
                .collect(toList());
        applications.add(ApplicationConfig.builder()
                .name("henvendelse")
                .gitUrl("ssh://git@stash.devillo.no:7999/dial/henvendelse.git")
                .build());
        return applications;
    }
}