document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("registrationForm");
    const msg = document.getElementById("regMsg");
    const dob = document.getElementById("dob");
    const ageField = document.getElementById("age");

    // ✅ AGE CALCULATION
    dob.addEventListener("change", function () {
        let birthDate = new Date(this.value);
        let today = new Date();

        let age = today.getFullYear() - birthDate.getFullYear();
        let m = today.getMonth() - birthDate.getMonth();

        if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }

        ageField.value = age;
    });

    // ✅ FORM SUBMIT
    form.addEventListener("submit", function (e) {
        e.preventDefault();

        msg.innerText = "";
        msg.style.color = "red";

        let firstName = form.firstName.value.trim();
        let email = form.email.value.trim();
        let password = form.password.value;
        let mobile = form.mobile.value.trim();
        let age = ageField.value;

        // 🔹 VALIDATION
        if (!firstName || !email || !password) {
            msg.innerText = "Fill all required fields";
            return;
        }

        if (!age || age < 15) {
            msg.innerText = "Age must be 15+";
            return;
        }

        if (!/^[0-9]{10}$/.test(mobile)) {
            msg.innerText = "Mobile must be 10 digits";
            return;
        }

        if (!/^[^ ]+@[^ ]+\.[a-z]{2,3}$/.test(email)) {
            msg.innerText = "Invalid email";
            return;
        }

        if (password.length < 6) {
            msg.innerText = "Password must be 6+ chars";
            return;
        }

        // 🔥 GET CSRF TOKEN FIRST
        fetch("/libs/granite/csrf/token.json")
        .then(res => res.json())
        .then(tokenData => {

            let formData = new FormData(form);

            // ✅ ADD CSRF TOKEN
            formData.append(":cq_csrf_token", tokenData.token);

            // ✅ CORRECT URL (NO .html)
            let url = window.location.pathname.replace(".html", "") + ".registration";

            return fetch(url, {
                method: "POST",
                body: formData
            });

        })
        .then(res => {
            console.log("STATUS:", res.status);

            if (!res.ok) {
                throw new Error("HTTP error " + res.status);
            }

            return res.json();
        })
        .then(data => {
            console.log("RESPONSE:", data);

            msg.style.color = data.status === "success" ? "green" : "red";
            msg.innerText = data.message;

            if (data.status === "success") {
                form.reset();
                ageField.value = "";
            }
        })
        .catch(err => {
            console.error("ERROR:", err);
            msg.innerText = "Something went wrong";
        });

    });

});