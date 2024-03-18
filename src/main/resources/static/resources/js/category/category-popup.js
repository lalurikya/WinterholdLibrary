(function(){
    addCloseButtonListener();
    addInsertButtonListener();
    addUpdateButtonListener();
    addSubmitFormListener();
}())


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

function addInsertButtonListener(){
    $('.create-button').click(function(event){
        event.preventDefault();
        $('.modal-layer').addClass('modal-layer--opened');
        $('.form-dialog').addClass('popup-dialog--opened');
    });
};

function addUpdateButtonListener(){
    $('.update-button').click(function(event){
        event.preventDefault();
        let categoryName = $(this).attr('data-name');
        $.ajax({
            url: `/api/category/${categoryName}`,
            success: function(response){
                populateInputForm(response);
                $('.modal-layer').addClass('modal-layer--opened');
                $('.form-dialog').addClass('popup-dialog--opened');
            }
        })
    })
};

function populateInputForm({name, floor, isle, bay}){
    let nameInput = $('.form-dialog .name').val(name);
    let floorInput = $('.form-dialog .floor').val(floor);
    let isleInput = $('.form-dialog .isle').val(isle);
    let bayInput = $('.form-dialog .bay').val(bay);

    nameInput.prop('readonly', true)
};

function addSubmitFormListener(){
    $('.form-dialog button').click(function(event){
        event.preventDefault();
        let dto = collectInputForm();
        let requestMethod = (dto.name === null) ? 'POST' : 'PUT';
        $.ajax({
            method: requestMethod,
            url: '/api/category',
            data: JSON.stringify(dto),
            contentType: 'application/json',
            success: function(response){
                location.reload();
            },
            error: function({status, responseJSON}){
                if(status == 422){
                    writeValidationMessage(responseJSON);
                }
            }
        });
    });
};

function collectInputForm(){
    let name = $('.form-dialog .name').val();
    let dto = {
        name: (name === "") ? null : name,
        floor: $('.form-dialog .floor').val(),
        isle: $('.form-dialog .isle').val(),
        bay: $('.form-dialog .bay').val()
    };
    return dto;
};

function writeValidationMessage(errorMessages){
    for(let error of errorMessages){
        let {field, message} = error;
        $(`.form-dialog [data-for=${field}]`).text(message);
    }
}



}
