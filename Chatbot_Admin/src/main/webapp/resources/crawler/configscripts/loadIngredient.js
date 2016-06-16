//$(document).ready(function () {
//    $('#load').click(function ()
//    {
//        $.ajax({
//            type: "POST",
//            url: "LoadIngredientServlet", //this is my servlet
//            data: "input=" + $('#recipeId').val(),
//            success: function (msg) {
//                $('#output').append(msg);
//            }
//        });
//    });
//
//});

function loadIngredient(recipeId)
{
    var xmlhttp;
    var content;
    var body = document.getElementById("ingredientBody");
    body.innerHTML = "";
    content = "";
    var url = "LoadIngredientServlet?id=" + recipeId;
    if (window.XMLHttpRequest)
    {
        xmlhttp = new XMLHttpRequest();
    }
    else
    {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function (response)
    {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
        {
            content = "<form action='UpdateMaterialServlet'>"
                    + "<input type='hidden' size='5' name='txtRecipeId' value='"
                    + recipeId + "'/>"
                    + "<table id ='example' class='table responsive-data-table data-table'>"
                    + "<thead><tr><th>No</th><th>Ingredient Name</th><th>Weight</th>"
                    + "</tr></thead><tbody>";
            // Parse the JSON
            var jsonOptions = JSON.parse(xmlhttp.responseText);
            // Loop over the JSON array.
            var count = 0, no = 0;
            var ingre;
            jsonOptions.forEach(function (item) {
                count++;
                if (count % 2 === 0) {
                    no++;
                    content = content + "<tr><td>" + no + "</td><td>"
                            + ingre + "</td><td>" + "<input type='text' size='5' name='txtWeight' value='"
                            + item + "'/>" + "</td></tr>";
                }
                else {
                    ingre = item;
                }
            });
            body.innerHTML = content + "</tbody></table></form>"
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}