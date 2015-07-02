$(document).ready(function(){
  $('.loginForm').css({
    'margin-left': calcMarginLeft(),
    'margin-top': calcMarginTop()
  });
});


function calcMarginLeft(){
  return -$('.loginForm').width()/2;
}

function calcMarginTop(){
  return -$('.loginForm').height();
}

$(window).resize(function(){
  $('.loginForm').css({
    'margin-left': calcMarginLeft(),
    'margin-top': calcMarginTop()
  });
});
