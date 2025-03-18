const PROJECT_NAME = 'Food Maze';

const PORT = 8080;
const API_END_POINT = `http://localhost:${PORT}/api/`;

const adminPages = ['/admin.html', '/adminOrders.html'];

const path = location.pathname;
if (sessionStorage.getItem('loggedIn') === "true" && sessionStorage.getItem('userId') !== null) {
    if (sessionStorage.getItem('admin') === 'true') {
        if (!adminPages.includes(path)) {
            location.href = '/admin.html';
        }
    } else {
        if (adminPages.includes(path)) {
            location.href = '/home.html';
        }
    }
} else {
    if (path !== '/index.html') {
        location.href = '/index.html';
    }
}

window.addEventListener('scroll', () => {
    const header = document.querySelector('.header');
    const floatingHeader = document.querySelector('.floating-header');
    if (floatingHeader.getBoundingClientRect().top <= 0) {
        header.classList.add('black');
    } else {
        header.classList.remove('black');
    }
});

const URL_MAP = {
    createUser: `${API_END_POINT}user`,
    validateUser: `${API_END_POINT}user/validateUser`,
    product: `${API_END_POINT}product`,
    userById: `${API_END_POINT}user/id`,
    updateUser: `${API_END_POINT}user/info`,
    createOrder: `${API_END_POINT}order`,
    userOrders: `${API_END_POINT}order/userId`,
    updateProduct: `${API_END_POINT}product/code/price`,
    deleteProduct: `${API_END_POINT}product/code`,
	analytics: `${API_END_POINT}order/analytics`
};

var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
})

// new
function getStatusBadge(status) {
    let color = "";
    let icon = "";
    
    switch (status) {
        case "Pending":
            color = "orange";
            icon = "⏳"; // Hourglass
            break;
        case "Completed":
            color = "green";
            icon = "✅"; // Checkmark
            break;
        case "Cancelled":
            color = "red";
            icon = "❌"; // Cross
            break;
        default:
            color = "gray";
            icon = "❓"; // Unknown
    }

    return `<span style="color: ${color}; font-weight: bold;">${icon} ${status}</span>`;
}
// new
/*document.addEventListener("DOMContentLoaded", async function () {
    try {
        const response = await axios.get(URL_MAP.analytics);
        const data = response.data;

        // Update cards
        document.getElementById("totalOrders").innerText = data.totalOrders;
        document.getElementById("totalRevenue").innerText = `$${data.totalRevenue.toFixed(2)}`;
        document.getElementById("pendingOrders").innerText = data.pendingOrders;

        // Render Order Status Pie Chart
        const orderStatusChart = new Chart(document.getElementById("orderStatusChart"), {
            type: "pie",
            data: {
                labels: ["Pending", "Completed", "Cancelled"],
                datasets: [{
                    data: [data.pendingOrders, data.completedOrders, data.cancelledOrders],
                    backgroundColor: ["yellow", "green", "red"]
                }]
            },
            options: {
                responsive: true
            }
        });

        // Render Monthly Sales Bar Chart
        const monthlySalesChart = new Chart(document.getElementById("monthlySalesChart"), {
            type: "bar",
            data: {
                labels: Object.keys(data.monthlySales),
                datasets: [{
                    label: "Revenue ($)",
                    data: Object.values(data.monthlySales),
                    backgroundColor: "blue"
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: { beginAtZero: true }
                }
            }
        });

    } catch (error) {
        console.error("Error fetching analytics data", error);
    }
});
*/

document.addEventListener("DOMContentLoaded", async function () {
	fetchAnalytics();
    try {
        // Ensure API fetches latest data by appending a timestamp
        const response = await axios.get(URL_MAP.analytics + "?timestamp=" + new Date().getTime());
        const data = response.data;

        // Update dashboard values
        document.getElementById("totalOrders").innerText = data.totalOrders;
        document.getElementById("totalRevenue").innerText = `$${data.totalRevenue.toFixed(2)}`;
        document.getElementById("pendingOrders").innerText = data.pendingOrders;

        // Destroy previous charts before re-rendering
        if (window.orderStatusChart) window.orderStatusChart.destroy();
        if (window.monthlySalesChart) window.monthlySalesChart.destroy();

        // Render Order Status Pie Chart
        window.orderStatusChart = new Chart(document.getElementById("orderStatusChart"), {
            type: "pie",
            data: {
                labels: ["Pending", "Completed", "Cancelled"],
                datasets: [{
                    data: [data.pendingOrders, data.completedOrders, data.cancelledOrders],
                    backgroundColor: ["yellow", "green", "red"]
                }]
            },
            options: { responsive: true }
        });

        // Render Monthly Sales Bar Chart
        window.monthlySalesChart = new Chart(document.getElementById("monthlySalesChart"), {
            type: "bar",
            data: {
                labels: Object.keys(data.monthlySales),
                datasets: [{
                    label: "Revenue ($)",
                    data: Object.values(data.monthlySales),
                    backgroundColor: "blue"
                }]
            },
            options: {
                responsive: true,
                scales: { y: { beginAtZero: true } }
            }
        });

    } catch (error) {
        console.error("Error fetching analytics data", error);
    }
});
async function fetchAnalytics() {
    try {
        console.log("Fetching latest analytics data..."); // Debugging
        const response = await axios.get(URL_MAP.analytics + "?timestamp=" + new Date().getTime());
        const data = response.data;

        console.log("Analytics Data Received:", data); // Debugging
        
        // Update dashboard values
        document.getElementById("totalOrders").innerText = data.totalOrders;
        document.getElementById("totalRevenue").innerText = `$${data.totalRevenue.toFixed(2)}`;
        document.getElementById("pendingOrders").innerText = data.pendingOrders;
        document.getElementById("completedOrders").innerText = data.completedOrders;
        document.getElementById("cancelledOrders").innerText = data.cancelledOrders;

    } catch (error) {
        console.error("Error fetching analytics data", error);
    }
}

// ✅ Ensure analytics updates when the page loads
document.addEventListener("DOMContentLoaded", fetchAnalytics);
document.addEventListener("DOMContentLoaded", function () {
    // Create the dropdown
    const languageDropdown = `
        <select id="language-select" class="form-select form-select-sm">
            <option value="en">🇺🇸 English</option>
            <option value="es">🇪🇸 Español</option>
            <option value="fr">🇫🇷 Français</option>
            <option value="de">🇩🇪 Deutsch</option>
        </select>
    `;

    // Inject dropdown into all pages that have the header
    const dropdownContainer = document.getElementById("language-dropdown-container");
    if (dropdownContainer) {
        dropdownContainer.innerHTML = languageDropdown;
    }

    // Load saved language preference
    const savedLanguage = localStorage.getItem("selectedLanguage") || "en";
    document.getElementById("language-select").value = savedLanguage;

    // Save language selection & reload page
    document.getElementById("language-select").addEventListener("change", function () {
        localStorage.setItem("selectedLanguage", this.value);
        location.reload(); // Reload to apply language change
    });
});

document.querySelector('.project-name').innerText = PROJECT_NAME;

const translations = {
    en: {
        home: "Home",
        cart: "Cart",
        orders: "Orders",
        profile: "Profile",
        logout: "Logout",
        menuCard: "Menu Card",
        sortBy: "Sort by",
        chooseCategory: "Choose Category",
        searchPlaceholder: "Search...",
        addToCart: "Add to Cart"
    },
    de: {
        home: "Startseite",
        cart: "Warenkorb",
        orders: "Bestellungen",
        profile: "Profil",
        logout: "Abmelden",
        menuCard: "Speisekarte",
        sortBy: "Sortieren nach",
        chooseCategory: "Kategorie wählen",
        searchPlaceholder: "Suchen...",
        addToCart: "In den Warenkorb"
    },
    fr: {
        home: "Accueil",
        cart: "Panier",
        orders: "Commandes",
        profile: "Profil",
        logout: "Déconnexion",
        menuCard: "Menu",
        sortBy: "Trier par",
        chooseCategory: "Choisir une catégorie",
        searchPlaceholder: "Chercher...",
        addToCart: "Ajouter au panier"
    },
    es: {
        home: "Inicio",
        cart: "Carrito",
        orders: "Pedidos",
        profile: "Perfil",
        logout: "Cerrar sesión",
        menuCard: "Menú",
        sortBy: "Ordenar por",
        chooseCategory: "Elegir categoría",
        searchPlaceholder: "Buscar...",
        addToCart: "Añadir al carrito"
    },
    hi: {
        home: "होम",
        cart: "कार्ट",
        orders: "आदेश",
        profile: "प्रोफ़ाइल",
        logout: "लॉग आउट",
        menuCard: "मेनू कार्ड",
        sortBy: "क्रमबद्ध करें",
        chooseCategory: "श्रेणी चुनें",
        searchPlaceholder: "खोजें...",
        addToCart: "कार्ट में डालें"
    },
    te: {
        home: "హోమ్",
        cart: "కార్ట్",
        orders: "ఆర్డర్స్",
        profile: "ప్రొఫైల్",
        logout: "లాగ్ అవుట్",
        menuCard: "మెను కార్డ్",
        sortBy: "క్రమబద్ధీకరించు",
        chooseCategory: "కేటగిరీ ఎంచుకోండి",
        searchPlaceholder: "వెతకండి...",
        addToCart: "కార్ట్‌లో జోడించండి"
    }
};

// Function to update UI with selected language
function updateLanguageUI(language) {
	let title = document.querySelector("h2");

	    if (language === "hi") {
	        title.classList.add("hindi-text");
	    } else if (language === "te") {
	        title.classList.add("telugu-text");
	    } else {
	        title.classList.remove("hindi-text", "telugu-text");
	    }
    document.querySelector("a[href='home.html']").innerText = translations[language].home;
    document.querySelector("a[href='cart.html']").innerText = translations[language].cart;
    document.querySelector("a[href='orders.html']").innerText = translations[language].orders;
    document.querySelector("a[href='profile.html']").innerText = translations[language].profile;
    document.querySelector("a[href='logout.html']").innerText = translations[language].logout;
    document.querySelector("h2").innerText = translations[language].menuCard;
    document.getElementById("sort").options[0].innerText = translations[language].sortBy;
    document.getElementById("category").options[0].innerText = translations[language].chooseCategory;
    document.querySelector(".search-input input").placeholder = translations[language].searchPlaceholder;
    document.querySelectorAll(".add-cart").forEach(button => {
        button.innerText = translations[language].addToCart;
    });
}

// Function to set language
function setLanguage(language) {
    localStorage.setItem("selectedLanguage", language);
    updateLanguageUI(language);
}

// Detect language change
document.getElementById("languageSelect").addEventListener("change", function() {
    setLanguage(this.value);
});

// Apply stored language on page load
document.addEventListener("DOMContentLoaded", function() {
    const savedLanguage = localStorage.getItem("selectedLanguage") || "en";
    document.getElementById("languageSelect").value = savedLanguage;
    updateLanguageUI(savedLanguage);
});