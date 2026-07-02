$(document).ready(function () {

    if ($("#loginBtn").length) {

        $("#loginBtn").on("click", function () {

            const msg = $("#loginMsg");
            msg.css("color", "red").text("");

            const email = $("#loginEmail").val().trim();
            const password = $("#loginPassword").val();

            
            if (!email || !password) {
                msg.text("Please enter email and password.");
                return;
            }

            if (!/^[^ ]+@[^ ]+\.[a-z]{2,}$/i.test(email)) {
                msg.text("Please enter a valid email address.");
                return;
            }

           
            const btn = $("#loginBtn");
            btn.prop("disabled", true);

            $.ajax({
                url: "/bin/userlogin",
                type: "POST", 
                data: {
                    email: email,
                    password: password
                },
                dataType: "json",
                timeout: 10000,

                success: function (data) {
                    console.log("RESPONSE:", data);

                   
                    if (typeof data === "string") {
                        try {
                            data = JSON.parse(data);
                        } catch (e) {
                            msg.text("Invalid server response.");
                            return;
                        }
                    }

                    // if (data.status === "success") {
                    //     msg.css("color", "green").text("Login successful!");

                    //     setTimeout(function () {
                    //         window.location.href = "/content/sony/us/en/home-page.html?wcmmode=disabled";
                    //     }, 500);
                    // }
                    
                    if (data.status === "success") {
    msg.css("color", "green").text("Login successful!");
    setTimeout(function () {
        window.location.href = "/content/sony/us/en/home-page.html?wcmmode=disabled&email=" + encodeURIComponent(email);
    }, 500);
}
                     else {
                        msg.text(data.message || "Login failed.");
                    }
                },

                error: function (xhr, status, error) {
                    console.log("STATUS:", xhr.status);
                    console.log("ERROR:", error);
                    console.log("RESPONSE:", xhr.responseText);

                    if (status === "timeout") {
                        msg.text("Request timed out. Please try again.");
                    } else if (xhr.status === 401) {
                        msg.text("Invalid credentials.");
                    } else if (xhr.status >= 500) {
                        msg.text("Server error. Please try later.");
                    } else {
                        msg.text("Something went wrong. Please try again.");
                    }
                },

                complete: function () {
                    btn.prop("disabled", false);
                }
            });

        });
    }

});

