H2 데이터베이스 설치
- [https://www.h2database.com]()
- 다운로드 및 설치
스프링 부트 2.x를 사용하면 **1.4.200 버전**을 다운로드 받으면 된다.
스프링 부트 3.x를 사용하면 **2.1.214 버전 이상** 사용해야 한다.
h2 데이터베이스 버전은 스프링 부트 버전에 맞춘다. 권한 주기: `chmod 755 h2.sh`
데이터베이스 파일 생성 방법
`jdbc:h2:~/image_db` (최소 한번)
`~/image_db.mv.db` 파일 생성 확인
이후 부터는 `jdbc:h2:tcp://localhost/~/image_db` 이렇게 접속
