#!/usr/bin/env bash

declare -A public_number_of_users
declare -A public_number_of_products

declare -A user_get_me
declare -A user_get_users
declare -A user_get_user
declare -A user_delete_user

declare -A product_get_products
declare -A product_create_product
declare -A product_delete_product

ADMIN_ACCESS_TOKEN=$(curl -s -X POST localhost:8080/auth/authenticate -H 'Content-Type: application/json' -d '{"username": "admin", "password": "admin"}' | jq -r .accessToken)
USER_ACCESS_TOKEN=$(curl -s -X POST localhost:8080/auth/authenticate -H 'Content-Type: application/json' -d '{"username": "user", "password": "user"}' | jq -r .accessToken)
USER2_ACCESS_TOKEN=$(curl -s -X POST localhost:8080/auth/signup -H 'Content-Type: application/json' -d '{"username": "user2", "password": "user2", "name": "User2", "email": "user2@mycompany.com"}' | jq -r .accessToken)

public_number_of_users[without_creds]=$(curl -w %{http_code} -s -o /dev/null localhost:8080/public/numberOfUsers)
public_number_of_users[user_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $USER_ACCESS_TOKEN" localhost:8080/public/numberOfUsers)
public_number_of_users[admin_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $ADMIN_ACCESS_TOKEN" localhost:8080/public/numberOfUsers)

public_number_of_products[without_creds]=$(curl -w %{http_code} -s -o /dev/null localhost:8080/public/numberOfProducts)
public_number_of_products[user_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $USER_ACCESS_TOKEN" localhost:8080/public/numberOfProducts)
public_number_of_products[admin_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $ADMIN_ACCESS_TOKEN" localhost:8080/public/numberOfProducts)

user_get_me[without_creds]=$(curl -w %{http_code} -s -o /dev/null localhost:8080/api/users/me)
user_get_me[user_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $USER_ACCESS_TOKEN" localhost:8080/api/users/me)
user_get_me[admin_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $ADMIN_ACCESS_TOKEN" localhost:8080/api/users/me)

user_get_users[without_creds]=$(curl -w %{http_code} -s -o /dev/null localhost:8080/api/users)
user_get_users[user_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $USER_ACCESS_TOKEN" localhost:8080/api/users)
user_get_users[admin_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $ADMIN_ACCESS_TOKEN" localhost:8080/api/users)

user_get_user[without_creds]=$(curl -w %{http_code} -s -o /dev/null localhost:8080/api/users/user)
user_get_user[user_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $USER_ACCESS_TOKEN" localhost:8080/api/users/user2)
user_get_user[admin_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $ADMIN_ACCESS_TOKEN" localhost:8080/api/users/user2)

user_delete_user[without_creds]=$(curl -w %{http_code} -s -o /dev/null -X DELETE localhost:8080/api/users/user2)
user_delete_user[user_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $USER_ACCESS_TOKEN" -X DELETE localhost:8080/api/users/user2)
user_delete_user[admin_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $ADMIN_ACCESS_TOKEN" -X DELETE localhost:8080/api/users/user2)

product_get_products[without_creds]=$(curl -w %{http_code} -s -o /dev/null localhost:8080/api/products)
product_get_products[user_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $USER_ACCESS_TOKEN" localhost:8080/api/products)
product_get_products[admin_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $ADMIN_ACCESS_TOKEN" localhost:8080/api/products)

product_create_product[without_creds]=$(curl -w %{http_code} -s -o /dev/null -X POST localhost:8080/api/products -H "Content-Type: application/json" -d '{"title": "American Pie"}')
product_create_product[user_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $USER_ACCESS_TOKEN" -X POST localhost:8080/api/products -H "Content-Type: application/json" -d '{"title": "American Pie"}')
product_create_product[admin_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $ADMIN_ACCESS_TOKEN" -X POST localhost:8080/api/products -H "Content-Type: application/json" -d '{"title": "American Pie"}')

product_delete_product[without_creds]=$(curl -w %{http_code} -s -o /dev/null -X DELETE localhost:8080/api/products/1)
product_delete_product[user_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $USER_ACCESS_TOKEN" -X DELETE localhost:8080/api/products/1)
product_delete_product[admin_creds]=$(curl -w %{http_code} -s -o /dev/null -H "Authorization: Bearer $ADMIN_ACCESS_TOKEN" -X DELETE localhost:8080/api/products/1)

printf "\n"
printf "%s\n" "POST auth/authenticate"
printf "%s\n" "======================"
printf "%s\n" "admin access token"
printf "%s\n" "------------------"
printf "%s\n" ${ADMIN_ACCESS_TOKEN}
printf "\n"
printf "%s\n" "user access token"
printf "%s\n" "-----------------"
printf "%s\n" ${USER_ACCESS_TOKEN}
printf "\n"
printf "%s\n" "POST auth/signup"
printf "%s\n" "================"
printf "%s\n" "user2 access token"
printf "%s\n" "------------------"
printf "%s\n" ${USER2_ACCESS_TOKEN}
printf "\n"
printf "%s\n" "Authorization"
printf "%s\n" "============="
printf "%25s | %13s | %11s | %12s |\n" "Endpoints" "without token" "user token" "admin token"
printf "%25s + %13s + %11s + %12s |\n" "-------------------------" "-------------" "-----------" "------------"
printf "%25s | %13s | %11s | %12s |\n" "GET public/numberOfUsers" ${public_number_of_users[without_creds]} ${public_number_of_users[user_creds]} ${public_number_of_users[admin_creds]}
printf "%25s | %13s | %11s | %12s |\n" "GET public/numberOfProducts" ${public_number_of_products[without_creds]} ${public_number_of_products[user_creds]} ${public_number_of_products[admin_creds]}
printf "%25s + %13s + %11s + %12s |\n" "........................." "............." "..........." "............"
printf "%25s | %13s | %11s | %12s |\n" "GET /api/users/me" ${user_get_me[without_creds]} ${user_get_me[user_creds]} ${user_get_me[admin_creds]}
printf "%25s | %13s | %11s | %12s |\n" "GET /api/users" ${user_get_users[without_creds]} ${user_get_users[user_creds]} ${user_get_users[admin_creds]}
printf "%25s | %13s | %11s | %12s |\n" "GET /api/users/user2" ${user_get_user[without_creds]} ${user_get_user[user_creds]} ${user_get_user[admin_creds]}
printf "%25s | %13s | %11s | %12s |\n" "DELETE /api/users/user2" ${user_delete_user[without_creds]} ${user_delete_user[user_creds]} ${user_delete_user[admin_creds]}
printf "%25s + %13s + %11s + %12s |\n" "........................." "............." "..........." "............"
printf "%25s | %13s | %11s | %12s |\n" "GET /api/products" ${product_get_products[without_creds]} ${product_get_products[user_creds]} ${product_get_products[admin_creds]}
printf "%25s | %13s | %11s | %12s |\n" "POST /api/products" ${product_create_product[without_creds]} ${product_create_product[user_creds]} ${product_create_product[admin_creds]}
printf "%25s | %13s | %11s | %12s |\n" "DELETE /api/products/abc" ${product_delete_product[without_creds]} ${product_delete_product[user_creds]} ${product_delete_product[admin_creds]}
printf "%72s\n" "------------------------------------------------------------------------"
printf " [200] Success -  [201] Created -  [401] Unauthorized -  [403] Forbidden"
printf "\n"