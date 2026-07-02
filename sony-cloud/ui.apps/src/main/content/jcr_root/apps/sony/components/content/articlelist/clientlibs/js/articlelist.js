$(document).ready(function () {

    $.ajax({

      
      url: "/content/sony/us/en/jcr:content/root/container/articlelist.json",

        type: "GET",

        success: function (data) {

            let html = "";

            data.forEach(function (page) {

                html += `
                    <div class="card">

                        <h2>${page.title}</h2>

                        <p><b>Name:</b> ${page.name}</p>

                        <p><b>Path:</b> ${page.path}</p>

                        <p><b>Description:</b> ${page.description}</p>

                    </div>
                `;
            });

            $("#page-data").html(html);
        },

        error: function (xhr) {

            console.log(xhr.responseText);

            $("#page-data").html("Error Loading Data");
        }

    });

});