(function(){
    addSummaryButtonListener();
    addCloseButtonListener();
}())

function addSummaryButtonListener(){
    $('.summary-button').click(function(event){
        let bookCode = $(this).attr('data-code');
        $.ajax({
        url: `/api/book/summary/${bookCode}`,
        success: function({summary}){

            $('.summary-dialog .summary').text(summary);
            $('.modal-layer').addClass('modal-layer--opened');
            $('.summary-dialog').addClass('popup-dialog--opened');
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
    $('.popup-dialog .name').prop('readonly', false);
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
