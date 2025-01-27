![withDog-logo-192x192](https://github.com/user-attachments/assets/bd412edd-d0c7-4dc8-b294-445dbcb2d57a) 
# WithDog - 반려견과 함께, 어디든지!

## 도메인 주소
### https://www.withdog.store

## 프로젝트 정보
### 소개 
반려견과 함께 여행하거나 특별한 시간을 보내고자 하는 반려인들을 위해, 반려견 동반 가능한 캠핑장, 전용 공원, 펜션 등의 정보를 제공합니다.

### 인원
1명
### 프로젝트 기간
2024-11 ~ 현재 (개발 진행 중)

### 시연 영상
![place-list](https://github.com/user-attachments/assets/6ff21f6c-b40c-469d-8025-f43a16ab2e37)<br>장소 목록 | ![place-detail](https://github.com/user-attachments/assets/75e1c64e-6add-4b72-a591-878c1fb3589e)<br>장소 상세정보 | ![place-bookmark](https://github.com/user-attachments/assets/1f673794-3eb4-4f8f-8a19-7be4342d6949)<br>장소 북마크
---|---|---|

![admin](https://github.com/user-attachments/assets/ed887958-9a99-454b-b3e1-0111a86d55a2)<br>장소 등록 및 수정 | ![search](https://github.com/user-attachments/assets/ea553011-5a8d-4fe5-ab80-b6e2fe6628ed)<br>장소 검색 | ![login-join](https://github.com/user-attachments/assets/cd1f74e0-ee16-4dbc-85ee-527069911edd)<br>(소셜)로그인 & 회원가입
---|---|---|

## RestAPI 명세서
### [명세서 링크](https://hyunsense.notion.site/withDodg-RESTapi-17b05c7d6d42802cae8bccaff7fca8c6?pvs=4)
기능|메서드|URL
----|---|----|
로그인 | POST | /api/v1/login
로그아웃 | GET | /api/v1/logout
회원가입 | POST | /api/v1/members
장소 전체 조회 | GET | /api/v1/plaecs?category=${category}&page=${page}&size=${size}
장소 상세 조회 | GET | /api/v1/places/${id}
장소 등록 | POST | /api/v1/places
장소 수정 | PUT | /api/v1/places
장소 삭제 | DELETE | /api/v1/places
북마크 상태 조회 | GET | /api/v1/places/${id}/boormarks/status
북마크 목록 조회 | GET | /api/v1/places/bookmarks
북마크 목록 삭제 | DELETE | /api/v1/places/bookmarks
북마크 등록 | POST | /api/v1/places/${id}/bookmarks
북마크 삭제 | DELETE | /api/v1/places/${id}/bookmarks
상위 TOP3 조회 | GET | /api/v1/places/top3
장소 검색 | GET | /api/v1/places/search?type=${type}&keyword=${keyword}&page=${page}&size=${size}

## ERD
![withdog-erd](https://github.com/user-attachments/assets/be45682b-dd09-4c5d-8581-12899f0517a5)

## 아키텍처
![architecture](https://github.com/user-attachments/assets/45c5d72b-9bac-4c6d-8467-89b299bcbfe9)

## 주요기능 (추가작성 필요)
- CDN 최적화를 통한 빠른 이미지 로드 (CloudFront + S3)
- ServerLess S3 정적 웹 호스팅을 통한 무중단 배포
- JWT 인증을 활용한 사용자 로그인 및 권한 관리
- Google, Kakao 소셜 로그인
- 반려견 동반 가능 장소 검색 및 카테고리 분류
- 북마크 기능을 통한 사용자만의 장소 관리
- **관리자** 인증을 통한 장소 등록,수정,삭제
- **관리자** 인증을 통한 블로그 URL로 스크래핑(크롤링)을 통한 장소에 관한 블로그 이미지,제목,내용 등록

## 기술 스택
### 프론트엔드

- **React 18.3:** 함수형 컴포넌트, Hooks를 활용한 SPA 구축
- **React Router:** 페이지 라우팅
- **React Context Api:** 상태 전역관리 (로그인 정보)
- **Styled-Component:** 컴포넌트와 CSS를 결합해, 재사용 가능한 스타일링과 동적 스타일 처리를 구현 (CSS-in-JS 방식)

### 백엔드

- **Spring Boot 3.3.4:** RESTful API 구축
- **Spring Security 6.3.3:** JWT 인증, 권한 관리 및 보안
- **Spring Data JPA:** MySQL 연동, 데이터베이스 ORM 처리
- **Jsoup:** Open Graph 속성을 통해 블로그 메타데이터 추출

### 데이터베이스

- **MySQL 8.0.4:** 데이터베이스 설계, 쿼리 최적화

### 서버 환경

- **Nginx 1.24:** 리버스 프록시로 HTTPS 환경 구성
- **AWS Services**
    - Route 53: DNS 관리 및 사용자 지정 도메인 설정
    - ACM: SSL 인증서 관리로 HTTPS 보안 연결 제공
    - CloudFront: CDN을 통한 정적 이미지 파일 및 웹 배포, 캐싱 최적화
    - S3: 정적 웹 파일 및 이미지 업로드 (Serverless)
    - EC2: SpringBoot Application과 Nginx 서버 호스팅
    - RDS: MySQL 데이터베이스 호스팅
 - **CI/CD**
    - Github Actions: 자동화된 빌드, 배포 파이프라인 구현
    - AWS CodeDeploy: EC2에 Spring Boot 애플리케이션 배포 자동화
