# Schedule App API

## [Schedule API]

### Create Schedule

스케줄 앱에 새로운 일정을 생성:

- **Method**: `POST`
    
- **URL**: `http://localhost:8080/schedules`
    

#### Request Body

요청바디에 JSON형태의 데이터를 보낸다:

\`\`\`json  
{  
"title": "일정 관리 앱",  
"contents": "일정 관리 앱 필수과제 Lv.1 끝내기",  
"name": "홍길동",  
"password": "1234"  
}

#### Example Request

POST /schedules HTTP/1.1  
Host: localhost:8080  
Content-Type: application/json

{  
"title": "일정 관리 앱",  
"contents": "일정 관리 앱 필수과제 Lv.1 끝내기",  
"name": "홍길동",  
"password": "1234"  
}

#### Example Response

HTTP/1.1 201 Created  
Content-Type: application/json

{  
"id": 1,  
"title": "일정 관리 앱",  
"contents": "일정 관리 앱 필수과제 Lv.1 끝내기",  
"name": "홍길동",  
"createdAt": "2024-02-03T12:34:56",  
"updatedAt": "2024-02-03T12:34:56"  
}

### Get Schedule

스케줄 앱의 **전체/선택** 일정 조회:

- **Method**: `GET`
    
- **URL:** `http://localhost:8080/schedules`
    
- `http://localhost:8080/schedules?name=홍길동`
    

#### Request Body

요청바디에 JSON형태의 데이터를 보낸다:

"조회시 요청 바디는 필요없다."

#### **Query Parameter**

| **Key** | **Value** |
| --- | --- |
| name | 특정 작성자 이름 |

#### Example Request

\[전체조회\]

GET /schedules HTTP/1.1  
Host: localhost:8080  
Content-Type: application/json

\[선택조회\]

GET /schedules?name=홍길동 HTTP/1.1  
Host: localhost:8080  
Content-Type: application/json

#### Example Response

HTTP/1.1 200 OK  
Content-Type: application/json

{  
"id": 1,  
"title": "일정 관리 앱",  
"contents": "일정 관리 앱 필수과제 Lv.1 끝내기",  
"name": "홍길동",  
"createdAt": "2024-02-03T12:34:56",  
"updatedAt": "2024-02-03T12:34:56"  
}


### Patch Schedule

스케줄 앱에 일정을 수정(업데이트):

- **Method**: `PATCH`
    
- **URL**: `http://localhost:8080/schedules/{scheduleId}`
    

#### Request Body

요청바디에 JSON형태의 데이터를 보낸다:

\`\`\`json  
{  
"title": "수정할 제목",  
"name": "수정할 작성자명",  
"password": "기존 비밀번호"  
}

#### Example Request

POST /schedules/1 HTTP/1.1  
Host: localhost:8080  
Content-Type: application/json

{  
"title": "일정 관리 앱 업데이트\~\~",  
"name": "누구게\~",  
"password": "1234"  
}

#### Example Response

HTTP/1.1 200 OK  
Content-Type: application/json

{  
"id": 1,  
"title": "일정 관리 앱 업데이트\~\~",  
"contents": "일정 관리 앱 필수과제 Lv.1 끝내기",  
"name": "누구게\~\~",  
"createdAt": "2024-02-03T12:34:56",  
"updatedAt": "2024-02-03T12:34:56"  
}

### Delete Schedule

스케줄 앱에 일정을 삭제:

- **Method**: `DELETE`
    
- **URL**: `http://localhost:8080/schedules/{scheduleId}`
    

#### Request Body

요청바디에 JSON형태의 데이터를 보낸다:

\`\`\`json  
{  
"password": "기존 비밀번호"  
}

#### Example Request

DELETE /schedules/1 HTTP/1.1  
Host: localhost:8080  
Content-Type: application/json

{  
"password": "1234"  
}

#### Example Response

HTTP/1.1 204 No Content

body 없음
------------------------------------------

## [Comment API]

### Create Comment

스케줄 앱에 일정에 새로운 댓글을 생성:

- **Method**: `POST`
    
- **URL**: `http://localhost:8080/schedules/{scheduleId}/comments`
    

#### Request Body

요청바디에 JSON형태의 데이터를 보낸다:

\`\`\`json  
{  
"contents": "와우 대박이네요!",  
"name": "고길동",  
"password": "1234"  
}

#### Example Request

POST /schedules.{scheduleId}/comments HTTP/1.1  
Host: localhost:8080  
Content-Type: application/json

{  
"contents": "와우 대박이네요!",  
"name": "고길동",  
"password": "1234"  
}

#### Example Response

HTTP/1.1 201 Created  
Content-Type: application/json

{  
"id": 1,  
"contents": "와우 대박이네요!",  
"name": "고길동",  
"createdAt": "2026-02-04T19:51:15.808828",  
"modifiedAt": "2026-02-04T19:51:15.808828"  
}

### Get Comment

스케줄 앱의 **선택** 일정과 해당 댓글 조회:

- **Method**: `GET`
    
- **URL:** `http://localhost:8080/schedules/{scheduleId}`
    

#### Request Body

요청바디에 JSON형태의 데이터를 보낸다:

"조회시 요청 바디는 필요없다."

#### Example Request

\[선택조회\]

GET /schedules/{scheduleId} HTTP/1.1  
Host: localhost:8080  
Content-Type: application/json

#### Example Response

HTTP/1.1 200 OK  
Content-Type: application/json

{  
"id": 1,  
"title": "일정 관리 앱",  
"contents": "일정 관리 앱 필수과제 Lv.1 끝내기",  
"name": "홍길동",  
"createdAt": "2026-02-04T19:48:27.734823",  
"modifiedAt": "2026-02-04T19:48:27.734823",  
"comments": \[  
{  
"id": 1,  
"contents": "와우 대박이네요!",  
"name": "고길동",  
"createdAt": "2026-02-04T19:51:15.808828",  
"modifiedAt": "2026-02-04T19:51:15.808828"  
}  
\]  
}

# ERD
<img width="708" height="1054" alt="image" src="https://github.com/user-attachments/assets/8b6d0028-fb8d-40ea-acdb-21571343c1e5" />

