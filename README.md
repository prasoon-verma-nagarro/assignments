##Purpose

*This program is responsible to calculate percentage based discount on the basis of user type.
* If the user is an employee of the store, he gets a 30% discount
* If the user is an affiliate of the store, he gets a 10% discount
* If the user has been a customer for over 2 years, he gets a 5% discount.
* For every $100 on the bill, there would be a $ 5 discount (e.g. for $ 990, you get $ 45
as a discount)

##Example Request
http://localhost:8080/retail-store/discount/calculate

{
"billId": 1,
"user": {
   "name": "Donald",
   "address": "770/71 Earth",
   "type": "CUSTOMER",
   "userSince": "2017-11-10"
},
"items": [{"name": "TABLES", "price": 2000.00, "quantity": 2, "type": "Furniture"}, {"name": "Deo", "price": 200.00, "quantity": 1, "type": "Cosmetics"}, {"name": "MILK", "price": 40.00, "quantity": 1, "type": "Groceries"}]
}

##Response
{
    "netPayableAmount": 3830,
    "billId": 1
}

##In case f any failure error handling is present.
*USER info is mandatory to check any discount. If user is not present then it will show 400 BAD request with proper error message.
*USER_TYPE is mandatory in USER because discount critiria is beased on user type. If user type is missing then it will show 400 BAD request with eror message.