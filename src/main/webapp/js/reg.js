$(document).ready(function(){
  $('.formReg').css({
    'margin-left': calcMarginLeft(),
    'margin-top': calcMarginTop()
  });
});

function calcMarginLeft(){
  return -$('.formReg').width()/2;
}

function calcMarginTop(){
  return -$('.formReg').height()/1.5;
}

$(window).resize(function(){
  $('.formReg').css({
    'margin-left': calcMarginLeft(),
    'margin-top': calcMarginTop()
  });
});
