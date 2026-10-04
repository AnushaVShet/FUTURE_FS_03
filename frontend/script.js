/* =========================================================
   ANVÉRA — HOMEPAGE JAVASCRIPT
   ========================================================= */

const API_URL = "https://futurefs03-production-a9fd.up.railway.app/api";


// =========================================================
// CART
// =========================================================

let cart = JSON.parse(localStorage.getItem("anveraCart")) || [];

function saveCart() {
    localStorage.setItem("anveraCart", JSON.stringify(cart));
    updateBagCount();
}

function updateBagCount() {
    const bagCount = document.getElementById("bagCount");

    if (!bagCount) return;

    const totalItems = cart.reduce(
        (total, item) => total + Number(item.quantity || 1),
        0
    );

    bagCount.textContent = totalItems;
}


// =========================================================
// ADD TO BAG
// =========================================================

function addToBag(product) {

    const existingItem = cart.find(
        item => item.id === product.id
    );

    if (existingItem) {

        existingItem.quantity += 1;

    } else {

        cart.push({
            id: product.id,
            name: product.name,
            category: product.category,
            price: product.price,
            imageUrl: product.imageUrl,
            sizes: product.sizes,
            quantity: 1
        });

    }

    saveCart();

    showNotification(
        `${product.name} added to your bag`
    );
}


// =========================================================
// PRODUCT IMAGE
// =========================================================

function getProductImage(product) {

    /*
       If your MySQL product has an image URL,
       use it.

       Otherwise use the Pinterest images
       you placed inside frontend/images/
    */

    if (
        product.imageUrl &&
        product.imageUrl.trim() !== ""
    ) {
        return product.imageUrl;
    }

    const category =
        String(product.category || "").toLowerCase();

    if (category.includes("saree")) {
        return "images/saree.jpg";
    }

    if (category.includes("co-ord")) {
        return "images/coord.jpg";
    }

    if (category.includes("festive")) {
        return "images/festive.jpg";
    }

    return "images/kurta.jpg";
}


// =========================================================
// DISPLAY PRODUCTS
// =========================================================

function displayProducts(products) {

    const container =
        document.getElementById("productContainer");

    if (!container) return;

    container.innerHTML = "";

    /*
       Show the first 4 products on homepage.
       The complete collection will appear
       on the Shop page.
    */

    const productsToShow =
        products.slice(0, 4);


    if (productsToShow.length === 0) {

        container.innerHTML = `
            <div class="no-products">
                <p>No new arrivals available.</p>
            </div>
        `;

        return;
    }


    productsToShow.forEach(product => {

        const card =
            document.createElement("article");

        card.className = "product-card";


        card.innerHTML = `

            <div class="product-image">

                <img
                    src="${getProductImage(product)}"
                    alt="${escapeHTML(product.name)}"
                    onerror="this.src='images/kurta.jpg'"
                >

            </div>


            <div class="product-info">

                <div class="product-category">
                    ${escapeHTML(product.category)}
                </div>

                <div class="product-name">
                    ${escapeHTML(product.name)}
                </div>

                <div class="product-price">
                    ₹${Number(product.price).toLocaleString("en-IN")}
                </div>

                <button
                    class="add-to-bag"
                    data-product-id="${product.id}"
                >
                    ADD TO BAG
                </button>

            </div>
        `;


        const button =
            card.querySelector(".add-to-bag");


        button.addEventListener(
            "click",
            function () {

                addToBag(product);

            }
        );


        container.appendChild(card);

    });
}


// =========================================================
// LOAD PRODUCTS FROM SPRING BOOT
// =========================================================

async function loadProducts() {

    const container =
        document.getElementById("productContainer");

    if (!container) return;


    try {

        container.innerHTML = `
            <div class="loading-products">
                Loading new arrivals...
            </div>
        `;


        const response =
            await fetch(`${API_URL}/products`);


        if (!response.ok) {

            throw new Error(
                "Unable to load products"
            );

        }


        const products =
            await response.json();


        displayProducts(products);


    } catch (error) {

        console.error(
            "Product loading error:",
            error
        );


        container.innerHTML = `

            <div class="no-products">

                <p>
                    Our collection is temporarily unavailable.
                </p>

                <small>
                    Please make sure the Spring Boot backend
                    is running on port 8084.
                </small>

            </div>

        `;

    }
}


// =========================================================
// SEARCH
// =========================================================

function setupSearch() {

    const searchButton =
        document.getElementById("searchButton");

    if (!searchButton) return;


    searchButton.addEventListener(
        "click",
        function () {

            const searchTerm =
                prompt("What are you looking for?");


            if (
                searchTerm &&
                searchTerm.trim() !== ""
            ) {

                window.location.href =
                    `shop.html?search=${encodeURIComponent(
                        searchTerm.trim()
                    )}`;

            }

        }
    );
}


// =========================================================
// NEWSLETTER
// =========================================================

function setupNewsletter() {

    const form =
        document.getElementById("newsletterForm");

    if (!form) return;


    form.addEventListener(
        "submit",
        function (event) {

            event.preventDefault();


            const email =
                form.querySelector("input").value;


            if (!email) return;


            showNotification(
                "Thank you for joining ANVÉRA."
            );


            form.reset();

        }
    );
}


// =========================================================
// NOTIFICATION
// =========================================================

function showNotification(message) {

    let notification =
        document.getElementById(
            "anveraNotification"
        );


    if (!notification) {

        notification =
            document.createElement("div");

        notification.id =
            "anveraNotification";


        notification.style.position =
            "fixed";

        notification.style.bottom =
            "25px";

        notification.style.right =
            "25px";

        notification.style.background =
            "#394332";

        notification.style.color =
            "#ffffff";

        notification.style.padding =
            "14px 20px";

        notification.style.fontSize =
            "12px";

        notification.style.letterSpacing =
            "0.05em";

        notification.style.zIndex =
            "9999";

        notification.style.boxShadow =
            "0 8px 30px rgba(0,0,0,0.15)";


        document.body.appendChild(
            notification
        );

    }


    notification.textContent =
        message;


    notification.style.opacity =
        "1";


    clearTimeout(
        window.anveraNotificationTimer
    );


    window.anveraNotificationTimer =
        setTimeout(
            function () {

                notification.style.opacity =
                    "0";

            },
            2500
        );
}


// =========================================================
// HTML SECURITY
// =========================================================

function escapeHTML(value) {

    return String(value ?? "")
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}


// =========================================================
// START WEBSITE
// =========================================================

document.addEventListener(
    "DOMContentLoaded",
    function () {

        updateBagCount();

        loadProducts();

        setupSearch();

        setupNewsletter();

    }
);
