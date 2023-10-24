# Blog
Trang web phục vụ cho mục đích đăng tải tài liệu, blog cá nhân
Sử dụng: Spring boot + thymeleaf + vuejs + tailwind css
## Backend
Thư mục BlogSite
### 1. Cài đặt database postgres
```
port: 5432
db default: blog
```
Hoặc cài postgres qua docker
```
$ docker run --name postgres-db -e POSTGRES_PASSWORD=mysecretpassword -e POSTGRES_DB=blog -p 5432:5432 -d postgres
```
Hoặc có thể sử dụng db khác, chỉnh sử cấu hình ở file pom.xml và application.properties
### 2. Chạy project BlogSite
Load các dependency và run project

Link: https://localhost:8443/

Postman test API [link](https://www.postman.com/planetary-desert-10407/workspace/blog-api/collection/14981914-609dfd89-ba97-4443-ab3a-293ee6f915fc?action=share&creator=14981914)

## Frontend
Thư mục blog-app
```
$ npm install
```
```
$ npm run dev
```

