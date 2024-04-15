package best.jacknie.finance.core

object BeanNames {

  /**
   * 카드 사용 내역 저장 처리 채널
   */
  const val SAVE_CARD_USAGE_REQUEST_CHANNEL = "fmApi::IntegrationConfiguration.saveCardUsageRequestChannel"

  /**
   * 사용자 조회 또는 생성 처리 채널
   */
  const val GET_OR_CREATE_USER_REQUEST_CHANNEL = "fmApi::IntegrationConfiguration.getOrCreateUserRequestChannel"

  /**
   * 카드 저장 처리 채널
   */
  const val SAVE_CARD_REQUEST_CHANNEL = "fmApi::IntegrationConfiguration.saveCardRequestChannel"
}
