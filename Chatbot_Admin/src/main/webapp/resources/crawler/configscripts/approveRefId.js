// Get the <datalist> and <input> elements.
var dataList = document.getElementById('datalist');
var input = document.getElementById('ajax');

// Create a new XMLHttpRequest.
var request = new XMLHttpRequest();

// Handle state changes for the request.
request.onreadystatechange = function (response) {
    if (request.readyState === 4) {
        if (request.status === 200) {
            // Parse the JSON
            var jsonOptions = JSON.parse(request.responseText);

            // Loop over the JSON array.
            jsonOptions.forEach(function (item) {
                // Create a new <option> element.
                var option = document.createElement('option');
                // Set the value using the item in the JSON array.
                option.value = item;
                // Add the <option> element to the <datalist>.
                dataList.appendChild(option);
            });

            // Update the placeholder text.
            input.placeholder = "e.g. category";
        } else {
            // An error occured :(
            input.placeholder = "Couldn't load datalist options :(";
        }
    }
};

// Update the placeholder text.
input.placeholder = "Loading options...";

// Set up and make the request.
request.open('GET', 'AjaxServlet', true);
request.send();

//var submitVal = 0;
//var currentText
//function setVal(val) {
//    currentText = val;
//}
//
////$( "form" ).submit(function( e ) {
//$('#btn-submit').on('click',function(e){
//    //e.preventDefault();
//    var str = currentText;
//    alert(currentText);
//    var compareVal;
//    for (var i = 0; i < dataList.options.length; i++) {
//        if (dataList.options[i].value.indexOf('|') > -1) {
//            var str_arr = dataList.options[i].value.split('|');
//            compareVal = str_arr[str_arr.length - 1];
//        } else {
//            compareVal = dataList.options[i].value;
//        }
//        if (compareVal == str) {
//            submitVal = 1;
//            return;
//
//            //alert(x)
//        }
//    }
//    swal({
//        title: "Are you sure?",
//        text: "This configuration existed. Do you want to config again?",
//        type: "warning",
//        showCancelButton: true,
//        confirmButtonColor: "#DD6B55",
//        onfirmButtonText: "Yes, delete it!",
//        cancelButtonText: "No, cancel plx!",
//        closeOnConfirm: true,
//        closeOnCancel: false
//    },
//    function (isConfirm) {
//        if (isConfirm) {
//            submitVal = 1;
//            alert(submitVal);
//            //swal("Deleted!", "Your imaginary file has been deleted.", "success");
//        } else {
//            submitVal = 0;
//            alert("submit:" + submitVal);
//            swal("Cancelled", "Your imaginary file is safe :)", "error");
//            return false;
//        }
//    });
//})

function checkInput(form) {
    var str = form.txtRef.value;
    var compareVal;
    for (var i = 0; i < dataList.options.length; i++) {
        if (dataList.options[i].value.indexOf('|') > -1) {
            var str_arr =  dataList.options[i].value.split('|');
            compareVal = str_arr[str_arr.length - 1];
        } else {
            compareVal = dataList.options[i].value;
        }
        if (compareVal == str) {
            return true;

            //alert(x)
        }
    }
    if (confirm("This category didn't exist. Do you want to add new category?") == true) {
    } else {
        return false;
    }
}

