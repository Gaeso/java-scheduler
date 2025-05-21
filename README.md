# 📅 Schedule Management API

Spring Boot 기반의 일정 관리 REST API 서비스입니다. 
사용자별 일정 생성, 조회, 수정, 삭제 기능을 제공합니다.

## 🚀 주요 기능

- **일정 CRUD 기능**
- **조건별 일정 조회**
- **페이징 처리 지원**
- **유효성 검증 및 예외 처리**

## 📚 API 문서

| **기**능 | Method | URL | request | response | 상태코드 |
| --- | --- | --- | --- | --- | --- |
| 일정 생성 | POST | /schedules | 요청 Body  <br>1.유저 id : Long(필수)  <br>2.일정 내용 : String(필수)  <br>3.비밀번호 : String(필수) | 일정 등록 정보  <br>1\. 일정 ID : Long  <br>2\. 유저 ID : Long  <br>3\. 유저 이름 : String  <br>4\. 이메일 : String  <br>5\. 일정 내용 : String  <br>6\. 생성일 : LocalDate  <br>7\. 수정일 : LocalDate  <br>  <br>\-- 이하 동문 -- | 201 : 정상 등록 |
| 일정 전체 조회 (조건 포함) | GET | /schedules | 요청 Param  <br>1\. 일자 : LocalDate  <br>2\. 유저 ID : Long | 다건 응답 정보 | 200 : 정상 등록 |
| 일정 단건 조회 | GET | /schedules/{id} | 요청 Param  <br>1\. 일정 ID : Long(필수) | 단건 응답 정보 | 200 : 정상 등록 |
| 일정 페이징 조회 | GET | /schedules/page | 요청 Param  <br>1\. 일자 : LocalDate  <br>2\. 유저 ID : Long  <br>3\. 페이지 번호 : Integer  <br>4\. 페이지 크기 : Integer | 다건 응답 정보 | 200 : 정상 등록 |
| 일정 단건 수정 | PATCH | /schedules/{id} | 요청 Param  <br>1\. 일정 ID : Long(필수)  <br>  <br>요청 Body  <br>1\. 유저 이름 : String  <br>2\. 일정 내용 : String  <br>3\. 비밀번호 : String(필수) | 일정 수정 정보 | 200 : 정상 등록 |
| 일정 단건 삭제 | DELETE | /schedules/{id} | 요청 Param  <br>1\. 일정 ID : Long(필수)  <br>  <br>요청 Body  <br>1\. 비밀번호 : String(필수) | \- | 204 : 정상 삭제 |

## 🧾ERD

<img width="374" alt="image" src="https://github.com/user-attachments/assets/77b50092-7be6-4aff-af80-6407cac65308" />

## 🛠 설치 및 실행

### 저장소 복제:
```
bash
git clone https://github.com/your-repo/schedule-management-api.git
```
### 애플리케이션 실행:
```
bash
./gradlew bootRun
```
### 테스트 실행:
```
bash
./gradlew test
```
