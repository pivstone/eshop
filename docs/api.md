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

- Role: Anonymous

```json
{
    "links": [
        {
            "rel": "self",
            "href": "http://localhost:8080/products?page=0&size=10&sort=name,asc"
        }
    ],
    "content": [
        {
            "id": "b80fe60c-2e4d-43b9-85db-73c150334ace",
            "name": "bike",
            "category": [
                {
                    "id": "baec93aa-7460-4701-abb0-4cc8dccd7659",
                    "name": "rabbit"
                },
                {
                    "id": "cd269694-c142-4093-8f29-d77b07885d41",
                    "name": "cat"
                },
                {
                    "id": "c391a849-9d8f-433a-9524-9008935f2587",
                    "name": "dog"
                },
                {
                    "id": "66b21fd9-f9f2-42d6-9a00-6208cac514df",
                    "name": "fox"
                }
            ],
            "price": 12.2,
            "currency": "EUR",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/products/b80fe60c-2e4d-43b9-85db-73c150334ace"
                },
                {
                    "rel": "list",
                    "href": "http://localhost:8080/products/"
                }
            ]
        },
        {
            "id": "2f5fa8ac-c7de-47a2-9b07-2fbcfcd1d5e4",
            "name": "car",
            "category": [
                {
                    "id": "baec93aa-7460-4701-abb0-4cc8dccd7659",
                    "name": "rabbit"
                },
                {
                    "id": "cd269694-c142-4093-8f29-d77b07885d41",
                    "name": "cat"
                },
                {
                    "id": "c391a849-9d8f-433a-9524-9008935f2587",
                    "name": "dog"
                },
                {
                    "id": "66b21fd9-f9f2-42d6-9a00-6208cac514df",
                    "name": "fox"
                }
            ],
            "price": 12.2,
            "currency": "EUR",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/products/2f5fa8ac-c7de-47a2-9b07-2fbcfcd1d5e4"
                },
                {
                    "rel": "list",
                    "href": "http://localhost:8080/products/"
                }
            ]
        },
        {
            "id": "24d3e1f4-0172-4c5c-a355-8d1e7321b49f",
            "name": "mobile",
            "category": [],
            "price": 11.2,
            "currency": "EUR",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/products/24d3e1f4-0172-4c5c-a355-8d1e7321b49f"
                },
                {
                    "rel": "list",
                    "href": "http://localhost:8080/products/"
                }
            ]
        }
    ],
    "page": {
        "size": 10,
        "totalElements": 3,
        "totalPages": 1,
        "number": 0
    }
}

```

##### retrieve a product

- Path: /products/{productId}

- Method: GET

- Role: Anonymous

- Params: None

- Reponse

```json
{
    "createdAt": 1509883496809,
    "updateAt": 1509885140376,
    "id": "9ad3b35e-5f10-4e7b-98b7-89e046075368",
    "name": "car",
    "category": [
        {
            "createdAt": 1509883496674,
            "updateAt": 1509883496674,
            "id": "b3532e06-6439-41e9-af6a-b8451f30b2c3",
            "name": "fox"
        }
    ],
    "price": 10.2,
    "currency": "EUR",
    "links": [
        {
            "rel": "self",
            "href": "http://localhost:8080/products/9ad3b35e-5f10-4e7b-98b7-89e046075368"
        },
        {
            "rel": "list",
            "href": "http://localhost:8080/products/"
        }
    ]
}
```

##### create product

- Path: /products/

- Role: Admin

- Method: POST

- Params:


name|type|notes
----|-----|-----
name|string| required
currency|string| default: EUR
price|number|min: 0.01
categoriesIdList| Array| categoriest UUID array

- Request Example
```json
{
    "name": "car",
    "price": 10.2,
    "currency": "EUR",
    "categoriesIdList":[
    	"b3532e06-6439-41e9-af6a-b8451f30b2c3"
    ]
}
```

- Response

```json
{
    "createdAt": 1509883496809,
    "updateAt": 1509885140376,
    "id": "9ad3b35e-5f10-4e7b-98b7-89e046075368",
    "name": "car",
    "category": [
        {
            "createdAt": 1509883496674,
            "updateAt": 1509883496674,
            "id": "b3532e06-6439-41e9-af6a-b8451f30b2c3",
            "name": "fox"
        }
    ],
    "price": 10.2,
    "currency": "EUR",
    "links": [
        {
            "rel": "self",
            "href": "http://localhost:8080/products/9ad3b35e-5f10-4e7b-98b7-89e046075368"
        },
        {
            "rel": "list",
            "href": "http://localhost:8080/products/"
        }
    ]
}
```

##### Update a product

- Path: /products/{productId}

- Role: Admin

- Method: PUT

- Params:

name|type|notes
----|-----|-----
name|string| required
currency|string| default: EUR
price|number|min: 0.01
categoriesIdList| Array| categoriest UUID array


- Request Example
```json
{
    "name": "car",
    "price": 10.2,
    "currency": "EUR",
    "categoriesIdList":[
    	"b3532e06-6439-41e9-af6a-b8451f30b2c3"
    ]
}
```

- Response

```json
{
    "createdAt": 1509883496809,
    "updateAt": 1509885140376,
    "id": "9ad3b35e-5f10-4e7b-98b7-89e046075368",
    "name": "car",
    "category": [
        {
            "createdAt": 1509883496674,
            "updateAt": 1509883496674,
            "id": "b3532e06-6439-41e9-af6a-b8451f30b2c3",
            "name": "fox"
        }
    ],
    "price": 10.2,
    "currency": "EUR",
    "links": [
        {
            "rel": "self",
            "href": "http://localhost:8080/products/9ad3b35e-5f10-4e7b-98b7-89e046075368"
        },
        {
            "rel": "list",
            "href": "http://localhost:8080/products/"
        }
    ]
}
```


##### Partial update a product

- Path: /products/{productId}

- Role: Admin

- Method: PATCH

- Params:

name|type|notes
----|-----|-----
name|string| required
currency|string| default: EUR
price|number|min: 0.01


- Request Example
```json
{
    "name": "bike"
}
```

- Response

```json
{
    "createdAt": 1509883496809,
    "updateAt": 1509885140376,
    "id": "9ad3b35e-5f10-4e7b-98b7-89e046075368",
    "name": "bike",
    "category": [
        {
            "createdAt": 1509883496674,
            "updateAt": 1509883496674,
            "id": "b3532e06-6439-41e9-af6a-b8451f30b2c3",
            "name": "fox"
        }
    ],
    "price": 10.2,
    "currency": "EUR",
    "links": [
        {
            "rel": "self",
            "href": "http://localhost:8080/products/9ad3b35e-5f10-4e7b-98b7-89e046075368"
        },
        {
            "rel": "list",
            "href": "http://localhost:8080/products/"
        }
    ]
}
```

##### Add a category into a product

- Path: /products/{productId}/categories/{categoryId}

- Role: Admin

- Method: PUT

- Response: 204 (the category already in the product's category list)

- Response: 201 (a new adding)


##### Remove a category from a product

- Path: /products/{productId}/categories/{categoryId}

- Role: Admin

- Method: DELETE

- Response: 204

- Response: 404 (the category not in product's category list


##### Check exists in a product

- Path: /products/{productId}/categories/{categoryId}

- Role: Admin

- Method: HEAD

- Response: 204 exists

- Response: 404 not found



##### Delete a product

- Path: /products/{productId}

- Role: Admin

- Method: DELETE

- Params: None

- Reponse: 204

##### Check a product exists?

- Path: /products/{productId}

- Role: Admin

- Method: HEAD

- Params: None

- Reponse: 204

#### Category

##### List all category

- Path: /catergories/

- Role: Anonymous

- Params:

name| type| notes
----|-----|-----
size|integer|
page|integer|
sort|string|

- Response 
```json
{
    "links": [
        {
            "rel": "self",
            "href": "http://localhost:8080/categories?page=0&size=10"
        }
    ],
    "content": [
        {
            "id": "66b21fd9-f9f2-42d6-9a00-6208cac514df",
            "name": "fox",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/categories/66b21fd9-f9f2-42d6-9a00-6208cac514df"
                },
                {
                    "rel": "list",
                    "href": "http://localhost:8080/categories/"
                },
                {
                    "rel": "products",
                    "href": "http://localhost:8080/categories/66b21fd9-f9f2-42d6-9a00-6208cac514df/products"
                }
            ]
        },
        {
            "id": "baec93aa-7460-4701-abb0-4cc8dccd7659",
            "name": "rabbit",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/categories/baec93aa-7460-4701-abb0-4cc8dccd7659"
                },
                {
                    "rel": "list",
                    "href": "http://localhost:8080/categories/"
                },
                {
                    "rel": "products",
                    "href": "http://localhost:8080/categories/baec93aa-7460-4701-abb0-4cc8dccd7659/products"
                }
            ]
        },
        {
            "id": "cd269694-c142-4093-8f29-d77b07885d41",
            "name": "cat",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/categories/cd269694-c142-4093-8f29-d77b07885d41"
                },
                {
                    "rel": "list",
                    "href": "http://localhost:8080/categories/"
                },
                {
                    "rel": "products",
                    "href": "http://localhost:8080/categories/cd269694-c142-4093-8f29-d77b07885d41/products"
                }
            ]
        },
        {
            "id": "c391a849-9d8f-433a-9524-9008935f2587",
            "name": "dog",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/categories/c391a849-9d8f-433a-9524-9008935f2587"
                },
                {
                    "rel": "list",
                    "href": "http://localhost:8080/categories/"
                },
                {
                    "rel": "products",
                    "href": "http://localhost:8080/categories/c391a849-9d8f-433a-9524-9008935f2587/products"
                }
            ]
        }
    ],
    "page": {
        "size": 10,
        "totalElements": 4,
        "totalPages": 1,
        "number": 0
    }
}
```

##### Retrieve a category

- Path: /categories/{categoryId}

- Method: GET

- Role: Anonymous

- Params: None

- Response
```json
{
    "createdAt": 1509883496674,
    "updateAt": 1509883496674,
    "id": "b3532e06-6439-41e9-af6a-b8451f30b2c3",
    "name": "fox",
    "links": [
        {
            "rel": "self",
            "href": "http://localhost:8080/categories/b3532e06-6439-41e9-af6a-b8451f30b2c3"
        },
        {
            "rel": "list",
            "href": "http://localhost:8080/categories/"
        },
        {
            "rel": "products",
            "href": "http://localhost:8080/categories/b3532e06-6439-41e9-af6a-b8451f30b2c3/products"
        }
    ]
}
```

##### create category

- Path: /categories/

- Method: POST

- Role: Admin

- Params


name|type|notes
----|----|-----
name|string|required

- Authorization: Basic Auth

- Account: user/password

- Response
```json
{
    "createdAt": 1509883496674,
    "updateAt": 1509883496674,
    "id": "b3532e06-6439-41e9-af6a-b8451f30b2c3",
    "name": "fox",
    "links": [
        {
            "rel": "self",
            "href": "http://localhost:8080/categories/b3532e06-6439-41e9-af6a-b8451f30b2c3"
        },
        {
            "rel": "list",
            "href": "http://localhost:8080/categories/"
        },
        {
            "rel": "products",
            "href": "http://localhost:8080/categories/b3532e06-6439-41e9-af6a-b8451f30b2c3/products"
        }
    ]
}
```


##### Delete a category

- Path: /categoris/{categoryId}

- Role: Admin

- Method: DELETE

- Params: None

- Reponse: 204



##### Check a category exists

- Path: /categoris/{categoryId}

- Role: Admin

- Method: HEAD

- Params: None

- Reponse: 204


##### List all products under the category

- Path: /categoris/{categoryId}/products

- Method: GET

- Role: anonymous

- Reponse:

```json
{
    "links": [
        {
            "rel": "self",
            "href": "http://localhost:8080/products?page=0&size=10&sort=name,asc"
        }
    ],
    "content": [
        {
            "createdAt": 1509884996371,
            "updateAt": 1509884996371,
            "id": "59af75ba-d9df-4853-974d-89edc5a83d52",
            "name": "bike",
            "category": [
                {
                    "createdAt": 1509883496763,
                    "updateAt": 1509883496763,
                    "id": "d5822892-fe26-441f-95bc-ca43ec6d3159",
                    "name": "cat"
                },
                {
                    "createdAt": 1509883496674,
                    "updateAt": 1509883496674,
                    "id": "b3532e06-6439-41e9-af6a-b8451f30b2c3",
                    "name": "fox"
                },
                {
                    "createdAt": 1509883496754,
                    "updateAt": 1509883496754,
                    "id": "afe64443-dadd-4345-a797-3be09752ca53",
                    "name": "rabbit"
                },
                {
                    "createdAt": 1509883496770,
                    "updateAt": 1509883496770,
                    "id": "b59caec5-d13c-4d19-92dd-86c91d6a2bef",
                    "name": "dog"
                }
            ],
            "price": 12.2,
            "currency": "EUR",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/products/59af75ba-d9df-4853-974d-89edc5a83d52"
                },
                {
                    "rel": "list",
                    "href": "http://localhost:8080/products/"
                }
            ]
        },
        {
            "createdAt": 1509883496809,
            "updateAt": 1509885140376,
            "id": "9ad3b35e-5f10-4e7b-98b7-89e046075368",
            "name": "car",
            "category": [
                {
                    "createdAt": 1509883496674,
                    "updateAt": 1509883496674,
                    "id": "b3532e06-6439-41e9-af6a-b8451f30b2c3",
                    "name": "fox"
                }
            ],
            "price": 10.2,
            "currency": "EUR",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/products/9ad3b35e-5f10-4e7b-98b7-89e046075368"
                },
                {
                    "rel": "list",
                    "href": "http://localhost:8080/products/"
                }
            ]
        },
        {
            "createdAt": 1509883496881,
            "updateAt": 1509883496881,
            "id": "4e17b845-9520-4fa2-8870-ec7b31af949b",
            "name": "mobile",
            "category": [
                {
                    "createdAt": 1509883496763,
                    "updateAt": 1509883496763,
                    "id": "d5822892-fe26-441f-95bc-ca43ec6d3159",
                    "name": "cat"
                },
                {
                    "createdAt": 1509883496674,
                    "updateAt": 1509883496674,
                    "id": "b3532e06-6439-41e9-af6a-b8451f30b2c3",
                    "name": "fox"
                },
                {
                    "createdAt": 1509883496754,
                    "updateAt": 1509883496754,
                    "id": "afe64443-dadd-4345-a797-3be09752ca53",
                    "name": "rabbit"
                },
                {
                    "createdAt": 1509883496770,
                    "updateAt": 1509883496770,
                    "id": "b59caec5-d13c-4d19-92dd-86c91d6a2bef",
                    "name": "dog"
                }
            ],
            "price": 12.2,
            "currency": "EUR",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/products/4e17b845-9520-4fa2-8870-ec7b31af949b"
                },
                {
                    "rel": "list",
                    "href": "http://localhost:8080/products/"
                }
            ]
        },
        {
            "createdAt": 1509883496859,
            "updateAt": 1509883524908,
            "id": "d77d459a-73ce-454a-b2af-0704428bece5",
            "name": "mobile",
            "category": [],
            "price": 10.2,
            "currency": "EUR",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/products/d77d459a-73ce-454a-b2af-0704428bece5"
                },
                {
                    "rel": "list",
                    "href": "http://localhost:8080/products/"
                }
            ]
        }
    ],
    "page": {
        "size": 10,
        "totalElements": 4,
        "totalPages": 1,
        "number": 0
    }
}
```

#### Account

##### Admin Login

- Path: /login

