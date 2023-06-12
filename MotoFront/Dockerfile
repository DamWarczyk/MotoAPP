### STAGE 1:BUILD ###
# Using node:lastest image as build
FROM node:latest AS build
# Create a directory in the docker image
WORKDIR /dist/src/app
# Copy files to directory
COPY package.json package-lock.json ./
# Run command 
RUN npm cache clean --force
# Copy files from local machine to virtual directory in docker image
COPY . .
RUN npm install
RUN npm run build --prod


# STAGE 2:RUN 
# Defining nginx image to be used
FROM nginx:latest AS ngi
COPY --from=build /dist/src/app/dist/moto-front /usr/share/nginx/html
COPY /nginx.conf  /etc/nginx/conf.d/default.conf
# Exposing a port
EXPOSE 80
