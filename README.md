
# API 서버구축 과제였지만, 참고용으로 남겨놔야지,,,

## 1. 회원가입 
  - 구현여부 : OK
  - 구현방법 : 입력정보를 RequestBody로 받은 후, 유효한 회원(이름&주민번호) 및 중복ID Validation 후 Hibernate를 이용하여 Insert
  - 성공응답 :  
<img width="219" alt="스크린샷 2022-03-18 오후 7 48 55" src="https://user-images.githubusercontent.com/56834479/158989623-cf381e17-e6c8-4a19-a360-caba0d829ac5.png"><img width="135" alt="스크린샷 2022-03-18 오후 7 51 21" src="https://user-images.githubusercontent.com/56834479/158989942-03a63189-15e0-4e40-a2fb-2e7c782829cb.png">
  - 실패응답 :  
파라미터 없을경우   
<img width="188" alt="스크린샷 2022-03-18 오후 8 04 07" src="https://user-images.githubusercontent.com/56834479/158991840-a5daea7e-62d1-4fd7-9037-7cc8227abd0d.png"> <img width="189" alt="스크린샷 2022-03-18 오후 8 13 38" src="https://user-images.githubusercontent.com/56834479/158993232-8a3aa577-2265-41b8-ae09-7bc07de43648.png"> <img width="192" alt="스크린샷 2022-03-18 오후 8 12 55" src="https://user-images.githubusercontent.com/56834479/158993137-d9aaa54a-94b8-44ea-9566-6be4b8af8a63.png"> <img width="233" alt="스크린샷 2022-03-18 오후 8 14 06" src="https://user-images.githubusercontent.com/56834479/158993295-c9a03f55-dee3-47a5-8185-89724e84d17b.png">  
유효하지 않은 회원  
<img width="233" alt="스크린샷 2022-03-18 오후 8 18 59" src="https://user-images.githubusercontent.com/56834479/158993958-c7c9c46d-bfad-4e07-95b3-865088ad6171.png"> <img width="224" alt="스크린샷 2022-03-18 오후 8 23 32" src="https://user-images.githubusercontent.com/56834479/158994528-c0fdfd0f-ede7-4564-a3be-04dfa4286b44.png">  
이미 가입한 주민등록번호 OR 계정  
<img width="218" alt="스크린샷 2022-03-18 오후 8 25 31" src="https://user-images.githubusercontent.com/56834479/158994807-ff4a1769-c037-48d9-a9ec-829221a1892c.png"> <img width="220" alt="스크린샷 2022-03-18 오후 8 27 43" src="https://user-images.githubusercontent.com/56834479/158995202-55751403-f9dc-4ff0-bff8-588c5cc3fc27.png">  


CODE : 101(ValidationException) / 102(가입할 수 있는 회원이 아닐경우) / 103(중복된 주민등록번호 OR 계정)
## 2. 로그인
  - 구현여부 : OK
  - 구현방법 : 아이디와 비밀번호 RequestBody로 받은 후, 해당 비밀번호를 Encrypt한 후 DB조회
  - 성공응답 :  
<img width="1215" alt="스크린샷 2022-03-18 오후 8 34 47" src="https://user-images.githubusercontent.com/56834479/158996050-6f32b3be-4d25-4ea0-acd2-c982fb240f02.png"> 로그인 성공시 JWT 발행
  - 실패응답 :  
없는 ID일 경우 OR 비밀번호가 맞지 않을경우  
<img width="183" alt="스크린샷 2022-03-18 오후 8 40 29" src="https://user-images.githubusercontent.com/56834479/158996799-54f01607-65c1-424f-8090-7d68654eb7f9.png"> <img width="230" alt="스크린샷 2022-03-18 오후 8 41 21" src="https://user-images.githubusercontent.com/56834479/158996917-8f63647a-56e5-49a5-8d88-0ea7b8b8c4e6.png">  

CODE : 201(없는 ID) / 202(맞지않는 비밀번호)    
## 3. 나의정보  
 - 구현여부 : OK
 - 구현방법 : Token을 RequestHeader에 받아서, 토큰에 등록된 정보로 유저 정보를 Return
 - 성공응답 :  
<img width="234" alt="스크린샷 2022-03-18 오후 8 57 04" src="https://user-images.githubusercontent.com/56834479/158998997-bd1860d2-3ced-444a-b3fe-3fdfe77550e0.png">

## 4. 스크랩  
 - 구현여부 : OK
 - 구현방법 : Token을 RequestHeader에 받아서, 토큰에 등록된 정보로 유저 정보를 가지고 이름과 주민번호로 POST 요청하여 스크랩 정보 적재 및 Return
 - 성공응답 :  
<img width="306" alt="스크린샷 2022-03-18 오후 9 02 24" src="https://user-images.githubusercontent.com/56834479/158999652-55fb2605-c0f0-4ed6-bf39-26b15a569ae0.png"> <img width="347" alt="스크린샷 2022-03-18 오후 9 02 50" src="https://user-images.githubusercontent.com/56834479/158999701-ad78829f-8eea-4640-84f5-e5e8418b23f0.png">
 - 실패응답 :  
Http응답 통신코드로 Code 내보냄  
<img width="334" alt="스크린샷 2022-03-18 오후 9 16 08" src="https://user-images.githubusercontent.com/56834479/159001538-4c33c387-4edc-4411-b709-2f070bbfa9a2.png">


문제에서 말씀하셨던 제공되는 URL의 문제점은 찾지 못하였습니다..!(많은 테스트를 진행했지만 연결이 잘 되었습니다) 꼭 알고 싶습니다.
## 5. 환급액 구하기
 - 구현여부 : OK
 - 구현방법 : Token을 RequestHeader에 받아서, 토큰에 등록된 정보로 유저 정보를 가지고 Scrap한 정보들을 DB에서 조회 후 계산
 - 성공응답 :  
<img width="183" alt="스크린샷 2022-03-18 오후 9 04 10" src="https://user-images.githubusercontent.com/56834479/158999847-8529ee87-7d3c-42b7-ab7c-a89b9586009f.png">. 
 - 실패응답 :  
스크랩을 하지 않았을 경우. 
<img width="239" alt="스크린샷 2022-03-18 오후 9 18 51" src="https://user-images.githubusercontent.com/56834479/159001720-d72e4875-ac75-4d2a-8e67-eb58eda51fe1.png">  
