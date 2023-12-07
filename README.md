# Blog
Trang web phục vụ cho mục đích đăng tải tài liệu, blog cá nhân

Sử dụng: **Spring Boot**, **PostgreSQL**, **Websocket**, **Vuejs**, **Tailwind CSS**

Bao gồm 3 project:

### 1. **BlogSite:** cho người dùng xem bài viết
   
![image](https://github.com/vanlinh619/Blog/assets/71812422/b5c83515-2be6-48e5-aabf-3814b9e9a5d2)

### 2. **blog-app:** sử dụng để quản lý, đăng tải bài viết

![image](https://github.com/vanlinh619/Blog/assets/71812422/a419f5ad-7498-49b1-b99f-fa4c69e551dc)


### 3. **Broadcast:** hỗ trợ **bình luận**, thông báo, gửi tin nhắn thời gian thực

![image](https://github.com/vanlinh619/Blog/assets/71812422/a74a6842-c535-4b0f-94db-3248bd6cce9e)

![image](https://github.com/vanlinh619/Blog/assets/71812422/fcee8755-12b9-4cce-888e-f22a6d2fcd71)


## 1. BlogSite
Thư mục BlogSite
### 1.1. Cài đặt database postgres
```
port: 5432
db default: blog
```
Hoặc cài postgres qua docker
```
$ docker run --name postgres-db -e POSTGRES_PASSWORD=mysecretpassword -e POSTGRES_DB=blog -p 5432:5432 -d postgres
```
Hoặc có thể sử dụng db khác, chỉnh sử cấu hình ở file **pom.xml** và **application.properties**
### 1.2. Chạy project BlogSite
Load các dependency và run project

Link: https://localhost:8443/

Postman test API [link](https://www.postman.com/planetary-desert-10407/workspace/blog-api/collection/14981914-609dfd89-ba97-4443-ab3a-293ee6f915fc?action=share&creator=14981914)

## 2. blog-app
Thư mục blog-app
```
$ npm install
```
```
$ npm run dev
```
## 3. Broadcast
Thư mục Broadcast

Thay đổi cấu hình trong file **application.properties** để sử dụng tính năng broadcast thời gian thực

```
#Broadcast
microservice.broadcast=true
microservice.url=ws://localhost:8440/comment
```
