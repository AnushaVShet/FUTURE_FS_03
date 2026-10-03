ANVÉRA FRONTEND V2
===================

This is the replacement premium frontend for the ANVÉRA fashion-store project.

FILES
- index.html      Premium homepage
- shop.html       Functional product shop
- product.html    Product detail page
- cart.html       Working localStorage bag
- *.css / *.js    Supporting styles/scripts

BACKEND
The frontend expects your Spring Boot API at:
http://localhost:8084/api/products

IMPORTANT
1. Keep Spring Boot running on port 8084.
2. Replace the old files inside your project's frontend folder with these files.
3. Open index.html with VS Code Live Server.
4. Shop products are fetched from MySQL through your existing Spring Boot API.
5. Cart is stored in browser localStorage for now.

IMAGE NOTE
The product database's existing image_url values take priority. If a product has no usable image URL, the frontend uses an editorial fallback image.

NEXT IMPLEMENTATION
Checkout -> customer/order/order_items
My Orders
Admin login/dashboard/products/orders
