package de.atomfrede.github.karaoke.server;

import com.codahale.metrics.health.HealthCheckRegistry;
import de.atomfrede.github.karaoke.server.config.KaraokeConfiguration;
import de.atomfrede.github.karaoke.server.mongo.JongoManaged;
import de.atomfrede.github.karaoke.server.mongo.MongoHealthCheck;
import de.atomfrede.github.karaoke.server.resource.PingResource;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Environment;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class KaraokeApplicationTest {

    @Mocked
    Environment environment;
    @Mocked
    JerseyEnvironment jersey;
    @Mocked
    LifecycleEnvironment lifecycle;
    @Mocked
    HealthCheckRegistry healthChecks;

    KaraokeApplication application = new KaraokeApplication();
    KaraokeConfiguration configuration = new KaraokeConfiguration();

    @Before
    public void setup() {

        new NonStrictExpectations() {{

            environment.jersey();
            result = jersey;

            environment.lifecycle();
            result = lifecycle;

            environment.healthChecks();
            result = healthChecks;
        }};
    }

    @Test
    public void shouldAddPingResource() throws Exception {

        application.run(configuration, environment);

        new Verifications() {{

            jersey.register(withInstanceOf(PingResource.class));
        }};
    }

    @Test
    public void shouldAddMongoHealthCheck() throws Exception {

        application.run(configuration, environment);

        new Verifications() {{

            healthChecks.register("MongoDB", withInstanceOf(MongoHealthCheck.class));
        }};
    }

    @Test
    public void shouldManageMongo() throws Exception {

        application.run(configuration, environment);

        new Verifications() {{

            environment.lifecycle().manage(withInstanceOf(JongoManaged.class));
        }};
    }
}
