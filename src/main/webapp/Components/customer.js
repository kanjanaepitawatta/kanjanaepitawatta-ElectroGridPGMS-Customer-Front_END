$(document).ready(function() {
    if ($('#alertSuccess').text().trim() == "") {
        $('#alertSuccess').hide();
    }

    $('#alertError').hide();
})

// SAVE
$(document).on("click","#btnSave", function(event) {
    // Clear alerts
    $("#alertSuccess").text(""); 
    $("#alertSuccess").hide(); 
    $("#alertError").text(""); 
    $("#alertError").hide();

    // Form validation
    var status = validateCustomerForm(); 
    if (status != true) 
    { 
        $("#alertError").text(status); 
        $("#alertError").show(); 
        return; 
    } 

    // if hidItemIDSave value is null set as POST else set as PUT
    var type = ($("#hidCustomerIDSave").val() == "") ? "POST" : "PUT";

    // ajax communication
    $.ajax({
        url: "CustomerAPI",
        type: type,
        data: $("#formCustomer").serialize(),
        dataType: "text",
        complete: function(response, status) {
            onCustomerSaveComplete(response.responseText, status);
        }
    });
});

// after completing save request
function onCustomerSaveComplete(response, status) {

    if (status == "success") { //if the response status is success
        var resultSet = JSON.parse(response);

        if (resultSet.status.trim() === "success") { //if the json status is success
            //display success alert
            $("#alertSuccess").text("Successfully saved");
            $("#alertSuccess").show();
    
            //load data in json to html
            $("#divCustomerGrid").html(resultSet.data);

        } else if (resultSet.status.trim() === "error") { //if the json status is error
            //display error alert
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") { 
        //if the response status is error
        $("#alertError").text("Error while saving");
        $("#alertError").show();
    } else { 
        //if an unknown error occurred
        $("#alertError").text("Unknown error occurred while saving");
        $("#alertError").show();
    } 

    //resetting the form
    $("#hidCustomerIDSave").val("");
    $("#formCustomer")[0].reset();
}

// UPDATE
//to identify the update button we didn't use an id we used a class
$(document).on("click", ".btnUpdate", function(event) 
{ 
    //get item id from the data-itemid attribute in update button
    $("#hidCustomerIDSave").val($(this).data('userid')); 
    //get data from <td> element
    $("#firstName").val($(this).closest("tr").find('td:eq(1)').text()); 
    $("#lastName").val($(this).closest("tr").find('td:eq(2)').text()); 
    $("#email").val($(this).closest("tr").find('td:eq(3)').text()); 
    $("#mobile").val($(this).closest("tr").find('td:eq(4)').text()); 
    $("#address").val($(this).closest("tr").find('td:eq(5)').text()); 
    $("#postalCode").val($(this).closest("tr").find('td:eq(6)').text()); 
   
}); 

// DELETE
$(document).on("click",".btnRemove", function(event) {
    // ajax communication
    $.ajax({
        url: "CustomerAPI",
        type: "DELETE",
        data: "userID=" + $(this).data("userid"),
        dataType: "text",
        complete: function(response, status) {
            onCustomerDeleteComplete(response.responseText, status);
        }
    });
});

// after completing delete request
function onCustomerDeleteComplete(response, status) {

    if (status == "success") { //if the response status is success
        var resultSet = JSON.parse(response);

        if (resultSet.status.trim() === "success") { //if the json status is success
            //display success alert
            $("#alertSuccess").text("Successfully deleted");
            $("#alertSuccess").show();
    
            //load data in json to html
            $("#divCustomerGrid").html(resultSet.data);

        } else if (resultSet.status.trim() === "error") { //if the json status is error
            //display error alert
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") { 
        //if the response status is error
        $("#alertError").text("Error while deleting");
        $("#alertError").show();
    } else { 
        //if an unknown error occurred
        $("#alertError").text("Unknown error occurred while deleting");
        $("#alertError").show();
    } 
}

// VALIDATION
function validateCustomerForm() { 
    // CODE 
    if ($("#firstName").val().trim() == "") 
    { 
        return "Insert First Name."; 
    } 
    
    // NAME 
    if ($("#lastName").val().trim() == "") 
    { 
        return "Insert Last Name."; 
    } 
    
    // PRICE
    if ($("#email").val().trim() == "") 
    { 
        return "Insert Email."; 
    } 
    
    // PRICE
    if ($("#mobile").val().trim() == "") 
    { 
        return "Insert Mobile."; 
    }
    
    // PRICE
    if ($("#address").val().trim() == "") 
    { 
        return "Insert Address."; 
    }
    // PRICE
    if ($("#postalCode").val().trim() == "") 
    { 
        return "Insert Postal Code."; 
    }
    
    return true; 
} 
 