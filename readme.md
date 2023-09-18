# 사용 개발언어
java
# 사용 프레임워크
springboot, JPA, Gradle, Mustache, Lombok, Junit5, Mockito
# 사용 RDBMS
H2DB
# 데이타설계

# 그 밖에 고민했던 또는 설명하고 싶은 부분
- 테스트 코드 작성 시, Mocking을 어떻게 할 것인가에 대한 고민. 
  - 너비와 깊이. 최소한으로 우선 개발 후 리팩토링 방향. 
- 데이터 일관성,동시성 제어에 대해 낙관적 동시성 제어 설정하였으나, 실제 서비스에서는 어떻게 처리할 것인가에 대한 고민.
  - 메시지 큐등을 이용하여 처리할 수 있겠으나, 이번 과제에서는 생략.
- 실시간 인기 강연을 pub/sub 방식으로 처리하는 방안
  - 고민을 했지만 task 볼륨이 커짐에 대한 고려과 오버스펙으로 판단하여 생략.

# web
localhost:8080/front

# bo
localhost:8080/admin

# DB conlose
localhost:8080/h2-console
