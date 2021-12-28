import com.village.soonyee.domain.Diary
import com.village.soonyee.dto.DiaryDto
import com.village.soonyee.response.ResponseService
import com.village.soonyee.response.result.CommonResult
import com.village.soonyee.response.result.ListResult
import com.village.soonyee.response.result.SingleResult
import com.village.soonyee.service.DiaryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class DiaryController(
    private val diaryService: DiaryService,
    private val responseService: ResponseService
) {

    //사실 저라면 여기서 DTO Class를 제작하지 않았을 겁니다.
    //여기서 그냥 Header로 받든해서 하나하나 따로 받아서 바로 Entity로 변환해서 사용했을거에요.
    //DiaryDTO 안에 들어가야하는 필드가 많으면 모를까 사실 3개 정도는 여기서 arg로 받고 처리하는게 코드 제작, 생산 속도가 더 빠를 겁니다.
    //물론 DTO로 나누는게 더 구조적이긴 하죠.
    @PostMapping("/diary")
    fun insertDiary(@RequestBody diaryDto: DiaryDto): CommonResult {
        diaryService.join(diaryDto)
        return responseService.successResult
    }

    @GetMapping("/diary/member/{memberIdx}")
    fun findByUser(@PathVariable memberIdx: Long): ListResult<Diary> {
        return responseService.getListResult(diaryService.findByUser(memberIdx))
    }

    @GetMapping("/diary/{diaryIdx}")
    fun findById(@PathVariable diaryIdx: Long): SingleResult<Diary> {
        return responseService.getSingleResult(diaryService.findById(diaryIdx))
    }
}