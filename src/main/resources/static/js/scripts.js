document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("reviewForm");
    const alertDiv = document.getElementById("youMustLoginDiv");
    const userFullNameInput = form.querySelector("input[name='loggedInUserFullName']");

    const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    if (!form) return;


    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const userFullName = userFullNameInput.value.trim();

        // User not logged in
        if (!userFullName) {
            e.preventDefault(); // Stop form submission
            alertDiv.style.display = "block"; // Show alert

            // Hide after 5 seconds
            setTimeout(() => {
                alertDiv.style.display = "none";
            }, 5000);

            return;
        }

        const bookId = form.getAttribute("data-bookId");
        const formData = new FormData(form);

        fetch(`/books/${bookId}/reviews`, {
            method: "POST",
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                [header]: token
            },
            body: formData
        })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url;
                    return;
                }
                return response.text();
            })
            .then(html => {
                if (html) {
                    const reviewList = document.getElementById("reviewList");
                    const tempDiv = document.createElement("div");
                    tempDiv.innerHTML = html.trim();
                    const newReview = tempDiv.firstChild;

                    newReview.classList.add("fade-in");
                    reviewList.querySelector(".list-group").prepend(newReview);
                    form.reset();
                }
            })
            .catch(err => alert("Error: " + err));
    });
});


document.addEventListener("DOMContentLoaded", function () {
    const loginBtn = document.getElementById("loginBtn");
    if (loginBtn) {
        loginBtn.addEventListener("click", function (e) {
            e.preventDefault();
            const currentUrl = window.location.pathname + window.location.search;
            window.location.href = `/login?returnTo=${encodeURIComponent(currentUrl)}`;
        });
    }
});
document.addEventListener("DOMContentLoaded", function () {
    const toggleBtn = document.getElementById('userToggleBtn');
    const userMenu = document.getElementById('userMenu');

    if (toggleBtn) {
        toggleBtn.addEventListener('click', function (e) {
            e.stopPropagation();
            userMenu.classList.toggle('show');
        });
    }

    // Hide dropdown when clicking outside
    if (userMenu) {
        window.addEventListener('click', function () {
            if (userMenu.classList.contains('show')) {
                userMenu.classList.remove('show');
            }
        });
    }

});


document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("reviewForm");
    const alertDiv = document.getElementById("youMustLoginDiv");
    const userFullNameInput = form.querySelector("input[name='loggedInUserFullName']");

    form.addEventListener("submit", function (e) {
        const userFullName = userFullNameInput.value.trim();

        if (!userFullName) {
            e.preventDefault(); // Stop form submission
            alertDiv.style.display = "block"; // Show alert

            // Hide after 5 seconds
            setTimeout(() => {
                alertDiv.style.display = "none";
            }, 5000);
        } else {
            alertDiv.style.display = "none"; // Hide if user is logged in
        }
    });
});



