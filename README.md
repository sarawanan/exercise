# Exercise

### NOTE: Please use any client tool like postman

1. to `upload csv`: (POST) http://localhost:8080/api/upload and attach csv file as form-data
2. to `download csv with all records`: (GET) http://localhost:8080/api/downloadAll
3. to `download csv by code`: (GET) http://localhost:8080/api/downloadByCode/{code} pass the {code} as path parameter
4. to `delete all records`: (DELETE) http://localhost:8080/api/deleteAll

Please NOTE: I haven't added unit tests to this exercise as it was not stated in the requirement. But usually I do add unit tests as best practice.
