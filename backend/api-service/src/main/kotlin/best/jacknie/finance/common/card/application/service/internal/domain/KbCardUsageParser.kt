package best.jacknie.finance.common.card.application.service.internal.domain

import best.jacknie.finance.spending.log.application.port.RawCardUsage
import best.jacknie.finance.common.card.application.service.internal.model.CardUsageParser
import best.jacknie.finance.spending.log.domain.CardUsageStatus
import best.jacknie.finance.common.file.domain.FileObject
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Component
class KbCardUsageParser: CardUsageParser {

  private val zone = ZoneId.of("Asia/Seoul")

  override fun getCardUsages(fileObject: FileObject): List<RawCardUsage> {
    val workbook = HSSFWorkbook(fileObject.content)
    val sheet = workbook.getSheetAt(0)
    val usages = mutableListOf<RawCardUsage>()
    for (rowIndex in 7 .. sheet.lastRowNum) {
      val row = sheet.getRow(rowIndex)
      val usage = toRawCardUsage(row)
      usages += usage
    }
    return usages
  }

  private fun toRawCardUsage(row: Row): RawCardUsage {
    val localDate = LocalDate.parse(row.getCell(0).stringCellValue)
    val localTime = LocalTime.parse(row.getCell(1).stringCellValue)
    val merchant = row.getCell(4).stringCellValue
    val amount = row.getCell(5).numericCellValue.toInt()
    val status = toCardUsageStatus(row.getCell(11).stringCellValue)
    val approvalNumber = row.getCell(13).stringCellValue
    val time = ZonedDateTime.of(localDate, localTime, zone)
    return RawCardUsage(
      approvalNumber = approvalNumber,
      merchant = merchant,
      status = status,
      amount = amount,
      time = time,
    )
  }

  private fun toCardUsageStatus(status: String): CardUsageStatus {
    return when (status) {
      "전표매입" -> CardUsageStatus.INVOICED
      "전표미매입" -> CardUsageStatus.NOT_INVOICED
      "취소전표매입" -> CardUsageStatus.CANCELLED_INVOICED
      "승인취소" -> CardUsageStatus.APPROVAL_CANCELLED
      else -> error("알 수 없는 상태 문자열: $status")
    }
  }
}
