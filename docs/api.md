### API

#### Product

##### List

- path: /products/

- params:

name| type| notes
----|-----|-----
size|integer|
page|integer|
sort|string|

- Response:

```json
{
    "content": [
        {
            "id": "24d3e1f4-0172-4c5c-a355-8d1e7321b49f",
            "name": "mobile",
            "price": 1.4,
            "categoriesIdList": [],
            "currency": "EUR"
        },
        {
            "id": "2f5fa8ac-c7de-47a2-9b07-2fbcfcd1d5e4",
            "name": "car",
            "price": 12.2,
            "categoriesIdList": [
                "cd269694-c142-4093-8f29-d77b07885d41",
                "c391a849-9d8f-433a-9524-9008935f2587",
                "baec93aa-7460-4701-abb0-4cc8dccd7659",
                "66b21fd9-f9f2-42d6-9a00-6208cac514df"
            ],
            "currency": "EUR"
        },
        {
            "id": "b80fe60c-2e4d-43b9-85db-73c150334ace",
            "name": "bike",
            "price": 12.2,
            "categoriesIdList": [
                "cd269694-c142-4093-8f29-d77b07885d41",
                "c391a849-9d8f-433a-9524-9008935f2587",
                "baec93aa-7460-4701-abb0-4cc8dccd7659",
                "66b21fd9-f9f2-42d6-9a00-6208cac514df"
            ],
            "currency": "EUR"
        }
    ],
    "totalPages": 1,
    "totalElements": 3,
    "last": true,
    "size": 15,
    "number": 0,
    "sort": [
        {
            "direction": "DESC",
            "property": "name",
            "ignoreCase": false,
            "nullHandling": "NATIVE",
            "ascending": false,
            "descending": true
        }
    ],
    "numberOfElements": 3,
    "first": true
}

```


##### create product

- Path: /products/

- Authorization: Basic Auth

- Account: user/password

- Params:

name|type|notes
name|string|
currency|string|
price|number|
categoriesIdList| Array| categoriest UUID array


- Response

```json
{
    "id": "24d3e1f4-0172-4c5c-a355-8d1e7321b49f",
    "name": "mobile",
    "price": 1.4,
    "categoriesIdList": [],
    "currency": "EUR"
}
```


#### Category

#### List all category

- Path: /catergories/

- Params:

name| type| notes
----|-----|-----
size|integer|
page|integer|
sort|string|

- Response 
```json
{
    "content": [
        {
            "id": "baec93aa-7460-4701-abb0-4cc8dccd7659",
            "name": "rabbit"
        },
        {
            "id": "66b21fd9-f9f2-42d6-9a00-6208cac514df",
            "name": "fox"
        },
        {
            "id": "c391a849-9d8f-433a-9524-9008935f2587",
            "name": "dog"
        },
        {
            "id": "cd269694-c142-4093-8f29-d77b07885d41",
            "name": "cat"
        }
    ],
    "totalPages": 1,
    "totalElements": 4,
    "last": true,
    "size": 15,
    "number": 0,
    "sort": [
        {
            "direction": "DESC",
            "property": "name",
            "ignoreCase": false,
            "nullHandling": "NATIVE",
            "ascending": false,
            "descending": true
        }
    ],
    "numberOfElements": 4,
    "first": true
}
```


##### create category

- Path: /categories/

- Method: POST

- Params


name|type|notes
----|----|-----
name|string|required

- Authorization: Basic Auth

- Account: user/password

- Response
```json
{
    "id": "baec93aa-7460-4701-abb0-4cc8dccd7659",
    "name": "rabbit"
}
```