jQuery(document).ready(function ($) {

    $("#loginForm").on("submit", function (e) {
        e.preventDefault();

        let formData = {
            username: $("input[name='username']").val(),
            password: $("input[name='password']").val()
        };

        let messageBox = $("#formMessage");

        $.ajax({
            url: "/bin/sony/login",
            type: "POST",
            data: formData,

            success: function (response) {

                let res = (typeof response === "string")
                    ? JSON.parse(response)
                    : response;

                if (res.status === "success") {

                    // 🔥 STORE USER
                    sessionStorage.setItem("loggedInUser", formData.username);

                    messageBox
                        .css("color", "green")
                        .text("Login successful... Redirecting");

                    setTimeout(function () {
                        window.location.href = "/content/sony/us/en/home-page.html";
                    }, 1000);

                    return;
                }

                if (res.error) {
                    messageBox
                        .css("color", "red")
                        .text(res.error);
                }

            },

            error: function () {
                messageBox.text("Server Error");
            }
        });

    });

});

// jQuery(document).ready(function ($) {

//     $("#loginForm").on("submit", function (e) {
//         e.preventDefault();

//         let formData = {
//             username: $("input[name='username']").val(),
//             password: $("input[name='password']").val()
//         };

    
//         let messageBox = $("#formMessage");

//         $.ajax({
//             url: "/bin/sony/login",
//             type: "POST",
//             data: formData,

//             success: function (response) {

//                 let res = (typeof response === "string")
//                     ? JSON.parse(response)
//                     : response;

//                 if (res.error === "not_found") {

//                     messageBox
//                         .css("color", "red")
//                         .text("User not found. Redirecting to registration...");

//                     setTimeout(function () {
//                         window.location.href = "/content/sony/us/en/form-page.html";
//                     }, 1500);

//                     return;
//                 }

    
//                 if (res.error === "invalid_password") {

//                     messageBox
//                         .css("color", "red")
//                         .text("Incorrect password");

//                     return;
//                 }

//                 if (res.error) {

//                     messageBox
//                         .css("color", "red")
//                         .text(res.error);

//                     return;
//                 }

       
//                 if (res.status === "success") {

//                     messageBox
//                         .css("color", "green")
//                         .css("font-weight", "600")
//                         .text("Login successful! Redirecting...");

//                     sessionStorage.setItem("loggedInUser", formData.username);

//                     setTimeout(function () {
//                         window.location.href = "/content/sony/us/en/home-page.html";
//                     }, 1500);
//                 }

//             },

//             error: function () {
//                 messageBox
//                     .css("color", "red")
//                     .text("Server Error. Please try again later.");
//             }
//         });

//     });

// });

