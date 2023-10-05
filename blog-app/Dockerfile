FROM node:lts-alpine

WORKDIR /app
COPY package*.json ./
COPY . .

RUN npm install
CMD npm run dev