/**
 * - 352Query
 * - 1.0.0
 */
const $ = (selector) => {
  const obj = new _352Query();
  const elems = Array.from(document.querySelectorAll(selector));
  elems.forEach((elem, index) => {
    obj[index] = elem;
  });
  obj.length = elems.length;
  return obj;
}
/**
 * - 클래스는 생성자함수
 * - 클래스의 메소드는 prototype객체에 작성
 * 
 * _352Query.prototype.css = function(){}
 */
class _352Query {
  /**
   * this는 _352Query객체이다.
   */
  css(name, value){
    for(let i = 0; i < this.length; i++)
      this[i].style[name] = value;
    return this;
  }
  html(html) {
    // getter : 첫번째 요소의 값
    if(!html)
      return this[0].innerHTML;

    // setter : 모든 요소의 값을 변경
    for(let i = 0; i < this.length; i++)
      this[i].innerHTML = html;
    return this;
  }
  click() {

  }
  prop() {

  }
  filter(selector) {

  }
  eq(index) {

  }
  get(index) {

  }
  hover(f1, f2) {

  }
  
}