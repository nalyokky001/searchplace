# searchplace

##과제 내용
카카오, 네이버 오픈 API를 이용한 **장소 검색 서비스** 구현

##사용한 외부 라이브러리 및 오픈소스
lombok (생산성 향상을 위해 사용)
gson (json 처리를 위해 사용)

##실행 방법
- [release](https://github.com/nalyokky001/searchplace/releases/) jar 파일 다운로드
- cd <<다운로드 경로>>
- java -jar searchplace-1.0.jar

##curl 테스트 방법(스토리 흐름에 맞게)
###윈도우 curl 에서는 '를 "로 변경, "를 ""로 변경 후 호출

(서비스 실행 시 더미 데이터로 testUser_0~4 5개 생성, 검색 키워드 카카오뱅크 5회, 공원 4회 생성)

회원 가입
- curl -X POST http://localhost:8080/member/join -H "Content-Type: application/json" -d '{"userId":"nalyokky001", "password":"nalyokkypassword"}'

로그인(로그인 시 받는 apiKey 로 검색 관련 api 호출, memory db인 H2 사용으로 인해 테스트케이스 실행시마다 apiKey 가 변경이 되어서 임시로 기존 소스 주석 처리 후 testApiKey 로 선언)
- curl -X POST http://localhost:8080/member/login -H "Content-Type: application/json" -d '{"userId":"nalyokky001", "password":"nalyokkypassword"}'

장소 검색(장소 4군데 검색 - 1군데 2번 검색)
- curl -X POST http://localhost:8080/search/place -H "Content-Type: application/json" -d '{"userId":"nalyokky001", "keyword":"신사역", "apiKey":"testApiKey"}'
- curl -X POST http://localhost:8080/search/place -H "Content-Type: application/json" -d '{"userId":"nalyokky001", "keyword":"판교", "apiKey":"testApiKey"}'
- curl -X POST http://localhost:8080/search/place -H "Content-Type: application/json" -d '{"userId":"nalyokky001", "keyword":"교보타워", "apiKey":"testApiKey"}'
- curl -X POST http://localhost:8080/search/place -H "Content-Type: application/json" -d '{"userId":"nalyokky001", "keyword":"판교", "apiKey":"testApiKey"}'

히스토리 검색
- curl -X POST http://localhost:8080/search/history -H "Content-Type: application/json" -d '{"userId":"nalyokky001", "apiKey":"testApiKey"}'

인기장소 검색
- curl -X POST http://localhost:8080/search/favorite -H "Content-Type: application/json" -d '{"userId":"nalyokky001", "apiKey":"testApiKey"}'

##ETC
- 네이버 API의 경우, 최대 5개까지만 목록을 내려주어서 과제 조건에 있던 10개를 채우지는 못했습니다. (변경 가능한 파라미터가 sort항목 뿐인데 검색 조건 자체가 변경되는 거 같아 sort 를 바꿔 한 번 더 호출 후 10개를 채우는 방법은 사용하지 않았습니다)
- 카카오 API, 네이버 API 의 목록을 받아와 장소명을 조건으로 비교 및 정렬했습니다. (네이버 API의 결과에 keyword를 감싼 html 태그 및 공백 삭제 후 비교했습니다)
- endpoint 에 대한 부하가 커질 경우, 이를 줄위기 위한 방법들은 기존에 업무를 진행했었던 클라우드 인프라 환경으로 설명하겠습니다. 서버 병목은 lb 를 통해 트래픽을 각각의 서버에 분산하고 분산 된 이후의 트래픽마저 증가한다면, 조건 설정을 통해 서버에 일정량 이상의 부하가 생기면 autoscaling 을 통해 scale out 으로 분산을 방지하겠습니다. DB 병목은 redis 등의 캐싱을 사용하여 분산을 줄이거나, DB 샤딩을 통해 부하를 줄이는 방법등이 있겠습니다.  