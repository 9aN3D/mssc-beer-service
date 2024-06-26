package guru.springframework.mssc.beer.service.configuration;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local-discovery")
@Configuration
@EnableDiscoveryClient
public class LocalDiscovery {

}
