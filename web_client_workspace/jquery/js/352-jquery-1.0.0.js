// $ 확장하는 라이브러리 - 352 jQuery

$.fn.extend({
  color(color){
    // this는 현재 jQuery객체이다.
    this.css('color', color);
    return this;
  }, 
  border(color = '#000', width = '1px', type = 'solid') {
    this.css('border', `${width} ${type} ${color}`);
    return this;
  }
});