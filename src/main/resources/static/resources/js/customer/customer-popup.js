(function(){
    addInfoButtonListener();
    addCloseButtonListener();
}())

function addInfoButtonListener() {
    $('.info-button').click(function (event) {
        let membershipNumber = $(this).attr('data-membershipNumber');
        $.ajax({
            url: `/api/customer/info/${membershipNumber}`,
            success: function ({ membershipNumber, fullName, birthDate, gender, phone, address }) {
                $('.info-dialog .membershipNumber').text(membershipNumber);
                $('.info-dialog .fullName').text(fullName);
                $('.info-dialog .birthDate').text(new Date(birthDate).toLocaleDateString('en-US'));
                $('.info-dialog .gender').text(gender);
                $('.info-dialog .phone').text(phone);
                $('.info-dialog .address').text(address);
                $('.modal-layer').addClass('modal-layer--opened');
                $('.info-dialog').addClass('popup-dialog--opened');
            }
        });
    });
}


function closeModal(){
    $(".modal-layer").removeClass("modal-layer--opened"); //close modal
    $(".popup-dialog").removeClass("popup-dialog--opened"); //close dialog
    $('.popup-dialog input').val("");
    $('.popup-dialog textarea').val("");
    $('.popup-dialog .validation-message').text("");
};

function addCloseButtonListener(){
// Close modal when ESC key is pressed
    $(document).keydown(function (event) {
            if (event.key === "Escape") {
                closeModal();
            }
        });

// Close modal when close button is clicked
    $('.close-button').click(function (event) {
        closeModal();
    });
};