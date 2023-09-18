# 사용 개발언어
java
# 사용 프레임워크
springboot, JPA, Gradle, Mustache, Lombok, Junit5, Mockito
# 사용 RDBMS
H2DB
# 데이타설계
  - Lecture (강연)
    - id: 강연의 고유 식별자입니다. 이 값은 자동으로 생성됩니다.
    - speaker: 강연을 진행하는 강연자의 이름입니다.
    - venue: 강연이 진행되는 장소입니다.
    - maxAttendees: 이 강연에 참가할 수 있는 최대 인원 수입니다.
    - startTime: 강연의 시작 시간입니다.
    - contents: 강연의 내용입니다.
    - reservations (Transient): 강연 예약 목록. 데이터베이스에는 저장되지 않는 임시 필드입니다.
  - LectureReservation (강연 예약)
    - id: 강연 예약의 고유 식별자입니다. 이 값은 자동으로 생성됩니다.
    - employeeId: 예약한 직원의 고유 식별자 (사번)입니다.
    - lectureId: 예약된 강연의 ID입니다.
    - createdDate: 예약이 생성된 날짜 및 시간입니다.
    - version: 버전 관리를 위한 필드입니다. (낙관적 동시성 제어)

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
