# Spring Boot Aws S3 File Upload/Download Example
## Introduction
This is a simple Spring Boot application that demonstrates how to upload and download files to/from AWS S3.
## Configuration
The application uses the following properties to connect to AWS S3:
```
aws.access-key
aws.secret-key
aws.s3.bucket
aws.s3.region
```
## Usage
### Upload
You can upload a file sending a POST request to the endpoint `/files/upload` with the following body (MultiPart Form):
```
file = <file>
```
It will return the key of the uploaded file.
```json
{
  "key": "<key>"
}
```
### Download
You can download a file sending a GET request to the endpoint `/files/download/{key}`, where `{key}` is the key of the file you want to download.
