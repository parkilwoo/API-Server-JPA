package park.ilwoo.javis.info;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import park.ilwoo.javis.common.AESEncryption;
import park.ilwoo.javis.common.CustomException;
import park.ilwoo.javis.info.entity.SCRAP001;
import park.ilwoo.javis.info.entity.SCRAP002;
import park.ilwoo.javis.info.repository.Scrap001Repository;
import park.ilwoo.javis.info.repository.Scrap002Repository;
import park.ilwoo.javis.user.entity.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Info Service
 */
@Service
@Slf4j
public class InfoService {

    private final String URL = "https://codetest.3o3.co.kr/scrap";
    private final String POST = "POST";
    private final String USER_AGENT = "Mozilla/5.0";
    private final String CONTENT_TYPE = "application/json; utf-8";
    private final String ACCEPT = "application/json";
    private final DecimalFormat decFormat = new DecimalFormat("##,####,####");

    private final Scrap001Repository scrap001Repository;
    private final Scrap002Repository scrap002Repository;

    public InfoService(Scrap001Repository scrap001Repository, Scrap002Repository scrap002Repository) {
        this.scrap001Repository = scrap001Repository;
        this.scrap002Repository = scrap002Repository;
    }

    public Map<String, Object> insertScrapInfo(User me) throws Exception {

        URL url = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(POST);
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Content-Type", CONTENT_TYPE);
        connection.setRequestProperty("Accept", ACCEPT);
        connection.setDoOutput(true);

        StringBuilder sb = new StringBuilder();
        sb.append("{\"name\":\"");
        sb.append(me.getName());
        sb.append("\",");
        sb.append("\"regNo\":\"");
        sb.append(AESEncryption.instance.decrypt(me.getRegNo()));
        sb.append("\"}");

        log.debug(sb.toString());
        OutputStream os = connection.getOutputStream();

        byte[] request_data = sb.toString().getBytes(StandardCharsets.UTF_8);
        os.write(request_data);
        os.close();


        //http 요청 실시
        connection.connect();

        int responseCode = connection.getResponseCode();

        if(responseCode != 200) {
            log.error(">>>>>> RespCode : " + responseCode);
            log.error(">>>>>> msg : " + connection.getResponseMessage());
            throw new CustomException(String.valueOf(responseCode), "회원님의 정보를 스크랩하는데 실패하였습니다.");
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        sb.setLength(0);
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null) {
            sb.append(inputLine);
        }
        bufferedReader.close();

        String response = sb.toString();

        JSONObject jsonObj = new JSONObject(response);

        ObjectMapper mapper = new ObjectMapper();

        JSONObject scrap001Json = jsonObj.getJSONObject("jsonList").getJSONArray("scrap001").getJSONObject(0);
        SCRAP001 scrap001Vo = mapper.readValue(scrap001Json.toString(), SCRAP001.class);
        scrap001Vo.setRegNo(me.getRegNo());
        scrap001Vo.setIdx(me.getIdx());
        scrap001Repository.save(scrap001Vo);

        JSONObject scrap002Json = jsonObj.getJSONObject("jsonList").getJSONArray("scrap002").getJSONObject(0);
        SCRAP002 scrap002Vo = mapper.readValue(scrap002Json.toString(), SCRAP002.class);
        scrap002Vo.setIdx(me.getIdx());
        scrap002Repository.save(scrap002Vo);

        return mapper.readValue(jsonObj.toString(), Map.class);
    }

    /**
     * 환급액 구하기
     * @param me -> 로그인한 유저 정보
     * @return
     * @throws CustomException
     */
    public Map<String, String> getRefund(User me) throws CustomException {
        Map<String, String> resultMap = new LinkedHashMap<>();

        resultMap.put("이름", me.getName());
        if(me.getScrap001() == null || me.getScrap002() == null) throw new CustomException("601", "스크랩을 먼저 진행해 주세요.");

        int limit = getLimit(me.getScrap001().getTotalPay());
        int taxCredit = getTaxCredit(me.getScrap002().getTotalAmount());
        int refund = Math.min(limit, taxCredit);

        resultMap.put("한도", formatNumber(limit));
        resultMap.put("공제액", formatNumber(taxCredit));
        resultMap.put("환급액", formatNumber(refund));
        return resultMap;
    }

    /**
     * 근로소득 세액공제 한도 구하기
     * @param totalPay -> 총급여액
     * @return
     */
    private int getLimit(Long totalPay) {
        int checkLimit;
        if(totalPay <= 33000000) return 740000;
        else if(totalPay <= 70000000) {
            checkLimit = Long.valueOf(Math.round(740000 - (totalPay - 33000000) * 0.008)).intValue();
            return Math.max(checkLimit, 660000);
        }
        else {
            checkLimit = Long.valueOf(Math.round(660000 - (totalPay - 70000000) * 0.5)).intValue();
            return Math.max(checkLimit, 500000);
        }
    }

    /**
     * 근로소득 세액공제 한도 구하기
     * @param taxAmount -> 산출세액
     * @return
     */
    private int getTaxCredit(Long taxAmount) {
        if(taxAmount <= 1300000) return Long.valueOf(Math.round(taxAmount * 0.55)).intValue();
        else return 715000 + Long.valueOf(Math.round((taxAmount-1300000) * 0.3)).intValue();
    }

    /**
     * 숫자를 요구한 format으로 변형
     * @param number 변경을 원하는 값
     * @return
     */
    private String formatNumber(int number) {
        if(number == 0) return "0원";

        String decimalFormatStr = decFormat.format(number);
        String[] formatStrArray = decimalFormatStr.split(",");

        StringBuilder sb = new StringBuilder();
        if(formatStrArray.length == 3) {
            sb.append(formatStrArray[0]);
            sb.append("억");
            sb.append(" ");
            sb.append(formatStrArray[1]);
            sb.append("만");
        }
        else if(formatStrArray.length == 2) {
            sb.append(formatStrArray[0]);
            sb.append("만");
        }

        String currentStr = sb.toString();
        sb.setLength(0);

        String[] unitStrArray = {"천", "백", "십", ""};
        String lastNum = formatStrArray[formatStrArray.length-1];
        for (int i = 0; i < lastNum.length(); i++) {
            if(lastNum.charAt(i) == '0') continue;
            sb.append(lastNum.charAt(i));
            sb.append(unitStrArray[i]);
        }

        String result = sb.length() == 0 ? currentStr + "원" : currentStr + " " + sb.toString() + "원";

        return result;

    }
}
