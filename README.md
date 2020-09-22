# searchplace

##과제 내용
카카오, 네이버 오픈 API를 이용한 **장소 검색 서비스** 구현

##실행 방법
- [release](https://github.com/nalyokky001/searchplace/releases/) jar 파일 다운로드
- cd <<다운로드 경로>>
- java -jar searchplace-1.0.jar

##curl 테스트 방법(스토리 흐름에 맞게)
###윈도우 curl 에서는 '를 "로 변경, "를 ""로 변경 후 호출

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

