$(document).ready(function(){
    $('#nav-here').load('navbar.html');
});

$(document).ready(function(){
    $('#nav-here2').load('navbar2.html');
});

$('.panel-heading a').on('click',function(e){
    if($(this).parents('.panel').children('.panel-collapse').hasClass('in')){
        e.stopPropagation();
    }
    // You can also add preventDefault to remove the anchor behavior that makes
    // the page jump
    // e.preventDefault();
});
