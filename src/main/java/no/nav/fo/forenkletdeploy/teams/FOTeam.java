package no.nav.fo.forenkletdeploy.teams;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static no.nav.json.JsonUtils.fromJson;
import static no.nav.sbl.rest.RestUtils.withClient;

public class FOTeam implements Team {

    private static List<String> IGNORED_APPLICATIONS = Arrays.asList(
            "modiacontextholder",
            "modiaeventdistribution",
            "internarbeidsflatedecorator",
            "veilarbdemo",
            "badkitty"
    );

    @Override
    public String getId() {
        return "fo";
    }

    @Override
    public String getDisplayName() {
        return "Forenklet Oppfølging";
    }

    @Override
    public List<ApplicationConfig> getApplicationConfigs() {
        String json = withClient(c -> c.target("https://raw.githubusercontent.com/navikt/jenkins-dsl-scripts/master/forenklet_oppfolging/config.json").request().get(String.class));
        Map<String, Map<String, String>> map = fromJson(json, Map.class);
        return map.entrySet().stream()
                .map(e -> ApplicationConfig.builder()
                        .name(e.getKey())
                        .gitUrl(e.getValue().get("gitUrl"))
                        .build()
                )
                .filter(FOTeam::applicationIsNotIgnored)
                .collect(toList());
    }

    private static boolean applicationIsNotIgnored(ApplicationConfig applicationConfig) {
        return !IGNORED_APPLICATIONS.contains(applicationConfig.name);
    }

}
