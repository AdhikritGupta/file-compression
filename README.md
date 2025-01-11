# File Compression API

This repository contains a Spring Boot-based API for compressing and decompressing PDF files to ZIP format, and storing them in a MongoDB database. The API allows users to upload files, compress them, retrieve compressed files, and view decompressed PDFs.

---

## Features

1. **File Compression**: Compress PDF files into ZIP format.
2. **Decompression**: Extract files from ZIP archives and view their content.
3. **MongoDB Integration**: Store compressed and original files in a MongoDB database.
4. **RESTful Endpoints**: Expose user-friendly APIs for file upload, retrieval, and manipulation.

---

## Libraries Used

### Backend Framework

- **Spring Boot**: Core framework for developing the API.

### Database

- **Spring Data MongoDB**: For seamless integration with MongoDB.

### Compression

- **Java Util Zip**: For handling ZIP file compression and decompression.

### Miscellaneous

- **Lombok**: For reducing boilerplate code.
- **Spring Web**: For building RESTful web services.
- **Spring Boot DevTools**: For improving development experience.
- **Spring Boot Validation**: For input validation.

---

## Prerequisites

1. **Java**: Ensure Java 17 or above is installed.
2. **MongoDB**: Set up a MongoDB instance and ensure it is running.
3. **Maven**: For building the project.

---

## How to Run the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/AdhikritGupta/file-compression.git
   ```

2. Navigate to the project directory:

   ```bash
   cd file-compression
   ```

3. Update the `application.properties` file with your MongoDB configuration:

   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/file_compression_db
   ```

4. Build and run the application:

   ```bash
   mvn spring-boot:run
   ```

5. The API will be available at `http://localhost:8080`.

---

## API Endpoints

### 1. **Compress PDF File**

#### Request

- **URL**: `/pdf/compress`
- **Method**: `POST`
- **Content-Type**: `multipart/form-data`

#### Sample cURL Command

```bash
curl -X POST \
  -F "username=john_doe" \
  -F "file=@/path/to/your/file.pdf" \
  http://localhost:8080/pdf/compress
```

#### Response

```json
{
  "File successfully compressed and stored in the list."
}
```

---

### 2. **Retrieve All ZIP Files**

#### Request

- **URL**: `/zip/all`
- **Method**: `GET`

#### Response

```json
[
  {
    "id": "12345",
    "fileName": "file.zip",
    "compressionStatus": "COMPRESSED",
    "size": 512
  }
]
```

---

### 3. **View Decompressed PDF File**

#### Request

- **URL**: `/pdf/view/{username}`
- **Method**: `GET`

#### Response

- Returns the PDF file as an inline response.

---

## Project Structure

```
file-compression
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.project.file_compression
│   │   │       ├── controller
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       └── service
│   └── resources
│       ├── application.properties
│       └── static
├── pom.xml
└── README.md
```

---

## Future Enhancements

1. **Support for Additional File Formats**: Extend compression and decompression for other file types.
2. **Authentication**: Add user authentication and role-based access control.
3. **Asynchronous Processing**: Improve performance with asynchronous operations for large files.
4. **Logging**: Add logging using frameworks like Logback or SLF4J.

---

##
