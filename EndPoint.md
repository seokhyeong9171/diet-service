[로그인]

- [x] POST = /login

[마이페이지]

- [x] GET = /{id}/mypage

[유저 정보 수정]

- [x] POST = /{id}/mypage/userinfo
- [x] POST = /{id}/mypage/nickname

[운동기록]

- [x] 조회 GET = /{id}/exercise
  - Pageable
  - 등록 내림차순

- [x] 등록 POST = /{id}/exercise
		- @RequestBody ExerciseForm

- [x] 수정 PATCH = /{id}/exercise/{exerciseId}
		- @RequestBody ExerciseForm

- [x] 삭제 DELETE = /{id}/exercise/{exerciseId}

[몸무게]

- [x] 조회 GET = /{id}/weight?scope=day , /{id}/weight?scope=week , /{id}/weight?scope=month

- [x] 등록 POST = /{id}/weight
		- @RequestBody UserWeightForm

- [ ] 수정 PATCH = /{id}/weight/{weightId}
		- @RequestBody UserWeightForm

- [ ] 삭제 DELETE = /{id}/weight/{weightId}

[식사 칼로리 계산]

- [ ] 조회 GET = /food
- [ ] 음식정보 조회
- [ ] 조회 GET = /{id}/diet?date={date}

- [ ] 등록
- [ ] POST = /{id}/meal/{date}/new
  - 새로운 식사 생성
  - @RequestBody MealAddForm

- [ ] POST = /{id}/meal/{mealId}
  - Redis에 담긴 섭취 음식 -> 식사 정보 업데이트

- [ ] POST = /{id}/meal/{mealId}/food
  - Redis에 섭취 음식 추가
  - @RequestBody ConsumeFoodForm

- [ ] DELETE = /{id}/diet/food/{consumefoodId}
			Redis에 담긴 음식 삭제


- [ ] 수정 PATCH = /{id}/consume/{consumeFoodId}
			섭취 식사 정보 수정

- [ ] 삭제 DELETE = /{id}/consume/{consumeFoodId}
			섭취 식사 정보 삭제 

- [ ] DELETE = /{id}/meal/{mealId}
			식사 정보 삭제

- [ ] DELETE = /{id}/diet/{date}
			해당 일자 식사 정보 모두 삭제

[식단 공유 게시판]

- [ ] 조회 GET = /post

- [ ] 등록 POST = /{id}/post
	- @RequesyBody PostForm

- [ ] 수정 PATCH = /{id}/post/{postId}
	- @RequesyBody PostForm

- [ ] 삭제 DELETE = /{id}/post/{postId}

[운동 모임]
- [ ] 조회 GET = /meeting

- [ ] 등록 POST = /{id}/meeting/add
	- @RequestBody MeetingForm

- [ ] 수정 PATCH = /{id}/meeting/{meetingId}
	- @RequestBody MeetingForm

- [ ] 삭제 DELETE = /{id}/meeting/{meetingId}


- [ ] 참가신청 POST = /{id}/meeting/{meetingId}/enroll
- [ ] 참가취소 POST = /{id}/meeting/{meetingId}/cancel

- [ ] 참가허가 POST = /{id}/meeting/{meetingId}/approve/{participantId}
- [ ] 참가거절 POST = /{id}/meeting/{meetingId}/decline/{participantId}

- [ ] 캘린더 등록 POST = /{id}/meeting/{meetingId}/calander

