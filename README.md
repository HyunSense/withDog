# WithDog - 반려견과 함께, 어디든지!

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

![admin](https://github.com/user-attachments/assets/ed887958-9a99-454b-b3e1-0111a86d55a2)<br>장소 등록 및 수정 |
---|

## 주요기능 (추가작성 필요)
- CDN 최적화를 통한 빠른 이미지 로드 (CloudFront + S3)
- ServerLess S3 정적 웹 호스팅을 통한 무중단 배포
- JWT 인증을 활용한 사용자 로그인 및 권한 관리
- Google, Kakao 소셜 로그인
- 반려견 동반 가능 장소 검색 및 카테고리 분류 (-------------------- 수정 필요)
- 북마크 기능을 통한 사용자만의 장소 관리
- **관리자** 인증을 통한 장소 등록,수정,삭제
- **관리자** 블로그 URL로 스크래핑(크롤링)을 통한 장소에 관한 블로그 이미지,제목,내용 등록

## 기술 스택
### 프론트엔드

- React 18.3: 함수형 컴포넌트, Hooks를 활용한 SPA 구축
- React Router: 페이지 라우팅
- React Context Api: 상태 전역관리 (로그인 정보)
- Styled-Component: 컴포넌트와 CSS를 결합해, 재사용 가능한 스타일링과 동적 스타일 처리를 구현 (CSS-in-JS 방식)

### 백엔드

- Spring Boot 3.3.4: RESTful API 구축
- Spring Security 6.3.3: JWT 인증, 권한 관리 및 보안
- Spring Data JPA: MySQL 연동, 데이터베이스 ORM 처리
- Jsoup: 추가 수정 필요 --------------------

### 데이터베이스

- MySQL 8.0.4: 데이터베이스 설계, 쿼리 최적화

### 서버 환경

- Nginx 1.24: 리버스 프록시 (Https 환경)
- AWS Services
    - Route 53: DNS 관리 및 도메인 설정
    - ACM: Route 53 + CloudFront의 SSL 인증서 관리
    - CloudFront: CDN을 통한 정적 이미지 파일 배포, 정적 웹 배포
    - S3: 이미지 업로드, 정적 웹 업로드 (Serverless)
    - EC2: SpringBoot Application, Nginx 서버 호스팅
    - RDS: MySQL 데이터베이스 호스팅

## RestAPI 명세서
[명세서 링크](https://hyunsense.notion.site/withDog-REST-API-16f05c7d6d428074803dfe50880ea054?pvs=4)

## ERD
![withdog-erd](https://github.com/user-attachments/assets/be45682b-dd09-4c5d-8581-12899f0517a5)

## 아키텍처
![architecture](https://github.com/user-attachments/assets/4ebd95a4-4622-46de-9614-1c28d2fced7e)


### 세부사항
#### FrontEnd
AWS S3의 정적 웹 호스팅 기능을 사용하여 React Application을 배포하였습니다. <br/>
Axios로 비동기 통신을 하였고, 이미지 요청은 CloudFront + S3를 사용하였습니다. <br/>
CDN 방식의 CloudFront를 통해 S3에 저장되어있는 이미지를 빠르게 로드할 수 있으며, 캐싱 기능을 통해
S3 의 요청부담을 감소시킬수 있었습니다. <br/>
Route 53 + CloudFront로 연계하여 ACM(SSL) 인증서를 통한 HTTPS 도메인 환경을 구축하였습니다.

<details>
    <summary><b>S3를 선택한 이유</b></summary><br/>
    Nginx와 S3 두 가지 배포 방식을 고민했으나, S3가 개인적으로 더 적합하다고 판단하였습니다.

<br/>

1. **서버 관리 부담 최소화**
    - Nginx를 사용하려면 EC2에 별도로 Nginx를 설치하고 설정해야 합니다. <br/> 
    현재 프리티어를 사용 중이므로, 메모리와 CPU 사용량에 대한 부담을 고려하여 S3를 선택하였습니다.

2. **간단한 설정**
    - Nginx는 설치 및 설정 과정이 복잡하지만, S3는 Serverless 방식으로 서버를 유지할 필요 없이 빌드 파일을<br/> 업로드하면 즉시 웹 호스팅이 가능합니다.

3. **AWS의 다른서비스(CloudFront, Route 53, ACM)와 연계 및 HTTPS 설정**
    - 손쉽게 AWS의 CloudFront와 연계하여 CDN 방식으로 캐싱 및 데이터 전송 최적화를 통해 더 빠르게 웹페이지를 로드할 수 있습니다.
    - **ACM(AWS Certificate Manager)** 및 **Route 53**과의 연계를 통해 HTTPS 도메인을 간단히 설정할 수 있습니다.
</details>
