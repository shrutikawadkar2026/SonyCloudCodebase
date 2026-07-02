$(document).ready(function () {

    
    $("#dob").on("change input", function () {
        var val = $(this).val();
        if (!val) {
            $("#age").val("");
            return;
        }

        var birth = new Date(val);
        var today = new Date();
        var age   = today.getFullYear() - birth.getFullYear();
        var m     = today.getMonth() - birth.getMonth();

        if (m < 0 || (m === 0 && today.getDate() < birth.getDate())) {
            age--;
        }

        if (isNaN(age) || age < 0 || age > 120) {
            $("#age").val("");
        } else {
            $("#age").val(age);
        }
    });

   
    $("#regEmail").on("blur", function () {
        var email = $(this).val().trim();
        if (!email) return;

        var base = email.split("@")[0]
                        .replace(/[^a-zA-Z0-9]/g, "")
                        .toLowerCase();

        if (!base) return;

        var rand = Math.floor(1000 + Math.random() * 9000);
        $("#username").val(base + "_" + rand);
    });

 
    $("#regBtn").on("click", function () {

        var msg = $("#regMsg");
        msg.css("color", "red").text("");

        var firstName = $("#firstName").val().trim();
        var lastName  = $("#lastName").val().trim();
        var email     = $("#regEmail").val().trim();
        var username  = $("#username").val().trim();
        var mobile    = $("#mobile").val().trim();
        var password  = $("#password").val();
        var age       = parseInt($("#age").val(), 10);
        var dob       = $("#dob").val();

        if (!firstName) {
            msg.text("First Name is required.");
            return;
        }
        if (!email) {
            msg.text("Email is required.");
            return;
        }
        if (!dob) {
            msg.text("Please select your Date of Birth.");
            return;
        }
        if (isNaN(age) || age < 15) {
            msg.text("You must be at least 15 years old.");
            return;
        }

        var digitsOnly = mobile.replace(/\D/g, "");
        if (digitsOnly.length !== 10) {
            msg.text("Please enter a valid 10-digit mobile number.");
            return;
        }

        if (!/^[^\s@]+@[^\s@]+\.[a-zA-Z]{2,}$/.test(email)) {
            msg.text("Please enter a valid email address.");
            return;
        }

        if (!password || password.length < 6) {
            msg.text("Password must be at least 6 characters.");
            return;
        }

        if (!username) {
            msg.text("Please click on the email field and tab out to generate username.");
            return;
        }

        $("#regBtn").prop("disabled", true).text("Submitting...");

        $.ajax({
            url: "/bin/register",
            method: "POST",
            data: {
                firstName : firstName,
                lastName  : lastName,
                email     : email,
                username  : username,
                mobile    : digitsOnly,
                password  : password,
                age       : age,
                dob       : dob
            },
            success: function (data) {
                if (typeof data === "string") {
                    try { data = JSON.parse(data); } catch (e) {
                        msg.text("Unexpected server response.");
                        $("#regBtn").prop("disabled", false).text("Submit Application");
                        return;
                    }
                }
                if (data.status === "success") {
                    window.location.href =
                        "/content/sony/us/en/registration-page/login-page.html?wcmmode=disabled";
                } else {
                    msg.css("color", "red").text(data.message || "Registration failed.");
                    $("#regBtn").prop("disabled", false).text("Submit Application");
                }
            },
            error: function (xhr) {
                console.error("AJAX Error:", xhr.status, xhr.responseText);
                msg.text("Something went wrong. Please try again.");
                $("#regBtn").prop("disabled", false).text("Submit Application");
            }
        });
    });

});