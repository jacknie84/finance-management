package best.jacknie.finance.core.integration.config

import best.jacknie.finance.core.BeanNames
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.MessageChannels

@Configuration
class IntegrationConfiguration {

  @Bean(name = [BeanNames.SAVE_CARD_USAGE_REQUEST_CHANNEL])
  fun saveCardUsageRequestChannel() = MessageChannels.queue()

  @Bean(name = [BeanNames.GET_OR_CREATE_USER_REQUEST_CHANNEL])
  fun getOrCreateUserRequestChannel() = MessageChannels.queue()

  @Bean(name = [BeanNames.SAVE_CARD_REQUEST_CHANNEL])
  fun saveCardRequestChannel() = MessageChannels.queue()
}
