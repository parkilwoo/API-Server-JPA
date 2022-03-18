package park.ilwoo.javis.info;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import park.ilwoo.javis.common.CustomException;
import park.ilwoo.javis.common.Result;
import park.ilwoo.javis.user.entity.User;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Tag(name = "INFO", description = "스크랩&환급액조회")
@Slf4j
public class InfoRestController {

    private final InfoService infoService;

    public InfoRestController(InfoService infoService) {
        this.infoService = infoService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "기본 응답값"),
            @ApiResponse(responseCode = "404", description = "페이지 없음"),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    @ApiOperation(
            value = "정보스크랩"
            , notes = "로그인한 유저 정보스크랩"
            , produces = "application/json"
            , response = Result.class
    )
    @Tag(name = "INFO", description = "정보스크랩")
    @GetMapping("/szs/scrap")
    public Result doScrap(@AuthenticationPrincipal @ApiIgnore User me) throws Exception {
        Result result = new Result();
        result.setSuccess(infoService.insertScrapInfo(me));
        return result;
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "기본 응답값"),
            @ApiResponse(responseCode = "404", description = "페이지 없음"),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    @ApiOperation(
            value = "환급액계산"
            , notes = "로그인한 환급액계산"
            , produces = "application/json"
            , response = Result.class
    )
    @Tag(name = "INFO", description = "환급액계산")
    @GetMapping("/szs/refund")
    public Result getRefund(@AuthenticationPrincipal @ApiIgnore User me) throws CustomException {
        Result result = new Result();
        result.setSuccess(infoService.getRefund(me));

        return result;
    }
}
