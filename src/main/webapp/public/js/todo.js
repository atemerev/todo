function onAddButtonPress() {
    var text = $('input').val();
    jQuery.ajax({
        type: 'POST',
        url: '/api/task',
        data: { 'text' : text },
        success: function(response) {
            var dom = $(response);
            $('ul').append(dom);
        },
        error: function(response) {
            "Error happened: " + response;
        },
        dataType: 'html'
    });
}

function onDoneButtonPress(target) {
    var id = event.target.id.substr(1);
    jQuery.ajax({
        type: 'POST',
        url: '/api/done',
        data: { 'id' : id },
        success: function(response) {
            $('li#l' + id).remove();
        },
        error: function(response) {
            "Error happened: " + response;
        }
    });
}

$(document).ready(function() {
    $('button#add').bind('click', onAddButtonPress);
    $('button.done').live('click', onDoneButtonPress);
});
