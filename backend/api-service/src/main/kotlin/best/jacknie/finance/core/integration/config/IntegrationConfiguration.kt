package best.jacknie.finance.core.integration.config

import best.jacknie.finance.core.BeanNames
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.MessageChannels

@Configuration
class IntegrationConfiguration {

  @Bean(name = [BeanNames.GET_OR_CREATE_USER_REQUEST_CHANNEL])
  fun getOrCreateUserRequestChannel() = MessageChannels.queue()
}
