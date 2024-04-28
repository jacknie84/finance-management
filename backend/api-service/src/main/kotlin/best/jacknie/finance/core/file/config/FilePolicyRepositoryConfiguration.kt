package best.jacknie.finance.core.file.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(FileProperties::class)
class FilePolicyRepositoryConfiguration
