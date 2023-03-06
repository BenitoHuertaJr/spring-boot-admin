$(function(){

  var headerHight = $('#navbar').outerHeight();

   $('.menu').click(function(e) {

    var linkhref = $(this).attr('href');

    $('html, body').animate({
      scrollTop: $(linkhref).offset().top-headerHight
    }, 1000);
    
    e.preventDefault();

  });

});