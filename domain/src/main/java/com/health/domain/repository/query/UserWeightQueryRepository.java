package com.health.domain.repository.query;

import com.health.domain.entity.QUserWeightEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.entity.UserWeightEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserWeightQueryRepository {

  private final JPAQueryFactory queryFactory;

  // 값 전체 가져오는 메서드
  public Page<UserWeightEntity> findAllByUser(UserEntity user, Pageable pageable) {
    List<UserWeightEntity> allList = getAllWeightRecordList(user);

    // 페이징 처리
    List<UserWeightEntity> pagenatedList = getPagenatedList(pageable, allList);

    return new PageImpl<>(pagenatedList, pageable, allList.size());
  }

  // 매주 일요일의 값만 추출하는 메서드
  public Page<UserWeightEntity> findWeeklyByUser(UserEntity user, Pageable pageable) {

    List<UserWeightEntity> allList = getAllWeightRecordList(user);

    List<UserWeightEntity> sundayList = extractSundayList(allList);

    // 페이징 처리
    List<UserWeightEntity> pagenatedList = getPagenatedList(pageable, sundayList);

    // 페이징 처리해서 반환
    return new PageImpl<>(pagenatedList, pageable, sundayList.size());
  }

  // 매달 마지막 일자의 값만 추출하는 메서드
  public Page<UserWeightEntity> findMonthlyByUser(UserEntity user, Pageable pageable) {

    List<UserWeightEntity> allList = getAllWeightRecordList(user);

    List<UserWeightEntity> lastDayOfMonthList = extractLastDayOfMonthList(allList);

    // 페이징 처리
    List<UserWeightEntity> pagenatedList = getPagenatedList(pageable, lastDayOfMonthList);

    // 페이징 처리해서 반환
    return new PageImpl<>(pagenatedList, pageable, lastDayOfMonthList.size());
  }


  // 유저의 모든 데이터 추출하는 메서드
  private List<UserWeightEntity> getAllWeightRecordList(UserEntity user) {
    QUserWeightEntity userWeightEntity = QUserWeightEntity.userWeightEntity;

    return queryFactory.selectFrom(userWeightEntity)
        .orderBy(userWeightEntity.weightRegDt.desc())
        .where(userWeightEntity.user.eq(user))
        .fetch();
  }

  // 매주 일요일의 데이터 추출하는 메서드
  private List<UserWeightEntity> extractSundayList(List<UserWeightEntity> allList) {
    return allList.stream()
        .filter(r -> r.getWeightRegDt().getDayOfWeek() == DayOfWeek.SUNDAY).toList();
  }


  // 매월 마지막일의 데이터 추출하는 메서드
  private List<UserWeightEntity> extractLastDayOfMonthList(List<UserWeightEntity> allList) {
    return allList.stream().filter(this::isLastDayOfMonth).toList();
  }

  // 해당 데이터가 매월 마지막 일자의 데이터인지 확인
  private boolean isLastDayOfMonth(UserWeightEntity userWeightEntity) {
    LocalDate date = userWeightEntity.getWeightRegDt();
    YearMonth yearMonth = YearMonth.of(date.getYear(), date.getMonth());
    return date.equals(yearMonth.atEndOfMonth());
  }


  // 페이징 처리 위한 메서드
  private List<UserWeightEntity> getPagenatedList
      (Pageable pageable, List<UserWeightEntity> resultList) {

    int start = getStart(pageable);
    int end = getEnd(pageable, start, resultList.size());

    return resultList.subList(start, end);
  }

  private int getStart(Pageable pageable) {
    return (int) pageable.getOffset();
  }

  private int getEnd(Pageable pageable, int start, int size) {
    return Math.min((start + pageable.getPageSize()), size);
  }

}
