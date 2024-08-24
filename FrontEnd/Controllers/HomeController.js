/**
 * @author : Kavithma Thushal
 * @project : Spring-Boot-POS
 * @since : 7:05 AM - 6/18/2024
 **/

function getCustomerCount() {
    $.ajax({
        url: customerUrl + "/getCustomerCount",
        method: "GET",
        success: function (res) {
            $("#customerCount").text(res.data);
            console.log(res.message);
        },
        error: function (error) {
            console.log(error.responseJSON.message);
        }
    });
}