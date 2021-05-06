## 더 파이러츠 채용 과제

### 구성

- Spring Boot 2.4
- Spring Data Jpa
- QueryDSL 
- H2 DB

### 요구사항
1. 설치 및 환경 설정 가이드
    ```
    QueryDSL을 사용했기에 프로젝트 빌드 시 
    gradle를 사용해 빌드 해주시기 바랍니다.
   
   9000 포트를 지정해서 사용하기에 사용중인 포트 중 
   9000번이 점유 중인지 확인 바랍니다. 
   
   - java 11
   - spring boot 2.1.5
    ```
   
<br/>

2. 테이블 생성 SQL
    ```
   JPA의 테이블 생성하는 'ddl-auto: create'로 설정하였기에
   따로 작성하지 않았습니다.
   ```

<br/>

3. API 사용 가이드
    ```
    경로 com.rest 패키지 내에 HttpClient를 작성해 놓았습니다.
    api.http 파일을 통해 API를 알 수 있습니다!
    ```
