$( document ).ready(function() {
    $('#sorting-hat').modal('toggle');
    setTimeout(function () {
        $('#sorting-hat').modal('toggle');
        $('#resultado').css({'display' : ''})
    }, 5000)
})