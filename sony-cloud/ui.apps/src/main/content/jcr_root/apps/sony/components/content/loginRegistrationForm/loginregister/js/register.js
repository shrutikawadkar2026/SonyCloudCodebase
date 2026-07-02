jQuery(document).ready(function ($) {

   
    $("#registrationForm").on("submit", function (e) {
        e.preventDefault();

        let formData = {
            name: $("input[name='name']").val(),
            username: $("input[name='username']").val(),
            email: $("input[name='email']").val(),
            password: $("input[name='password']").val()
        };

        $.ajax({
            url: "/bin/sony/register",
            type: "POST",
            data: formData,

            success: function (response) {

                let res = (typeof response === "string")
                    ? JSON.parse(response)
                    : response;

                if (res.error) {
                    alert(res.error);
                    return;
                }

                if (res.status === "success") {
                    alert("Registration Successful!");
                    $("#registrationForm")[0].reset();
                    window.location.href = "/content/sony/us/en/login-page.html";
                }
            },

            error: function () {
                alert("Server Error");
            }
        });

    });

});



window.goToLogin = function () {
    window.location.href = "/content/sony/us/en/login-page.html";
};


// jQuery(document).ready(function ($) {

//     $("#registrationForm").on("submit", function (e) {
//         e.preventDefault();

//         let formData = {
//             name: $("input[name='name']").val(),
//             username: $("input[name='username']").val(),
//             email: $("input[name='email']").val(),
//             password: $("input[name='password']").val()
//         };

//         $.ajax({
//             url: "/bin/sony/register",
//             type: "POST",
//             data: formData,

//             success: function (response) {

//                 let res = (typeof response === "string")
//                     ? JSON.parse(response)
//                     : response;

//                 if (res.error) {
//                     alert(res.error);
//                     return;
//                 }

//                 if (res.status === "success") {
//                     alert("Registration Successful!");
//                     $("#registrationForm")[0].reset();
//                 }
//             },

//             error: function () {
//                 alert("Server Error");
//             }
//         });

//     });

// });