![withDog-logo-192x192](https://github.com/user-attachments/assets/bd412edd-d0c7-4dc8-b294-445dbcb2d57a) 
# WithDog - 반려견과 함께, 어디든지! 🐾
**반려견과 함께하는 특별한 순간을 위한 서비스**

## 📌 프로젝트 개요
### ❓ 왜 WithDog을 만들었나요?
반려인 1500만 시대, 반려견과 함게하는 여행 수요가 증가했지만 **체계적인 정보 부족**이라는 문제를 해결하고자 합니니다.  
**반려견 동반 가능 장소** 검색에 소요되는 시간을 절감하고, **사용자의 경험 중심의 정보 제공**을 목표로 개발하였습니다.

<!-- ### ✨ 핵심 가치
✅ **Discover**: 지역/카테고리 기반 맞춤형 장소 추천  
✅ **Connect**: 반려인 커뮤니티 기반 리뷰 시스템(추가 개발 예정)  
✅ **Trust**: 관리자 검증을 통한 신뢰할 수 있는 정보 -->

## 🖥️ 시연 영상
![place-list](https://github.com/user-attachments/assets/6ff21f6c-b40c-469d-8025-f43a16ab2e37)<br>장소 목록 | ![place-detail](https://github.com/user-attachments/assets/75e1c64e-6add-4b72-a591-878c1fb3589e)<br>장소 상세정보 | ![place-bookmark](https://github.com/user-attachments/assets/1f673794-3eb4-4f8f-8a19-7be4342d6949)<br>장소 북마크
---|---|---|

![admin](https://github.com/user-attachments/assets/ed887958-9a99-454b-b3e1-0111a86d55a2)<br>장소 등록 및 수정 | ![search](https://github.com/user-attachments/assets/ea553011-5a8d-4fe5-ab80-b6e2fe6628ed)<br>장소 검색 | ![login-join](https://github.com/user-attachments/assets/cd1f74e0-ee16-4dbc-85ee-527069911edd)<br>로그인 & 회원가입
---|---|---|

## 🚀 바로가기
### 🌐 도메인 주소: https://www.withdog.store

### 📜 REST API 명세서: [API Documentation](https://hyunsense.notion.site/withDodg-RESTapi-17b05c7d6d42802cae8bccaff7fca8c6?pvs=4)

기능|메서드|URL
----|---|----|
로그인 | POST | `/api/v1/login`
로그아웃 | GET | `/api/v1/logout`
회원가입 | POST | `/api/v1/members`
장소 전체 조회 | GET | `/api/v1/plaecs?category=${category}&page=${page}&size=${size}`
장소 상세 조회 | GET | `/api/v1/places/${id}`
장소 등록 | POST | `/api/v1/places`
장소 수정 | PUT | `/api/v1/places`
장소 삭제 | DELETE | `/api/v1/places`
북마크 상태 조회 | GET | `/api/v1/places/${id}/boormarks/status`
북마크 목록 조회 | GET | `/api/v1/places/bookmarks`
북마크 목록 삭제 | DELETE | `/api/v1/places/bookmarks`
북마크 등록 | POST | `/api/v1/places/${id}/bookmarks`
북마크 삭제 | DELETE | `/api/v1/places/${id}/bookmarks`
상위 TOP3 조회 | GET | `/api/v1/places/top3`
장소 검색 | GET | `/api/v1/places/search?type=${type}&keyword=${keyword}&page=${page}&size=${size}`

## 🛠️ 기술 스택
### FrontEnd
분야 | 버전/도구 | 활용 내용 |
---|---|---|
**React** | 18.3 | 함수형 컴포넌트 및 Custom Hooks 기반 SPA 구현
**React Router** |  6 | 동적 라우팅 적용
**ContextAPI** | - | 전역 사용자 인증 상태 관리 (JWT 기반)
**Styled-Components** | - | CSS-in-JS 방식으로 동적 테마 적용 및 스타일 모듈화

### Backend
분야 | 버전/도구 | 활용 내용 |
---|---|---|
**Spring Boot** | 3.3.4 | RESTful API 설계, 비즈니스 로직 구현 및 JPA 연동 
**Spring Security** |  6.3 | JWT 및 소셜 로그인(OAuth2) 기반 인증 구현
**Jsoup** | - | 블로그 Open Graph 메타데이터 스크래핑 (네이버, 다음)
**MySQL** |  8.0 | 데이터 저장, Full-Text Search + JPA Query 최적화

### Infrastructure & DevOps
기술/서비스 | 활용 내용 |
---|---|
**AWS EC2, RDS** | 백엔드 서버 및 데이터베이스 호스팅
**AWS S3** | - **이미지 파일 저장소**: 반려견 관련 이미지 파일 저장 및 안정적인 제공<br>- **Spring Boot 배포 저장소**: 백엔드 애플리케이션 배포 파일 저장 및 관리<br>- **정적 웹호스팅**: 프론트엔드 정적 파일(HTML, CSS, JS) 호스팅으로 서버리스 환경 구현<br> → 이를 통해 무중단 배포 및 빠른 업데이트가 가능
**AWS CloudFront** | - 엣지 서버를 통한 콘텐츠 캐싱 및 배포<br> - **빠른 배포 및 낮은 지연 시간**: 정적 콘텐츠의 빠른 로딩과 응답속도 개선<br> (이미지 로딩 속도 약 30% 개선)<br> - **캐싱 정책 적용**: 원본(S3) 요청 횟수 대폭 감소 및 안정적 서비스 제공
**Nginx** | 리버스 프록시 및 SSL (Let's Encrypt) 설정으로 HTTPS 환경 제공, `https://api.withdog.store`<br> -> `http://localhost:8080` 요청을 중계
**GitHub Actions<br> + CodeDeploy** | CI/CD 자동화 배포 (배포 시간 단축: 5분 → 1분)

## Architecture
![architecture](https://github.com/user-attachments/assets/d2f98c37-711b-46c8-917e-eb094caacf63)

## ERD
### 🔗 [ERD-cloud](https://www.erdcloud.com/d/J8ax78zWsn5kLZ5Fj)
![withdog-erd](https://github.com/user-attachments/assets/bfcfca2f-a9b6-4a07-94d9-b94e185485d8)
- 북마크 설계: Member-Place 간 N:M 관계를 연결테이블(bookmarks)로 분리 (복합 PK로 데이터 무결성 보장)
- 주간 통계: place_weekly-stats 테이블을 통해 조회수/북마크 추적 (월요일 기준 롤링)

## 📈 핵심 기능
### 1️⃣ 장소 탐색
- 카테고리/키워드 검색:
    - 반려견 동반 가능 캠핑, 공원 등 다양한 장소 검색
    - 키워드로 필터링해 원하는 장소 빠르게 찾기
- 실시간 인기 장소 추천:
    - 주간 조회수 기반 TOP3 장소 자동 추천

### 2️⃣ 장소 상세 정보 확인
- 다양한 이미지와 블로그 후기:
    - 사용자들이 업로드한 실제 방문 사진과 블로그 후기 확인
    - 블로그 URL 입력 시 제목/썸네일 자동 생성
- 상세 정보 제공:
    - 주소, 운영 시간, 반려견 정책 등 필수 정보 한눈에 확인

### 3️⃣ 관리자 장소 등록 및 관리
- 다중 이미지 업로드:
    - 최대 5장의 사진을 순서대로 업로드
    - 업로드 후 순서 변경 가능
- 블로그 후기 공유:
    - 방문한 장소의 블로그 URL 공유
    - 메타데이터 자동 추출로 간편한 입력

### 4️⃣ 실시간 통계
- 조회수 + 북마크수 기반 인기 장소 분석:
    - 등록한 장소의 주간 조회수 및 북마크수 집계
    - 사용자들이 가장 많이 찾은 장소 TOP3 제공

### 5️⃣ 편리한 로그인 및 보안
- 소셜 로그인 지원:
    - 네이버/카카오로 간편 로그인
- 안전한 계정 관리:
    - JWT 기반 자동 토큰 갱신
    - Redis를 활용한 보안 강화(추후 지원 예정)

## 🎯 성과 및 배운 점
- UI/UX 개선
    - 폼 검증 강화: 회원가입시 실시간 유효성 검사 (이메일형식, 패스워드 복잡도 표시)
    - 오류 메시지 구체화: "비밀번호는 8~12자 영문 대소문자와 숫자, 특수문자는 선택 입력해주세요." 와같은 명확한 가이드 제공 
- CloudFront 캐시 정책으로 AWS 비용 절감 및 이미지 로딩 속도 개선
- CI/CD 자동화 경험
    - GitHub Actions → CodeDeploy 활용, 배포 시간 5분 → 1분으로 단축
- CORS 이슈 해결
    - Nginx 리버시 프록시 설정을 통해 API 요청 차단 문제 해결
    - HTTPS 환경 구축
